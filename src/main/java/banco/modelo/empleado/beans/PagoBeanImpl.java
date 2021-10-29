package banco.modelo.empleado.beans;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class PagoBeanImpl implements Serializable, PagoBean {

	private static Logger logger = LoggerFactory.getLogger(PagoBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private int nroPrestamo;	
	private int nroPago;
	private Date fechaVencimiento;
	private Date fechaPago;

	@Override
	public int getNroPrestamo() {
		return nroPrestamo;
	}
	@Override
	public void setNroPrestamo(int nroPrestamo) {
		this.nroPrestamo = nroPrestamo;
	}
	@Override
	public int getNroPago() {
		return nroPago;
	}
	@Override
	public void setNroPago(int nroPago) {
		this.nroPago = nroPago;
	}
	@Override
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	@Override
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	@Override
	public Date getFechaPago() {
		return fechaPago;
	}
	@Override
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
}
