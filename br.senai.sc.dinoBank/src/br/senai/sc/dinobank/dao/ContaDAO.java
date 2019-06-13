 package br.senai.sc.dinobank.dao;

import br.senai.sc.dinobank.model.Conta;
import java.sql.SQLException;
import java.util.List;

public interface ContaDAO {

    public void save(Conta conta) throws SQLException;

    public void update(Conta conta) throws SQLException;
    
    public void updateTransacao(String contaOrigem,String contaDestino,Double valor,String acao) throws SQLException;

    public void delete(Conta conta) throws SQLException;

    public List<Conta> getAll() throws SQLException;

    public Conta getContaByNumero(String numero) throws SQLException;

    public Conta getContaByNumeroAndAgencia(String numero, String agencia) throws SQLException;
    
    public List<Conta> getContaByTitular(String titular) throws SQLException;
    
    public Conta getContaByCodigo(Integer codigo) throws SQLException;

}
