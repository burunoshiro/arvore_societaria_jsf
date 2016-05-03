package br.com.arvore_societaria_jsf.dao;

import javax.persistence.EntityManager;

import br.com.arvore_societaria_jsf.enums.ResultadoOperacao;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

public class GenericoDAO<T extends IEntidadeDAO> {

	protected EntityManager em;
	
	public ResultadoOperacao salva(T objeto) {

		ResultadoOperacao resultado;
		
		try {
			
			em = JPAUtil.getEntityManager();
			
			em.getTransaction().begin();

			if(objeto.getId() == null) {
				
				em.persist(objeto);
				
				resultado = ResultadoOperacao.salvo;
				
			}
			else {

				em.merge(objeto);	
				
				em.detach(objeto);

				resultado = ResultadoOperacao.alterado;
				
			}

			em.getTransaction().commit();
			
		} catch(Exception e) {

			e.printStackTrace();
			
			em.getTransaction().rollback();
			
			resultado = ResultadoOperacao.erro;

		} finally {
			
			em.close();
			
		}
		
		return resultado;
	}

	public void remove(T objeto) throws Exception{
		
		try {
			
			em = JPAUtil.getEntityManager();
			
			em.getTransaction().begin();
			
			if(objeto.getId() != null) {
				
				T objeto_aux = em.merge(objeto);
				
				em.remove(objeto_aux);
				
			}
			
			em.getTransaction().commit();
			
		} catch(Exception e) {
			
			em.getTransaction().rollback();
			
			throw new Exception("Erro ao tentar remover moeda!"); 
		}
		finally {
			
			em.close();
			
		}
		
	}
	
	public T buscaPorID(Class<T> classe, Long id){
		
		try {
			
			em = JPAUtil.getEntityManager();
			
			if(id >= 0) {
				
				return em.find(classe, id); 
				
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
}
