/**
 *
 */
package accdat.UD02.accesobdrelacionales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author JESUS
 *
 */
public class Ejercicio5 {
	public static Connection con = null;
	public static String insert = 
			"INSERT INTO ALUMNOS (nombre, apellido1, apellido2, email, edad) "
			+ "VALUES ('Pepe', 'Pérez', 'Antúnez', 'pepeperez@mail.es', 20);";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Obtenermos una instancia de un objeto que implementa la interface statement.
			Statement st = obtenerStatement();
			
			int resultado = st.executeUpdate(insert);
			
			if (resultado==1) {
				System.out.println("Alumno insertado con éxito.");
			}else {
				System.out.println("Ha ocurrido algún error en la inserción del alumno.");
			}

			//Cerramos Statement
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}
	
	/**
	 * Método estático que establece una conexión con la base de datos PruebaConexionBD y devuelve un objeto Statement.
	 * @return
	 * @throws SQLException
	 */
	public static Statement obtenerStatement() throws SQLException{
		// Si trabajaramos con JDBC < 4.0 tendríamos que indicar esta línea
		// para indicar el tipo de driver que tiene que cargar DriverManager.
		// Class.forName("com.mysql.jdbc.Driver");

		// Obtenemos la conexión a partir de la URL jdbc correspondiente
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PruebaConexionBD", "root", "");

		// Obtenermos una instancia de un objeto que implementa la interface statement.
		Statement st = con.createStatement();
		
		return st;
	}

}
