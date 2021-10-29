package banco.modelo.empleado.beans;

public interface DAOCliente {

		
	/**
	 * Recupera el cliente que tenga un documento que se corresponda con los parámetros recibidos.  
	 *  
	 * @param tipoDoc
	 * @param nroDoc
	 * @return
	 * @throws Exception Si no existe dicho cliente o hay un error de conexión.
	 */
	public ClienteBean recuperarCliente(String tipoDoc, int nroDoc) throws Exception;

	/**
	 * Recupera el cliente por nroCliente si no lo encuentra retorna null
	 * 
	 * @param nroCliente
	 * @return
	 * @throws Exception	 * 
	 */
	public ClienteBean recuperarCliente(Integer nroCliente) throws Exception;
		
	
}
