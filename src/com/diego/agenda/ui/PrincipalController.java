package com.diego.agenda.ui;

import com.diego.agenda.principal.BancoDados;
import com.diego.agenda.principal.Carta;
import com.diego.agenda.principal.Contato;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class PrincipalController {
    private Contato contatoAberto;

    //Lista de Contatos
    @FXML
    public VBox container;

    //Contato
    @FXML
    public AnchorPane painelAdicionarContato;
    @FXML
    public Button adicionarContatoBotao;
    @FXML
    public AnchorPane painelEditarContato;
    @FXML
    public Button editarContatoBotao;
    @FXML
    public Button deletarContatoBotao;

    @FXML
    public TextField nomeContato;
    @FXML
    public TextField numPrincipalContato;
    @FXML
    public TextField outroNumeroContato;
    @FXML
    public TextField emailContato;

    public PrincipalController(){}

    @FXML
    public void initialize(){
        mostrarContato(1); //Contato inicial
        desenharTodasCartas();
    }

    public void desenharTodasCartas(){
        ArrayList<Carta> cartas = BancoDados.carregarCartas();

        container.getChildren().clear();
        for(Carta carta : cartas){
            //Desenhar Carta
            Button novoBotao = new Button(carta.nomeCarta);
            novoBotao.setMinHeight(60);
            novoBotao.setPrefWidth(230);
            novoBotao.setPrefHeight(60);

            Font fonte = new Font(17);
            novoBotao.setFont(fonte);
            container.getChildren().add(novoBotao);

            novoBotao.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mostrarContato(carta.idBD);
                }
            });
        }
    }

    public void mostrarContato(int idBD){
        travarFields();

        Contato contato = BancoDados.carregarContato(idBD);

        nomeContato.setText(contato.getNome());
        numPrincipalContato.setText(contato.getNumeroPrincipal());
        outroNumeroContato.setText(contato.getNumeroSecundario());
        emailContato.setText(contato.getEmail());

        contatoAberto = contato;
    }

    @FXML
    public void mostrarMeuContatoBotao(){
        mostrarContato(1);
    }

    @FXML
    public void adicionarContatoBotao(){
        limparFields();
        painelAdicionarContato.setVisible(true);
        adicionarContatoBotao.setDisable(false);
        liberarFields();
    }

    @FXML
    public void concluirAdicionarBotao(){
        int novoIdBD = BancoDados.gerarNovoIdBD();
        Contato contato = new Contato(novoIdBD, nomeContato.getText(),
                numPrincipalContato.getText(), null, emailContato.getText());

        if(outroNumeroContato.getText() == null)
            contato.setNumeroSecundario("");
        else
            contato.setNumeroSecundario(outroNumeroContato.getText());

        BancoDados.adicionarContato(contato);
        mostrarContato(novoIdBD);
        desenharTodasCartas();
    }

    @FXML
    public void cancelarAdicionarBotao(){
        mostrarContato(contatoAberto.idBD);
    }

    @FXML
    public void editarContatoBotao(){
        painelEditarContato.setVisible(true);
        liberarFields();
    }

    @FXML
    public void cancelarEditarBotao(){
        mostrarContato(contatoAberto.idBD);
    }

    @FXML
    public void concluirEditarBotao(){
        contatoAberto.setNome(nomeContato.getText());
        contatoAberto.setNumeroPrincipal(numPrincipalContato.getText());

        if(outroNumeroContato.getText() == null)
            contatoAberto.setNumeroSecundario("");
        else
            contatoAberto.setNumeroSecundario(outroNumeroContato.getText());

        contatoAberto.setEmail(emailContato.getText());
        BancoDados.editarContato(contatoAberto);
        mostrarContato(contatoAberto.idBD);
        desenharTodasCartas();
    }

    public void travarFields(){
        editarContatoBotao.setVisible(true);
        painelEditarContato.setVisible(false);
        painelAdicionarContato.setVisible(false);
        deletarContatoBotao.setVisible(true);

        nomeContato.setEditable(false);
        numPrincipalContato.setEditable(false);
        outroNumeroContato.setEditable(false);
        emailContato.setEditable(false);
    }

    public void liberarFields(){
        editarContatoBotao.setVisible(false);
        deletarContatoBotao.setVisible(false);

        nomeContato.setEditable(true);
        numPrincipalContato.setEditable(true);
        outroNumeroContato.setEditable(true);
        emailContato.setEditable(true);
    }

    public void limparFields(){
        nomeContato.setText("");
        numPrincipalContato.setText("");
        outroNumeroContato.setText("");
        emailContato.setText("");
    }

    @FXML
    public void deletarContato(){
        BancoDados.deletarContato(contatoAberto.idBD);
        mostrarMeuContatoBotao();
        desenharTodasCartas();
    }
}
