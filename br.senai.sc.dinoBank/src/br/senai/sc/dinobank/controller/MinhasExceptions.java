/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Senai
 */
public class MinhasExceptions extends RuntimeException{

    public MinhasExceptions() {
        new Alert(Alert.AlertType.ERROR, super.getMessage(), ButtonType.OK).showAndWait();
    }

    public MinhasExceptions(String message) {
        super(message);
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    public MinhasExceptions(String message, Throwable cause) {
        super(message, cause);
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    public MinhasExceptions(Throwable cause) {
        super(cause);
        new Alert(Alert.AlertType.ERROR, super.getMessage(), ButtonType.OK).showAndWait();
    }

    public MinhasExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    
    
}
