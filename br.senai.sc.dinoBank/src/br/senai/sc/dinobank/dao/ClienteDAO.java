/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.dao;

import br.senai.sc.dinobank.model.Cliente;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Senai
 */
public interface ClienteDAO {
    
    public void save(Cliente cliente) throws SQLException;

    public void update(Cliente cliente) throws SQLException;

    public void delete(Cliente cliente) throws SQLException;

    public List<Cliente> getAll() throws SQLException;

    public List<Cliente> getClienteByNome(String nome) throws SQLException;
}
