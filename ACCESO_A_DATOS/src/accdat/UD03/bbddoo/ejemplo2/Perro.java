package accdat.UD03.bbddoo.ejemplo2;

public class Perro {
	private String nombrePerro;

	public Perro() {
		super();
	}

	public Perro(String nombrePerro) {
		super();
		this.nombrePerro = nombrePerro;
	}

	public String getNombrePerro() {
		return nombrePerro;
	}

	public void setNombrePerro(String nombrePerro) {
		this.nombrePerro = nombrePerro;
	}

	@Override
	public String toString() {
		return "Perro [nombrePerro=" + nombrePerro + "]";
	}
	
}
