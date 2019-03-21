package com.diego.agenda.principal;

import java.sql.*;
import java.util.ArrayList;

public class BancoDados {
    static boolean isConectado;
    private static Connection conexao;
    private static Statement statement;

    public static Contato carregarContato(int id){ //carregar atraves da ordem em que foi adicionado.
        if(!isConectado){
            conectar();
        }
        Contato novoContato = new Contato(id, "", "", null, "", "fotoNula.png");

        String comando = "SELECT * FROM Contatos where idBD=" + id;

        try {
            ResultSet rs = statement.executeQuery(comando);
            while(rs.next()){
                novoContato.setNome(rs.getString(2));
                novoContato.setNumeroPrincipal(Util.formatarNumero(rs.getString(3)));
                novoContato.setEmail(rs.getString(5));
                novoContato.setFoto(rs.getString(6));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        //Carregar do banco meu contato
        //Contato novoContato = new Contato(id, "Diego Mendes","40028922", null, "diegomendes1998@gmail.com", "fotoNula.png");

        return novoContato;
    }

    public static ArrayList<Carta> carregarCartas(){
        ArrayList<Carta> cartas = new ArrayList<Carta>();
        String comando = "SELECT idBD, Nome, Foto FROM Contatos";

        try {
            ResultSet rs = statement.executeQuery(comando);
            while(rs.next()){
                Carta novaCarta = new Carta(-1, "", "");
                novaCarta.idBD = rs.getInt(1);
                novaCarta.nomeCarta = rs.getString(2);
                novaCarta.foto = rs.getString(3);

                cartas.add(novaCarta);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return cartas;
    }

    public static void adicionarContato(Contato contato){
        //Cria ou atualiza contato.
    }

    public static void editarContato(Contato contato){

    }

    public static int gerarNovoIdBD(){
        return 0;
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
