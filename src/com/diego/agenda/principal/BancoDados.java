package com.diego.agenda.principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoDados {
    static boolean isConectado;
    private static Connection conexao;
    private static Statement statement;

    public static Contato carregarMeuContato(){
        if(!isConectado){
            conectar();
        }

        //Carregar do banco meu contato
        Contato novoContato = new Contato("Diego Mendes","40028922", null, "diegomendes1998@gmail.com", "null");

        return novoContato;
    }

    public static void AtualizarContato(Contato contato){
        //Cria ou atualiza contato.
    }

    private static void conectar(){
        try{
            conexao = DriverManager.getConnection("jdbc:sqlite:banco.db");
            statement = conexao.createStatement();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
