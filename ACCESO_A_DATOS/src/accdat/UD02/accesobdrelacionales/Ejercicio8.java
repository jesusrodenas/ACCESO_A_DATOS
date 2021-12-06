/**
 * 
 */
package accdat.UD02.accesobdrelacionales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JESUS
 *
 */
public class Ejercicio8 {
	public static void main(String[] args) {

		Connection con = null;

		try {
			// Si trabajaramos con JDBC < 4.0 tendríamos que indicar esta línea
			// para indicar el tipo de driver que tiene que cargar DriverManager.
			// Class.forName("com.mysql.jdbc.Driver");

			// Obtenemos la conexión a partir de la URL jdbc correspondiente
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PruebaConexionBD", "root", "");

			// Obtenermos una instancia de un objeto que implementa la interface statement.
			String sql = "SELECT * FROM ALUMNOS WHERE ID = ?";
			PreparedStatement st = con.prepareStatement(sql);	
			
			st.setInt(1, 50);
			// No podremos adulterar la vulnerabilidad de la BD como sí hacíamos en el ejercicio anterior.
			//st.setString(1, "-1 or 1=1");
			
			ResultSet infoAlumno = st.executeQuery();

			// De esta forma vamos a saber si hay tablas o no
			boolean hayFilas = false;
			while (infoAlumno.next()) {
				hayFilas = true;
				System.out.println("************");
				System.out.println("Nombre: " + infoAlumno.getString("nombre"));
				System.out.println("Apellido1: " + infoAlumno.getString("apellido1"));
				System.out.println("Apellido2: " + infoAlumno.getString("apellido2"));
				System.out.println("email: " + infoAlumno.getString("email"));
				System.out.println("edad: " + infoAlumno.getInt("edad"));
				
			}
			if (!hayFilas) {
				System.out.println("No hay resultados que mostrar");
			}

			//Cerramos ResultSet y Statement
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
}