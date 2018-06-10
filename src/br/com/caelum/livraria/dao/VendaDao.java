package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Venda;

@SuppressWarnings("serial")
public class VendaDao implements Serializable {
	
	@Inject
	EntityManager manager;
	
	public List<Venda> vendas() {
		String jpql = "select v from Venda v";
		TypedQuery<Venda> query = manager.createQuery(jpql, Venda.class);
		return query.getResultList();
	}
}
