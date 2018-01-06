/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AcessoPass.DAO;

import br.AcessoPass.exception.ErroSistema;
import com.AcessoPass.entidade.Senha;
import com.AcessoPass.util.FabricaConexão;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dev
 */
public class SenhaDAO {

    public void salvar(Senha senha) throws ErroSistema {
        try {
            Connection conexao = FabricaConexão.getConexao();
            PreparedStatement ps;
            if (senha.getId() == null) {
                ps = conexao.prepareStatement("INSERT INTO `acesso-pass`.`senha`(`ORIGEM`,`SENHA`)VALUES(?,?)");
            } else {
                ps = conexao.prepareStatement("update senha set ORIGEM=?, SENHA=? where ID=?");
                ps.setInt(3, senha.getId());
            }
            ps.setString(1, senha.getOrigem());
            ps.setString(2, senha.getSenha());
            ps.execute();
            FabricaConexão.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao salvar a senha!", ex);
        }

    }
    
  
    
    public void deletar(Integer idCarro) throws ErroSistema{
        try {
            Connection conexao = FabricaConexão.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from senha where ID=?");
            ps.setInt(1, idCarro);
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar a senha!", ex);
        }
    }

    public List<Senha> buscar() throws ErroSistema {

        try {
            Connection conexao = FabricaConexão.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from `acesso-pass`.`senha`");
            ResultSet resultset = ps.executeQuery();
            List<Senha> senhas = new ArrayList<>();
            while (resultset.next()) {
                Senha senha = new Senha();
                senha.setId(resultset.getInt("ID"));
                senha.setOrigem(resultset.getString("ORIGEM"));
                senha.setSenha(resultset.getString("SENHA"));

                senhas.add(senha);
            }
            FabricaConexão.fecharConexao();
            return senhas;

        } catch (SQLException ex) {
           throw new ErroSistema("Erro ao buscar a senha!", ex);
        }

    }
}
