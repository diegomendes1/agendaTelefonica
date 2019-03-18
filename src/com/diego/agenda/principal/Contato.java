package com.diego.agenda.principal;

import java.util.ArrayList;

public class Contato {
    private String nome;
    private String numeroPrincipal;
    private ArrayList<String> numerosSecundarios;
    private String email;
    private String foto;

    public Contato(String nome, String numeroPrincipal, ArrayList<String> numerosSecundarios, String email, String foto){
        this.nome = nome;
        this.numeroPrincipal = numeroPrincipal;
        this.numerosSecundarios = numerosSecundarios;
        this.email = email;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public ArrayList<String> getNumerosSecundarios() {
        return numerosSecundarios;
    }

    public void setNumerosSecundarios(ArrayList<String> numerosSecundarios) {
        this.numerosSecundarios = numerosSecundarios;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
