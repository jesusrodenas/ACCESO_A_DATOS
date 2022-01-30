package accdat.UD03.bbddoo.ejemplo3;

import com.db4o.query.QueryComparator;

public class ComparadorPorNombre implements QueryComparator<Persona> {

	@Override
	public int compare(Persona persona1, Persona persona2) {
		return persona1.getNombre().compareTo(persona2.getNombre());
	}

}
