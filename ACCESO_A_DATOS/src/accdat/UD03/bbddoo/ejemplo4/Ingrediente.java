package accdat.UD03.bbddoo.ejemplo4;

public class Ingrediente {
	
	private String ingrediente;
	private int cantidad;
	
	public Ingrediente() {
		
	}
	public Ingrediente (String ingrediente) {
		this.ingrediente=ingrediente;
	}
	public Ingrediente(String ingrediente, int cantidad) throws RecetaException {
		setIngrediente(ingrediente);
		setCantidad(cantidad);
	}

	public String getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(String ingrediente) {
		this.ingrediente = ingrediente.toUpperCase();
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) throws RecetaException {
		if (cantidad <=0) {
			throw new RecetaException("Cantidad incorrecta");
		}
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "Ingrediente [ingrediente=" + ingrediente + ", cantidad=" + cantidad + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingrediente == null) ? 0 : ingrediente.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		if (ingrediente == null) {
			if (other.ingrediente != null)
				return false;
		} else if (!ingrediente.equals(other.ingrediente))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
}
