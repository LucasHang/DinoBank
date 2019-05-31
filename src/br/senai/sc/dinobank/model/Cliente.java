/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Senai
 */
public class Cliente {
    
    private final IntegerProperty codigo = new SimpleIntegerProperty();
    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty telefone = new SimpleStringProperty();
    private final StringProperty cpf = new SimpleStringProperty();
    private final IntegerProperty idade = new SimpleIntegerProperty();
    
    
    public Cliente(){
        
    }
    
    public Cliente(Integer codigo, String nome, String telefone, String cpf, Integer idade){
        this.codigo.set(codigo);
        this.nome.set(nome);
        this.telefone.set(telefone);
        this.cpf.set(cpf);
        this.idade.set(idade);
    }

    public int getCodigo() {
        return this.codigo.get();
    }

    public void setCodigo(int value) {
        this.codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return this.codigo;
    }
    
    public int getIdade() {
        return this.idade.get();
    }

    public void setIdade(int value) {
        this.idade.set(value);
    }

    public IntegerProperty idadeProperty() {
        return this.idade;
    }

    public String getCpf() {
        return this.cpf.get();
    }

    public void setCpf(String value) {
        this.cpf.set(value);
    }

    public StringProperty cpfProperty() {
        return this.cpf;
    }


    public String getTelefone() {
        return this.telefone.get();
    }

    public void setTelefone(String value) {
        this.telefone.set(value);
    }

    public StringProperty telefoneProperty() {
        return this.telefone;
    }

    public String getNome() {
        return this.nome.get();
    }

    public void setNome(String value) {
        this.nome.set(value);
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }
    
    
}
