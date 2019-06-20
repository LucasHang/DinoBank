/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Senai
 */
public class ConnectionFactory {
    
    private Connection connection = null;
    protected PreparedStatement prepared = null;
    
    
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    private Connection getConnetion(String url, String user, String password) throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
    
    private Connection getConnectionOracle(){
        return null;
    }
    
    private Connection getConnectionMySQL(){
        return null;
    }
    
    private Connection getConnetionPostgres() throws SQLException{
       return getConnetion("jdbc:postgresql://localhost/dinobak", "postgres", "senai");
    }
    
    protected void preparedStatementInitialize(String sql) throws SQLException{
        connection = getConnetionPostgres();
        if(connection == null){
            throw new SQLException("Não foi possível gerar o Connection.");
        }
        
        prepared = connection.prepareStatement(sql);
        if(prepared == null){
            throw new SQLException("Não foi possível gerar o Prepared.");
        }
    }
    
    protected void preparedStatementInitialize(String sql, String[] colunasRetorno) throws SQLException{
        connection = getConnetionPostgres();
        if(connection == null){
            throw new SQLException("Não foi possível gerar o Connection.");
        }
        
        prepared = connection.prepareStatement(sql, colunasRetorno);
        if(prepared == null){
            throw new SQLException("Não foi possível gerar o Prepared.");
        }
    }
    
    protected void closePreparedStatement() throws SQLException{
        prepared.close();
        connection.close();
    }
}
