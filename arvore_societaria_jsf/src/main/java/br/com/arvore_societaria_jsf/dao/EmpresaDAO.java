package br.com.arvore_societaria_jsf.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import br.com.arvore_societaria_jsf.bean.Empresa;
import br.com.arvore_societaria_jsf.jpautil.JPAUtil;

public class EmpresaDAO extends GenericoDAO<Empresa>{

	public ArrayList<Empresa> buscaTodos() {

		try {
		
			em = JPAUtil.getEntityManager();
			
			Query query = em.createQuery("SELECT e FROM Empresa e", Empresa.class);

			System.out.println("mudanca");
			return (ArrayList<Empresa>) query.getResultList();	
		
		} catch(Exception e) {
			
			e.printStackTrace();
			return null;
			
		} finally {
			
			em.close();
			
		}
		
		
	}

}
