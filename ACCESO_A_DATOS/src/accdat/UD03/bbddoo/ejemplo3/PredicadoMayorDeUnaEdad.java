package accdat.UD03.bbddoo.ejemplo3;
import com.db4o.query.Predicate;

public class PredicadoMayorDeUnaEdad extends Predicate<Persona> {
	
	private int edad;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	PredicadoMayorDeUnaEdad (int edad){
		this.edad=edad;
	}

	
	@Override
	public boolean match(Persona persona) {
		
		return (persona.getEdad() > edad)  ;
	}

}
