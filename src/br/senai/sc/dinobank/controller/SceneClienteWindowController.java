/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.controller;

import br.senai.sc.dinobank.MeuAlerta;
import br.senai.sc.dinobank.dao.DAOFactory;
import br.senai.sc.dinobank.model.Cliente;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Senai
 */
public class SceneClienteWindowController implements Initializable {

    @FXML
    private Button btnNovoCliente;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtIdade;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Button btnSalvarCliente;
    @FXML
    private Button btnExcluirCliente;
    @FXML
    private TableView<Cliente> tableClientes;
    @FXML
    private Button btnCarregar;

    /**
     * Initializes the controller class.
     */
    Cliente clienteSelecionado = null;
    Cliente novoCliente = null;
    MeuAlerta alerta = new MeuAlerta();
    
    Boolean invalido = null;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCarregarOnAction(null);
        
        tableClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            unbindFields(oldValue);
            bindFields(newValue);
            clienteSelecionado = newValue;
        });
    }    

    @FXML
    private void btnNovoClienteOnAction(ActionEvent event) {
        unbindFields(clienteSelecionado);
        novoCliente = new Cliente();
        tableClientes.getItems().add(novoCliente);
        bindFields(novoCliente);
    }

    @FXML
    private void btnSalvarClienteOnAction(ActionEvent event) {
        
        if(validacaoDoFormulario()){
            return;
        }
        
        txtNome.getStyleClass().remove("no-validation");
        txtTelefone.getStyleClass().remove("no-validation");
        txtCpf.getStyleClass().remove("no-validation");
        txtIdade.getStyleClass().remove("no-validation");
        

        unbindFields(novoCliente);
        unbindFields(clienteSelecionado);
        try {
            if (novoCliente != null) {
                DAOFactory.getClienteDAO().save(novoCliente);
                novoCliente = null;
            } else {
                DAOFactory.getClienteDAO().update(clienteSelecionado);
                
            }
            limparFields();
        } catch (SQLException ex) {
            Logger.getLogger(SceneClienteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }
    
    public Boolean validacaoDoFormulario(){
        invalido = false;
        
        if(txtNome.textProperty().isNull().get()){
            txtNome.getStyleClass().add("no-validation");
            invalido = true;
            
        }else{
            txtNome.getStyleClass().remove("no-validation");
        }
        if(txtTelefone.textProperty().isNull().get()){
            txtTelefone.getStyleClass().add("no-validation");
            invalido = true;   
        }else{
            txtTelefone.getStyleClass().remove("no-validation");
        }
        if(txtCpf.textProperty().isNull().get()){
            txtCpf.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            txtCpf.getStyleClass().remove("no-validation");
        }
        if(txtIdade.textProperty().isNull().get()){
            txtIdade.getStyleClass().add("no-validation");
            invalido = true;
        }else{
            txtIdade.getStyleClass().remove("no-validation");
        }
        
        
        return invalido;
    }
    
    @FXML
    private void btnExcluirClienteOnAction(ActionEvent event) {
        try {
            unbindFields(clienteSelecionado);
            DAOFactory.getClienteDAO().delete(clienteSelecionado);
            tableClientes.getItems().remove(clienteSelecionado);
        } catch (SQLException ex) {
            Logger.getLogger(SceneClienteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }
   
    
    private void bindFields(Cliente cliente){
        if(cliente != null){
            txtCodigo.textProperty().bind(cliente.codigoProperty().asString());
            txtNome.textProperty().bindBidirectional(cliente.nomeProperty());
            txtTelefone.textProperty().bindBidirectional(cliente.telefoneProperty());
            txtCpf.textProperty().bindBidirectional(cliente.cpfProperty());
            txtIdade.textProperty().bindBidirectional(cliente.idadeProperty(), new NumberStringConverter());
        }
    }
    private void unbindFields(Cliente cliente){
        if(cliente != null){
            txtCodigo.textProperty().unbind();
            txtNome.textProperty().unbindBidirectional(cliente.nomeProperty());
            txtTelefone.textProperty().unbindBidirectional(cliente.telefoneProperty());
            txtCpf.textProperty().unbindBidirectional(cliente.cpfProperty());
            txtIdade.textProperty().unbindBidirectional(cliente.idadeProperty());
        }
    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
        try {
           
              tableClientes.setItems(FXCollections.observableList(DAOFactory.getClienteDAO().
                         getClienteByNome(txtFiltro.getText())));   

        } catch (SQLException ex) {
            Logger.getLogger(SceneClienteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }
    
    private void limparFields(){
        txtCodigo.clear();
        txtNome.clear();
        txtTelefone.clear();
        txtCpf.clear();
        txtIdade.clear();
    }
}
