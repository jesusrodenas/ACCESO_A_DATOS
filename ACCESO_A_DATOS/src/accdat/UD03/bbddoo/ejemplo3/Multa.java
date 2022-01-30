package accdat.UD03.bbddoo.ejemplo3;

public class Multa {
	private String textoMulta;
	

	public Multa(String textoMulta) {
		super();
		this.textoMulta = textoMulta;
	}

	public Multa() {
		
	}

	public String getTextoMulta() {
		return textoMulta;
	}

	public void setTextoMulta(String textoMulta) {
		this.textoMulta = textoMulta;
	}

	@Override
	public String toString() {
		return "Multa [textoMulta=" + textoMulta + "]";
	}
	
	
}
