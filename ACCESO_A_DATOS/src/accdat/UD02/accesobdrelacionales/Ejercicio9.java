/**
 *
 */
package accdat.UD02.accesobdrelacionales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author JESUS
 *
 */
public class Ejercicio9 {
public static Connection con = null;
	
	public static String consulta_codigo_modulo = 
			" SELECT * FROM MODULO WHERE CODIGO = ? ";
	public static String inserta_modulo = 
			" INSERT INTO MODULO (CODIGO,DESCRIPCION) VALUES (?, ?)";
	public static String consulta_id_alumno = 
			" SELECT * FROM ALUMNO WHERE ID = ? ";
	public static String consulta_matricula = 
			" SELECT 1 FROM MATRICULA WHERE ID = ? AND CODIGO = ? ";
	public static String inserta_matricula = 
			" INSERT INTO MATRICULA VALUES (?, ?) ";

	/**
	 * Muestra el menú y ejecuta la opción seleccionada mientras ésta no sea salir.
	 * @param args
	 */
	public static void main(String[] args) {
		int opc = -1;
		do {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PruebaConexionBD", "root", "");
				opc = muestraMenu();
				tratarOpcion(opc, con);				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (con != null)
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}while(opc!=3);
		System.out.println("¡Hasta la próxima!");
	}
	
	/**
	 * Muestra las distintas opciones.
	 * @return Opción seleccionada por el usuario.
	 * @throws SQLException
	 */
	public static int muestraMenu() throws SQLException {
		Scanner sc = new Scanner(System.in);
		int opc = 0;
		System.out.println("Seleccione la operación a realizar: ");
		System.out.println("1. Insertar un módulo profesional.");
		System.out.println("2. Matricular un alumno en un módulo profesional");
		System.out.println("3. Salir");
		opc = Integer.parseInt(sc.nextLine());
		return opc;
		
	}
	
	/**
	 * Llama a los métodos que realizan cada una de las opciones.
	 * @param opc
	 * @param c
	 * @throws SQLException
	 */
	public static void tratarOpcion(int opc, Connection c) throws SQLException {
		switch(opc) {
		case 1:
			insertarModulo(c);
			break;
		case 2:
			matricularAlumno(c);
			break;		
		}
	}
	
	/**
	 * Comprueba que el módulo seleccionado no esté registrado y lo inserta en caso de que no exista.
	 * @param c
	 * @throws SQLException
	 */
	public static void insertarModulo(Connection c) throws SQLException {
		PreparedStatement pstConsulta = c.prepareStatement(consulta_codigo_modulo);
		PreparedStatement pstInserta = c.prepareStatement(inserta_modulo);
		ResultSet rsConsultaModulo = null;
		
		Scanner sc = new Scanner(System.in);
		String codigo = null;
		String descripcion = null;
		
		boolean existe = true;
		while (existe) {
			System.out.println("Introduzca el código del módulo:");
			codigo = sc.nextLine();
			pstConsulta.setString(1, codigo);
			rsConsultaModulo = pstConsulta.executeQuery();
			existe = rsConsultaModulo.next();
			
			if(existe) {
				System.out.println("El código introducido ya existe en la relación de módulos, por favor repita la operación introduciendo un nuevo código.");
			}
		}
		
		System.out.println("Introduzca la descripción del módulo:");
		descripcion = sc.nextLine();
		
		pstInserta.setString(1, codigo);
		pstInserta.setString(2, descripcion);
		
		pstInserta.executeUpdate();
		
		System.out.println("Módulo insertado con éxito.");
		
		rsConsultaModulo.close();
		pstConsulta.close();
		pstInserta.close();
	}
	
	/**
	 * Comprueba la existencia del alumno cuyo ID es el insertado.
	 * Comprueba que el módulo indicado exista.
	 * Comprueba que la matrícula no exista previamente. 
	 * Si todas estas condiciones se dan, registra la matrícula.
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public static void matricularAlumno(Connection c) throws SQLException {
		PreparedStatement pstConsultaAlumno = c.prepareStatement(consulta_id_alumno);
		PreparedStatement pstConsultaModulo = c.prepareStatement(consulta_codigo_modulo);
		PreparedStatement pstConsultaMatricula = c.prepareStatement(consulta_matricula);
		
		ResultSet rsConsultaAlumno = null;
		ResultSet rsConsultaModulo = null;
		ResultSet rsCompruebaMatricula = null;
		
		PreparedStatement pstInsertaMatricula = c.prepareStatement(inserta_matricula);
		
		Scanner sc = new Scanner(System.in);
		int id = -1;
		String codigoModulo = null;
		String continuar = "";
		
		boolean existeAlumno = false;
		boolean existeModulo = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el id del alumno:");
			id = Integer.parseInt(sc.nextLine());
			pstConsultaAlumno.setInt(1, id);
			rsConsultaAlumno = pstConsultaAlumno.executeQuery();
			existeAlumno = rsConsultaAlumno.next();
			
			if(!existeAlumno) {
				System.out.println("No existe en la BD ningún alumno con el ID indicado. Por favor, repita la operación indicando un ID válido.");
			}else {
				System.out.println("Los datos del alumno indicado son:");				
				System.out.println("Nombre: " + rsConsultaAlumno.getString("nombre"));
				System.out.println("Apellido1: " + rsConsultaAlumno.getString("apellido1"));
				System.out.println("Apellido2: " + rsConsultaAlumno.getString("apellido2"));
				System.out.println("email: " + rsConsultaAlumno.getString("email"));
				System.out.println("edad: " + rsConsultaAlumno.getInt("edad"));
			}
		}
		
		while (!existeModulo) {
			System.out.println("Introduzca el código del módulo:");
			codigoModulo = sc.nextLine();
			pstConsultaModulo.setString(1, codigoModulo);
			rsConsultaModulo = pstConsultaModulo.executeQuery();
			existeModulo = rsConsultaModulo.next();
			
			if(!existeModulo) {
				System.out.println("No existe en la BD ningún módulo con el código indicado. Por favor, repita la operación indicando un código válido.");
			}else {
				
				pstConsultaMatricula.setInt(1, id);
				pstConsultaMatricula.setString(2, codigoModulo);
				
				rsCompruebaMatricula = pstConsultaMatricula.executeQuery();
				
				if (rsCompruebaMatricula.next()) {
					System.out.println("La matrícula que desea registrar ya existe en el sistema");
				}else {
					System.out.println("Va a insertar al alumno indicado anteriormente en el módulo " + rsConsultaModulo.getString("descripcion") + ".");
				}					
			}
		}		
		
		System.out.println("¿Desea continuar? (S/N)");
		continuar = sc.nextLine();
		if ("S".equals(continuar)) {			
			pstInsertaMatricula.setInt(1, id);
			pstInsertaMatricula.setString(2, codigoModulo);
			
			pstInsertaMatricula.executeUpdate();
			
			System.out.println("Matrícula registrada con con éxito.");			
		}
		
		pstConsultaAlumno.close();
		pstConsultaModulo.close();
		pstConsultaMatricula.close();
		
		rsConsultaAlumno.close();
		rsConsultaModulo.close();
		rsCompruebaMatricula.close();
		
	}
}
