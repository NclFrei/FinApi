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
        repository.add(conta);
        return conta;
    }


}
