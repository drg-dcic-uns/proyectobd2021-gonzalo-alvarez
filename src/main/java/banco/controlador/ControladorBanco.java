package banco.controlador;

public interface ControladorBanco {
	
	/**
	 * Muestra la ventana para la interacción del usuario.
	 * 
	 * Se espera que esta acción se realice posteriormente de haber realizado una conexión exitosa.
	 */
	public void ejecutar();

	/**
	 * Método que cierra la sesión del usuario actual y vuelve a la ventana de login
	 */
	public void cerrarSesion();
	
	/**
	 * Método que permite salir en forma segura de la aplicación.
	 * 
	 * Informa al modelo para que realice la desconexión de la BD conexion.desconectarBD();
	 * Realiza cualquier otro ajuste necesario (dispose de objetos)
	 * System.exit(0);
	 */	
	public void salirAplicacion();	
}
