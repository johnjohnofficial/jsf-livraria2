package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDAO;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public RedirectView efetuaLogin() {
		System.out.println("Efetuando login " + this.usuario.getEmail());

		FacesContext context = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDAO().existe(this.usuario);
		
		if (existe) {
			context.getExternalContext().getSessionMap()
				.put("usuarioLogado", this.usuario);						
			return new RedirectView("livro");
		}		
		
		// context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usu�rio ou senha inv�lido!"));
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
