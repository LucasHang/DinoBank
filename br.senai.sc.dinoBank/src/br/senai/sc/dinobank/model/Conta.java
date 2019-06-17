package br.senai.sc.dinobank.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Conta {
    
    private final IntegerProperty codigo = new SimpleIntegerProperty();
    private final StringProperty agencia = new SimpleStringProperty();
    private final StringProperty conta = new SimpleStringProperty();
    private final StringProperty titular = new SimpleStringProperty();
    private final DoubleProperty saldo = new SimpleDoubleProperty();

    public Conta(){
    }
    
    public Conta(Double valorInicial){
        this.saldo.set(100.0 + valorInicial);
    }
    
    public Conta(Integer codigo, String numero, String agencia, String titular, Double saldo){
        this.codigo.set(codigo);
        this.conta.set(numero);
        this.agencia.set(agencia);
        this.titular.set(titular);
        this.saldo.set(saldo);
    }
    
    public String getAgecia() {
        return this.agencia.get();
    }

    public void setAgencia(String value) {
        this.agencia.set(value);
    }

    public StringProperty agenciaProperty() {
        return this.agencia;
    }
    
    public String getConta() {
        return this.conta.get();
    }

    public void setConta(String value) {
        this.conta.set(value);
    }

    public StringProperty contaProperty() {
        return this.conta;
    }
        
    public String getTitular() {
        return this.titular.get();
    }

    public void setTitular(String value) {
        this.titular.set(value);
    }

    public StringProperty titularProperty() {
        return this.titular;
    }
    
    public Double getSaldo() {
        return this.saldo.get();
    }

    public void setSaldo(Double value) {
        this.saldo.set(this.saldo.get() + value);
    }

    public DoubleProperty saldoProperty() {
        return this.saldo;
    }
    
    
    public Integer getCodigo() {
        return this.codigo.get();
    }

    public void setCodigo(Integer value) {
        this.codigo.set(value);
    }

    public IntegerProperty codigoProperty() {
        return this.codigo;
    }
    
}
