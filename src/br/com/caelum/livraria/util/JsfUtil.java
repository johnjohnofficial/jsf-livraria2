package br.com.caelum.livraria.util;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
public class JsfUtil implements Serializable {
	
	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
