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
public class Ejercicio7 {
	public static Connection con = null;
	public static String sql = 
			"SELECT * FROM ALUMNOS WHERE ID = ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Obtenermos una instancia de un objeto que implementa la interface statement.
			Statement st = obtenerStatement();
			
			int idAlumno = 50;
			// Si el parámetro es como el siguiente, el uso de Statement provocará vulnerabilidades en la base de datos.
			// String idAlumno = "-1 or 1=1";
						
			ResultSet infoAlumno = st.executeQuery(sql + idAlumno);
			
			while (infoAlumno.next()) {
				System.out.println("************");
				System.out.println("Nombre: " + infoAlumno.getString("nombre"));
				System.out.println("Apellido1: " + infoAlumno.getString("apellido1"));
				System.out.println("Apellido2: " + infoAlumno.getString("apellido2"));
				System.out.println("email: " + infoAlumno.getString("email"));
				System.out.println("edad: " + infoAlumno.getInt("edad"));
			}
			
			//Cerramos			
			infoAlumno.close();
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
