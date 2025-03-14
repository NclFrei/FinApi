package br.com.fiap.fin_api.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale.Category;

import javax.print.DocFlavor.STRING;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_api.model.Conta;
import br.com.fiap.fin_api.model.TipoConta;


@RestController
@RequestMapping("contas")
public class ContaController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<Conta> repository = new ArrayList<>();

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Conta create(@RequestBody Conta conta) {
        System.out.println("Cadastrando conta do " + conta.getNomeTitular());
        validarConta(conta);
        repository.add(conta);
        return conta;
    }

    @GetMapping
    public List<Conta> index() {
        return repository;
    }

    @GetMapping("{id}")
    public ResponseEntity<Conta> get(@PathVariable Long id) {
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getConta(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Conta> get(@PathVariable String cpf) {
        log.info("Buscando categoria " + cpf);
        return ResponseEntity.ok(getContaCPF(cpf));
    }

    private Conta getConta(Long id) {
        return repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada")
                );
    }

    private Conta getContaCPF(String cpf) {
        return repository.stream()
                .filter(c -> c.getCpfTitular().equals(cpf))
                .findFirst()
                .orElseThrow(() -> 
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada")
                );
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

    private boolean isTipoValido(TipoConta tipo) {
        return tipo != null && EnumSet.allOf(TipoConta.class).contains(tipo);
    }

}
