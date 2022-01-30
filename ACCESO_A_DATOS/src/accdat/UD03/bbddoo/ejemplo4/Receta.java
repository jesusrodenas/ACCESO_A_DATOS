package accdat.UD03.bbddoo.ejemplo4;

import java.util.LinkedList;

public class Receta {
	private String nombreReceta;
	private int dificultad;
	private LinkedList<Ingrediente> ingredientes;
	private LinkedList<String> pasos;
	
	public Receta() {
		
	}
	
	//Constructor para patron
	public Receta( int dificultad) {
		this.dificultad = dificultad;
	}
	
	//Constructor para patron
	public Receta(String nombreReceta) {
		this.nombreReceta=nombreReceta;
	}
	public Receta(String nombreReceta, int dificultad) {
		super();
		this.nombreReceta = nombreReceta;
		this.dificultad = dificultad;
		ingredientes=new LinkedList<Ingrediente>();
		pasos=new LinkedList<String>();
	}

	public String getNombreReceta() {
		return nombreReceta;
	}

	public void setNombreReceta(String nombreReceta) {
		this.nombreReceta = nombreReceta;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}
	
	public int numeroIngredientes() {
		return ingredientes.size();
	}
	/**
	 * Metodo para saber si una receta tiene ese ingrediente
	 * @param ingrediente
	 * @return
	 */
	public boolean contieneEsteIngrediente(String ingrediente) {
		return ingredientes.contains(new Ingrediente(ingrediente));
	}
	/**
	 * Metodo para añadir ingredientes
	 * @param ingredienteNuevo
	 */
	public void annadirIngrediente(Ingrediente ingredienteNuevo) {
		ingredientes.add(ingredienteNuevo);
	}
	
	/**
	 * Método para añadir pasos
	 * @param paso
	 */
	public void annadirPaso(String paso) {
		pasos.add(paso);
	}
	
	@Override
	public String toString() {
		return "Receta [nombreReceta=" + nombreReceta + ", dificultad=" + dificultad + ", ingredientes=" + ingredientes
				+ ", pasos=" + pasos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreReceta == null) ? 0 : nombreReceta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receta other = (Receta) obj;
		if (nombreReceta == null) {
			if (other.nombreReceta != null)
				return false;
		} else if (!nombreReceta.equals(other.nombreReceta))
			return false;
		return true;
	}
	
	
	

	
}
