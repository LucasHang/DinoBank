/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.dinobank.dao;

import br.senai.sc.dinobank.model.Transacao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.input.DataFormat;

/**
 *
 * @author Senai
 */
public class TransacaoPostgressDAO extends ConnectionFactory implements TransacaoDAO {
    
    DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date sqlDate;
    

    @Override
    public void save(Transacao transacao) throws SQLException, ParseException {
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into transacao (codContaOrigem, codContaDestino, acao, valor, dataTrans) values (?,?,?,?,?)",
                codigoGerado);
        super.prepared.setInt(1, DAOFactory.getContaDAO().getContaByNumero(transacao.getNumContaOrigem()).getCodigo());
        super.prepared.setInt(2, DAOFactory.getContaDAO().getContaByNumero(transacao.getNumContaDestino()).getCodigo());
        super.prepared.setString(3, transacao.getAcao());
        super.prepared.setDouble(4, transacao.getValor());
        sqlDate = new java.sql.Date(dataFormat.parse(transacao.getData()).getTime());
        super.prepared.setDate(5, sqlDate);
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar a transaçao");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            transacao.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closePreparedStatement();
    }

    @Override
    public void update(Transacao transacao) throws SQLException,ParseException {
        super.preparedStatementInitialize(
                "update transacao  codContaOrigem = ?,codContaDestino = ?, acao = ?, valor = ?, dataTrans = ? where codigo = ?");
        super.prepared.setInt(1, DAOFactory.getContaDAO().getContaByNumero(transacao.getNumContaOrigem()).getCodigo());
        super.prepared.setInt(2, DAOFactory.getContaDAO().getContaByNumero(transacao.getNumContaDestino()).getCodigo());
        super.prepared.setString(3, transacao.getAcao());
        super.prepared.setDouble(4, transacao.getValor());
        sqlDate = new java.sql.Date(dataFormat.parse(transacao.getData()).getTime());
        super.prepared.setDate(5, sqlDate);
        super.prepared.setInt(6, transacao.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar as informações da transaçao");
        }
        super.closePreparedStatement();
    }

    @Override
    public void delete(Transacao transacao) throws SQLException {
        super.preparedStatementInitialize(
                "delete from transaco where codigo = ?");
        super.prepared.setInt(1, transacao.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar a transaçao");
        }
        
        super.closePreparedStatement();
    }

    @Override
    public List<Transacao> getAll() throws SQLException {
        List<Transacao> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from transacao");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Transacao(resultSetRows.getInt("codigo"),
                    DAOFactory.getContaDAO().getContaByCodigo(resultSetRows.getInt("codContaOrigem")).getConta(),
                    DAOFactory.getContaDAO().getContaByCodigo(resultSetRows.getInt("codContaDestino")).getConta(),
                    resultSetRows.getString("acao"),
                    resultSetRows.getDouble("valor"),
                    resultSetRows.getString("dataTrans")));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return rows;
    }

    @Override
    public List<Transacao> getTransacaoByData(Integer data) throws SQLException {
        List<Transacao> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from transacao where dataTrans = ?");
        super.prepared.setInt(1, data);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Transacao(resultSetRows.getInt("codigo"),
                    DAOFactory.getContaDAO().getContaByCodigo(resultSetRows.getInt("codContaOrigem")).getConta(),
                    DAOFactory.getContaDAO().getContaByCodigo(resultSetRows.getInt("codContaDestino")).getConta(),
                    resultSetRows.getString("acao"),
                    resultSetRows.getDouble("valor"),
                    resultSetRows.getString("dataTrans")));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return rows;
    }

    @Override
    public Transacao getTransacaoByNumeroConta(String numero) throws SQLException {
        Transacao novaTransacao = null;
        super.preparedStatementInitialize("select * from transacao where numContaOrigem = ?");
        super.prepared.setString(1, numero);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        if (resultSetRows.next()) {
            novaTransacao = new Transacao(resultSetRows.getInt("codigo"),
                    DAOFactory.getContaDAO().getContaByCodigo(resultSetRows.getInt("codContaOrigem")).getConta(),
                    DAOFactory.getContaDAO().getContaByCodigo(resultSetRows.getInt("codContaDestino")).getConta(),
                    resultSetRows.getString("acao"),
                    resultSetRows.getDouble("valor"),
                    resultSetRows.getString("dataTrans"));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return novaTransacao;
    }
    
}
