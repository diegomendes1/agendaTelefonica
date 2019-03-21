package com.diego.agenda.ui;

import com.diego.agenda.principal.BancoDados;
import com.diego.agenda.principal.Carta;
import com.diego.agenda.principal.Contato;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class PrincipalController {
    private Contato contatoAberto;
    private File imgTEMP;

    //Lista de Contatos
    @FXML
    public VBox container;

    //Contato
    @FXML
    public AnchorPane painelAdicionarContato;
    @FXML
    public AnchorPane painelEditarContato;
    @FXML
    public Button editarContatoBotao;

    @FXML
    public Button fotoContatoBotao;

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


        mostrarContato(1); //Contato inicial
        desenharTodasCartas();
    }

    public void desenharTodasCartas(){
        ArrayList<Carta> cartas = BancoDados.carregarCartas();

        container.getChildren().clear();
        for(Carta carta : cartas){
            //Desenhar Carta
            Button novoBotao = new Button(carta.nomeCarta);
            //novoBotao.minHeight(60);
            //novoBotao.prefWidth(230);
            //novoBotao.prefHeight(60);
            container.getChildren().add(novoBotao);


            Image img = new Image(getClass().getResourceAsStream("/com/diego/agenda/resources/" + carta.foto));

            ImageView novaFoto = new ImageView(img);
            novaFoto.setFitWidth(50);
            novaFoto.setFitHeight(50);

            novoBotao.setGraphic(novaFoto);
        }
    }

    public void mostrarContato(int idBD){
        travarFields();

        Contato contato = BancoDados.carregarContato(idBD);
        Image imgArquivo = new Image(getClass().getResourceAsStream("/com/diego/agenda/resources/" + contato.getFoto()));
        fotoContato.setImage(imgArquivo);

        fotoContato.setFitHeight(70);
        fotoContato.setFitWidth(70);

        Rectangle clip = new Rectangle(70, 70);
        clip.setArcHeight(20);
        clip.setArcWidth(20);
        fotoContato.setClip(clip);

        nomeContato.setText(contato.getNome());
        numPrincipalContato.setText(contato.getNumeroPrincipal());
        outrosNumerosContato.setText("Vazio");
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
        liberarFields();
    }

    @FXML
    public void concluirAdicionarBotao(){
        int novoIdBD = BancoDados.gerarNovoIdBD();
        Contato contato = new Contato(novoIdBD, nomeContato.getText(), numPrincipalContato.getText(), null, emailContato.getText(), "fotoNula.png");

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
        try{
            contatoAberto.setNome(nomeContato.getText());
            contatoAberto.setNumeroPrincipal(numPrincipalContato.getText());
            contatoAberto.setNumerosSecundarios(null);
            contatoAberto.setEmail(emailContato.getText());

            if(imgTEMP != null){
                Path src = Paths.get(imgTEMP.getPath());
                Path dest = Paths.get("src/com/diego/agenda/resources/" + imgTEMP.getName());
                Files.copy(src.toAbsolutePath(), dest.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
                contatoAberto.setFoto(imgTEMP.getName());
            }else {
                contatoAberto.setFoto("fotoNula.png");
            }

            BancoDados.editarContato(contatoAberto);
            mostrarContato(contatoAberto.idBD);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void travarFields(){
        editarContatoBotao.setVisible(true);

        painelEditarContato.setVisible(false);
        painelAdicionarContato.setVisible(false);

        nomeContato.setEditable(false);
        numPrincipalContato.setEditable(false);
        outrosNumerosContato.setEditable(false);
        emailContato.setEditable(false);
        fotoContatoBotao.setMouseTransparent(true);
    }

    public void liberarFields(){
        editarContatoBotao.setVisible(false);

        nomeContato.setEditable(true);
        numPrincipalContato.setEditable(true);
        outrosNumerosContato.setEditable(true);
        emailContato.setEditable(true);
        fotoContatoBotao.setMouseTransparent(false);
    }

    public void limparFields(){
        nomeContato.setText("");
        numPrincipalContato.setText("");
        outrosNumerosContato.setText("");
        emailContato.setText("");

        Image imgArquivo = new Image(getClass().getResourceAsStream("/com/diego/agenda/resources/fotoNula.png"));
        fotoContato.setImage(imgArquivo);

        //contatoAberto = null;
    }

    @FXML
    public void escolherImgBotao(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma Imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg"));

        File foto = fileChooser.showOpenDialog(fotoContato.getScene().getWindow());

        if(foto != null){
            imgTEMP = foto;
        }
    }
}
