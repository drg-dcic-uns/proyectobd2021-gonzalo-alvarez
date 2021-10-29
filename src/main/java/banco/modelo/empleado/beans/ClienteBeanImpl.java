package banco.modelo.empleado.beans;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class ClienteBeanImpl implements Serializable, ClienteBean {

	private static Logger logger = LoggerFactory.getLogger(ClienteBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private int nroCliente;
	private String apellido;
	private String nombre;
	private String tipoDocumento;
	private int nroDocumento;
	private String direccion;	
	private String telefono;
	private Date fechaNacimiento;

	@Override
	public int getNroCliente() {
		return nroCliente;
	}

	@Override
	public void setNroCliente(int nroCliente) {
		this.nroCliente = nroCliente;
	}

	@Override
	public String getApellido() {
		return apellido;
	}

	@Override
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	@Override
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Override
	public int getNroDocumento() {
		return nroDocumento;
	}

	@Override
	public void setNroDocumento(int nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	@Override
	public String getDireccion() {
		return direccion;
	}

	@Override
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
}
