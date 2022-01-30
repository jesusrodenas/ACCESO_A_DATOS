package accdat.UD03.bbddoo.ejemplo4;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;

public class Principal {

	private static final String BD_RECETAS = "recetas.oo";
	private static final int OPCION_SALIR = 8;
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int opc;

		cargarDatosIniciales();

		do {
			opc = solicitarOpcion();
			try {
				tratarOpcion(opc);
			} catch (RecetaException e) {
				System.out.println(e.getMessage());
			}
		} while (opc != OPCION_SALIR);

	

	}
	


	private static int solicitarOpcion() {
		int opc;
		System.out.println("1.Consulta de las recetas de una cierta dificultad(1-5)");
		System.out.println("2.Consulta de las recetas que contienen un ingrediente (ordenado por número de ingredientes)");
		System.out.println("3.Modificar la dificultad de una receta");
		System.out.println("4.Consulta de una receta");
		System.out.println("5.Borrar una receta");
		System.out.println("6.Mostrar todo");
		System.out.println("7.Añadir ingrediente a receta");
		System.out.println("8.Salir");
		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > OPCION_SALIR);
		return opc;
	}

	private static void cargarDatosIniciales() {

		File f = new File(BD_RECETAS); // SI NO EXISTE EL FICHERO CON LA BD LO CREA
		if (!f.exists()) {
			try {
				ObjectContainer db = abrirBd();

				Receta receta1 = new Receta("Salmorejo", 1);
				receta1.annadirIngrediente(new Ingrediente("Tomate", 1200));
				receta1.annadirIngrediente(new Ingrediente("Aceite", 80));
				receta1.annadirIngrediente(new Ingrediente("Ajo", 1));

				receta1.annadirPaso("Pelar los tomates y añadirlos a la batidora");
				receta1.annadirPaso("Pelar el ajo y añadirlo a la batidora");
				receta1.annadirPaso("Añadir poco a poco el aceite");

				db.store(receta1);

				Receta receta2 = new Receta("Revuelto de patatas y jamon", 2);
				receta2.annadirIngrediente(new Ingrediente("Huevos", 4));
				receta2.annadirIngrediente(new Ingrediente("Patatas", 500));
				receta2.annadirIngrediente(new Ingrediente("Jamon", 80));

				receta2.annadirPaso("Pelar las patatas");
				receta2.annadirPaso("Freir las patatas");
				
				db.store(receta2);
				
				Receta receta3 = new Receta("Lentejas", 2);
				receta3.annadirIngrediente(new Ingrediente("Lentejas", 500));
				receta3.annadirIngrediente(new Ingrediente("Tomate", 100));
				receta3.annadirIngrediente(new Ingrediente("Pimiento", 80));
			

				receta3.annadirPaso("Lavar las lentejas");
				receta3.annadirPaso("Sofreir la cebolla");
				receta3.annadirPaso("Añadir el pimiento al sofrito");

				db.store(receta3);

				cerrarBd(db);
			} catch (RecetaException e) {
				System.out.println(e.getMessage());
			}
		}

	}
	
	private static ObjectContainer abrirBd(int nivel, Class clase, boolean updateOnCascade) {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().activationDepth(nivel);
		config.common().objectClass(clase).cascadeOnUpdate(updateOnCascade);
		
		
		ObjectContainer db = Db4oEmbedded.openFile(config, BD_RECETAS);

		return db;
	}
	
	
	private static ObjectContainer abrirBd(int nivel) {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().activationDepth(nivel);
		config.common().objectClass(Receta.class).cascadeOnDelete(true);
		
		
		ObjectContainer db = Db4oEmbedded.openFile(config, BD_RECETAS);

		return db;
	}

	private static ObjectContainer abrirBd() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		
		config.common().objectClass(Receta.class).cascadeOnDelete(true);
		
		
		ObjectContainer db = Db4oEmbedded.openFile(config, BD_RECETAS);

		return db;
	}

	private static void cerrarBd(ObjectContainer db) {
		db.close();
	}

	private static void tratarOpcion(int opc) throws RecetaException {
		int dificultad;
		
		String nombreReceta;
		switch (opc) {
		case 1:
			dificultad=solicitarEntero("Introduce la dificultad de las recetas");
			consultarRecetasConDificultad( dificultad);
			break;
		case 2:
			String ingrediente=solicitarCadena("Introduce el ingrediente:");
			consultarRecetasConIngrediente( ingrediente);
			break;

		case 3:
			nombreReceta=solicitarCadena("Introduce el nombre de la receta a modificar");
			dificultad=solicitarEntero("Introduce la nueva dificultad de la receta");
			modificarDificultadDeReceta( nombreReceta, dificultad);
			
			break;
		case 4: //consultar Receta
			nombreReceta=solicitarCadena("Introduce el nombre de la receta");
			consultarReceta(nombreReceta);
			break;
		case 5:
			nombreReceta=solicitarCadena("Introduce el nombre de la receta a borrar:");
			borrarReceta(nombreReceta);
			break;
		
		case 6: //mostrar todo
			mostrarObjetos("Recetas:" , new Receta() );
			mostrarObjetos("Ingredientes:" , new Ingrediente());
			break;
		case 7: //añadir un nuevo ingrediente a una receta
			nombreReceta=solicitarCadena("Introduce el nombre de la receta:");
			annadirIngredienteAReceta(nombreReceta);
			break;
			
		}

	}



	private static void consultarReceta(String nombreReceta) {
		
		
		ObjectContainer db=abrirBd(3);
		Receta receta= recetafindByNombre( db, nombreReceta);
		
		if (receta==null) {
			System.out.println("No existe la receta " + nombreReceta);
		}else {
			System.out.println(receta);
		}
		cerrarBd(db);
	}



	private static void annadirIngredienteAReceta(String nombreReceta) throws RecetaException {
		
		ObjectContainer db=abrirBd(3, Receta.class, true);
		Receta receta= recetafindByNombre( db, nombreReceta);
		
		if (receta==null) {
			System.out.println("No existe la receta " + nombreReceta);
		}else {
			
			Ingrediente ingredienteNuevo=new Ingrediente(solicitarCadena("Introduzca nombre del nuevo ingrediente:"), solicitarEntero("Introduzca cantidad:"));
			
			receta.annadirIngrediente(ingredienteNuevo);
			
			db.store(receta);
			
		}
		cerrarBd(db);
		
		
	}



	private static void mostrarObjetos(String string, Object patron) {
		
		ObjectContainer db=abrirBd();
		ObjectSet<Receta> resultado=db.queryByExample(patron);
		
		System.out.println(string);
		for (Object objeto : resultado) {
			System.out.println(objeto);
		}
		cerrarBd(db);
		
		
		
	}



	private static void borrarReceta(String nombreReceta) {
		
		ObjectContainer db=abrirBd(3);
		Receta receta= recetafindByNombre( db, nombreReceta);
		
		if (receta==null) {
			System.out.println("No existe la receta " + nombreReceta);
		}else {
			db.delete(receta);
		}
		
		cerrarBd(db);
		
	}



	private static Receta recetafindByNombre(ObjectContainer db, String nombreReceta) {
		Receta receta=null;
		
		ObjectSet<Receta> resultado=db.queryByExample(new Receta(nombreReceta));
		if ( resultado.size()== 1) {
			receta=resultado.next();
		}
		
		return receta;
		
	}



	private static void modificarDificultadDeReceta(String nombreReceta, int dificultad) {
		
		ObjectContainer db=abrirBd(1, Receta.class, false);
		Receta receta= recetafindByNombre( db, nombreReceta);
		
		if (receta==null) {
			System.out.println("No existe la receta " + nombreReceta);
		}else {
			receta.setDificultad(dificultad);
			db.store(receta);
		}
		
		cerrarBd(db);
		
		
		
	}

	private static void consultarRecetasConIngrediente(String ingrediente) {
		
		
		ObjectContainer bd=abrirBd(3);
		

		
		QueryComparator<Receta> comparador=new QueryComparator<Receta>() {

			@Override
			public int compare(Receta receta1, Receta receta2) {
				return Integer.compare(receta1.numeroIngredientes(), receta2.numeroIngredientes());
				
			}
		
		};
		
		
		ObjectSet<Receta> resultado=bd.query(new Predicate<Receta>() {

			@Override
			public boolean match(Receta receta) {
				return receta.contieneEsteIngrediente(ingrediente);
			}
			
		}
		, comparador);
		
		if (resultado.size()==0) {
			System.out.println("No hay recetas con el ingrediente " + ingrediente);
		}
		else {
			for (Receta receta : resultado) {
				System.out.println(receta);
			}
		}
		
		cerrarBd(bd);
		
	}

	private static void consultarRecetasConDificultad(int dificultad) {
	
		
		ObjectContainer bd=abrirBd(1);
		Receta patron=new Receta(dificultad );
		
		ObjectSet<Receta> resultado=bd.queryByExample( patron);
		
		if (resultado.size()==0) {
			System.out.println("No hay recetas con la dificultad " + dificultad);
		}
		else {
			for (Receta receta : resultado) {
				System.out.println(receta);
				System.out.println(receta.getNombreReceta());
			}
		}
		
		cerrarBd(bd);
		
	}

	private static int solicitarEntero(String msg) {
		int numero;

		System.out.println(msg);
		numero = Integer.parseInt(teclado.nextLine());

		return numero;
	}

	private static String solicitarCadena(String msg) {
		String nombre;
		System.out.println(msg);
		nombre = teclado.nextLine();
		return nombre;
	}

}
