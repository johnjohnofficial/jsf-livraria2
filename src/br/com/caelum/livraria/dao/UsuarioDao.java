package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.livraria.modelo.Usuario;

@SuppressWarnings("serial")
public class UsuarioDao implements Serializable {
	
	@Inject
	EntityManager manager;

	public boolean existe(Usuario usuario) {

		Query query = manager.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		try {
			query.getSingleResult();
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}

}
