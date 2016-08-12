package br.com.arvore_societaria_jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.arvore_societaria_jsf.dao.EmpresaDAO;
import br.com.arvore_societaria_jsf.dao.MoedaDAO;
import br.com.arvore_societaria_jsf.enums.Acao;
import br.com.arvore_societaria_jsf.enums.ResultadoOperacao;
import br.com.arvore_societaria_jsf.model.Empresa;
import br.com.arvore_societaria_jsf.model.Moeda;

@ManagedBean
@ViewScoped
public class EmpresaBean {

	private Empresa empresa = new Empresa();
	private List<Empresa> listaEmpresas = null;
	private Acao acao = Acao.save;
	private String mensagem = "";
	private String url = "";
	private List<SelectItem> moedasSelected; 
	private Moeda moedaSelecionada;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@PostConstruct
	public void iniciaBean() {
		
		Empresa empresaSelecionada = (Empresa)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("empresaSelecionada");

		if(empresaSelecionada != null) {
			empresa = empresaSelecionada;
			System.out.println("empresa: " + empresa.getRazaoSocial());
		}
		
	}
	
	public List<SelectItem> getMoedasSelected() {
		
		if (moedasSelected == null) {

			moedasSelected = new ArrayList<SelectItem>();
			
			MoedaDAO moedaDAO = new MoedaDAO();

			List<Moeda> listaMoeda = moedaDAO.buscaAtivas();

			for(Moeda moeda : listaMoeda) {

				moedasSelected.add(new SelectItem(moeda, moeda.getNome()));

			}
		}
		
		return moedasSelected;
	}

	public void salva() {

		EmpresaDAO empresaDAO = new EmpresaDAO();

		ResultadoOperacao resultadoOperacao; 


		resultadoOperacao = empresaDAO.salva(this.empresa);

		if (resultadoOperacao == ResultadoOperacao.alterado) {

			adicionaMensagem(Acao.update);

		} 
		else if(resultadoOperacao == ResultadoOperacao.salvo) {

			adicionaMensagem(Acao.save);

			if(listaEmpresas != null) {
				listaEmpresas.add(this.empresa);
			}

			this.empresa = new Empresa();

		}
		else if(resultadoOperacao == ResultadoOperacao.erro) {

			System.out.println("Erro ao tentar salvar/alterar empresa!");

			adicionaMensagem(Acao.erro);

		}

	}

	public List<Empresa> getListaEmpresas() {

		if(listaEmpresas == null) {

			EmpresaDAO empresaDAO = new EmpresaDAO();

			listaEmpresas = empresaDAO.buscaTodos();

		}

		return listaEmpresas;
	}

	public void remove(Empresa empresa) {

		EmpresaDAO empresaDAO = new EmpresaDAO();

		try {
			empresaDAO.remove(empresa);

			listaEmpresas.remove(empresa);
		}
		catch(Exception e) {

			e.printStackTrace();

		}

	}

	public String alterarEmpresa(Empresa empresaSelecionada) {

		for(Empresa empresa : listaEmpresas) {
			if(empresaSelecionada == empresa) {
				acao = Acao.update; //Coloca ação como update para a empresa não ser apagada pelo botão "novo"
				//this.empresa = empresa;
				FacesContext.getCurrentInstance().getExternalContext().getFlash().put("empresaSelecionada", empresa);
			}
		}

		return "/empresa/cadastro_de_empresa?faces-redirect=true";
	}

	public void novaEmpresa(){

		//Se o comando for chamado em uma operação de alteração de empresa, mantém o registro
		//Necessário, pois o comando "window.onunload", é chamado mesmo ao entrar na página devido ao jsf 
		if(this.acao == Acao.update) {
			this.acao = Acao.save;
			return;
		}

		this.empresa = new Empresa();

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

	public Moeda getMoedaSelecionada() {
		return moedaSelecionada;
	}

	public void setMoedaSelecionada(Moeda moedaSelecionada) {
		this.moedaSelecionada = moedaSelecionada;
	}

}
