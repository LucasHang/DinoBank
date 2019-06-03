/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.controller;


import br.senai.sc.dinobank.MeuAlerta;
import br.senai.sc.dinobank.dao.DAOFactory;
import br.senai.sc.dinobank.model.Transacao;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Senai
 */
public class SceneTransacaoWindowController implements Initializable {

    @FXML
    private Button btnNovaTransacao;
    @FXML
    private ComboBox<String> comboAcao;
    @FXML
    private TableView<Transacao> tblTransacoes;
    @FXML
    private TextField txtContaOrigem;
    @FXML
    private TextField txtContaDestino;
    @FXML
    private TextField txtValor;
    @FXML
    private Button btnCarregar;
    @FXML
    private TextField txtCarregar;
    @FXML
    private Label labelContaDestino;
    @FXML
    private Button btnExecutar;

    
    Transacao transacaoSelecionada = null;
    Transacao novaTransacao = null;
    
    MeuAlerta alerta = new MeuAlerta();
    
    Boolean invalido = null;
    
    List<String> acoes = Arrays.asList("Deposíto", "Saque", "Transferência");
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        comboAcao.setItems(FXCollections.observableArrayList(acoes));
    }    

    @FXML
    private void btnNovaTransacaoOnAction(ActionEvent event) {
        
        if(novaTransacao == null){
            unbindFields(transacaoSelecionada);
            novaTransacao = new Transacao();
            tblTransacoes.getItems().add(novaTransacao);
            bindFields(novaTransacao);
        }
    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
    }
    
    @FXML
    private void comboAcaoOnAction(ActionEvent event) {
        acaoTransferencia();
    }

    @FXML
    private void btnExecutarOnAction(ActionEvent event) {
        
        if(validationForm()){
            return;
        }
        
        txtContaOrigem.getStyleClass().remove("no-validation");
        txtValor.getStyleClass().remove("no-validation");
        txtContaDestino.getStyleClass().remove("no-validation");
        comboAcao.getStyleClass().remove("no-validation");
        

        unbindFields(novaTransacao);
        unbindFields(transacaoSelecionada);
        try {
            if (novaTransacao != null) {
                novaTransacao.setData(pegaData());
                DAOFactory.getTransacaoDAO().save(novaTransacao);
                novaTransacao = null;
            } else {
                transacaoSelecionada.setData(pegaData());
                DAOFactory.getTransacaoDAO().update(transacaoSelecionada);
                
            }
            limparFields();
        } catch (SQLException ex) {
            Logger.getLogger(SceneClienteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }
    
    private void bindFields(Transacao transacao){
        txtContaOrigem.textProperty().bindBidirectional(transacao.numContaOrigemProperty());
        if(acaoTransferencia()){
            txtContaDestino.textProperty().bindBidirectional(transacao.numContaDestinoProperty());
        }
        txtValor.textProperty().bindBidirectional(transacao.valorProperty(), new NumberStringConverter());
        comboAcao.accessibleTextProperty().bindBidirectional(transacao.acaoProperty());
        
    }

    private void unbindFields(Transacao transacao){
        txtContaOrigem.textProperty().unbindBidirectional(transacao.numContaOrigemProperty());
        if(acaoTransferencia()){
            txtContaDestino.textProperty().unbindBidirectional(transacao.numContaDestinoProperty());
        }
        txtValor.textProperty().unbindBidirectional(transacao.valorProperty());
        comboAcao.accessibleTextProperty().unbindBidirectional(transacao.acaoProperty());
    }
    
    private Boolean acaoTransferencia(){
        if(comboAcao.getValue().equals("Transferência")){
            txtContaDestino.setVisible(true);
            labelContaDestino.setVisible(true);
            return true;
        }
        txtContaDestino.setVisible(false);
        labelContaDestino.setVisible(false);
        return false;
    }
    
    private Integer pegaData(){
        Integer data = null;
        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat();
        data = parseInt(dataFormat.format(date));
        return data;
    }
    
    private void limparFields(){
        txtContaOrigem.clear();
        txtContaDestino.clear();
        txtValor.clear();
        comboAcao.setValue(null);
    }
    
    private Boolean validationForm(){
        invalido = false;
        
        if(txtContaOrigem.textProperty().isNull().get()){
            txtContaOrigem.getStyleClass().add("no-validation");
            invalido = true;
            
        }else{
            txtContaOrigem.getStyleClass().remove("no-validation");
        }
        if(txtValor.textProperty().isNull().get()){
            txtValor.getStyleClass().add("no-validation");
            invalido = true;   
        }else{
            txtValor.getStyleClass().remove("no-validation");
        }
        if(acaoTransferencia() && txtContaDestino.textProperty().isNull().get()){
            txtContaDestino.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            txtContaDestino.getStyleClass().remove("no-validation");
        }
        if(comboAcao.accessibleTextProperty().isNull().get()){
            comboAcao.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            comboAcao.getStyleClass().remove("no-validation");
        }
          
        return invalido;
    }
}
