package br.com.arvore_societaria_jsf.managedbean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.sun.faces.config.FaceletsConfiguration;

import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

@ManagedBean
public class MoedaBean {

	private Moeda moeda = new Moeda();
	private List<Moeda> listaMoedas;
	private String mensagem = "";
	
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

			em.persist(moeda);

			em.getTransaction().commit();

		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			em.getTransaction().rollback();
			
			System.out.println("Erro ao tentar salvar moeda!");
			
			FacesContext.getCurrentInstance().addMessage("salvar", new FacesMessage("Erro ao tentar salvar moeda!"));
			
		}
		finally {
			
			em.close();
		
		}

		System.out.println("Moeda salva. Nome: " + moeda.getNome() + "  País: " + moeda.getPais()  );
		
		FacesContext.getCurrentInstance().addMessage("salvar", new FacesMessage("Moeda salva com sucesso!"));
		
		moeda = new Moeda();
		
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

	public void excluir(Moeda moeda) {

		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tr = em.getTransaction();

		tr.begin();
		
		Moeda moedaBanco = em.merge(moeda);
		em.remove(moedaBanco);

		listaMoedas.remove(moeda);
		
		tr.commit();

		em.close();

	}

}
