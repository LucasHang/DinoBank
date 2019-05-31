/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank;

import java.util.Date;

/**
 *
 * @author Senai
 */
public class Saudacao {

    public static String verificaHora() {
        Date stamp = new Date();
        int hours = stamp.getHours();
        if (hours >= 18 && hours < 24) {
            return "Boa Noite!";
        }

        if (hours >= 12 && hours < 18) {
            return "Boa Tarde!";
        }

        if (hours >= 0 && hours < 12) {
            return "Bom Dia!";
        }

        return "";
    }
}
