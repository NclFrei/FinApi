package br.com.fiap.fin_api.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_api.model.Conta;
import br.com.fiap.fin_api.model.TipoConta;


@RestController
public class ContaController {

    private List<Conta> repository = new ArrayList<>();

    @PostMapping("/contas")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Conta create(@RequestBody Conta conta) {
        System.out.println("Cadastrando conta do " + conta.getNomeTitular());
        validarConta(conta);
        repository.add(conta);
        return conta;
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
