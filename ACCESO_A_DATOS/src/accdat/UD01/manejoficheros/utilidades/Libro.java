/**
 *
 */
package accdat.UD01.manejoficheros.utilidades;

import java.io.Serializable;

/**
 * Clase necesaria para el ejercicio 11
 * @author JESUS
 *
 */
public class Libro implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8912577566218352429L;
	private String isbn, titulo;
	private int numEjemplares;
	private Autor autor;

	/**
	 * @param isbn
	 * @param titulo
	 * @param numEjemplares
	 * @param autor
	 */
	public Libro(String isbn, String titulo, Autor autor, int numEjemplares) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.numEjemplares = numEjemplares;
		this.autor = autor;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the numEjemplares
	 */
	public int getNumEjemplares() {
		return numEjemplares;
	}
	/**
	 * @param numEjemplares the numEjemplares to set
	 */
	public void setNumEjemplares(int numEjemplares) {
		this.numEjemplares = numEjemplares;
	}
	/**
	 * @return the autor
	 */
	public Autor getAutor() {
		return autor;
	}
	/**
	 * @param autor the autor to set
	 */
	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String mensaje = "Libro: \n";
		mensaje       += " - Título: " + titulo + "\n";
		mensaje       += " - Autor: " + autor.nombre;
		if (autor.apellidos!=null) {
			mensaje   += " " + autor.apellidos[0] + autor.apellidos[1]+ "\n";
		}else {
			mensaje   += "\n";
		}
		mensaje       += " - Núm ejemplares: " + numEjemplares + "\n";

		return mensaje;
	}


}
