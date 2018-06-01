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
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
				.put("usuarioLogado", this.usuario);						
			return new RedirectView("livro");
		}		
		
		this.usuario = new Usuario();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Usuário ou senha inválido!"));
		
		return null;
	}
	
	
	public String logoff() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
				
		if (usuario != null) {
			context.getExternalContext().getSessionMap().clear();
		}
		
		return "login?faces-redirect=true";
	}
}
