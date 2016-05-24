package br.com.arvore_societaria_jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.arvore_societaria_jsf.bean.Moeda;
import br.com.arvore_societaria_jsf.dao.MoedaDAO;

@FacesConverter(forClass = Moeda.class)
public class MoedaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigoString) {

		if(codigoString != null && !codigoString.trim().isEmpty()) {

			MoedaDAO moedaDAO = new MoedaDAO();
			
			Long codigo = Long.parseLong(codigoString);
			
			Moeda moeda = moedaDAO.buscaPorID(Moeda.class, codigo);;
			
			return moeda;
			
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		
		if(value instanceof Moeda) {
			
			Moeda moeda = (Moeda) value;
			
			return moeda.getId().toString();
			
		}
		
		return null;
		
	}

	
	
}
