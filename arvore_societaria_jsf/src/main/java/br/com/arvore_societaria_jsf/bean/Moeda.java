package br.com.arvore_societaria_jsf.bean;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import br.com.arvore_societaria_jsf.dao.IEntidadeDAO;

@Entity
public class Moeda implements IEntidadeDAO{

	@GeneratedValue @Id
	private Long id;
	private String nome;
	private String pais;
	private Boolean ativa;
	
	@Transient
	private String situacao;
	
	public String getSituacao() {
		
		if(ativa) {
			situacao = "ativa";
		}
		else {
			situacao = "inativa";
		}
		
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Boolean getAtiva() {
		return ativa;
	}
	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
	
}
