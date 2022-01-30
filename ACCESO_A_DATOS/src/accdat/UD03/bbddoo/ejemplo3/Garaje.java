package accdat.UD03.bbddoo.ejemplo3;

public class Garaje {
	private String direccion;

	@Override
	public String toString() {
		return "Direccion=" + direccion + "]";
	}

	public Garaje(String direccion) {
		super();
		this.direccion = direccion;
	}

	public Garaje() {
		
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
}
