package br.com.arvore_societaria_jsf.jpautil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("arvore_societaria_jsf");
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}
