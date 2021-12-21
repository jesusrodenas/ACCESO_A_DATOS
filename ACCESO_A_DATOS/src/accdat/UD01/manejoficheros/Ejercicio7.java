/**
 *
 */
package accdat.UD01.manejoficheros;

import java.io.File;
import java.util.Scanner;

import accdat.UD01.manejoficheros.utilidades.Cadenas;

/**
 * @author JESUS
 *
 */
public class Ejercicio7 {

	/**
	 * Ejercicio 7:
	 * Modificación del ejercicio 6 para que los ficheros sean creados unos dentro de otros en forma de directorios.
	 * Es decir, creará una jerarquía de carpetas.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String mensaje = "";

		System.out.println("¿Cuántos directorios quiere crear?");

		// Variable para almacenar el número de ficheros.
		int numFile = sc.nextInt();

		// Variable para almacenar el nombre del fichero.
		String nomFile = "";

		for (int i=1; i<=numFile; i++) {

			// Se crea el objeto de tipo File, abstracción que Java hace de un archivo/directorio.
			System.out.println("Introduzca el nombre del " + i + "º directorio:");

			// Para conseguir que se creen de manera recursiva (una carpeta dentro de otra)
			// en lugar de asignar cadena vacía al nombre de fichero a cada iteración como se hace en el
			// Ejercicio6, lo que se hará será añadir al nombre del fichero anterior una barra "/" y el nuevo nombre
			// introducido por el usuario, consiguiendo así el efecto deseado:
			// - directorio1
			// - directorio1/directorio2
			// - directorio1/directorio2/directorio3
			// ...
			nomFile += "/" + sc.next();

			File f = new File(Cadenas.getUbicacion(nomFile));

			try {
				// Si el fichero es creado con éxito, el mensaje seá satisfactorio.
				// En caso contrario el mensaje informará acerca del error.
				mensaje = f.mkdir()?"El directorio ha sido creado con éxito.":"El directorio no ha podido ser creado.";

			}finally {
				System.out.println(mensaje);
			}

		}

		sc.close();
	}
}
