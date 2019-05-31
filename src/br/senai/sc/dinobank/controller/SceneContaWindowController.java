package br.senai.sc.dinobank.controller;

import br.senai.sc.dinobank.MeuAlerta;
import br.senai.sc.dinobank.dao.DAOFactory;
import br.senai.sc.dinobank.model.Conta;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;

public class SceneContaWindowController implements Initializable {

    @FXML
    private Button btnNovaConta;
    @FXML
    private TextField tboxNumeroAgencia;
    @FXML
    private TextField tboxConta;
    @FXML
    private TextField tboxNomeTitular;
    @FXML
    private TextField tboxSaldoInicial;
    @FXML
    private Button btnSalvaConta;
    @FXML
    private Button btnCarregarTudo;
    @FXML
    private TableView<Conta> tblContas;
    @FXML
    private TextField tboxCodigoConta;
    @FXML
    private Button btnExcluirConta;
    @FXML
    private TextField tboxProcurarTitular;
    @FXML
    private Button btnProcurar;

    Conta contaSelecionada = null;
    Conta novaConta = null;
    
    MeuAlerta alerta = new MeuAlerta();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCarregarTudoOnAction(null);
        tblContas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            unbindDataObject(oldValue);
            bindDataObject(newValue);
            contaSelecionada = newValue;
        });

    }

    @FXML
    private void btnNovaContaOnAction(ActionEvent event) throws SQLException {
        unbindDataObject(contaSelecionada);
        novaConta = new Conta();
        tblContas.getItems().add(novaConta);
        bindDataObject(novaConta);
    }

    @FXML
    private void btnSalvaContaOnAction(ActionEvent event) {

        if(validacaoDoFormulario()){
            return;
        }
        
        tboxNumeroAgencia.getStyleClass().remove("no-validation");
        tboxConta.getStyleClass().remove("no-validation");
        tboxNomeTitular.getStyleClass().remove("no-validation");
        
        unbindDataObject(novaConta);
        unbindDataObject(contaSelecionada);
        try {
            if (novaConta != null) {
                DAOFactory.getContaDAO().save(novaConta);
                novaConta = null;
            } else {
                DAOFactory.getContaDAO().update(contaSelecionada);      
            }
            limparFields();
        } catch (SQLException ex) {
            Logger.getLogger(SceneContaWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }

    public Boolean validacaoDoFormulario(){
        Boolean invalido = false;

        if(tboxNumeroAgencia.textProperty().isNull().get()){
            tboxNumeroAgencia.getStyleClass().add("no-validation");
            invalido = true;     
        }else{
            tboxNumeroAgencia.getStyleClass().remove("no-validation");
        }
        
        if(tboxConta.textProperty().isNull().get()){
            tboxConta.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            tboxConta.getStyleClass().remove("no-validation");
        }
        
        if(tboxNomeTitular.textProperty().isNull().get()){
            tboxNomeTitular.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            tboxNomeTitular.getStyleClass().remove("no-validation");
        }
        
        
        return invalido;
    }

    private void bindDataObject(Conta conta) {
        if (conta != null) {
            tboxCodigoConta.textProperty().bind(conta.codigoProperty().asString());
            tboxNumeroAgencia.textProperty().bindBidirectional(conta.agenciaProperty());
            tboxConta.textProperty().bindBidirectional(conta.contaProperty());
            tboxNomeTitular.textProperty().bindBidirectional(conta.titularProperty());
            tboxSaldoInicial.textProperty().bindBidirectional(conta.saldoProperty(), new NumberStringConverter());
        }
    }

    private void unbindDataObject(Conta conta) {
        if (conta != null) {
            tboxCodigoConta.textProperty().unbind();
            tboxNumeroAgencia.textProperty().unbindBidirectional(conta.agenciaProperty());
            tboxConta.textProperty().unbindBidirectional(conta.contaProperty());
            tboxNomeTitular.textProperty().unbindBidirectional(conta.titularProperty());
            tboxSaldoInicial.textProperty().unbindBidirectional(conta.saldoProperty());
        }
    }

    @FXML
    private void btnCarregarTudoOnAction(ActionEvent event) {
        try {
            tblContas.setItems(FXCollections.observableList(DAOFactory.getContaDAO().getAll()));

        } catch (SQLException ex) {
            Logger.getLogger(SceneContaWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void btnExcluirContaOnAction(ActionEvent event) {
        try {
            unbindDataObject(contaSelecionada); 
            DAOFactory.getContaDAO().delete(contaSelecionada);
            tblContas.getItems().remove(contaSelecionada);
            
        } catch (SQLException ex) {
            Logger.getLogger(SceneContaWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void tboxProcurarTitularOnKeyReleased(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnProcurarOnAction(null);
        }
    }

    @FXML
    private void btnProcurarOnAction(ActionEvent event) {
        try {
            tblContas.setItems(FXCollections.observableList(
                    DAOFactory.getContaDAO().getContaByTitular(tboxProcurarTitular.getText())));
        } catch (SQLException ex) {
            Logger.getLogger(SceneContaWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void limparFields(){
        tboxCodigoConta.clear();
        tboxNumeroAgencia.clear();
        tboxNomeTitular.clear();
        tboxSaldoInicial.clear();
        tboxConta.clear();
    }

}
