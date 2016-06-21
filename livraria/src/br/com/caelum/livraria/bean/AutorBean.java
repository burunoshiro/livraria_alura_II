package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Autor autor = new Autor();
	private Integer autorId;
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Autor getAutor() {
		return autor;
	}

	public void carregarAutorPeloId() {
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}
	
	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() != null && this.autor.getId().intValue() > 0) {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		else {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		}
	}
	
	public void remover(Autor autor){
		new DAO<Autor>(Autor.class).remove(autor);
	}
	
	public List<Autor> getListaAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void alterar(Autor autor) {
		System.out.println("Alterando autor");
		this.autor = autor;
	}
}
