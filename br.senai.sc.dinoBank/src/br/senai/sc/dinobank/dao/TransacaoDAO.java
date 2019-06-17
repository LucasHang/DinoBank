/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.dao;

import br.senai.sc.dinobank.model.Transacao;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author Senai
 */
public interface TransacaoDAO {
    
    public void save(Transacao cliente) throws SQLException,ParseException;

    public void update(Transacao cliente) throws SQLException,ParseException;

    public void delete(Transacao cliente) throws SQLException;

    public List<Transacao> getAll() throws SQLException;

    public List<Transacao> getTransacaoByData(Integer data) throws SQLException;
    
    public Transacao getTransacaoByNumeroConta(String numero) throws SQLException;
    
    
    
}
