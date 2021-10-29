package banco.vista.login;

import java.util.List;

import banco.controlador.ControladorLogin;
import banco.vista.Ventana;

public interface VentanaLogin extends Ventana {

	/**
	 * Informa a la vista quien es su controlador de Login
	 * 
	 * @param controlador
	 */
	public void registrarControlador(ControladorLogin controlador);
		
	
	/**
	 * Llena un combobox con una lista de nombres de tipos de usaurio
	 * @param obtenerNombresUsuarios
	 */
	public void poblarComboTipoUsuario(List<String> obtenerNombresUsuarios);
	
}
