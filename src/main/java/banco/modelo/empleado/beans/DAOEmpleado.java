package banco.modelo.empleado.beans;

public interface DAOEmpleado {

	/**
	 * Recupera el empleado según el legajo.  
	 *  
	 * @param legajo
	 * @return Un empleado que corresponde al legajo. Si no existe ese legajo retorna null.
	 * @throws Exception si hubo un error de conexión
	 */
	public EmpleadoBean recuperarEmpleado(int legajo) throws Exception;
		
	
}
