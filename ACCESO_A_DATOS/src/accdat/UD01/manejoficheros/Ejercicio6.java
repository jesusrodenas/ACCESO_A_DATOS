/**
 *
 */
package accdat.UD01.manejoficheros;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import accdat.UD01.manejoficheros.utilidades.Cadenas;

/**
 * @author JESUS
 *
 */
public class Ejercicio6 {

	/**
	 * Ejercicio 6:
	 * Implementar clase java que solicita al usuario número de ficheros a crear y los nombres de los mismos.
	 * A continuación creará los ficheros indicados.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String mensaje = "";

		System.out.println("¿Cuántos ficheros quiere crear?");

		// Variable para almacenar el número de ficheros.
		int numFile = sc.nextInt();

		for (int i=1; i<=numFile; i++) {
			// Variable para almacenar el nombre del fichero.
			String nomFile = "";

			// Se crea el objeto de tipo File, abstracción que Java hace de un archivo/directorio.
			System.out.println("Introduzca el nombre del " + i + "º fichero:");
			nomFile = sc.next();
			File f = new File(Cadenas.getUbicacion(nomFile));

			try {
				// Si el fichero es creado con éxito, el mensaje será satisfactorio.
				// En caso contrario el mensaje informará acerca del error.
				mensaje = f.createNewFile()?"El archivo ha sido creado con éxito.":"El archivo no ha podido ser creado.";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				System.out.println(mensaje);
			}

		}
		sc.close();
	}
}