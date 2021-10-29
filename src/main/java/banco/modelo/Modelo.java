package banco.modelo;

import java.sql.ResultSet;

public interface Modelo {

	/**
	 * Intenta realizar la conexion a la BD con el par (username, password)
	 * 
	 * @param username
	 * @param password
	 * @return verdadero si pudo conectar.
	 */
	public boolean conectar(String username, String password);
	
	public void desconectar();
	
	/**
	  * Método encargado de realizar una consulta de SQL
	  */
	public ResultSet consulta(String sql);
	
	/**
	  * Método encargado de ejecutar una actualizacion en la base de datos con la sentencia SQL recibida por parametro
	  */
	public void actualizacion (String sql);


}
