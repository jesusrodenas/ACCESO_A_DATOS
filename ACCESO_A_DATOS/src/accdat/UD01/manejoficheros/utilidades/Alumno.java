/**
 *
 */
package accdat.UD01.manejoficheros.utilidades;

/**
 * @author JESUS
 *
 */
public class Alumno implements Comparable<Alumno>{
	private String nombre, apellido1, apellido2;
	private int edad;

	/** Constructor parametrizado.
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param edad
	 */
	public Alumno(String nombre, String apellido1, String apellido2, int edad) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.edad = edad;
	}

	/***************** GETTERS Y SETTERS ********************/

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	/**
	 * @return the edad
	 */
	public int getEdad() {
		return edad;
	}
	/**
	 * @param edad the edad to set
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/************* FIN GETTERS Y SETTERS ********************/

	/**
	 * Método estático que recibe una líne en formato csv y genera un objeto alumno.
	 * @param linea línea de fichero csv de formato "apellido1;apellido2;nombre;edad"
	 * @return Objeto alumno con la información.
	 */
	public static Alumno procesaLinea(String linea) {
		String[] datos = linea.split(";");
		Alumno alu = new Alumno(datos[2], datos[0], datos[1], Integer.valueOf(datos[3]));
		return alu;
	}

	@Override
	/**
	 * Implementación del comparable.
	 *
	 * Criterios de ordenación:
	 * 1. apellido1
	 * 2. apellido2
	 * 3. nombre
	 * 4. edad
	 *
	 */
	public int compareTo(Alumno alu2) {
		int compare = this.getApellido1().compareTo(alu2.getApellido1());

		if (compare==0) {
			compare = this.getApellido2().compareTo(alu2.getApellido2());
		}

		if (compare==0) {
			compare = this.getNombre().compareTo(alu2.getNombre());
		}

		if (compare==0) {
			compare = this.getEdad() - alu2.getEdad();
		}
		return compare;
	}

    @Override
    /**
     * Implementamos el método equals de la clase Alumno
     */
    public boolean equals(Object obj) {
    	Alumno aux = (Alumno) obj;

    	return (aux.nombre.equals(this.nombre) && aux.apellido1.equals(this.apellido1) && aux.apellido2.equals(this.apellido2) && (aux.edad==this.edad));
    }

    @Override
    /**
     * Implementación del método toString para la salida por pantalla del apartado a.
     */
    public String toString() {
    	String salida = "Alumno {Apellidos: " + this.apellido1 + " " + this .apellido2;
    	salida += ", Nombre: " + this.nombre + ", Edad: " + this.edad;

    	return salida;
    }

    /**
     * Método que obtendrá la línea de fichero csv de un objeto alumno.
     *
     * @return  línea de fichero csv de formato "apellido1;apellido2;nombre"
     */
    public String toString_csv() {
    	return apellido1 + ";" + apellido2 + ";" + nombre + ";" + edad;
    }

    public String toString_sql(int idAlumno) {
    	String insert = "INSERT INTO ALUMNOS ( ID_ALUMNO, APELLIDO1, APELLIDO2, NOMBRE, EDAD) ";
    	insert       += "VALUES ( " + idAlumno + ", '" + apellido1 + "', '" + apellido2 + "', '" + nombre + "', " + edad + ");";

    	return insert;
    }
}
