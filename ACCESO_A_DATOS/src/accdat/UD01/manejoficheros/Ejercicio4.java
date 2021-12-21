/**
 *
 */
package accdat.UD01.manejoficheros;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import accdat.UD01.manejoficheros.utilidades.Cadenas;

/**
 * Ejercicio 4:
 * Escribir un fichero de bytes con un mensaje (no será legible) y enviárselo a un compañero para que lo "descifre" mediante la lectura, 
 * transformación y escritura por pantalla del mismo.
 *
 * @author JESUS
 *
 */
public class Ejercicio4 {

	private static final String FIC_OCULTO = "mensaje_oculto.dat";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mensaje = "Este es el mensaje a ocultar";

		ocultarMensaje(mensaje);
		descifrarMensaje(FIC_OCULTO);
	}

	public static void ocultarMensaje(String mensaje) {
		try (BufferedOutputStream bout =
				new BufferedOutputStream(new FileOutputStream(Cadenas.getUbicacion(FIC_OCULTO)))){

			bout.write(mensaje.getBytes());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void descifrarMensaje(String nombreFichero) {
		String leido = null;
		// Utilizo la utilidad de InputStreamReader que recibe un flujo binario y lo convierte en un flujo de caracteres.
		try(BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(Cadenas.getUbicacion(FIC_OCULTO))))){
			while ((leido = br.readLine())!=null){
				System.out.println(leido);
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