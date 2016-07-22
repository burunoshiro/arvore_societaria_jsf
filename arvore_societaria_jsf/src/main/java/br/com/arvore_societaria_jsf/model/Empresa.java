package br.com.arvore_societaria_jsf.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.arvore_societaria_jsf.dao.IEntidadeDAO;

@Entity
public class Empresa implements IEntidadeDAO{

	@Id @GeneratedValue 
	private Long id;
	
	private String razaoSocial;
	private String pais;
	
	@ManyToOne
	private Moeda moeda;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Moeda getMoeda() {
		return moeda;
	}
	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

}
