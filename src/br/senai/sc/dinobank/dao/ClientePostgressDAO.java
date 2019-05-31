/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.dao;

import br.senai.sc.dinobank.model.Cliente;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Senai
 */
public class ClientePostgressDAO extends ConnectionFactory implements ClienteDAO{

    @Override
    public void save(Cliente cliente) throws SQLException {
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into cliente (nome, telefone, cpf, idade) values (?,?,?,?)",
                codigoGerado);
        super.prepared.setString(1, cliente.getNome());
        super.prepared.setString(2, cliente.getTelefone());
        super.prepared.setString(3, cliente.getCpf());
        super.prepared.setInt(4, cliente.getIdade());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar o novo cliente");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            cliente.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closePreparedStatement();
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        
        super.preparedStatementInitialize(
                "update cliente set nome = ?, telefone = ?, cpf = ?, idade = ? where codigo = ?");
        super.prepared.setString(1, cliente.getNome());
        super.prepared.setString(2, cliente.getTelefone());
        super.prepared.setString(3, cliente.getCpf());
        super.prepared.setInt(4, cliente.getIdade());
        super.prepared.setInt(5, cliente.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações do cliente");
        }
        super.closePreparedStatement();
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        super.preparedStatementInitialize(
                "delete from cliente where codigo = ?");
        super.prepared.setInt(1, cliente.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar o cliente");
        }
        
        super.closePreparedStatement();
    }

    @Override
    public List<Cliente> getAll() throws SQLException {
        List<Cliente> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from cliente");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Cliente(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("telefone"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getInt("idade")));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return rows;
    }

    @Override
    public List<Cliente> getClienteByNome(String nome) throws SQLException {
        List<Cliente>  rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from cliente where upper(nome) like ?");
        super.prepared.setString(1,"%"+nome.toUpperCase()+"%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Cliente(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("nome"),
                    resultSetRows.getString("telefone"),
                    resultSetRows.getString("cpf"),
                    resultSetRows.getInt("idade")));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return rows;
    }
    
}
