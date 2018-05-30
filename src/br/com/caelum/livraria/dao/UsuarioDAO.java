package br.com.caelum.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDAO {

	public boolean existe(Usuario usuario) {

		EntityManager em = new JPAUtil().getEntityManager();
		
		Query query = em.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		try {
			query.getSingleResult();
		} catch(Exception e) {
			return false;
		} finally {
			em.close();
		}
		return true;
	}

}
