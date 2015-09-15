package br.com.arvore_societaria_jsf.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

@ManagedBean
public class MoedaBean {

	private Moeda moeda = new Moeda();
	private List<Moeda> listaMoedas;
	
	public Moeda getMoeda() {
		return moeda;
	}

	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	public void salva() {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(moeda);
		
		em.getTransaction().commit();
		
		em.close();
		
		System.out.println("Moeda salva. Nome: " + moeda.getNome() + "  País: " + moeda.getPais()  );
		
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
		
		if(listaMoedas == null) {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT m FROM Moeda m", Moeda.class);
			
			listaMoedas = query.getResultList();
			em.close();
		}
		
	
	}
	
}
