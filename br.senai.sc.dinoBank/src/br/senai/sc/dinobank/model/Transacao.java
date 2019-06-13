/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.model;

import br.senai.sc.dinobank.dao.DAOFactory;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Senai
 */
public class Transacao {
    
    
    private final StringProperty numContaDestino = new SimpleStringProperty();
    private final StringProperty numContaOrigem = new SimpleStringProperty();
    private final StringProperty acao = new SimpleStringProperty();
    private final DoubleProperty valor = new SimpleDoubleProperty();
    private final StringProperty data = new SimpleStringProperty();
    private final IntegerProperty codigo = new SimpleIntegerProperty();
   
    

    public Transacao(){
        
    }
    
    public Transacao(Integer codigo,String conta1,String conta2,String acao,Double valor,String data){
        this.codigo.set(codigo);
        this.numContaOrigem.set(conta1);
        this.numContaDestino.set(conta2);
        this.acao.set(acao);
        this.valor.set(valor);
        this.data.set(data);
        
    }
    
    
    public Integer getCodigo() {
        return this.codigo.get();
    }

    public void setCodigo(int value) {
        this.codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return this.codigo;
    }
    
    public String getNumContaDestino() {
        return numContaDestino.get();
    }

    public void setNumContaDestino(String value) {
        numContaDestino.set(value);
    }

    public StringProperty numContaDestinoProperty() {
        return numContaDestino;
    }
    
    public String getNumContaOrigem() {
        return this.numContaOrigem.get();
    }

    public void setNumContaOrigem(String value) {
        this.numContaOrigem.set(value);
    }

    public StringProperty numContaOrigemProperty() {
        return this.numContaOrigem;
    }
    
    public String getAcao() {
        return this.acao.get();
    }

    public void setAcao(String value) {
        this.acao.set(value);
    }

    public StringProperty acaoProperty() {
        return this.acao;
    }
    
    public Double getValor() {
        return this.valor.get();
    }

    public void setValor(Double value) {
        this.valor.set(value);
    }

    public DoubleProperty valorProperty() {
        return this.valor;
    }
    
    public String getData() {
        return this.data.get();
    }

    public void setData(String value) {
        this.data.set(value);
    }

    public StringProperty dataProperty() {
        return this.data;
    }
    
}
