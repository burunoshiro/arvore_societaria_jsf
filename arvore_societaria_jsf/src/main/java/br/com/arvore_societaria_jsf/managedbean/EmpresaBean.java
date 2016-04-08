package br.com.arvore_societaria_jsf.managedbean;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.arvore_societaria_jsf.bean.Empresa;

@ManagedBean
@SessionScoped
public class EmpresaBean {

	Empresa empresa = new Empresa();
	
	public void salva() {
		
		empresa = null;
		
	}
	
}
