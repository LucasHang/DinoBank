package br.senai.sc.dinobank.dao;

import br.senai.sc.dinobank.BancoDeDados;
import br.senai.sc.dinobank.model.Conta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaOracleDAO extends ConnectionFactory implements ContaDAO {

    @Override
    public void save(Conta conta) throws SQLException {
        String[] codigoGerado = {"codigo"};
        super.preparedStatementInitialize(
                "insert into conta (numero, agencia, titular, saldo) values (?,?,?,?)",
                codigoGerado);
        super.prepared.setString(1, conta.getConta());
        super.prepared.setString(2, conta.getAgecia());
        super.prepared.setString(3, conta.getTitular());
        super.prepared.setDouble(4, conta.getSaldo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível cadastrar a nova conta");
        }
        
        ResultSet resultSetRows = super.prepared.getGeneratedKeys();
        if (resultSetRows.next()) {
            conta.setCodigo(resultSetRows.getInt("codigo"));
        }
        resultSetRows.close();
        super.closePreparedStatement();
    }

    @Override
    public void update(Conta conta) throws SQLException {
        super.preparedStatementInitialize(
                "update conta set numero = ?, agencia = ?, titular = ?, saldo = ? where codigo = ?");
        super.prepared.setString(1, conta.getConta());
        super.prepared.setString(2, conta.getAgecia());
        super.prepared.setString(3, conta.getTitular());
        super.prepared.setDouble(4, conta.getSaldo());
        super.prepared.setInt(5, conta.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível aletrar a conta");
        }
        
        super.closePreparedStatement();
    }

    @Override
    public void delete(Conta conta) throws SQLException {
        super.preparedStatementInitialize(
                "delete from conta where codigo = ?");
        super.prepared.setInt(1, conta.getCodigo());
        int linhasAfetadas = super.prepared.executeUpdate();
        if (linhasAfetadas == 0){
            throw new SQLException("Não foi possível deletar a conta");
        }
        
        super.closePreparedStatement();
    }

    @Override
    public List<Conta> getAll() throws SQLException {
        List<Conta> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from conta");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Conta(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("numero"),
                    resultSetRows.getString("agencia"),
                    resultSetRows.getString("titular"),
                    resultSetRows.getDouble("saldo")));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return rows;
    }

    @Override
    public Conta getContaByNumero(String numero) throws SQLException {
        Conta novaConta = null;
        super.preparedStatementInitialize("select * from conta where numero = ?");
        super.prepared.setString(1, numero);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        if (resultSetRows.next()) {
            novaConta = new Conta(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("numero"),
                    resultSetRows.getString("agencia"),
                    resultSetRows.getString("titular"),
                    resultSetRows.getDouble("saldo"));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return novaConta;
    }

    @Override
    public Conta getContaByNumeroAndAgencia(String numero, String agencia) throws SQLException {
        Conta novaConta = null;
        super.preparedStatementInitialize("select * from conta where numero = ? and agencia = ?");
        super.prepared.setString(1, numero);
        super.prepared.setString(2, agencia);
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        if (resultSetRows.next()) {
            novaConta = new Conta(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("numero"),
                    resultSetRows.getString("agencia"),
                    resultSetRows.getString("titular"),
                    resultSetRows.getDouble("saldo"));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return novaConta;
    }

    @Override
    public List<Conta> getContaByTitular(String titular) throws SQLException {
        List<Conta> rows = new ArrayList<>();
        super.preparedStatementInitialize("select * from conta where upper(titular) like ?");
        super.prepared.setString(1, "%"+titular.toUpperCase()+"%");
        super.prepared.execute();
        ResultSet resultSetRows = super.prepared.getResultSet();
        while (resultSetRows.next()) {
            rows.add(new Conta(resultSetRows.getInt("codigo"),
                    resultSetRows.getString("numero"),
                    resultSetRows.getString("agencia"),
                    resultSetRows.getString("titular"),
                    resultSetRows.getDouble("saldo")));
        }
        resultSetRows.close();
        super.closePreparedStatement();

        return rows;
    }

}
