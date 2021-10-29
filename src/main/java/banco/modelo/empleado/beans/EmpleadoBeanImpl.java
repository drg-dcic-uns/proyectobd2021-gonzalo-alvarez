package banco.modelo.empleado.beans;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class EmpleadoBeanImpl implements Serializable, EmpleadoBean {

	private static Logger logger = LoggerFactory.getLogger(EmpleadoBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private int legajo;
	private String apellido;
	private String nombre;
	private String tipoDocumento;
	private int nroDocumento;
	private String direccion;	
	private String telefono;
	private String cargo;
	private String password;
	private int nroSucursal;
	
	@Override
	public int getLegajo() {
		return legajo;
	}

	@Override
	public void setLegajo(int legajo) {
		this.legajo =  legajo;		
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
	public String getCargo() {
		return cargo;
	}

	@Override
	public void setCargo(String cargo) {
		this.cargo = cargo;		
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;		
	}

	@Override
	public int getNroSucursal() {
		return nroSucursal;
	}

	@Override
	public void setNroSucursal(int nroSucursal) {
		this.nroSucursal = nroSucursal;
		
	}


	
}
