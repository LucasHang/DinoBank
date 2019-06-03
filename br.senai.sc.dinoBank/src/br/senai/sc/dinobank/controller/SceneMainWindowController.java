/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.controller;

import br.senai.sc.dinobank.MeuAlerta;
import br.senai.sc.dinobank.Saudacao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Senai
 */
public class SceneMainWindowController implements Initializable {

    @FXML
    private Label lbSaudacao;
    @FXML
    private TabPane tabPanesScenes;

    Tab abaConta = null;
    Tab abaCliente = null;
    Tab abaTransacao = null;
    Stage stageSobre = null;
    MeuAlerta alerta = new MeuAlerta();
    AnchorPane painelSceneConta = null;
    AnchorPane painelSceneCliente = null;
    AnchorPane painelSceneTransacao = null;
    
            
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbSaudacao.setText(Saudacao.verificaHora());
    }    

    @FXML
    private void menuCadastrosCadastroContasOnAction(ActionEvent event) {
        
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/dinobank/view/SceneContaWindow.fxml"));
           // }
            if(abaConta == null){
                abaConta = new Tab("Cadastro de Contas");
            }
            if(painelSceneConta == null){
                painelSceneConta = cargaDoScene.load();
            }
            abaConta.setContent(painelSceneConta);
            abaConta.setOnCloseRequest((eventClose) -> {
                if (alerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanesScenes.getTabs().remove(abaConta);
                    painelSceneConta = null;
                }
            });
            if(!tabPanesScenes.getTabs().contains(abaConta)){
            tabPanesScenes.getTabs().add(abaConta);
            }
            tabPanesScenes.getSelectionModel().select(abaConta);
        } catch (IOException ex) {
            Logger.getLogger(SceneMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
        
    }

    
    @FXML
    private void menuCadastrosCadastroClientesOnAction(ActionEvent event) {
         try {
             //if(cargaDoSceneCliente == null){
            FXMLLoader cargaDoScene =
                    new FXMLLoader(getClass().getResource("/br/senai/sc/dinobank/view/SceneClienteWindow.fxml"));
             //}
            if(abaCliente == null){
            abaCliente = new Tab("Cadastro de Clientes");
            }
            if(painelSceneCliente == null){
                painelSceneCliente = cargaDoScene.load();
            }
            abaCliente.setContent(painelSceneCliente);
            abaCliente.setOnCloseRequest((eventClose) -> {
                if (alerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanesScenes.getTabs().remove(abaCliente);
                    painelSceneCliente = null;
                }
            });
            if(!tabPanesScenes.getTabs().contains(abaCliente)){
                tabPanesScenes.getTabs().add(abaCliente);
            }
            tabPanesScenes.getSelectionModel().select(abaCliente);
        } catch (IOException ex) {
            Logger.getLogger(SceneMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }
    
    @FXML
    private void menuAjudaSobreOnAction(ActionEvent event) {
        
        try {
            if(stageSobre == null){
            stageSobre = new Stage();
            }
            Parent sceneSobre = FXMLLoader.load(getClass().getResource("/br/senai/sc/dinobank/view/SceneSobreWindow.fxml"));
            Scene scene = new Scene(sceneSobre);
            stageSobre.setScene(scene);
            stageSobre.initStyle(StageStyle.UTILITY);
            stageSobre.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SceneMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void menuCadastrosTransacaoOnAction(ActionEvent event) {
        try {
           
                FXMLLoader cargaDoScene =
                       new FXMLLoader(getClass().getResource("/br/senai/sc/dinobank/view/SceneTransacaoWindow.fxml"));
           // }
            if(abaTransacao == null){
                abaTransacao = new Tab("Transacao");
            }
            if(painelSceneTransacao == null){
                painelSceneTransacao = cargaDoScene.load();
            }
            abaTransacao.setContent(painelSceneTransacao);
            abaTransacao.setOnCloseRequest((eventClose) -> {
                if (alerta.alertaDeConfirmacao("Deseja realmente fechar a janela?").showAndWait().get() != ButtonType.YES){
                    eventClose.consume();
                }else{
                    tabPanesScenes.getTabs().remove(abaTransacao);
                    painelSceneTransacao = null;
                }
            });
            if(!tabPanesScenes.getTabs().contains(abaTransacao)){
            tabPanesScenes.getTabs().add(abaTransacao);
            }
            tabPanesScenes.getSelectionModel().select(abaTransacao);
        } catch (IOException ex) {
            Logger.getLogger(SceneMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.alertaDeErro(ex.getMessage()).showAndWait();
        }
    }

    
    
}
