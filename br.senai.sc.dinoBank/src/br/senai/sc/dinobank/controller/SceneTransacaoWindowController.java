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
import java.text.ParseException;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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
    @FXML
    private TableColumn<Transacao, String> tblColumnData;
    
    
    Transacao transacaoSelecionada = null;
    Transacao novaTransacao = null;
    
    MeuAlerta alerta = new MeuAlerta();
    
    Boolean invalido;
    
    List<String> acoes = Arrays.asList("Depósíto", "Saque", "Transferência");
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            btnCarregarOnAction(null);
        } catch (SQLException ex) {
            Logger.getLogger(SceneTransacaoWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage());
        }
        
        tblColumnData.setCellFactory((TableColumn<Transacao, String> param) -> {
            TableCell cell = new TableCell<Transacao, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item.isEmpty()) {
                            setText("");
                        } else {
                            String dataString = "";
                            List<String> aux = Arrays.asList(item.split("-"));
                            dataString = dataString.concat(aux.get(2));
                            dataString = dataString.concat("/");
                            dataString = dataString.concat(aux.get(1));
                            dataString = dataString.concat("/");
                            dataString = dataString.concat(aux.get(0));
                            setText(dataString);
                        }

                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });
        
        /*tblTransacoes.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            unbindFields(oldValue);
            bindFields(newValue);
            transacaoSelecionada = newValue;
        });*/
        comboAcao.setItems(FXCollections.observableArrayList(acoes));
    }    


    @FXML
    private void btnCarregarOnAction(ActionEvent event) throws SQLException {
        tblTransacoes.setItems(FXCollections.observableArrayList(DAOFactory.getTransacaoDAO().getAll()));
        
    }
    
    @FXML
    private void comboAcaoOnAction(ActionEvent event) {
        if(acaoTransferencia()){
            txtContaDestino.textProperty().bindBidirectional(novaTransacao.numContaDestinoProperty());
        }
  
        }


    @FXML
    private void btnNovaTransacaoOnAction(ActionEvent event) {
        if(novaTransacao == null){
    //          unbindFields(transacaoSelecionada);
            allowFields();
                novaTransacao = new Transacao();
                tblTransacoes.getItems().add(novaTransacao);
                bindFields(novaTransacao);
            }
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
        
       // unbindFields(transacaoSelecionada);
        try {
            if (novaTransacao != null) {
                
                if(novaTransacao.getNumContaDestino() == null){
                    novaTransacao.setNumContaDestino(novaTransacao.getNumContaOrigem());
                }
                novaTransacao.setData(pegaData());
                DAOFactory.getTransacaoDAO().save(novaTransacao);
                
                //switch(novaTransacao.getAcao()){
                    //case "Depósito":
                       // DAOFactory.getContaDAO().updateDeposito(novaTransacao.getNumContaOrigem(),novaTransacao.getValor());
                  //  break;
                   // case "Saque":
                   //     DAOFactory.getContaDAO().updateSaque(novaTransacao.getNumContaOrigem(),novaTransacao.getValor());
                  //  break;
                  //  case "Transferência":
                        DAOFactory.getContaDAO().updateTransferencia(novaTransacao.getNumContaOrigem(),novaTransacao.getNumContaDestino(),novaTransacao.getValor());
                 //   break;
                //}
                
                novaTransacao = null;
            }/* else {
                transacaoSelecionada.setData(pegaData());
                DAOFactory.getTransacaoDAO().update(transacaoSelecionada);
                
            }*/
            limparFields();
        } catch (SQLException ex) {
            Logger.getLogger(SceneClienteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        } catch (ParseException ex) {
            Logger.getLogger(SceneTransacaoWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }
    
    private void bindFields(Transacao transacao){
        txtContaOrigem.textProperty().bindBidirectional(transacao.numContaOrigemProperty());
        txtValor.textProperty().bindBidirectional(transacao.valorProperty(), new NumberStringConverter());
        comboAcao.valueProperty().bindBidirectional(transacao.acaoProperty());
        if(acaoTransferencia()){
            txtContaDestino.textProperty().bindBidirectional(transacao.numContaDestinoProperty());
        }
        
    }

    private void unbindFields(Transacao transacao){
        txtContaOrigem.textProperty().unbindBidirectional(transacao.numContaOrigemProperty());
        txtValor.textProperty().unbindBidirectional(transacao.valorProperty());
        comboAcao.valueProperty().unbindBidirectional(transacao.acaoProperty());
        if(acaoTransferencia()){
            txtContaDestino.textProperty().unbindBidirectional(transacao.numContaDestinoProperty());
        }
        
    }
    
    private Boolean acaoTransferencia(){
        if(comboAcao.getValue() != null){
            if(comboAcao.getValue().equalsIgnoreCase("Transferência")){
                 txtContaDestino.setVisible(true);
                 labelContaDestino.setVisible(true);
                 return true;
             }
            txtContaDestino.setVisible(false);
            labelContaDestino.setVisible(false);
            return false;
        }
        return false;
    }
    
    private String pegaData(){
        String data = null;
        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        data = dataFormat.format(date);
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
        if(comboAcao.valueProperty().isNull().get()){
            comboAcao.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            comboAcao.getStyleClass().remove("no-validation");
        }
          
        return invalido;
    }
    
    private void allowFields(){
        txtContaOrigem.setDisable(false);
        txtValor.setDisable(false);
        comboAcao.setDisable(false);
    }
}
