package br.com.arvore_societaria_jsf.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import br.com.arvore_societaria_jsf.jpautil.JPAUtil;
import br.com.arvore_societaria_jsf.model.Moeda;

public class MoedaDAO extends GenericoDAO<Moeda> {

	public ArrayList<Moeda> buscaTodos() {
		
		try {
			
			em = JPAUtil.getEntityManager();
		
			Query query = em.createQuery("SELECT m FROM Moeda m", Moeda.class);
			
			return (ArrayList<Moeda>) query.getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();	
			return null;
			
		} finally {
			
			em.close();
			
		}
		
	}
	
	public ArrayList<Moeda> buscaAtivas() {
		
		try {
			
			em = JPAUtil.getEntityManager();
			
			Query query = em.createQuery("SELECT m FROM Moeda m WHERE ativa = 1", Moeda.class);
			
			return (ArrayList<Moeda>) query.getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();	
			return null;
			
		} finally {
			
			em.close();
			
		}
		
	}
	
	
}
