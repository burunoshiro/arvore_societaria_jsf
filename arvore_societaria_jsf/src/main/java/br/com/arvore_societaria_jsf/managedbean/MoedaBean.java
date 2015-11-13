package br.com.arvore_societaria_jsf.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.enums.Acao;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

@ManagedBean
@SessionScoped
public class MoedaBean {

	private Moeda moeda = new Moeda();
	private List<Moeda> listaMoedas;//= new ArrayList<Moeda>();
	private String mensagem = "";
	private Acao acao = Acao.save;
	private String url = ""; //Utilizado para redicionamento 
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

		EntityManager em = JPAUtil.getEntityManager();

		try {
			
			em.getTransaction().begin();

			if(moeda.getId() != null && em.find(Moeda.class, moeda.getId()) != null) {
				em.merge(this.moeda);
				
				adicionaMensagem(Acao.update);
				
				em.detach(this.moeda);
				
			} else {
				
				em.persist(this.moeda);
				
				adicionaMensagem(Acao.save);
				
				if(listaMoedas != null) {
					listaMoedas.add(this.moeda);
				}
				
				this.moeda = new Moeda();
				
			}
			em.getTransaction().commit();

		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			em.getTransaction().rollback();
			
			System.out.println("Erro ao tentar salvar moeda!");
			
			return;
		}
		finally {
			
			em.close();
		
		}

	}

	public List<Moeda> getListaMoedas() {

		if(listaMoedas == null) {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT m FROM Moeda m", Moeda.class);

			listaMoedas = query.getResultList();
			em.close();
		}

		return listaMoedas;

	}

	public void remove(Moeda moeda) {

		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tr = em.getTransaction();

		tr.begin();
		
		Moeda moedaBanco = em.merge(moeda);
		em.remove(moedaBanco);

		listaMoedas.remove(moeda);
		
		tr.commit();

		em.close();
		
	}

	public String alterarMoeda(Moeda moedaSelecionada) {
		
		for(Moeda moeda : listaMoedas) {
			if(moedaSelecionada == moeda) {
				this.moeda = moeda;
			}
		}
		
		return "/moeda/cadastro_de_moeda?faces-redirect=true";
	}

	
	public void novaMoeda(){
		
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
