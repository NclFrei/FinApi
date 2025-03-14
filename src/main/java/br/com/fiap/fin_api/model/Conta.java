package br.com.fiap.fin_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class Conta {
    private Long id;
    private String numero;
    private String nomeTitular;
    private String agencia;
    private String cpfTitular;
    private LocalDateTime dataAbertura;
    private Double saldo;
    private boolean ativa;;
    private TipoConta tipo;


    public Conta(String numero, String agencia, String nomeTitular, String cpfTitular, LocalDateTime dataAbertura, Double saldo, TipoConta tipo) {
        this.id = Math.abs(new Random().nextLong());
        this.numero = numero;
        this.agencia = agencia;
        this.nomeTitular = nomeTitular;
        this.cpfTitular = cpfTitular;
        this.dataAbertura = LocalDateTime.now();
        this.saldo = saldo;
        this.ativa = true; 
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public Double getSaldo() {
        return saldo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public TipoConta getTipo() {
        return tipo;
    }


}
