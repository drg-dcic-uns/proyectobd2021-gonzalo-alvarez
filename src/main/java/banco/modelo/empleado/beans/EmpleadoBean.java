package banco.modelo.empleado.beans;

public interface EmpleadoBean {
	
	/**
	 * @return el legajo
	 */
	int getLegajo();

	/**
	 * @param Legajo
	 */
	void setLegajo(int legajo);

	/**
	 * @return the apellido
	 */
	String getApellido();

	/**
	 * @param apellido the apellido to set
	 */
	void setApellido(String apellido);

	/**
	 * @return the nombre
	 */
	String getNombre();

	/**
	 * @param nombre the nombre to set
	 */
	void setNombre(String nombre);

	/**
	 * @return the tipoDocumento
	 */
	String getTipoDocumento();

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	void setTipoDocumento(String tipoDocumento);

	/**
	 * @return the nroDocumento
	 */
	int getNroDocumento();

	/**
	 * @param nroDocumento the nroDocumento to set
	 */
	void setNroDocumento(int nroDocumento);

	/**
	 * @return the direccion
	 */
	String getDireccion();

	/**
	 * @param direccion the direccion to set
	 */
	void setDireccion(String direccion);

	/**
	 * @return the telefono
	 */
	String getTelefono();

	/**
	 * @param telefono the telefono to set
	 */
	void setTelefono(String telefono);

	/**
	 * @return Cargo
	 */
	String getCargo();

	/**
	 * @param Cargo
	 */
	void setCargo(String cargo);
	
	/**
	 * @return Password
	 */
	String getPassword();

	/**
	 * @param Password
	 */
	void setPassword(String password);
	
	/**
	 * @return the nroSucursal
	 */
	int getNroSucursal();

	/**
	 * @param nroSucursal the nroSucursal to set
	 */
	void setNroSucursal(int nroSucursal);	
	
}
