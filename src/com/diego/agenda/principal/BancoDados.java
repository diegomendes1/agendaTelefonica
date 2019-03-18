package com.diego.agenda.principal;

public class BancoDados {
    public static Contato carregarMeuContato(){
        //Carregar do banco meu contato
        Contato novoContato = new Contato("Diego Mendes","40028922", null, "diegomendes1998@gmail.com", null);

        return novoContato;
    }
}
