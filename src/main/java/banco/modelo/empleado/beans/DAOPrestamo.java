package banco.modelo.empleado.beans;

public interface DAOPrestamo {

	/**
	 * Crea o actualiza el Prestamo segun el Bean. 
	 * 
	 *  Si el bean tiene nroPrestamo es una actualizacion. Si el nro de prestamo en null es un nuevo prestamo.
	 * 
	 * @throws Exception
	 */
	public void crearActualizarPrestamo(PrestamoBean prestamo) throws Exception;


	/**
	 * Obtiene el prestamo según el id nroPrestamo
	 * 
	 * @param nroPrestamo
	 * @return Un prestamo que corresponde a ese id o null
	 * @throws Exception si hubo algun problema de conexión
	 */
	public PrestamoBean recuperarPrestamo(int nroPrestamo) throws Exception;
		
	
}
