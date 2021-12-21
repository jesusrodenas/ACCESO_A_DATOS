/**
 *
 */
package accdat.UD01.manejoficheros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import accdat.UD01.manejoficheros.utilidades.Cadenas;

/**
 * Ejercicio 1
 *
 * Preparar un fichero de texto plano e implementar una clase Java cuyo método main lea su contenido y lo escriba por pantalla.
 * El fichero está ubicado en ficheros/fichero_txt_plano.txt
 * @author JESUS
 *
 */
public class Ejercicio1 {

	/**
	 * @param args
	 */
	private static final String FICHERO = "fichero_txt_plano.txt";

	public static void main	(String[] args) {
		String leido = null;

		// try con recursos que abre un flujo de lectura desde un fichero y
		// que puede ser leido línea a línea gracias al filtro BufferedReader.
		/*
		 * LA UTILIDAD CADENAS.GETUBICACION ÚNICAMENTE DEVUELVE EL NOMBRE DE LA CARPETA DE FICHEROS MÁS EL DEL FICHERO EN CUESTIÓN.
		 */
		try (BufferedReader br = new BufferedReader(
				new FileReader(Cadenas.getUbicacion(FICHERO)))){

			// lectura línea a línea y su posterior escritura mientras
			// el flujo posea líneas.
			leido = br.readLine();
			while (leido!=null) {
				System.out.println(leido);
				leido = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
