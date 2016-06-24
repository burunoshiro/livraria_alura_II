package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Livro livro = new Livro();
	private List<Livro> listaLivros = null; //new ArrayList<Livro>();
	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor"));
		}
		
		DAO<Livro> daoLivro = new DAO<Livro>(Livro.class);
		
		if(livro.getId() != null && livro.getId().intValue() > 0) {
			System.out.println("Atualizando livro");
			daoLivro.atualiza(this.livro);
		}
		else {
			System.out.println("Salvando livro");
			daoLivro.adiciona(this.livro);
		}
		
		this.listaLivros = daoLivro.listaTodos();
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		livro.adicionaAutor(autor);
	}

	public List<Autor> getAutoresDoLivro() {
		return livro.getAutores();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}

	public List<Livro> getListaLivros() {
		
		if(listaLivros == null) {
			DAO<Livro> daoLivro = null;
			daoLivro = new DAO<Livro>(Livro.class);
			listaLivros = daoLivro.listaTodos();
		}
		
		return listaLivros;
	}

	public String formAutor() {
		System.out.println("Chamando form autor");
		return "autor?faces-redirect=true";
	}

	public void removerLivro(Livro livro) {
		System.out.println("Removendo livro");
		new DAO<Livro>(Livro.class).remove(livro);
	}

	public void alterarLivro(Livro livro) {
		System.out.println("Buscando livro para alteração");
		this.livro = livro;
	}

	public void removerAutor(Autor autor) {
		System.out.println("Removendo autor: " + autor.getNome());
		this.livro.getAutores().remove(autor);
	}

	public void setListaLivros(List<Livro> listaLivros) {
		this.listaLivros = listaLivros;
	}
}
