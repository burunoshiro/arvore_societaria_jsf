package br.com.arvore_societaria_jsf.managedbean;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.arvore_societaria_jsf.bean.Empresa;
import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.dao.EmpresaDAO;
import br.com.arvore_societaria_jsf.dao.MoedaDAO;
import br.com.arvore_societaria_jsf.enums.Acao;

@ManagedBean
@SessionScoped
public class EmpresaBean {

	private Empresa empresa = new Empresa();
	private List<Empresa> listaEmpresas = null;
	private Acao acao = Acao.save;
	
	public void salva() {
		
		EmpresaDAO dao = new EmpresaDAO();
		
		if(empresa != null) {
			dao.salva(empresa);
		}

		
		
	}
	
	public List<Moeda> getListaMoedas() {

		if(listaMoedas == null) {

			MoedaDAO moedaDAO = new MoedaDAO();

			listaMoedas = moedaDAO.buscaTodos();

		}

		return listaMoedas;
	}

	public void remove(Moeda moeda) {

		MoedaDAO moedaDAO = new MoedaDAO();

		try {
			moedaDAO.remove(moeda);

			listaMoedas.remove(moeda);
		}
		catch(Exception e) {

			e.printStackTrace();

		}
		
	}

	public String alterarMoeda(Empresa empresaSelecionada) {

		for(Empresa empresa : listaEmpresas) {
			if(empresaSelecionada == moeda) {
				acao = Acao.update; //Coloca ação como update para a moeda não ser apagada pelo botão "novo"
				this.moeda = moeda;
			}
		}

		return "/moeda/cadastro_de_moeda?faces-redirect=true";
	}

	public void novaMoeda(){
		
		//Se o comando for chamado em uma operação de alteração de moeda, mantém o registro
		//Necessário, pois o comando "window.onunload", é chamado mesmo ao entrar na página devido ao jsf 
		if(this.acao == Acao.update) {
			this.acao = Acao.save;
			return;
		}

		this.moeda = new Moeda();

	}

	public String redireciona(){
		return this.url;
	}
	
	private void adicionaMensagem(Acao acao) {

		if(acao == Acao.save) {

			FacesContext.getCurrentInstance().addMessage("salvar", new FacesMessage("Empresa salva com sucesso!"));


		} else if(acao == Acao.update) {

			FacesContext.getCurrentInstance().addMessage("alterar", new FacesMessage("Empresa alterada com sucesso!"));

		} else if(acao == Acao.delete) {

			FacesContext.getCurrentInstance().addMessage("deletar", new FacesMessage("Empresa deletada com sucesso!"));

		}

	}
	
	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}
	
}
