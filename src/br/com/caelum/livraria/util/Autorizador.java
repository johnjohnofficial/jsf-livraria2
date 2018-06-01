package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public void afterPhase(PhaseEvent event) {
			
		FacesContext context = event.getFacesContext();
		String pagina = context.getViewRoot().getViewId();
		
		if ("/login.xhtml".equals(pagina)) {
			return;
		} 
		
		Usuario usuario = (Usuario) context.getExternalContext()
										.getSessionMap().get("usuarioLogado");
				
		if (usuario != null) {
			return;
		}
		
		// navegação programaticamente com o JSF
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "login?faces-redirect=true");
		
		// renderizando o contexto para devolver ao navegador
		context.renderResponse();
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
