/**
 *
 */
package accdat.UD01.manejoficheros;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import accdat.UD01.manejoficheros.utilidades.Cadenas;

/**
 * Ejercicio 2.
 *
 * Implementar una clase Java cuyo método main copie el contenido de un
 * fichero "origen.txt" en un fichero "destino.txt"
 *
 * @author JESUS
 *
 */
public class Ejercicio3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String leido, ficOrigen, ficDestino = null;
		Scanner sc = null;

		// PrintWriter para el fichero de destino.
		PrintWriter pw = null;

		// Variable que nos permitirá repetir la operación de solicitar nombre de origen mientras no se inserte uno válido.
		// Se inicializa a falso para entrar la primera vez en el programa.
		boolean existe = false;

		while (!existe) {
			System.out.println("Introduzca la ruta de origen: ");

			sc = new Scanner(System.in);
			ficOrigen = sc.nextLine();

			try (BufferedReader br = new BufferedReader(new FileReader(Cadenas.getUbicacion(ficOrigen)))){
				// Ha conseguido crear el flujo
				existe = true;

				System.out.println("Introduzca la ruta de destino: ");
				ficDestino = sc.nextLine();

				sc.close();

				pw = new PrintWriter (new FileWriter(Cadenas.getUbicacion(ficDestino)));
				while((leido=br.readLine())!=null) {
					pw.println(leido);
				}

			} catch (FileNotFoundException e) {
				// El fichero no existe.
				existe = false;
				System.out.println(" ************** El fichero no existe. Repita la operación ********************");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (pw!=null) {
					pw.close();
				}
				System.out.println("Operación finalizada. Archivo copiado en: " + Cadenas.getUbicacion(ficDestino));
			}
		}
	}

}
