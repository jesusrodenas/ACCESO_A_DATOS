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
			
			ResultSet rs = st.executeQuery();

			// De esta forma vamos a saber si hay tablas o no
			boolean hayFilas = false;
			while(rs.next()) {
				hayFilas = true;
				System.out.println(rs.getString("APELLIDO1") + " " + rs.getString("APELLIDO2") + ", " + rs.getString("NOMBRE"));
			}
			if (!hayFilas) {
				System.out.println("No hay resultados que mostrar");
			}

			//Cerramos ResultSet y Statement
			rs.close();
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