package br.com.fiap.fin_api.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_api.Request.PixRequest;
import br.com.fiap.fin_api.Service.ContaService;
import br.com.fiap.fin_api.model.Conta;


@RestController
@RequestMapping("contas")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Conta create(@RequestBody Conta conta) {
        return contaService.create(conta);
    }

    @GetMapping
    public List<Conta> index() {
        return contaService.index();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Conta> get(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.get(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Conta> get(@PathVariable String cpf) {
        return ResponseEntity.ok(contaService.getByCpf(cpf));
    }

    @PatchMapping("/encerrarConta/{id}")
    public ResponseEntity<Conta> update(@PathVariable Long id, @RequestBody Conta conta) {

        Conta contaUpdate = contaService.get(id);
        contaUpdate.setAtiva(false);
        return ResponseEntity.ok(contaUpdate);
    }

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestBody Conta conta) {
        if (conta.getValor() == null || conta.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do depósito deve ser positivo.");
        }
        return ResponseEntity.ok(contaService.depositar(id, conta.getValor()));
    }

    @PatchMapping("/{id}/saque")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestBody Conta conta) {
        if (conta.getValor() == null || conta.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do saque deve ser positivo.");
        }
        return ResponseEntity.ok(contaService.sacar(id, conta.getValor()));
    }

    @PostMapping("/pix")
    public ResponseEntity<String> realizarPix(@RequestBody PixRequest pixRequest) {
        if (pixRequest.getValor() == null || pixRequest.getValor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do PIX deve ser positivo.");
        }
        contaService.realizarPix(pixRequest.getContaOrigemId(), pixRequest.getContaDestinoId(), pixRequest.getValor());
        return ResponseEntity.ok("Transferência PIX realizada com sucesso!");
    }

}
