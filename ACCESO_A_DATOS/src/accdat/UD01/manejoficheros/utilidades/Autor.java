/**
 *
 */
package accdat.UD01.manejoficheros.utilidades;

import java.io.Serializable;

/**
 * Clase necesaria para la realización del ejercicio 11
 * @author JESUS
 *
 */
public class Autor implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String nombre;
	String[] apellidos;

	public Autor(String parte) {
		String[] datos = parte.split(" ");

		nombre = datos[0];
		if (datos.length>1) {
			apellidos = new String[2];
			apellidos[0] = datos[1];
			if (datos.length==3) {
				apellidos[1] = datos[2];
			}else {
				apellidos[1] = "";
			}
		}
	}
}