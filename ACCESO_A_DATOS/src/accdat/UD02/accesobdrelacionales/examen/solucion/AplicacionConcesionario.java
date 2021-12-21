/**
 * 
 */
package accdat.UD02.accesobdrelacionales.examen.solucion;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author JESUS
 *
 */
public class AplicacionConcesionario {

	/** Mostrará el siguiente menú al usuario mientras éste no decida salir.
	 * 
	 * 1. Registra nuevo vehículo.
	 * 2. Registra nueva venta.
	 * 3. Listar ventas de una marca.
	 * 4. Calcular total de ventas de un empleado.
	 * 5. Informe de situación.
	 * 6. Salir.
	 * @param args
	 */
	// 5%
	public static void main(String[] args) {
		GestorConcesionario gc = new GestorConcesionario();		
		int opc = -1;
		do {
			opc = muestraMenu();
			tratarOpcion(opc, gc);
		}while(opc!=6);
		gc.finaliza();
		System.out.println("¡Hasta la próxima!");		
	}
	
	/** 
	 * Muestra las distintas opciones de menú del programa
	 * @return devuelve el valor de la opción introducida por el usuario.
	 */
	public static int muestraMenu(){
		Scanner sc = new Scanner(System.in);
		int opc = 0;
		System.out.println("Menú");
		System.out.println("1. Registra nuevo vehículo.");
		System.out.println("2. Registra nueva venta.");
		System.out.println("3. Listar ventas de una marca.");
		System.out.println("4. Calcular total de ventas de un empleado.");
		System.out.println("5. Informe de situación.");
		System.out.println("6. Salir.");
		
		opc = Integer.parseInt(sc.nextLine());
		return opc;		
	}
	
	/**
	 * llamará a cada uno de los métodos que se encargan de la realización de las funcionalidades indicadas por el usuario.
	 * @param opcion operación introducida por el usuario.
	 * @param gc Objeto que encapsula la funcionalidad de la aplicación.
	 */
	public static void tratarOpcion(int opcion, GestorConcesionario gc) {
		try {
			switch (opcion) {
				case 1:
					gc.registraNuevoVehiculo();							
					break;
				case 2:
					gc.registraNuevaVenta();
					break;
				case 3:
					gc.pintaVentasPorMarcas();
					break;
				case 4:
					gc.pintaTotalVentasEmpleado();
					break;
				case 5:
					gc.informeSituacion();
					break;					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
