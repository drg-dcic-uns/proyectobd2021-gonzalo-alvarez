package banco.modelo.empleado.beans;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class PrestamoBeanImpl implements Serializable, PrestamoBean {

	private static Logger logger = LoggerFactory.getLogger(PrestamoBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private Integer nroPrestamo;
	private Date fecha;
	private int cantidadMeses;
	private double monto;
	private double tasaInteres;
	private double interes;
	private double valorCuota;	
	private Integer legajo;
	private Integer nroCliente;
	
	@Override
	public Integer getNroPrestamo() {
		return this.nroPrestamo;
	}
	@Override
	public void setNroPrestamo(Integer nroPrestamo) {
		this.nroPrestamo = nroPrestamo;		
	}
	
	@Override
	public Date getFecha() {
		return this.fecha;
	}
	@Override
	public void setFecha(Date fecha) {
		this.fecha = fecha;		
	}
	
	@Override
	public int getCantidadMeses() {
		return this.cantidadMeses;
	}
	@Override
	public void setCantidadMeses(int cantidadMeses) {
		this.cantidadMeses = cantidadMeses;		
	}
	
	@Override
	public double getMonto() {
		return this.monto;
	}
	@Override
	public void setMonto(double monto) {
		this.monto = monto;		
	}
	
	@Override
	public double getTasaInteres() {
		return this.tasaInteres;
	}
	@Override
	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;		
	}
	
	@Override
	public double getInteres() {
		return this.interes;
	}
	@Override
	public void setInteres(double interes) {
		this.interes = interes;
	}
	
	@Override
	public double getValorCuota() {
		return this.valorCuota;
	}
	@Override
	public void setValorCuota(double valorCuota) {
		this.valorCuota = valorCuota;
	}
	
	@Override
	public Integer getLegajo() {
		return this.legajo;
	}
	@Override
	public void setLegajo(Integer legajo) {
		this.legajo = legajo;
	}
	
	@Override
	public Integer getNroCliente() {
		return this.nroCliente;
	}
	@Override
	public void setNroCliente(Integer nroCliente) {
		this.nroCliente = nroCliente;		
	}


}
