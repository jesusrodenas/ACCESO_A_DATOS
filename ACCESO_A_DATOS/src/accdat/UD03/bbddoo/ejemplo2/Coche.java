package accdat.UD03.bbddoo.ejemplo2;

public class Coche {
	private String matricula;
	
	Coche(){
		
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Coche(String matricula) {
		super();
		this.matricula = matricula;
	}

	@Override
	public String toString() {
		return "Coche [matricula=" + matricula + "]";
	}
	
	
}
