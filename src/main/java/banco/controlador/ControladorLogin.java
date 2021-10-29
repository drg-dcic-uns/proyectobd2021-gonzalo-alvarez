package banco.controlador;

public interface ControladorLogin { 

	/**
	 * Informa al controlador que se desea ingresar con un usuario con rol EMPLEADO
	 * 
	 * @param username
	 * @param password
	 */
	public void ingresarComoEmpleado(String legajo, char[] password);

	/**
	 * Informa al controlador que se desea ingresar con un usuario con rol CLIENTE
	 * 
	 * @param username
	 * @param password
	 */
	public void ingresarComoCliente(String tarjeta, char[] pin);
	
}
