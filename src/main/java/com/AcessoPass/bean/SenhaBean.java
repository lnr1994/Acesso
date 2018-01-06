/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AcessoPass.bean;

import br.AcessoPass.exception.ErroSistema;
import com.AcessoPass.DAO.SenhaDAO;
import com.AcessoPass.entidade.Senha;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class SenhaBean {

    private Senha senha = new Senha();
    private List<Senha> senhas = new ArrayList<>();
    private SenhaDAO senhaDAO = new SenhaDAO();

    public void adicionar() {

        try {
            senhaDAO.salvar(senha);
            senha = new Senha();
            adicionarMensagem("Salvo", "Senha salva com sucesso!",  FacesMessage.SEVERITY_ERROR);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void listar() {
        try {
            senhas = senhaDAO.buscar();
            if(senhas==null || senhas.size() == 0 ){
                adicionarMensagem("Nenhuma senha encontrada!", "Sua Busca nao escontrou nenhuma senha cadastrada!", FacesMessage.SEVERITY_ERROR);
            }
                
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void deletar(Senha s){
        try {
            senhaDAO.deletar(s.getId());
            adicionarMensagem("Deletado", "Senha deletada com sucesso!",  FacesMessage.SEVERITY_ERROR);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(Senha s) {
        senha = s;

    }

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);

    }

    /**
     * @return the senha
     */
    public Senha getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    /**
     * @return the senhas
     */
    public List<Senha> getSenhas() {
        return senhas;
    }

    /**
     * @param senhas the senhas to set
     */
    public void setSenhas(List<Senha> senhas) {
        this.senhas = senhas;
    }

}
