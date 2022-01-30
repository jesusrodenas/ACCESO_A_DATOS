package accdat.UD03.bbddoo.ejemplo3;

import java.util.Scanner;
import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;

public class Principal {
	private static final int PROFUNDIDAD_POR_DEFECTO = 5;
	private static final String BD_PERSONAS = "bd.oo";
	private static Scanner teclado = new Scanner(System.in);
	private static ObjectContainer db;

	public static void main(String[] args) {

		int opc;
		

		do {
			opc = solicitarOpcion();
			tratarOpcion(opc);
		} while (opc != 12);
		
		

	}
	
	private static ObjectContainer abrirBd() {
		return abrirBd( PROFUNDIDAD_POR_DEFECTO);
	}

	private static ObjectContainer abrirBd(int profundidadActivacion) {

		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		
		// Si se modifica una persona, se modifican todo lo que "cuelga" de la persona en cascada
		config.common().objectClass(Persona.class).cascadeOnUpdate(true);
		// Si se borra una persona, se borra todo lo que "cuelga" de ella en cascada
		config.common().objectClass(Persona.class).cascadeOnDelete(true);
		// Si se modifica un coche, se modifica todo lo que "cuelga" de él en cascada
		config.common().objectClass(Coche.class).cascadeOnUpdate(true);
		// Si se borra un coche, se borra todo lo que "cuelga" de él en cascada
		config.common().objectClass(Coche.class).cascadeOnDelete(true);

		// System.out.println("Nivel de activacion " +
		// config.common().activationDepth());

		// Sirve para establecer la profundidad de nivel en que se cargaran los
		// objetos
		// Para el ejemplo si consultamos la personas los niveles serían los
		// siguientes:
		// 1. Personas (solo nombre y edad)
		// 2. Personas con el array de Coches (todos los coches a null)
		// 3. Personas con el array de Coches. Los coches con su matricula y multas y garaje a null 
		// 4. Personas con el array de Coches. Los coches con su matricula y garaje y multas cargadas a null
		// 5. Personas con el array de Coches. Los coches con su matricula, garaje y multas.

		config.common().activationDepth(profundidadActivacion);

		ObjectContainer db = Db4oEmbedded.openFile(config, BD_PERSONAS);

		return db;

	}

	private static void cerrarBd(ObjectContainer db) {
		db.close();
	}

	private static void tratarOpcion(int opc) {
		int edad;
		
		
		switch (opc) {
		case 1:
			db=abrirBd();
			insertarPersonaEnBd();
			cerrarBd(db);
			break;

		case 2:
			db=abrirBd();
			System.out.println("Personas:");
			consultarBd(new Persona());
			System.out.println("Coches:");
			consultarBd(new Coche());
			System.out.println("Multas:");
			consultarBd(new Multa());
			System.out.println("Garajes:");
			consultarBd(new Garaje());
			cerrarBd(db);
			break;
		case 3:
			db=abrirBd();
			consultarPersonas();
			cerrarBd(db);
			break;
		case 4:
			db=abrirBd();
			edad = solicitarEdad();
			consultarPersonasConEdadMayorA(edad);
			cerrarBd(db);
			break;
		case 5:
			db=abrirBd();
			edad = solicitarEdad();
			consultarPersonasConEdadMayorAOrdenadosPorNombre(edad);
			cerrarBd(db);
			break;
		case 6:
			db=abrirBd();
			insertarCoche();
			cerrarBd(db);
			break;

		case 7:
			db=abrirBd();
			insertarCocheAPersona();
			cerrarBd(db);
			break;

		case 8:
			db=abrirBd();
			borrarPersona();
			cerrarBd(db);
			break;

		case 9: { // insertar garaje a un coche
			db=abrirBd();
			insertarGarajeACoche();
			cerrarBd(db);
			break;
		}
		case 10: {
			db=abrirBd();
			insertarMultaACoche();
			cerrarBd(db);
			break;
		}
		
		case 11: { // consulta de solo los datos de primer nivel de Persona
			db=abrirBd(1);
			System.out.println("Personas (solo datos de primer nivel) :");
			consultarBd(new Persona());
			cerrarBd(db);
			
		}
		}
		
		

	}

	private static void insertarMultaACoche() {
		
		String matricula = solicitarCadena("Introduce matricula: ");

		Coche coche = localizarCoche( matricula);
		if (coche == null) {
			System.out.println("No se encuentra el coche");
		} else {
			Multa multa = new Multa(solicitarCadena("Introduce el texto de la multa: "));
			coche.addMulta(multa);
			db.store(coche);
		}

		

	}

	private static void insertarGarajeACoche() {

		
		String matricula = solicitarCadena("Introduce matricula: ");

		Coche coche = localizarCoche( matricula);
		if (coche == null) {
			System.out.println("No se encuentra el coche");
		} else {
			Garaje garaje = new Garaje(solicitarCadena("Introduce la calle del garaje: "));
			coche.setGaraje(garaje);
			db.store(coche);
		}

		
	}

	private static void borrarPersona() {
		String nombre = solicitarCadena("Introduce el nombre:");
		Persona personaPatron = new Persona(nombre, 0); // consultar todas las personas
												// que tienen ese nombre
		Persona persona;
		
		ObjectSet<Persona> result = db.queryByExample(personaPatron);

		if (result.size() != 1)
			System.out.println("BD Vacia o mas de una persona con ese nombre");
		else {
			persona = result.next();
			//Al estar activado cascade on update de Persona se borrarán también sus coches
			//Al estar activado cascade on update de Coche se borrarán también sus garajes y sus multas
			db.delete(persona);
			
			
		}

		
	}
	
	

	private static void insertarCocheAPersona() {
		String nombre = solicitarCadena("Introduce el nombre del cliente: ");
		String matricula = solicitarCadena("Introduce matricula: ");
		Coche coche;

	

		Persona persona = new Persona(nombre, 0); // consultar todas las personas
												// que tienen esa nombre

		ObjectSet<Persona> result = db.queryByExample(persona);

		if (result.size() != 1)
			System.out.println("No existen persona o  hay más de una persona con ese nombre");
		else {
			persona = result.next();
			coche = localizarCocheOCrearNuevo(db, matricula);
			persona.addCoche(coche);
			System.out.println(persona);
			db.store(persona);
		}

		

	}

	/**
	 * Busca un coche por su matricula en la BD. Si no lo encuentra devuelve el
	 * nuevo objeto coche
	 * 
	 * @param db
	 * @param matricula
	 * @return el coche localizado en la BD, y si no lo encuentra un nuevo
	 *         objeto coche.
	 */
	private static Coche localizarCocheOCrearNuevo(ObjectContainer db, String matricula) {

		Coche coche = new Coche(matricula);

		ObjectSet<Coche> result = db.queryByExample(coche);

		if (result.size() != 0)
			coche = result.next();

		return coche;
	}

	/**
	 * Busca un coche por su matricula, devuelve null si no lo encuenta
	 * 
	 * @param db
	 * @param matricula
	 * @return coche localizado o null si no lo encuentra
	 */
	private static Coche localizarCoche( String matricula) {

		Coche coche = new Coche(matricula);

		ObjectSet<Coche> result = db.queryByExample(coche);

		if (result.size() != 0)
			coche = result.next();
		else
			coche = null;

		return coche;
	}

	private static Coche insertarCoche() {

		
		String matricula = solicitarCadena("Introduce matricula: ");
		Coche coche = new Coche(matricula);

		db.store(coche);

		
		return coche;
	}

	private static int solicitarOpcion() {
		int opc;
		System.out.println("1.Insertar persona en  BD");
		System.out.println("2.Consutar BD completa");
		System.out.println("3.Consultar todas personas");
		System.out.println("4.Consultar personas con edad mayor a ...");
		System.out.println("5.Consultar personas con edad mayor a .... ordenadas por nombre");
		System.out.println("6.Insertar coche en BD");
		System.out.println("7.Asignar un coche a una persona");
		System.out.println("8.Borrar una persona (junto con sus coches)");
		System.out.println("9.Insertar garaje a un coche");
		System.out.println("10.Insertar multa a un coche");
		System.out.println("11.Consultar datos de primer nivel de la persona");
		System.out.println("12.Salir");
		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > 12);
		return opc;
	}

	private static void consultarBd(Object objetoPatron) {

		
		ObjectSet<Object> result = db.queryByExample(objetoPatron);

		if (result.size() == 0)
			System.out.println("BD Vacia");
		else {
			System.out.println("Numero de " + objetoPatron.getClass().getName() + " " + result.size());
			while (result.hasNext()) {
				objetoPatron = result.next();
				System.out.println(objetoPatron);
			}
		}
		

	}

	private static void consultarPersonasConEdadMayorAOrdenadosPorNombre(int edad) {

		
		final int edadFinal = edad;

	
		 ObjectSet<Persona> result = db.query(
				new Predicate<Persona>() {
					@Override
					public boolean match(Persona p) {
						return (p.getEdad() > edadFinal);
					}
				}
		,
		 
		new QueryComparator<Persona>(){

			@Override
			public int compare(Persona persona1, Persona persona2) {
				return persona1.getNombre().compareTo(persona2.getNombre());
			}
			
		}
		 
		)
		;
		 
		listarObjectSetPersona(result);
		
		 
	}
	
	private static void consultarPersonasConEdadMayorA( int edad) {

		
		 final int edadFinal = edad;

		// Primera forma
		//	ObjectSet<Persona> result = db.query(new Predicado(edad));

		// Segunda forma
		 ObjectSet<Persona> result = db.query(new Predicate<Persona>() {
			 @Override
			 public boolean match(Persona p) {
				 return p.getEdad() > edadFinal;
			 }
		 }
		 );
		 
		 
	
		listarObjectSetPersona(result);

		

	}

	private static void listarObjectSetPersona(ObjectSet<Persona> result) {
		Persona persona;
		if (result.size() == 0)
			System.out.println("No hay personas con esas condiciones");
		else {
			while (result.hasNext()) {
				persona = result.next();
				System.out.println(persona);
			}
		}
	}

	private static void consultarPersonas() {

		
		Persona personaPatron = new Persona(null, 0);

		ObjectSet<Persona> result = db.queryByExample(personaPatron);

		if (result.size() == 0)
			System.out.println("BD Vacia");
		else {
			System.out.println("Numero de personas  son: " + result.size());
			while (result.hasNext()) {
				personaPatron = result.next();
				System.out.println(personaPatron);
			}
		}

	

	}

	private static void insertarPersonaEnBd() {

		
		char resp;
		Persona persona = crearPersona();
		Coche c;

		resp = solicitarTieneCoche("Tiene coche?");
		while (resp == 'S') {

			c = insertarCoche();
			persona.addCoche(c);

			resp = solicitarTieneCoche("Tiene otro coche?");
		}
		while (resp == 'S')
			;

		db.store(persona);

		

	}

	private static char solicitarTieneCoche(String msg) {
		char resp;
		System.out.println(msg);
		resp = teclado.nextLine().charAt(0);
		resp = Character.toUpperCase(resp);
		return resp;
	}

	private static Persona crearPersona() {
		Persona p = new Persona(solicitarCadena("Introduce el nombre: "), solicitarEdad());

		return p;
	}

	private static int solicitarEdad() {
		int edad;
		do {
			System.out.println("Introduce edad:");
			edad = Integer.parseInt(teclado.nextLine());
		} while (edad < 0);
		return edad;
	}

	private static String solicitarCadena(String msg) {
		String nombre;
		System.out.println(msg);
		nombre = teclado.nextLine();
		return nombre;
	}

}
