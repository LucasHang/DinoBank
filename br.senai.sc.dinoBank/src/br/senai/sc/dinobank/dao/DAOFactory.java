package br.senai.sc.dinobank.dao;

public class DAOFactory {

    public static ContaDAO getContaDAO(){
        return new ContaOracleDAO();
    }
 
    public static ClienteDAO getClienteDAO(){
        return new ClientePostgressDAO();
    }
    
    public static TransacaoDAO getTransacaoDAO(){
        return new TransacaoPostgressDAO();
    }
}
