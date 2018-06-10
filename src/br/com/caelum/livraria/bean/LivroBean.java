package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private List<Livro> livros;
	
	@Inject
	private LivroDataModel livroDataModel;
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");
	
	@Inject
	private AutorDao autorDao;
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private FacesContext context;

	public Livro getLivro() {
		return livro;
	}
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public List<Autor> getAutores() {
		List<Autor> autores = this.autorDao.listaTodos();
		
		return autores;
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			context.addMessage("message", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}
		
		if (this.livro.getId() == null) {
			this.livroDao.adiciona(this.livro);
		} else {
			this.livroDao.atualiza(this.livro);
		}

		// Carregamos a lista de livros novamente.
		this.livros = this.livroDao.listaTodos();
		this.livro = new Livro();
	}
	
	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo o livro: " + livro.getTitulo());
		this.livroDao.remove(livro);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando o livro: " + livro.getTitulo());
//		this.livro = livro;
		this.livro = livroDao.buscaPorId(livro.getId());
	}
	
	public void removerAutorDoLivro(Autor autor) {
		System.out.println("Retirando o Autor do livro: " + autor.getNome());
		this.livro.removerAutor(autor);
	}
	
	@Transacional
	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public void carregaLivroPorId() {
		this.livro = this.livroDao.buscaPorId(this.livroId);
		
		if (this.livro == null) {
			this.livro = new Livro();
		}
	}
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();
		}
		
		return this.livros;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
//		String valor = value.toString();
		
//		if(!valor.startsWith("1")) {
//			throw new ValidatorException(new FacesMessage("O campo ISBN deveria começar com caracter 1"));
//		}
	}
	
	public String formAutor() {
		return "autor?faces-redirect=true";
	}
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

        //tirando espaços do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        // o filtro é nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela é nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
}
}
