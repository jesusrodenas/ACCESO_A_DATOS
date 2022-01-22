package accdat.UD03.bbddoo.ejemplo1;

/**
 * 
 * @author JESUS
 *
 */
public class Persona {
	private String dni;
	private String nombre;
	private int edad;
	public Persona(String dni, String nombre, int edad) {
		this.dni= dni;
		this.nombre = nombre;
		this.edad = edad;
	}
//	
	public Persona() {
		
	}
	@Override
	public String toString() {
		return "Persona DNI = " + dni + " Nombre=" + nombre + ", edad=" + edad + "]";
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
	
//	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}	
}
