package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
@SuppressWarnings("serial")
public class GerenciandoTransacao implements Serializable {
	
	@Inject
	EntityManager manager;
	
	@AroundInvoke
	public Object transacao(InvocationContext contexto) throws Exception {
		// abre transacao
		System.out.println("[ INFO ] iniciando uma transação.");
		manager.getTransaction().begin();

		// dar segmento na transação
		Object resultado = contexto.proceed();

		// commita a transacao
		System.out.println("[ INFO ] finalizando uma transação.");
		manager.getTransaction().commit();
		
		return resultado;
	}
}
