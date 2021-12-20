/**
 * 
 */
package accdat.UD02.accesobdrelacionales.examen.solucion;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author JESUS
 *
 */
public class TestConexiones {
	public static void main(String[] args) {
		Connection con = null;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduzca la conexi�n que desea testear:");
		System.out.println("1. Local");
		System.out.println("2. Externa");
		
		int conexion = Integer.parseInt(sc.nextLine());
		
		try {			
			con = obtenerConexion(conexion);
			System.out.println("Conexión testeada exitosamente.");
		} catch (SQLException e) {
			System.out.println("No se ha podido crear la conexión.");
			e.printStackTrace();
		}finally {
			if (con != null) {
				try {
					con.close();
					sc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public static Connection obtenerConexion(int numConexion) throws SQLException {		
		Connection con = null;
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();		
		DocumentBuilder builder = null;
		Document arbol= null;
		
		String host      = null;
		String puerto    = null;
		String nombre_bd = null;
		String usuario   = null;
		String password  = null;
		
		try {
			builder = factory.newDocumentBuilder();
			arbol=builder.parse(new File("conexiones/datos_conexiones.xml"));
			
			// Se obtiene una referencia al nodo cliente (ser� un �nico nodo, por lo que accedemos directamente al 0).
			Element conexion = (Element) arbol.getElementsByTagName("conexion").item(numConexion-1);
			host      = conexion.getElementsByTagName("host").item(0).getTextContent();
			puerto    = conexion.getElementsByTagName("puerto").item(0).getTextContent();
			nombre_bd = conexion.getElementsByTagName("nombre_bd").item(0).getTextContent();
			usuario   = conexion.getElementsByTagName("usuario").item(0).getTextContent();
			password  = conexion.getElementsByTagName("password").item(0).getTextContent();
			
			System.out.println("Datos conexi�n: ");
			System.out.println("- host: " + host);
			System.out.println("- puerto: " + puerto);
			System.out.println("- nombre_bd: " + nombre_bd);
			System.out.println("- usuario: " + usuario);
			System.out.println("- password: " + password);
			
			System.out.println();
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // crea el arbol DOM a partir del fichero		
		
		con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + puerto + "/" + nombre_bd, usuario, password);		
		
		return con;
	}
	
	//////////////////////////////// INTRODUCE AQU� TU RESPUESTA.
	/**
	 * �Crees que aporta alguna ventaja registrar los datos de un documento? �Cu�l? �Por qu�? 
	 * �Crees que tiene alg�n inconveniente? �Cu�l? �Por qu�?
	 */
}
