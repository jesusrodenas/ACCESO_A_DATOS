package accdat.UD03.bbddoo.ejemplo2;

import java.util.Scanner;


import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;

public class Principal {

	private static final String BD_PERSONAS = "bd.oo";
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int opc;

		do {
			opc = solicitarOpcion();
			tratarOpcion(opc);
		} while (opc != 6);

	}

	private static ObjectContainer abrirBd() {
		
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		
		// Aqui no se está modificando
		//config.common().objectClass(Persona.class).cascadeOnUpdate(true);
		config.common().objectClass(Persona.class).cascadeOnDelete(false);
		
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
			insertarPersonaEnBd();
			break;

		case 2:
			System.out.println("Personas:");
			consultarBd(new Persona());
			
			System.out.println("Coches:");
			consultarBd(new Coche());
			
			
			System.out.println("Perros:");
			consultarBd(new Perro());
	
			break;
		case 3:
			edad = solicitarEdad();
			consultarPersonasConEdad(edad);
			break;
		case 4:
			insertarCoche();
			break;
			
		case 5:
			borrarPersona();
			break;
		}

	}

	
	


	private static void borrarPersona() {
	
		String nombre = solicitarNombre();
		Persona personaPatron = new Persona(nombre, 0); // consultar todas las personas
												// que tienen ese nombre
		Persona persona;

		ObjectContainer db = abrirBd();
		ObjectSet<Persona> result = db.queryByExample(personaPatron);

		if (result.size() != 1)
			System.out.println("BD Vacia o mas de una persona con ese nombre");
		else {
			persona = result.next();
			System.out.println(persona);
			// Antes de borrar la persona borrar sus coches
			for (Coche coche: persona.getCoches()){
				db.delete(coche);
			}
			db.delete(persona);
			// El perro no se borrará porque no esta activado cascadeOnDelete
			System.out.println("Despues" + persona);
			
		}

		
		
		
		cerrarBd(db);
				
				
	}

	private static void insertarCoche() {

		ObjectContainer db = abrirBd();
		String matricula = solicitarMatricula();
		db.store(new Coche(matricula));

		cerrarBd(db);
	}

	private static int solicitarOpcion() {
		int opc;
		System.out.println("1.Insertar persona en  BD");
		System.out.println("2.Consutar BD completa");
		System.out.println("3.Consultar personas con una edad");
		System.out.println("4.Insertar un coche");
		System.out.println("5.Borrar persona (junto con sus coches y no con sus perro)");
		System.out.println("6.Salir");
		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > 6);
		return opc;
	}

	private static void consultarBd(Object patron) {

		ObjectContainer db = abrirBd();
		ObjectSet<Object> result = db.queryByExample(patron);
		Object objeto;

		if (result.size() == 0)
			System.out.println("BD Vacia");
		else {
			System.out.println("Numero de " + patron.getClass().getName() + " " + result.size());
			while (result.hasNext()) {
				objeto = result.next();
				System.out.println(objeto);
			}
		}
		cerrarBd(db);

	}

	private static void consultarPersonasConEdad(int edad) {

		ObjectContainer db = abrirBd();

		Persona personaPatron = new Persona(null, edad); // consultar todas las personas
												// que tienen esa edad
		Persona persona;

		ObjectSet<Persona> result = db.queryByExample(personaPatron);

		if (result.size() == 0)
			System.out.println("BD Vacia");
		else {
			System.out.println("Numero de personas con edad  " + edad + " son: " + result.size());
			while (result.hasNext()) {
				persona = result.next();
				
				System.out.println(persona);
			}
		}

		cerrarBd(db);

	}

	private static void insertarPersonaEnBd() {

		ObjectContainer db=abrirBd();
		char resp;
		Persona persona = crearPersona();

		resp = solicitarTieneCoche("Tiene coche?");
		while (resp=='S'){
			
			Coche coche = new Coche(solicitarMatricula());
			persona.addCoche(coche);
			
		
			resp=solicitarTieneCoche("Tiene otro coche?");
		}while (resp=='S');
		
		resp = solicitarTieneCoche("Tiene perro?");
		if ( resp=='S'){
			Perro perro=new Perro(solicitarNombre());
			persona.setPerro(perro);
			
		}

		db.store(persona);
		
		cerrarBd(db);

	}

	

	private static char solicitarTieneCoche(String msg) {
		char resp;
		System.out.println(msg);
		resp = teclado.nextLine().charAt(0);
		resp = Character.toUpperCase(resp);
		return resp;
	}

	private static Persona crearPersona() {
		Persona p = new Persona(solicitarNombre(), solicitarEdad());

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

	private static String solicitarNombre() {
		String nombre;
		System.out.println("Introduce el nombre:");
		nombre = teclado.nextLine();
		return nombre;
	}

	private static String solicitarMatricula() {
		String nombre;
		System.out.println("Introduce la matricula:");
		nombre = teclado.nextLine();
		return nombre;
	}

}
