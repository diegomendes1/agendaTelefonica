package com.diego.agenda.ui;

import com.diego.agenda.principal.BancoDados;
import com.diego.agenda.principal.Contato;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PrincipalController {
    //Contato
    @FXML
    public AnchorPane painelEdicaoContato;
    @FXML
    public ImageView fotoContato;

    @FXML
    public TextField nomeContato;

    @FXML
    public TextField numPrincipalContato;

    @FXML
    public TextField outrosNumerosContato;

    @FXML
    public TextField emailContato;

    public PrincipalController(){

    }

    @FXML
    public void initialize(){
        mostrarContatoInicial();
    }

    public void mostrarContatoInicial(){
        travarFields();
        System.out.println("Mostrando Contato Inicial");
        Contato contatoInicial = BancoDados.carregarMeuContato();
        mostrarInfoContato(contatoInicial);
    }

    public void mostrarInfoContato(Contato contato){
        if(contato.getFoto().equals("null")){
            Image imgArquivo = new Image(getClass().getResourceAsStream("/com/diego/agenda/resources/fotoNula.png"));
            fotoContato.setImage(imgArquivo);
        }

        nomeContato.setText(contato.getNome());
        numPrincipalContato.setText(contato.getNumeroPrincipal());
        outrosNumerosContato.setText("Vazio");
        emailContato.setText(contato.getEmail());
    }

    @FXML
    public void mostrarMeuContatoBotao(){
        mostrarContatoInicial();
    }

    @FXML
    public void novoContatoBotao(){
        liberarFields();

    }

    @FXML
    public void concluirBotao(){
        travarFields();
        Contato contato = new Contato(nomeContato.getText(), numPrincipalContato.getText(), null, emailContato.getText(), "fotoNula");

    }

    public void travarFields(){
        painelEdicaoContato.setVisible(false);
    }

    public void liberarFields(){
        painelEdicaoContato.setVisible(true);
    }
}
