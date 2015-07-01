package br.com.arvore_societaria_jsf;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteHibernate {
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("arvore_societaria_jsf");
		
		EntityManager em = emf.createEntityManager();
		
		Moeda moeda = new Moeda();
		
		moeda.setNome("real");
		moeda.setPais("Brasil");
		
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		
		em.persist(moeda);
		
		et.commit();
		
		System.out.println("Moeda salva");
		
		
		
	}

}
