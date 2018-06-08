package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class AutorBean implements Serializable {

	private Autor autor = new Autor();
	private Integer autorId;
	
	@Inject
	private AutorDao dao;	// CDI faz new AutorDao()
	
	public AutorBean() {
	}
	
	@PostConstruct
	void init() {
		System.out.println("AutorBean está nascendo ...");
	}
	
	@PreDestroy
	void morte() {
		System.out.println("AutoBean está morrendo ...");
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return autor;
	}

	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}

//	public void gravar() {
//	public String gravar() {
	public RedirectView gravar() {
		System.out.println("Gravando autor " + autor.getNome());
		
		if (this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}
		
		this.autor = new Autor();
		
//		return "livro?faces-redirect=true";
		return new RedirectView("livro");
	}
	
	public void remover(Autor autor) {
		System.out.println("Removendo autor: " + autor.getNome());
		this.dao.remove(autor);
	}
	
	public void carregar(Autor autor) {
		System.out.println("Carregando autor: " + autor.getNome());
		this.autor = autor;
	}
	
	public void carregarAutorPelaId() {
		this.autor = this.dao.buscaPorId(this.autorId);
	}
}
