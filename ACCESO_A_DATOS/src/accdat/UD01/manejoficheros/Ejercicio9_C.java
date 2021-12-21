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

import accdat.UD01.manejoficheros.utilidades.Alumno;

/**
 * @author JESUS
 *
 */
public class Ejercicio9_C {

	public static final String FICHERO_ALUMNOS = "ficheros/alumnos.csv";
	public static final String FICHERO_SCRIPTS = "ficheros/script.sql";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int pk = 255;
		try {
			generaScript(pk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Genera tantos insert como líneas de alumnos hay en el fichero.
	 *
	 * @param idAlumno Recibe la primera clave del script.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void generaScript(int idAlumno) throws FileNotFoundException, IOException {
		try (FileReader flujoLectura = new FileReader(FICHERO_ALUMNOS);
		BufferedReader filtroLectura = new BufferedReader(flujoLectura)){
			PrintWriter pw = new PrintWriter(new FileWriter(FICHERO_SCRIPTS));

			String linea = filtroLectura.readLine();
			while (linea!=null) {
				Alumno alumno = Alumno.procesaLinea(linea);
				pw.println(alumno.toString_sql(idAlumno));
				linea = filtroLectura.readLine();

				if(linea!=null)
					idAlumno++;
			}
			pw.println("COMMIT;");
			pw.write("/");
			pw.close();

			System.out.println("Último índice insertado: " + idAlumno);
		}
	}
}