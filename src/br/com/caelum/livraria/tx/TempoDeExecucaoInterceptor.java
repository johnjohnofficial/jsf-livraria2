package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
@SuppressWarnings("serial")
public class TempoDeExecucaoInterceptor implements Serializable {

	@AroundInvoke
	public Object log(InvocationContext contexto) throws Exception {
		
		long inicio = System.currentTimeMillis();
		
		String metodo = contexto.getMethod().getName();
		
		Object retorno = contexto.proceed();
		
		long fim = System.currentTimeMillis();
		
		System.out.println("Método executado: " + metodo + " Tempo de execução: " + (fim - inicio));
		
		return retorno;
	}
}
