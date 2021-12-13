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
public class Ejercicio10 {
	public static Connection con = null;
	
	public static String inserta_alumno =			 
			"INSERT INTO ALUMNO (nombre, apellido1, apellido2, email, edad) "
			+ "VALUES (?, ?, ?, ?, ?);";
	public static String consulta_codigo_modulo = 
			" SELECT * FROM MODULO WHERE CODIGO = ? ";
	public static String inserta_modulo = 
			" INSERT INTO MODULO (CODIGO,DESCRIPCION) VALUES (?, ?)";
	public static String consulta_alumno = 
			" SELECT * FROM ALUMNO WHERE NUMESC = ? ";
	public static String consulta_matricula = 
			" select * from matricula m, alumno a  where m.id = a.id and a.numesc = ? and m.codigo = ? ";
	public static String inserta_matricula = 
			" INSERT INTO MATRICULA VALUES (?, ?) ";
	public static String lista_matriculas_alu = 
			" select mo.descripcion from matricula ma, alumno al, modulo mo where ma.id = al.id and ma.CODIGO = mo.CODIGO and al.numesc = ? ";	
	public static String borra_matriculas_alu = 
			" delete from matricula where id = (select id from alumno where numesc = ? )" ;
	public static String lista_todas_matriculas =
			" select " +
	        " concat(apellido1, nvl2(apellido2, ' ', ''), nvl(apellido2, ''), ', ', nombre) as alumno, " + 
			" nvl(MO.DESCRIPCION, 'El alumno no tiene matrículas registradas') as descripcion " +
	    	" from ALUMNO AL " +
	    	" left join MATRICULA MA using(ID) " +
			" left join MODULO MO using(CODIGO) " +
	    	" order by 1, 2 ";

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
		}while(opc!=7);
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
		System.out.println("1. Insertar un alumno.");
		System.out.println("2. Insertar un módulo profesional.");
		System.out.println("3. Matricular un alumno en un módulo profesional.");
		System.out.println("4. Listar los módulos en los que está matriculado un alumno.");
		System.out.println("5. Borrar todas las matrículas de un alumno.");
		System.out.println("6. Listado de alumnos y matrículas.");
		System.out.println("7. Salir");
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
			insertarAlumno(c);
			break;
		case 2:
			insertarModulo(c);
			break;
		case 3:
			matricularAlumno(c);
			break;
		case 4:
			listarMatriculasAlu(c);
			break;
		case 5:
			borrarMatriculasAlu(c);
			break;
		case 6:
			listaTodasMatriculas(c);
			break;	
		}
	}
	
	/**
	 * Comprueba la existencia de un alumno
	 * @param c Conexión para realizar la consulta.
	 * @param numesc del alumno a comprobar
	 * @return true si existe, false en caso contrario
	 * @throws SQLException
	 */
	public static boolean compruebaAlumno(Connection c, int numesc) throws SQLException {
		boolean existeAlumno = false;
		PreparedStatement pstConsultaAlumno = c.prepareStatement(consulta_alumno);
		pstConsultaAlumno.setInt(1, numesc);
		ResultSet rsConsultaAlumno = pstConsultaAlumno.executeQuery();
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
		
		pstConsultaAlumno.close();
		rsConsultaAlumno.close();
		
		return existeAlumno;
	}
	
	/**
	 * Comprueba la existencia de un alumno
	 * @param c Conexión para realizar la consulta.
	 * @param numesc del alumno a comprobar
	 * @return el id del alumno si existe, -1 en caso contrario
	 * @throws SQLException
	 */
	public static int compruebaAlumnoYDevuelveID(Connection c, int numesc) throws SQLException {
		int idAlumno = -1;
		PreparedStatement pstConsultaAlumno = c.prepareStatement(consulta_alumno);
		pstConsultaAlumno.setInt(1, numesc);
		ResultSet rsConsultaAlumno = pstConsultaAlumno.executeQuery();
		
		if(!rsConsultaAlumno.next()) {
			System.out.println("No existe en la BD ningún alumno con el ID indicado. Por favor, repita la operación indicando un ID válido.");
		}else {
			System.out.println("Los datos del alumno indicado son:");				
			System.out.println("Nombre: " + rsConsultaAlumno.getString("nombre"));
			System.out.println("Apellido1: " + rsConsultaAlumno.getString("apellido1"));
			System.out.println("Apellido2: " + rsConsultaAlumno.getString("apellido2"));
			System.out.println("email: " + rsConsultaAlumno.getString("email"));
			System.out.println("edad: " + rsConsultaAlumno.getInt("edad"));
			
			idAlumno = rsConsultaAlumno.getInt("id");
		}
		
		pstConsultaAlumno.close();
		rsConsultaAlumno.close();
		
		return idAlumno;
	}
	
	/**
	 * Comprueba la existencia del módulo indicado en el sistema.
	 * @param c Conexión a través de la cual ejecutar las sentencias.
	 * @param codigo de módulo a consultar.
	 * @return true en caso de que el módulo exista, false en caso contrario.
	 * @throws SQLException
	 */
	public static boolean compruebaModulo(Connection c, String codigo) throws SQLException{
		PreparedStatement pstConsultaModulo = c.prepareStatement(consulta_codigo_modulo);
		boolean existeModulo = false;
		
		pstConsultaModulo.setString(1, codigo);
		ResultSet rsConsultaModulo = pstConsultaModulo.executeQuery();
		existeModulo = rsConsultaModulo.next();
		
		pstConsultaModulo.close();
		rsConsultaModulo.close();
		
		return existeModulo;
	}
	
	/**
	 * Comprueba la existencia de la matrícula indicada en el sistema
	 * @param c Conexión a través de la cual ejecutar sentencias.
	 * @param numesc número escolar del alumno.
	 * @param codigo código del módulo.
	 * @return devolverá true en caso de que exista la matrícula, false en caso contrario.
	 * @throws SQLException
	 */
	public static boolean compruebaMatricula(Connection c, int numesc, String codigo) throws SQLException{
		boolean existeMatricula = false;
		PreparedStatement pstConsultaMatricula = c.prepareStatement(consulta_matricula);

		pstConsultaMatricula.setInt(1, numesc);
		pstConsultaMatricula.setString(2, codigo);
		
		ResultSet rsCompruebaMatricula = pstConsultaMatricula.executeQuery();
		existeMatricula = rsCompruebaMatricula.next(); 
		
		if (existeMatricula) {
			System.out.println("La matrícula que desea registrar ya existe en el sistema");
		}else {
			System.out.println("Va a insertar al alumno indicado anteriormente en el módulo " + codigo + ".");
		}		
		
		pstConsultaMatricula.close();		
		rsCompruebaMatricula.close();
		
		return existeMatricula;
	}	

	
	/**
	 * Solicita por teclado los datos de un alumno a insertar.
	 * @param c
	 * @throws SQLException 
	 */
	public static void insertarAlumno(Connection c) throws SQLException {
		PreparedStatement pstInsertarAlumno = c.prepareStatement(inserta_alumno);
		
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
		
		pstInsertarAlumno.setString(1, nombre);
		pstInsertarAlumno.setString(2, apellido1);
		pstInsertarAlumno.setString(3, apellido2);
		pstInsertarAlumno.setString(4, email);
		pstInsertarAlumno.setInt(5, edad);
		
		int resultado = pstInsertarAlumno.executeUpdate();
		
		if (resultado==1) {
			System.out.println("Alumno insertado con éxito.");
		}else {
			System.out.println("Ha ocurrido algún error en la inserción del alumno.");
		}

		//Cerramos Statement
		pstInsertarAlumno.close();
	}
	
	
	/**
	 * Comprueba que el módulo seleccionado no esté registrado y lo inserta en caso de que no exista.
	 * @param c
	 * @throws SQLException
	 */
	public static void insertarModulo(Connection c) throws SQLException {
		PreparedStatement pstConsulta = c.prepareStatement(consulta_codigo_modulo);
		PreparedStatement pstInserta = c.prepareStatement(inserta_modulo);
		
		Scanner sc = new Scanner(System.in);
		String codigo = null;
		String descripcion = null;
		
		boolean existe = true;
		while (existe) {
			System.out.println("Introduzca el código del módulo:");
			codigo = sc.nextLine();
			existe = compruebaModulo(c, codigo);		
			
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
		Scanner sc = new Scanner(System.in);
		PreparedStatement pstInsertaMatricula = c.prepareStatement(inserta_matricula);
				
		int numesc = -1;
		int id = -1;
		String codigoModulo = null;
		String continuar = "";
		
		boolean existeAlumno = false;
		boolean existeModulo = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el número escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			id = compruebaAlumnoYDevuelveID(c, numesc);
			existeAlumno = (id>0);
		}
		
		while (!existeModulo) {
			System.out.println("Introduzca el código del módulo:");
			codigoModulo = sc.nextLine();
			
			existeModulo = compruebaModulo(c, codigoModulo);
			
			if(!existeModulo) {
				System.out.println("No existe en la BD ningún módulo con el código indicado. Por favor, repita la operación indicando un código válido.");
			}else {
				compruebaMatricula(c, numesc, codigoModulo);							
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
	}
	
	/**
	 * Método que gestiona el listado de matrículas de un alumno. Solicita elnúmero escolar, comprueba que está registrado en el sistema y muestra sus matrículas.
	 * @param c
	 * @throws SQLException
	 */
	public static void listarMatriculasAlu(Connection c) throws SQLException {
		Scanner sc = new Scanner(System.in);
		PreparedStatement pstListarMatriculasAlu = c.prepareStatement(lista_matriculas_alu);
		
		int numesc = -1;
		boolean existeAlumno = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el número escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			existeAlumno = compruebaAlumno(c, numesc);
		}		
		
		pstListarMatriculasAlu.setInt(1, numesc);
		ResultSet matriculasAlu = pstListarMatriculasAlu.executeQuery();
		boolean hayMatriculas = matriculasAlu.next();
		
		if (hayMatriculas) {
			System.out.println("El alumno está matriculado en los siguientes módulos: ");
			matriculasAlu.beforeFirst();
			while (matriculasAlu.next()) {
				System.out.println("- " + matriculasAlu.getString("descripcion"));
			}
		}else {
			System.out.println("El alumno indicado no tiene matrículas registradas en el sistema.");
		}
	}	
	
	/**
	 * Método que gestiona el listado de matrículas de un alumno. Solicita elnúmero escolar, comprueba que está registrado en el sistema y muestra sus matrículas.
	 * @param c
	 * @throws SQLException
	 */
	public static void borrarMatriculasAlu(Connection c) throws SQLException {
		Scanner sc = new Scanner(System.in);
		PreparedStatement pstBorrarrMatriculasAlu = c.prepareStatement(borra_matriculas_alu);
		String continuar = "";
		
		int numesc = -1;
		boolean existeAlumno = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el número escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			existeAlumno = compruebaAlumno(c, numesc);
		}		
		
		System.out.println("Va a proceder a borrar TODAS las matrículas del alumno indicado, ¿Está seguro? (S/N)");
		continuar = sc.nextLine();
		
		if("S".equals(continuar)) {
			pstBorrarrMatriculasAlu.setInt(1, numesc);
			pstBorrarrMatriculasAlu.executeUpdate();	
		}				
	}
	
	/**
	 * Listará todos los alumnos con sus matrículas (en caso de que la tengan)
	 * @param c
	 * @throws SQLException
	 */
	public static void listaTodasMatriculas(Connection c) throws SQLException{
		Statement st = c.createStatement();
		ResultSet rsMatriculas = st.executeQuery(lista_todas_matriculas);
		String alumno = "";
		boolean aluMostrado = false;
		
		if(rsMatriculas.next()) {
			alumno = rsMatriculas.getString("alumno");
		}else {
			System.out.println("No existen alumnos mariculados.");
		}
		
		if(!("").equals(alumno)) {
			rsMatriculas.beforeFirst();
			while(rsMatriculas.next()) {
				// Quiere decir que ha cambiado el alumno del listado.
				if(!rsMatriculas.getString("alumno").equals(alumno)) {
					alumno = rsMatriculas.getString("alumno");
					aluMostrado = false;
				}
				
				if(!aluMostrado) {
					System.out.println(alumno + ": ");
					aluMostrado = true;
				}
				System.out.println("\t-" + rsMatriculas.getString("descripcion"));
				
			}			
		}		
	}
}