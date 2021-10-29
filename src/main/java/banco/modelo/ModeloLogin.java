package banco.modelo;

import java.util.List;


public interface ModeloLogin { 

	/**
	 * 
	 * @return Lista de nombres de usuarios (displayname) de la base de datos que sean legibles a ser mostrados por pantalla
	 */
	public List<String> obtenerNombresUsuarios(); 
	
	/**
	 * Verifica que el usuario y clave provistos correspondan al displayname seleccionado
	 * 
	 * @param displayname
	 * @param usuario
	 * @param clave luego de validar la clave se llena de 0s para que no pueda buscarse en la memoria la clave.
	 * @return 
	 */	
	public boolean validar(String displayname, String usuario, char[] clave);
	
	/**
	 * Realiza la carga de propiedades para establecer la conexión
	 * 
	 * @exception Si la carga de parametros falla.
	 */
	public void iniciarConexion() throws Exception;
	
	/**
	 * Recupera la información del usuario según el displayname
	 * 
	 * @param displayname valor que aparece en la componente que se muestra al usuario, nombre representativo
	 * 
	 * @return información del usuario
	 */
	public UsuarioBean obtenerUsuario(String displayname);
}
