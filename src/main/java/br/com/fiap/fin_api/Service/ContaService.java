package br.com.fiap.fin_api.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_api.model.Conta;

@Service
public class ContaService {

    private final List<Conta> repository;

    public ContaService() {
        this.repository = new ArrayList<>();
    }

    public Conta create(Conta conta) {
        validarConta(conta);
        repository.add(conta);
        return conta;
    }

    public List<Conta> index() {
        return repository;
    }

    public Conta get(Long id) {
        return getConta(id);
    }

    public Conta getByCpf(String cpf) {
        return getContaCPF(cpf);
    }

    public Conta depositar(Long id, Double valor) {
        var contaUpdate = getConta(id);
        contaUpdate.setSaldo(contaUpdate.getSaldo() + valor);
        return contaUpdate;
    }

    public Conta sacar(Long id, Double valor) {
        var contaUpdate = getConta(id);
        contaUpdate.setSaldo(contaUpdate.getSaldo() - valor);
        return contaUpdate;
    }

    public void realizarPix(Long contaOrigemId, Long contaDestinoId, Double valor) {
        Conta contaOrigem = getConta(contaOrigemId);
        Conta contaDestino = getConta(contaDestinoId);
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
    }

    private Conta getConta(Long id) {
        return repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }

    private Conta getContaCPF(String cpf) {
        return repository.stream()
                .filter(c -> c.getCpfTitular().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }

    private void validarConta(Conta conta) {
        if (conta.getSaldo() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O saldo da conta não pode ser negativo!");
        }
        validarCampo(conta.getNomeTitular(), "O nome do titular não pode estar vazio!");
        validarCampo(conta.getCpfTitular(), "O CPF do titular não pode estar vazio!");
    }

    private void validarCampo(String campo, String mensagemErro) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagemErro);
        }
    }
}
