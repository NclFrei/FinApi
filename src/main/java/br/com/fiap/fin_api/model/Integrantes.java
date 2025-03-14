package br.com.fiap.fin_api.model;

public class Integrantes {
    private String nome;
    private String rm;


    public String getNome() {
        return nome;
    }


    public String getRm() {
        return rm;
    }


    public Integrantes(String nome, String rm) {
        this.nome = nome;
        this.rm = rm;
    }

}
