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
 * @author JESUS
 *
 */
public class GestorConcesionario {
	public Connection con;
	String sql_insertaCocheBD = "INSERT INTO COCHE(IDMARCA) VALUES (?)";
	String sql_insertaVentaBD = "INSERT INTO VENTA (IDEMP, IDCOCHE, IMPORTE) VALUES (?, ? , ?)";
	String sql_actualizaCocheBD = "UPDATE COCHE SET CODCOCHE = ? WHERE IDCOCHE = ?";
	String sql_listaMarcas = "SELECT * FROM MARCA";
	String sql_listaEmpleados = "SELECT IDEMP, CONCAT(APE1EMP, ' ',  APE2EMP, ' ', NOMEMP) EMPLEADO FROM EMPLEADO";
	String sql_cochesNoVendidos = "SELECT C.IDCOCHE, CONCAT(M.DESCMARCA, '-', NVL(C.CODCOCHE, 'NO MATRICULADO')) AS COCHE FROM COCHE C JOIN MARCA M USING(IDMARCA) WHERE C.IDCOCHE NOT IN (SELECT IDCOCHE FROM VENTA) ORDER BY C.IDCOCHE ";
	String sql_ventasPorMarca = "SELECT CONCAT(E.NOMEMP, ' ', E.APE1EMP , ' ', E.APE2EMP , ' - ', M.DESCMARCA , ' - ', V.IMPORTE ) AS DATOS FROM VENTA V JOIN EMPLEADO E USING(IDEMP) JOIN COCHE C USING (IDCOCHE) JOIN MARCA M USING(IDMARCA) WHERE M.DESCMARCA = ?";
	String sql_ventasPorEmpleado = "select sum(importe) as cantidad from venta where idemp = ? group by idemp";
	String sql_informSituacion = 
			"select concat(nomemp, ' ', APE1EMP, ' ', APE2EMP) as empl, nvl(mar.DESCMARCA, 'Este empleado aún no ha registrado ventas.') as vendido, ven.importe from empleado emp " +
			" left join venta ven using(idemp) " +
			" left join coche coc using(idcoche) " +
			" left join marca mar using(idmarca) ";
	
	// llamada a la función de base de datos.
	public static String llamada_funcion = "{ ? = call obtener_codigo(?)}";
	
	String datos_conexion = "jdbc:mysql://localhost:3306/concesionario";
	String usuario = "root";
	String password = "";
	
	public GestorConcesionario() {
		try {
			con = DriverManager.getConnection(datos_conexion, usuario, password);
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
		pst.setInt(1,  idCoche);
		pst.setInt(2,  idEmpleado);
		pst.setInt(3,  importe);
		insertado = (pst.executeUpdate()==1);
		return insertado;
	}
	
	public boolean actualizaCocheBD(int idCoche, String codCoche) throws SQLException {
		boolean insertado = false;
		PreparedStatement pst = con.prepareStatement(sql_actualizaCocheBD);
		pst.setInt(1,  idCoche);
		pst.setString(2,  codCoche);
		
		insertado = (pst.executeUpdate()==1);
		return insertado;
	}
	
	// Métodos auxiliares
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
		System.out.println("Inqique el empleado de los posibles:");
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
		System.out.println("Inqique el vehículo de los posibles:");
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
	public void muestraDescMarcas() throws SQLException {
		Statement stMarcas = con.createStatement();
		ResultSet rsMarcas = stMarcas.executeQuery(sql_listaMarcas);
		System.out.println("Inqique la marca de las posibles:");
		while (rsMarcas.next()) {
			System.out.println(rsMarcas.getString("DESCMARCA"));
		}
	}
	
	/**
	 * Solicita al usuario una marca del listado de posibles.
	 * @throws SQLException
	 */
	public void pintaVentasPorMarcas() throws SQLException {
		Scanner sc = new Scanner(System.in);
		muestraDescMarcas();
		String marca = sc.nextLine();
		pintaVentasPorMarca(marca);
	}
	
	public void cierraConexion() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		Scanner sc = new Scanner(System.in);
		muestraMarcas();
		int idMarca = Integer.parseInt(sc.nextLine());
		
		int idCoche = insertaCocheBDDevuelveClave(idMarca);
		String codCoche = generarCodigoCoche(idCoche);
		
		actualizaCocheBD(idCoche, codCoche);
		
		System.out.println("Coche insertado con éxito.");
		sc.close();		
	}
	
	/**
	 * 2. Registra nueva venta.
	 * 1) Muestra al usuario los empleados del concesionario.
	 * 2) Muestra aquellos coches que aún no han sido vendidos. (MARCA.DESCMARCA - COCHE.CODCOCHE)
	 * @throws SQLException 
	 */
	public void registraNuevaVenta() throws SQLException {	
		Scanner sc = new Scanner(System.in);
		
		muestraEmpleados();
		int idEmpleado = Integer.parseInt(sc.nextLine());
		
		muestraCochesNoVendidos();
		int idCoche = Integer.parseInt(sc.nextLine());
		
		System.out.println("Indique el importe: ");
		int importe = Integer.parseInt(sc.nextLine());
		
		insertaVentaBD(idCoche, idEmpleado, importe);
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
		PreparedStatement pst = con.prepareStatement(sql_ventasPorMarca);
		pst.setString(1, marca);
		ResultSet rsVentas = pst.executeQuery();
		while (rsVentas.next()) {
			System.out.println(rsVentas.getString("datos"));
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
		Scanner sc = new Scanner(System.in);
		
		muestraEmpleados();
		int idEmpleado = Integer.parseInt(sc.nextLine());
		
		PreparedStatement pst = con.prepareStatement(sql_ventasPorMarca);
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
	 *  
	 */
	public void informeSituacion() {		
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
		CallableStatement cst = con.prepareCall(llamada_funcion);
					
		// Se define el tipo de salida
		cst.registerOutParameter(1, Types.VARCHAR);
		// Se pasa el parámetro como segundo '?' de la llamada
		cst.setInt(2, idCoche);

		// Se realiza la ejecución
		cst.executeUpdate();
		
		String codRet = cst.getString(1);
		cst.close();
		
		return codRet; 
	}
}
