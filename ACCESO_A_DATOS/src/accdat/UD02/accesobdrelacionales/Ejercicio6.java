/**
 *
 */
package accdat.UD02.accesobdrelacionales;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author JESUS
 *
 */
public class Ejercicio6 {
	public static Connection con = null;
	public static String insert = 
			"INSERT INTO ALUMNOS (nombre, apellido1, apellido2, email, edad) "
			+ "VALUES (?, ?, ?, ?, ?);";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Obtenermos una instancia de un objeto que implementa la interface statement.
			PreparedStatement pst = obtenerPreparedStatement(insert);
			
			Scanner sc = new Scanner(System.in);
			
			String nombre, apellido1, apellido2, email;
			int edad; 
			
			System.out.println("Introduzca el nombre: ");
			nombre = sc.nextLine();
			System.out.println("Introduzca el apellido1: ");
			apellido1 = sc.nextLine();
			System.out.println("Introduzca el apellido2: ");
			apellido2 = sc.nextLine();
			System.out.println("Introduzca el email: ");
			email = sc.nextLine();
			System.out.println("Introduzca la edad: ");
			edad = Integer.parseInt(sc.nextLine());
			
			pst.setString(1, nombre);
			pst.setString(2, apellido1);
			pst.setString(3, apellido2);
			pst.setString(4, email);
			pst.setInt(5, edad);
			
			int resultado = pst.executeUpdate();
			
			if (resultado==1) {
				System.out.println("Alumno insertado con éxito.");
			}else {
				System.out.println("Ha ocurrido algún error en la inserción del alumno.");
			}

			//Cerramos Statement
			pst.close();

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
	 * Método estático que establece una conexión con la base de datos PruebaConexionBD y devuelve un objeto PreparedStatement con el sent pasado como parámetro.
	 * @param sent
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement obtenerPreparedStatement(String sent) throws SQLException{
		// Si trabajaramos con JDBC < 4.0 tendríamos que indicar esta línea
		// para indicar el tipo de driver que tiene que cargar DriverManager.
		// Class.forName("com.mysql.jdbc.Driver");

		// Obtenemos la conexión a partir de la URL jdbc correspondiente
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PruebaConexionBD", "root", "");

		// Obtenermos una instancia de un objeto que implementa la interface statement.
		PreparedStatement pst = con.prepareStatement(sent);
		
		return pst;
	}

}
