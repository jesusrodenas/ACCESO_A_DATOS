package accdat.UD03.bbddoo.ejemplo3;
import java.util.ArrayList;

public class Coche {
	private String matricula;
	private ArrayList<Multa> multas;
	private Garaje garaje;

	public Coche() {
		multas = new ArrayList<Multa>();
	}
	
	public Coche(String matricula) {
		super();
		multas = new ArrayList<Multa>();
		this.matricula = matricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int numeroDeMultas() {
		return multas.size();
	}
	

	public Garaje getGaraje() {
		return garaje;
	}

	public void setGaraje(Garaje garaje) {
		this.garaje = garaje;
	}

	@Override
	public String toString() {
		String resultado;
		if (multas==null || multas.size() == 0)
			resultado = "Coche [matricula=" + matricula + "]";
		else
			resultado = "Coche [matricula=" + matricula + "]" + "Multas " + multas;
		
		if ( garaje!= null)
			resultado= resultado + "Garaje " + garaje;
		
		return resultado;
	}

	public void addMulta(Multa multa) {
		multas.add(multa);
	}
	
	

}
