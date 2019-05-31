/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Senai
 */
public class Alerta {
    
    Alert alerta = null;
    
    public Alert alertaDeErro(String message){
        alerta = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        return alerta;
    }
    
    public Alert alertaDeConfirmação(String message){
        alerta = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO);
        alerta.getButtonTypes().add(ButtonType.YES);
        return alerta;
    }
}
