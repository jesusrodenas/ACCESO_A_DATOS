/**
 * 
 */
package accdat.UD02.accesobdrelacionales.utilidades;

import java.sql.SQLException;

/**
 * @author JESUS
 *
 */
public class Utilidades {
	public static void muestraErrorSQL(SQLException e) {
		System.err.println(" SQL ERROR mensaje : " + e.getMessage());
		System.err.println(" SQL Estado : " + e.getSQLState());
		System.err.println(" SQL código especifico: " + e.getErrorCode());
	}
}
