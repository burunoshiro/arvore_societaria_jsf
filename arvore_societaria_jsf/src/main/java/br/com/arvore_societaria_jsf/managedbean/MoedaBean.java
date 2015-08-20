package br.com.arvore_societaria_jsf.managedbean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

@ManagedBean
public class MoedaBean {

	private Moeda moeda = new Moeda();
	
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
	
	public ArrayList<Moeda> busca(Moeda moeda) {
		 return null;
	}
	
}
