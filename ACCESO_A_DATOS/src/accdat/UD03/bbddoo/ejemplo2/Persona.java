package accdat.UD03.bbddoo.ejemplo2;

import java.util.ArrayList;


public class Persona {
	private String nombre;
	private int edad;
	private ArrayList<Coche> coches=new ArrayList<Coche>();
	private Perro perro;
	
	
	public Persona(String nombre, int edad) {
		super();
		this.nombre = nombre;
		this.edad = edad;
	}
	
	public ArrayList<Coche> getCoches() {
		return coches;
	}
	public void setCoches(ArrayList<Coche> coches) {
		this.coches = coches;
	}
	
	
	public Perro getPerro() {
		return perro;
	}
	public void setPerro(Perro perro) {
		this.perro = perro;
	}
	public Persona(){
		
	}
	
	@Override
	public String toString() {
		String cadena="Persona [nombre=" + nombre + ", edad=" + edad + "]" + "Coches" + coches ;
		if (perro!=null)
			cadena= cadena + "Perro " + perro;
		return cadena;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public void addCoche(Coche c) {
		coches.add(c);
	}
	
	
	
}
