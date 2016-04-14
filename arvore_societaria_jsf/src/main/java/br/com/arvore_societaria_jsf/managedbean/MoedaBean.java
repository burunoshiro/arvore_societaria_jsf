package br.com.arvore_societaria_jsf.managedbean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.dao.MoedaDAO;
import br.com.arvore_societaria_jsf.enums.Acao;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

@ManagedBean
@SessionScoped
public class MoedaBean {

	private Moeda moeda = new Moeda();
	private List<Moeda> listaMoedas;
	private String mensagem = "";
	private Acao acao = Acao.save;
	private String url = ""; //Utilizado para redirecionamento 
	//private String nomeBotao = "btn_excluir"; //Utilizado para nome do botao concatenado com id 


	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Moeda getMoeda() {
		return moeda;
	}

	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	public void salva() {

		MoedaDAO moedaDAO = new MoedaDAO();

		try {
			//atualiza registro da moeda
			if(moeda.getId() != null && moedaDAO.buscaPorID(Moeda.class, moeda.getId()) != null) {

				moedaDAO.salva(this.moeda);

				adicionaMensagem(Acao.update);

			} 
			//gera novo registro de moeda
			else {

				moedaDAO.salva(this.moeda);

				adicionaMensagem(Acao.save);

				if(listaMoedas != null) {
					listaMoedas.add(this.moeda);
				}
				
				this.moeda = new Moeda();
				
			}

		}
		catch(Exception e) {

			e.printStackTrace();

			System.out.println("Erro ao tentar salvar/alterar moeda!");

			adicionaMensagem(Acao.erro);

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

	public String alterarMoeda(Moeda moedaSelecionada) {

		for(Moeda moeda : listaMoedas) {
			if(moedaSelecionada == moeda) {
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

			FacesContext.getCurrentInstance().addMessage("salvar", new FacesMessage("Moeda Salva com sucesso!"));

			System.out.println("Moeda salva. Nome: " + moeda.getNome() + "  País: " + moeda.getPais()  );

		} else if(acao == Acao.update) {

			FacesContext.getCurrentInstance().addMessage("alterar", new FacesMessage("Moeda alterada com sucesso!"));

		} else if(acao == Acao.delete) {

			FacesContext.getCurrentInstance().addMessage("deletar", new FacesMessage("Moeda deletada com sucesso!"));

		}

	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
	}
}
