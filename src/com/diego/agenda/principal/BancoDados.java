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
        Contato novoContato = new Contato(id, "", "", "", "");

        String comando = "SELECT * FROM Contatos where idBD=" + id;

        try {
            ResultSet rs = statement.executeQuery(comando);
            while(rs.next()){
                novoContato.setNome(rs.getString(2));
                novoContato.setNumeroPrincipal(Util.formatarNumero(rs.getString(3)));
                novoContato.setNumeroSecundario(Util.formatarNumero(rs.getString(4)));
                novoContato.setEmail(rs.getString(5));
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
        String comando = "SELECT idBD, Nome FROM Contatos";

        try {
            ResultSet rs = statement.executeQuery(comando);
            while(rs.next()){
                Carta novaCarta = new Carta(-1, "");
                novaCarta.idBD = rs.getInt(1);
                novaCarta.nomeCarta = rs.getString(2);

                cartas.add(novaCarta);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return cartas;
    }

    public static void adicionarContato(Contato contato){
        //Cria ou atualiza contato.
        String comando = "INSERT INTO Contatos (idBD, Nome, NumeroPrincipal, NumeroSecundario, OutrosNumeros, Email) VALUES(?,?,?,?,?,?)";

        try{
            PreparedStatement statement = conexao.prepareStatement(comando);
            statement.setInt(1, contato.idBD);
            statement.setString(2, contato.getNome());
            statement.setString(3, contato.getNumeroPrincipal());
            statement.setString(4, contato.getNumeroSecundario());
            statement.setString(5, contato.getEmail());

            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void editarContato(Contato contato){
        String comando = "UPDATE Contatos SET Nome=?, NumeroPrincipal=?, NumeroSecundario=?, OutrosNumeros=?, Email=? where idBD=?";

        try{
            PreparedStatement pstmt = conexao.prepareStatement(comando);
            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getNumeroPrincipal());
            pstmt.setString(3, contato.getNumeroSecundario());
            pstmt.setString(4, contato.getEmail());
            pstmt.setInt(6, contato.idBD);

            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static int gerarNovoIdBD(){
        String comando = "SELECT COUNT(*) AS numContatos FROM Contatos";

        try{
            ResultSet rs = statement.executeQuery(comando);
            rs.next();
            int numContatos = rs.getInt("numContatos");

            return numContatos+1;
        }catch(SQLException e){
            e.printStackTrace();
        }
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
