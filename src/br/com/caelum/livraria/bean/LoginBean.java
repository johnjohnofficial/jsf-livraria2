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

		boolean existe = new UsuarioDAO().existe(this.usuario);
		if (existe) {
			return new RedirectView("livro");
		}		
		
		this.usuario = new Usuario();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usu�rio ou senha inv�lido!"));
		
		return null;
	}
}
