/**
 * 
 */
package accdat.UD02.accesobdrelacionales.examen.solucion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

/**
 * Clase encargada de implementar la funcionalidad del progama del concesionario.
 * Supondremos que el usuario inserta valores válidos entre los mostrados.
 * 
 * @author JESUS
 *
 */
public class GestorConcesionario {
	
	// Conexión con la BD a través de la cual obtener Statement y PreparedStatement.
	private Connection con;
	private Scanner sc;
	
	// Sentencias necesarias en el programa.
	private String sql_insertaCocheBD = "INSERT INTO COCHE(IDMARCA) VALUES (?)";
	private String sql_insertaVentaBD = "INSERT INTO VENTA (IDEMP, IDCOCHE, IMPORTE) VALUES (?, ? , ?)";
	private String sql_actualizaCocheBD = "UPDATE COCHE SET CODCOCHE = ? WHERE IDCOCHE = ?";
	private String sql_listaMarcas = "SELECT * FROM MARCA";
	private String sql_listaEmpleados = "SELECT IDEMP, CONCAT(APE1EMP, ' ',  APE2EMP, ' ', NOMEMP) EMPLEADO FROM EMPLEADO";
	private String sql_cochesNoVendidos = "SELECT C.IDCOCHE, CONCAT(M.DESCMARCA, '-', NVL(C.CODCOCHE, 'NO MATRICULADO')) AS COCHE FROM COCHE C JOIN MARCA M USING(IDMARCA) WHERE C.IDCOCHE NOT IN (SELECT IDCOCHE FROM VENTA) ORDER BY C.IDCOCHE ";
	private String sql_ventasPorMarca = "SELECT CONCAT(E.NOMEMP, ' ', E.APE1EMP , ' ', E.APE2EMP , ' - ', M.DESCMARCA , ' - ', V.IMPORTE ) AS DATOS FROM VENTA V JOIN EMPLEADO E USING(IDEMP) JOIN COCHE C USING (IDCOCHE) JOIN MARCA M USING(IDMARCA) WHERE M.DESCMARCA = ?";
	private String sql_ventasPorEmpleado = "select sum(importe) as cantidad from venta where idemp = ? group by idemp";
	private String sql_informeSituacion = 
			"select concat(nomemp, ' ', APE1EMP, ' ', APE2EMP) as empl, nvl(mar.DESCMARCA, 'Este empleado aún no ha registrado ventas.') as vendido, ven.importe from empleado emp " +
			" left join venta ven using(idemp) " +
			" left join coche coc using(idcoche) " +
			" left join marca mar using(idmarca) ";	
	// llamada a la función de base de datos.
	private  static String llamada_funcion = "{ ? = call obtener_codigo(?)}";
	
	// Datos de conexión.
	private String datos_conexion = "jdbc:mysql://localhost:3306/concesionario";
	private String usuario = "root";
	private String password = "";
	
	/**
	 * El constructor sin parámetros creará la conexión con la BD
	 */
	public GestorConcesionario() {
		try {
			con = DriverManager.getConnection(datos_conexion, usuario, password);
			sc = new Scanner(System.in);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// ** SECCIÓN DE INSERCIONES/ACTUALIZACIONES. (18%)
	// EN ESTE APARTADO GESTIONAREMOS LA MANIPULACIÓN DE LA BD	LOCAL
	public boolean insertaCocheBD(int idMarca) throws SQLException {	
		boolean insertado = false;
		PreparedStatement pst = con.prepareStatement(sql_insertaCocheBD);
		pst.setInt(1,  idMarca);
		insertado = (pst.executeUpdate()==1);
		return insertado;
	}
	
	public boolean insertaVentaBD(int idCoche, int idEmpleado, int importe) throws SQLException {
		boolean insertado = false;
		PreparedStatement pst = con.prepareStatement(sql_insertaVentaBD);		
		pst.setInt(1,  idEmpleado);
		pst.setInt(2,  idCoche);
		pst.setInt(3,  importe);
		insertado = (pst.executeUpdate()==1);
		return insertado;
	}
	
	public boolean actualizaCocheBD(int idCoche, String codCoche) throws SQLException {
		boolean insertado = false;
		PreparedStatement pst = con.prepareStatement(sql_actualizaCocheBD);		
		pst.setString(1,  codCoche);
		pst.setInt(2,  idCoche);
		
		insertado = (pst.executeUpdate()==1);
		return insertado;
	}
	
	/////// Métodos auxiliares
	/////// Métodos implementados para solucionar diversos subproblemas del programa. 
	/**
	 * Insertará vehículo devolviendo el id.
	 * @param idMarca
	 * @return
	 * @throws SQLException
	 */
	public int insertaCocheBDDevuelveClave(int idMarca) throws SQLException {
		int idCocheInsertado = -1;
		
		PreparedStatement pst = con.prepareStatement(sql_insertaCocheBD, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1,  idMarca);
		
		pst.executeUpdate();
		
		ResultSet rsInsertado = pst.getGeneratedKeys();
		rsInsertado.next();
		idCocheInsertado = rsInsertado.getInt(1);
		
		return idCocheInsertado;
	}
	
	/** 
	 * Muestra las marcas registradas en el concesionario con su id.
	 * @throws SQLException
	 */
	public void muestraMarcas() throws SQLException {
		Statement stMarcas = con.createStatement();
		ResultSet rsMarcas = stMarcas.executeQuery(sql_listaMarcas);
		System.out.println("Inqique la marca de las posibles:");
		while (rsMarcas.next()) {
			System.out.print(rsMarcas.getString("IDMARCA"));
			System.out.print(" - ");
			System.out.println(rsMarcas.getString("DESCMARCA"));
		}
	}
	
	/**
	 * Muestra los empleados registrados en el concesionario con su id.
	 * @throws SQLException
	 */
	public void muestraEmpleados() throws SQLException {
		Statement stMarcas = con.createStatement();
		ResultSet rsMarcas = stMarcas.executeQuery(sql_listaEmpleados);
		System.out.println("Indique el empleado de los posibles:");
		while (rsMarcas.next()) {
			System.out.print(rsMarcas.getString("IDEMP"));
			System.out.print(" - ");
			System.out.println(rsMarcas.getString("EMPLEADO"));
		}
	}
	
	/**
	 * Listado de coches disponibles para la venta.
	 * @throws SQLException
	 */
	public void muestraCochesNoVendidos() throws SQLException {
		Statement stMarcas = con.createStatement();
		ResultSet rsMarcas = stMarcas.executeQuery(sql_cochesNoVendidos);
		System.out.println("Indique el vehículo de los posibles:");
		while (rsMarcas.next()) {
			System.out.print(rsMarcas.getString("IDCOCHE"));
			System.out.print(". ");
			System.out.println(rsMarcas.getString("COCHE"));
		}
	}
	
	/** Muestra las descripciones de las marcas registradas en el sistema.
	 * 
	 * @throws SQLException
	 */
	public void muestraDescripcionMarcas() throws SQLException {
		Statement stMarcas = con.createStatement();
		ResultSet rsMarcas = stMarcas.executeQuery(sql_listaMarcas);
		System.out.println("Indique la marca de las posibles:");
		while (rsMarcas.next()) {
			System.out.println(rsMarcas.getString("DESCMARCA"));
		}
	}
	
	/**
	 * Solicita al usuario una marca del listado de posibles.
	 * @throws SQLException
	 */
	public void pintaVentasPorMarcas() throws SQLException {
		muestraDescripcionMarcas();
		String marca = sc.nextLine();
		pintaVentasPorMarca(marca);
	}
	
	/**
	 * Método que será invocado en la finalización del programa para cerrar la conexión y la lectura de datos del usuario.	
	 */
	public void finaliza() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		sc.close();
	}
	
	// ** SECCIÓN DE FUNCIONALIDAD DE LA APLICACIÓN.(30%)
	// EN ESE APARTADO IMPLEMENTAREMOS LOS MÉTODOS QUE SERÁN LLAMADOS DESDE EL MENÚ
	/**
	 * 1. Registra nuevo vehículo.
	 *  
	 * 1) Muestra a los usuarios las marcas disponibles.
	 * 2) El usuario selecciona la marca.
	 * 3) Inserta el vehículo y obtiene el id
	 * 4) Llama al método generarCodigo y actualiza el COCHE.CODCOCHE
	 * 3) Muestra mensaje de inserción OK y el COCHE.CODCOCHE del vehículo insertado 
	 * @throws SQLException 
	 */
	public void registraNuevoVehiculo() throws SQLException {		
		muestraMarcas();
		int idMarca = Integer.parseInt(sc.nextLine());
		
		int idCoche = insertaCocheBDDevuelveClave(idMarca);
		String codCoche = generarCodigoCoche(idCoche);
		
		actualizaCocheBD(idCoche, codCoche);		
		System.out.println("Coche de código " + codCoche + " insertado con éxito.");
	}
	
	/**
	 * 2. Registra nueva venta.
	 * 1) Muestra al usuario los empleados del concesionario.
	 * 2) Muestra aquellos coches que aún no han sido vendidos. (MARCA.DESCMARCA - COCHE.CODCOCHE)
	 * @throws SQLException 
	 */
	public void registraNuevaVenta() throws SQLException {		
		muestraEmpleados();
		int idEmpleado = Integer.parseInt(sc.nextLine());
		
		muestraCochesNoVendidos();
		int idCoche = Integer.parseInt(sc.nextLine());
		
		System.out.println("Indique el importe: ");
		int importe = Integer.parseInt(sc.nextLine());
		
		insertaVentaBD(idCoche, idEmpleado, importe);
		System.out.println("Venta registrada con éxito.");
	}
	
	// ** SECCIÓN DE INFORMES DE LA APLICAIÓN (36%)
	// En esta sección si implementarán los distintos informes que puede mostrar la aplicación.
	
	/**
	 * 3. Listar ventas de una marca. 
	 * @param marca Marca de la que quieren pintarse todas sus ventas ventas y empleado que la realización con el siguiente formato:
	 * Nombre empleado - marca - importe
	 * Ejemplo:
	 * Germán Ruiz Domínguez - Ford - 12500
	 * @throws SQLException 
	 */
	public void pintaVentasPorMarca(String marca) throws SQLException {	
		boolean existe = false;
		PreparedStatement pst = con.prepareStatement(sql_ventasPorMarca);
		pst.setString(1, marca);
		ResultSet rsVentas = pst.executeQuery();
		while (rsVentas.next()) {
			existe = true;
			System.out.println(rsVentas.getString("datos"));
		}		
		
		if (!existe) {
			System.out.println("La marca introducida no está presente entre las registradas.");
		}
	}
	
	/**
	 * 4. Calcular total de ventas de un empleado
	 * Calculará las ventas acumuladas de un determinado empleado cuyo ID es solicitado al usuario por pantalla.
	 * Se comprobará previamente la existencia del ID facilitado en la BD.
	 * @param idEmpleado
	 * @throws SQLException 
	 */
	public void pintaTotalVentasEmpleado() throws SQLException {		
		muestraEmpleados();
		int idEmpleado = Integer.parseInt(sc.nextLine());
		
		PreparedStatement pst = con.prepareStatement(sql_ventasPorEmpleado);
		pst.setInt(1, idEmpleado);
		ResultSet rsVentas = pst.executeQuery();
		while (rsVentas.next()) {
			System.out.println("El usuario indicado ha vendido por un total de " + rsVentas.getString("cantidad"));
		}
	}
	
	/**
	 * 5. Informe de situación
	 * Mostrará todos los empleados y sus ventas si tuvieran siguiendo este formato.
	 * Germán Ruiz Domínguez:
	 *     - Ford: 12500
	 *     - Kia: 18000
	 * Laura Escudero Benítez:
	 *     - Este empleado aún no ha registrado ventas.
	 * @throws SQLException 
	 *  
	 */
	public void informeSituacion() throws SQLException {		
		Statement stInforme = con.createStatement();
		ResultSet rsInforme = stInforme.executeQuery(sql_informeSituacion);		

		String empleado = "", marca = "";
		int importe = -1;		

		boolean emplMostrado = false;	

		while(rsInforme.next()) {
			// Quiere decir que ha cambiado el empleado del listado.
			if(!rsInforme.getString("empl").equals(empleado)) {
				empleado = rsInforme.getString("empl");
				emplMostrado = false;
			}

			marca    = rsInforme.getString("vendido");
			importe  = rsInforme.getInt("importe");

			if(!emplMostrado) {
				System.out.println(empleado + ": ");
				emplMostrado = true;
			}

			if ("Este empleado aún no ha registrado ventas.".equals(marca)) {
				System.out.println("\t- " +  marca );
			}else {
				System.out.println("\t- " +  marca + ": " + importe + ".");
			}
		}
	}
	
	// SECCIÓN GENERAR CÓDIGO DE VEHÍCULO. (11%)
	/**
	 * Este método generará un código asociado al idCoche.
	 * Deberá llamar a la función obtener_codigo
	 * create function obtener_codigo(ID INTEGER) RETURNS varchar(50)
	 * que genera un CÓDIGO a partir del id pasado como parámetro.
	 * @param idCoche
	 * @return
	 * @throws SQLException 
	 */
	public String generarCodigoCoche(int idCoche) throws SQLException {
		Connection con_externa = DriverManager.getConnection("jdbc:mysql://185.224.138.49:3306/u579684516_ACCDAT", "u579684516_ACCDAT", "AccDat_2DAM");
		
		// Obtenermos una instancia de un objeto que implementa la interface CallableStatement.
		CallableStatement cst = con_externa.prepareCall(llamada_funcion);
					
		// Se define el tipo de salida
		cst.registerOutParameter(1, Types.VARCHAR);
		// Se pasa el parámetro como segundo '?' de la llamada
		cst.setInt(2, idCoche);

		// Se realiza la ejecución
		cst.executeUpdate();
		
		String codRet = cst.getString(1);
		cst.close();
		con_externa.close();
		
		return codRet; 
	}
}
