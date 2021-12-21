/**
 *
 */
package accdat.UD01.manejoficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import accdat.UD01.manejoficheros.utilidades.Cadenas;

/**
 * Ejercicio 8:
 * Realizar una modificación del ejercicio 6 para que los nombres de los
 * archivos a crear los lea de otro archivo llamado ficheros.txt.
 * @author JESUS
 *
 */
public class Ejercicio8 {

	private static final String FIC_ARCHIVOS = "ficheros.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mensaje = "";

		try(BufferedReader br = new BufferedReader(
				new FileReader(Cadenas.getUbicacion(FIC_ARCHIVOS)))) {
			// Este ejercicio radica en leer línea a línea el fichero. Cada una de ellas será el nombre del
			// fichero a crear.
			String nombreArchivo = "";
			nombreArchivo = br.readLine();

			while (nombreArchivo!=null) {
				File f = new File(Cadenas.getUbicacion(nombreArchivo));

				// Esta versión controlará que no exista un fichero del mismo nombre que vaya a ser machacado por error.
				if(f.exists()) {
					mensaje = "El archivo " + nombreArchivo + " ya existe. No ha podido ser creado";
				}else {
					mensaje = f.createNewFile()?"Archivo " + nombreArchivo + " creado con éxito.":"El archivo " + nombreArchivo + " no ha podido de ser creado.";
				}

				// Indicamos el mensaje de lo sucedido y leemos una nueva línea del fichero de nombres de archivo.
				System.out.println(mensaje);
				nombreArchivo = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}