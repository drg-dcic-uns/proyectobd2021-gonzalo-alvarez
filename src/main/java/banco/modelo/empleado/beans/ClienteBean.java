package banco.modelo.empleado.beans;

import java.util.Date;

public interface ClienteBean {
	
	/**
	 * @return the nroCliente
	 */
	int getNroCliente();

	/**
	 * @param nroCliente the nroCliente to set
	 */
	void setNroCliente(int nroCliente);

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
	 * @return the fechaNacimiento
	 */
	Date getFechaNacimiento();

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	void setFechaNacimiento(Date fechaNacimiento);
	

}
