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
public class Ejercicio11 {
	public static Connection con = null;
	
	public static String inserta_alumno =			 
			"INSERT INTO ALUMNO (nombre, apellido1, apellido2, email, edad) "
			+ "VALUES (?, ?, ?, ?, ?);";
	public static String actualiza_numesc = 
			"UPDATE ALUMNO SET NUMESC = ? WHERE ID = ?";
	public static String consulta_codigo_modulo = 
			" SELECT * FROM MODULO WHERE CODIGO = ? ";
	public static String inserta_modulo = 
			" INSERT INTO MODULO (CODIGO,DESCRIPCION) VALUES (?, ?)";
	public static String consulta_alumno = 
			" SELECT * FROM ALUMNO WHERE NUMESC = ? ";
	public static String consulta_matricula = 
			" select * from matricula m, alumno a  where m.id = a.id and a.numesc = ? and m.codigo = ? and m.curso = ? ";
	public static String inserta_matricula = 
			" INSERT INTO MATRICULA (ID, CODIGO, CURSO) VALUES (?, ?, ?) ";
	public static String lista_matriculas_alu = 
			" select mo.descripcion from matricula ma, alumno al, modulo mo where ma.id = al.id and ma.CODIGO = mo.CODIGO and al.numesc = ? and ma.curso = ? ";	
	public static String borra_matriculas_alu = 
			" delete from matricula where id = (select id from alumno where numesc = ? ) and curso = ?" ;
	public static String lista_todas_matriculas =
			" select " +			
	        " concat(apellido1, nvl2(apellido2, ' ', ''), nvl(apellido2, ''), ', ', nombre) as alumno, " +
			" ma.curso, " + 
			" MO.DESCRIPCION " +
	    	" from ALUMNO AL " +
	    	" left join MATRICULA MA using(ID) " +
			" left join MODULO MO using(CODIGO) " + 
			" order by 1, 2, 3 ";
	public static String graba_nota = 
			"update matricula " +
			"    set nota = ? " +
			"  where id = (select id from alumno where numesc = ? ) " +
			"    and codigo = ? " +
			"    and curso = ? ";
	public static String modulos_pendientes = 
			" select codigo from MATRICULA ma, alumno al where ma.ID = al.id and al.numesc = ? and nota< 5";

	/**
	 * Muestra el men?? y ejecuta la opci??n seleccionada mientras ??sta no sea salir.
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
		}while(opc!=9);
		System.out.println("??Hasta la pr??xima!");
	}
	
	/**
	 * Muestra las distintas opciones.
	 * @return Opci??n seleccionada por el usuario.
	 * @throws SQLException
	 */
	public static int muestraMenu() throws SQLException {
		Scanner sc = new Scanner(System.in);
		int opc = 0;
		System.out.println("Seleccione la operaci??n a realizar: ");
		System.out.println("1. Insertar un alumno.");
		System.out.println("2. Insertar un m??dulo profesional.");
		System.out.println("3. Matricular un alumno en un m??dulo profesional.");
		System.out.println("4. Listar los m??dulos en los que est?? matriculado un alumno.");
		System.out.println("5. Borrar las matr??culas de un alumno para un curso determinado.");
		System.out.println("6. Listado de alumnos y matr??culas.");
		System.out.println("7. Grabar una nota.");
		System.out.println("8. Actualizar matr??culas de un alumno.");
		System.out.println("9. Salir");
		opc = Integer.parseInt(sc.nextLine());
		return opc;
		
	}
	
	/**
	 * Llama a los m??todos que realizan cada una de las opciones.
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
		case 7:
			grabarNota(c);
			break;
		case 8:
			actualizaMatriculas(c);
			break;
		}
	}
	
	/**
	 * Comprueba la existencia de un alumno
	 * @param c Conexi??n para realizar la consulta.
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
			System.out.println("No existe en la BD ning??n alumno con el ID indicado. Por favor, repita la operaci??n indicando un ID v??lido.");
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
	 * @param c Conexi??n para realizar la consulta.
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
			System.out.println("No existe en la BD ning??n alumno con el ID indicado. Por favor, repita la operaci??n indicando un ID v??lido.");
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
	 * Comprueba la existencia del m??dulo indicado en el sistema.
	 * @param c Conexi??n a trav??s de la cual ejecutar las sentencias.
	 * @param codigo de m??dulo a consultar.
	 * @return true en caso de que el m??dulo exista, false en caso contrario.
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
	 * Comprueba la existencia de la matr??cula indicada en el sistema
	 * @param c Conexi??n a trav??s de la cual ejecutar sentencias.
	 * @param numesc n??mero escolar del alumno.
	 * @param codigo c??digo del m??dulo.
	 * @return devolver?? true en caso de que exista la matr??cula, false en caso contrario.
	 * @throws SQLException
	 */
	public static boolean compruebaMatricula(Connection c, int numesc, String codigo, int curso, boolean conMensaje) throws SQLException{
		boolean existeMatricula = false;
		PreparedStatement pstConsultaMatricula = c.prepareStatement(consulta_matricula);

		pstConsultaMatricula.setInt(1, numesc);
		pstConsultaMatricula.setString(2, codigo);
		pstConsultaMatricula.setInt(3, curso);
		
		ResultSet rsCompruebaMatricula = pstConsultaMatricula.executeQuery();
		existeMatricula = rsCompruebaMatricula.next(); 
		
		if(conMensaje) {
			if (existeMatricula) {
				System.out.println("La matr??cula que desea registrar ya existe en el sistema");
			}else {
				System.out.println("Va a insertar al alumno indicado anteriormente en el m??dulo " + codigo + ".");
			}			
		}		
		
		pstConsultaMatricula.close();		
		rsCompruebaMatricula.close();
		
		return existeMatricula;
	}	
	
	/**
	 * M??todo para insertar una matr??cula.
	 * @param c Conexi??n sobre la BD
	 * @param id id del alumno
	 * @param numesc n??mero escolar del alumno.
	 * @param nota nota a aplicar.
	 * @return
	 * @throws SQLException
	 */
	public static int insertaMatricula(Connection c, int id, String codigo, int curso) throws SQLException{
		PreparedStatement pstInsertaMatricula = c.prepareStatement(inserta_matricula);
		pstInsertaMatricula.setInt(1, id);
		pstInsertaMatricula.setString(2, codigo);
		pstInsertaMatricula.setInt(3,  curso);
		
		return pstInsertaMatricula.executeUpdate();		
		
	}

	
	/**
	 * Solicita por teclado los datos de un alumno a insertar.
	 * @param c
	 * @throws SQLException 
	 */
	public static void insertarAlumno(Connection c) throws SQLException {
		PreparedStatement pstInsertarAlumno = c.prepareStatement(inserta_alumno, Statement.RETURN_GENERATED_KEYS);
		
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
			ResultSet gk = pstInsertarAlumno.getGeneratedKeys();
			gk.next();
			int id = gk.getInt(1);
			int numesc = id*2;
			
			System.out.println("Alumno insertado con ??xito.");
			PreparedStatement pstActualizaNumesc = c.prepareStatement(actualiza_numesc);
			pstActualizaNumesc.setInt(1,  numesc);
			pstActualizaNumesc.setInt(2,  id);
			resultado = pstActualizaNumesc.executeUpdate();
			if (resultado==1) {
				System.out.println("El alumno insertado tiene como n??mero de escolarizaci??n: " + numesc);
			}else {
				System.out.println("Ha ocurrido alg??n error en la actualizaci??n del n??mero de escolarizaci??n.");
			}		
			pstActualizaNumesc.close();
		}else {
			System.out.println("Ha ocurrido alg??n error en la inserci??n del alumno.");
		}

		//Cerramos Statement
		pstInsertarAlumno.close();
	}
	
	
	/**
	 * Comprueba que el m??dulo seleccionado no est?? registrado y lo inserta en caso de que no exista.
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
			System.out.println("Introduzca el c??digo del m??dulo:");
			codigo = sc.nextLine();
			existe = compruebaModulo(c, codigo);		
			
			if(existe) {
				System.out.println("El c??digo introducido ya existe en la relaci??n de m??dulos, por favor repita la operaci??n introduciendo un nuevo c??digo.");
			}
		}
		
		System.out.println("Introduzca la descripci??n del m??dulo:");
		descripcion = sc.nextLine();
		
		pstInserta.setString(1, codigo);
		pstInserta.setString(2, descripcion);
		
		pstInserta.executeUpdate();
		
		System.out.println("M??dulo insertado con ??xito.");
		
		pstConsulta.close();
		pstInserta.close();
	}
	
	/**
	 * Comprueba la existencia del alumno cuyo ID es el insertado.
	 * Comprueba que el m??dulo indicado exista.
	 * Comprueba que la matr??cula no exista previamente. 
	 * Si todas estas condiciones se dan, registra la matr??cula.
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
		int curso = -1;
		String continuar = "";
		
		boolean existeAlumno = false;
		boolean existeModulo = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el n??mero escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			id = compruebaAlumnoYDevuelveID(c, numesc);
			existeAlumno = (id>0);
		}
		
		while (!existeModulo) {
			System.out.println("Introduzca el c??digo del m??dulo:");
			codigoModulo = sc.nextLine();			
			
			existeModulo = compruebaModulo(c, codigoModulo);
			
			if(!existeModulo) {
				System.out.println("No existe en la BD ning??n m??dulo con el c??digo indicado. Por favor, repita la operaci??n indicando un c??digo v??lido.");
			}else {
				System.out.println("Introduzca el curso para la matr??cula:");
				curso = Integer.parseInt(sc.nextLine());
				
				compruebaMatricula(c, numesc, codigoModulo, curso, true);							
			}
		}		
		
		System.out.println("??Desea continuar? (S/N)");
		continuar = sc.nextLine();
		if ("S".equals(continuar)) {			
			pstInsertaMatricula.setInt(1, id);
			pstInsertaMatricula.setString(2, codigoModulo);
			pstInsertaMatricula.setInt(3, curso);
			
			pstInsertaMatricula.executeUpdate();
			
			System.out.println("Matr??cula registrada con con ??xito.");			
		}	
	}
	
	/**
	 * M??todo que gestiona el listado de matr??culas de un alumno. Solicita eln??mero escolar, comprueba que est?? registrado en el sistema y muestra sus matr??culas.
	 * @param c
	 * @throws SQLException
	 */
	public static void listarMatriculasAlu(Connection c) throws SQLException {
		Scanner sc = new Scanner(System.in);
		PreparedStatement pstListarMatriculasAlu = c.prepareStatement(lista_matriculas_alu);
		
		int numesc = -1;
		boolean existeAlumno = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el n??mero escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			existeAlumno = compruebaAlumno(c, numesc);
		}	
		
		System.out.println("Introduzca el curso a consultar:");
		int curso = Integer.parseInt(sc.nextLine());
		
		pstListarMatriculasAlu.setInt(1, numesc);
		pstListarMatriculasAlu.setInt(2, curso);
		ResultSet matriculasAlu = pstListarMatriculasAlu.executeQuery();
		boolean hayMatriculas = matriculasAlu.next();
		
		if (hayMatriculas) {
			System.out.println("El alumno est?? matriculado en los siguientes m??dulos para el curso " + curso + ": ");
			matriculasAlu.beforeFirst();
			while (matriculasAlu.next()) {
				System.out.println("- " + matriculasAlu.getString("descripcion"));
			}
		}else {
			System.out.println("El alumno indicado no tiene matr??culas registradas en el sistema.");
		}
	}	
	
	/**
	 * M??todo que gestiona el listado de matr??culas de un alumno. Solicita eln??mero escolar, comprueba que est?? registrado en el sistema y muestra sus matr??culas.
	 * @param c
	 * @throws SQLException
	 */
	public static void borrarMatriculasAlu(Connection c) throws SQLException {
		Scanner sc = new Scanner(System.in);
		PreparedStatement pstBorrarrMatriculasAlu = c.prepareStatement(borra_matriculas_alu);
		String continuar = "";
		
		int numesc = -1;
		boolean existeAlumno = false;
		
		int curso = -1;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el n??mero escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			existeAlumno = compruebaAlumno(c, numesc);
		}	
		
		System.out.println("Introduzca el curso del que quiere elminar las matr??culas.");
		curso = Integer.parseInt(sc.nextLine());
		
		System.out.println("Va a proceder a borrar TODAS las matr??culas del alumno indicado, ??Est?? seguro? (S/N)");
		continuar = sc.nextLine();
		
		if("S".equals(continuar)) {
			pstBorrarrMatriculasAlu.setInt(1, numesc);
			pstBorrarrMatriculasAlu.setInt(2, curso);
			pstBorrarrMatriculasAlu.executeUpdate();	
		}				
	}
	
	/**
	 * Listar?? todos los alumnos con sus matr??culas (en caso de que la tengan)
	 * @param c
	 * @throws SQLException
	 */
	public static void listaTodasMatriculas(Connection c) throws SQLException{
		Statement st = c.createStatement();
		ResultSet rsMatriculas = st.executeQuery(lista_todas_matriculas);
		String alumno = "";
		String curso = "";
		boolean aluMostrado = false;
		boolean cursoMostrado = false;
		
		if(rsMatriculas.next()) {
			alumno = rsMatriculas.getString("alumno");
			curso  = rsMatriculas.getString("curso");
		}else {
			System.out.println("No existen alumnos mariculados.");
		}
		
		if(!("").equals(alumno)) {
			rsMatriculas.beforeFirst();
			while(rsMatriculas.next()) {
				
				// Quiere decir que ha cambiado el alumno del listado.
				if(!rsMatriculas.getString("alumno").equals(alumno)) {
					alumno = rsMatriculas.getString("alumno");
					curso = rsMatriculas.getString("curso");
					
					aluMostrado = false;
					cursoMostrado = false;
				}else {
					// No ha cambiado el alumno pero s?? el curso
					if(rsMatriculas.getString("curso")!=null) {
						if(!rsMatriculas.getString("curso").equals(curso)) {
							curso = rsMatriculas.getString("curso");
							
							cursoMostrado = false;
						}	
					}					
				}
				
				if(!aluMostrado) {
					System.out.println(alumno + ": ");
					aluMostrado = true;
				}
				
				if(rsMatriculas.getString("curso")!=null) {
					if(!cursoMostrado) {
						System.out.println("\t-" + curso + ": ");
						cursoMostrado = true;
					}
					
					System.out.println("\t\t-" + rsMatriculas.getString("descripcion"));
				}else {
					System.out.println("\t-El alumno no tiene matr??culas registradas");
				}
			}			
		}		
	}
	
	/**
	 * M??todo para grabar notas de un determinado alumno en el m??dulo indicado.
	 * @param c
	 * @throws SQLException
	 */
	public static void grabarNota(Connection c) throws SQLException {	
		
		Scanner sc = new Scanner(System.in);
		PreparedStatement pstGrabaNota = c.prepareStatement(graba_nota);
				
		int numesc = -1;
		int id = -1;
		String codigoModulo = null;
		int curso = -1;
		String continuar = "";
		double nota = -1.0;
		
		boolean existeAlumno = false;
		boolean existeModulo = false;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el n??mero escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			id = compruebaAlumnoYDevuelveID(c, numesc);
			existeAlumno = (id>0);
		}
		
		while (!existeModulo) {
			System.out.println("Introduzca el c??digo del m??dulo:");
			codigoModulo = sc.nextLine();			
			
			existeModulo = compruebaModulo(c, codigoModulo);
			
			if(!existeModulo) {
				System.out.println("No existe en la BD ning??n m??dulo con el c??digo indicado. Por favor, repita la operaci??n indicando un c??digo v??lido.");
			}else {
				System.out.println("Introduzca el curso para la grabar la nota:");
				curso = Integer.parseInt(sc.nextLine());
				
				if(!compruebaMatricula(c, numesc, codigoModulo, curso, false)) {
					System.out.println("No existe en la BD la matr??cula introducida.");
					existeModulo = false;
				}
					
			}
		}	
		
		System.out.println("Introduzca la calificaci??n para grabar:");
		nota = Double.parseDouble(sc.nextLine());
		
		System.out.println("??Desea continuar? (S/N)");
		continuar = sc.nextLine();
		if ("S".equals(continuar)) {			
			pstGrabaNota.setDouble(1, nota);
			pstGrabaNota.setInt(2, numesc);
			pstGrabaNota.setString(3, codigoModulo);
			pstGrabaNota.setInt(4, curso);
			
			pstGrabaNota.executeUpdate();
			
			System.out.println("Calificaci??n registrada con con ??xito.");			
		}			
	}
	
	/**
	 * Actualiza las matr??culas para el curso indicado.
	 * @param c
	 */
	public static void actualizaMatriculas(Connection c) throws SQLException {
		Scanner sc = new Scanner(System.in);
		boolean existeAlumno = false;
		int numesc = 0;
		int id=-1;
		
		while (!existeAlumno) {
			System.out.println("Introduzca el n??mero escolar del alumno:");
			numesc = Integer.parseInt(sc.nextLine());
			id = compruebaAlumnoYDevuelveID(c, numesc);
			existeAlumno = (id>0);
		}
		PreparedStatement pstModPendientes = c.prepareStatement(modulos_pendientes);
		pstModPendientes.setInt(1, id);
		ResultSet rsModPendientes = pstModPendientes.executeQuery();
		while(rsModPendientes.next()){
			insertaMatricula(c, id, rsModPendientes.getString("codigo"), 2021);
		}
		
	}
}