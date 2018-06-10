package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class LoginBean implements Serializable {

	@Inject
	private Usuario usuario;
	
	@Inject
	private UsuarioDao dao;
	
	@Inject
	FacesContext context;
	
	public LoginBean() {
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public RedirectView efetuaLogin() {
		System.out.println("Efetuando login " + this.usuario.getEmail());

		boolean existe = this.dao.existe(this.usuario);
		
		if (existe) {
			context.getExternalContext().getSessionMap()
				.put("usuarioLogado", this.usuario);						
			return new RedirectView("livro");
		}		
		
		// context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário ou senha inválido!"));
		this.usuario = new Usuario();
		
		return null;
	}
	
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap()
			.remove("usuarioLogado");			
		
		return "login?faces-redirect=true";
	}
}
