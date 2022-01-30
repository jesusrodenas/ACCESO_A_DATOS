package accdat.UD03.bbddoo.ejemplo3;

import java.util.ArrayList;
import java.util.Arrays;

public class Persona {
	private String nombre;
	private int edad;
	private ArrayList<Coche> coches=new ArrayList<Coche>();
	
	
	Persona(){
		
	}
	public Persona(String nombre, int edad) {
		super();
		this.nombre = nombre;
		this.edad = edad;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + "]" + "Coches" + coches;
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
