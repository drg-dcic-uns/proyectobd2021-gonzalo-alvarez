package banco.modelo.empleado.beans;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class ClienteMorosoBeanImpl implements Serializable, ClienteMorosoBean {

	private static Logger logger = LoggerFactory.getLogger(ClienteMorosoBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private ClienteBean cliente;
	private PrestamoBean prestamo;
	private int cantidadCuotasAtrasadas;
	
	
	@Override
	public ClienteBean getCliente() {
		return this.cliente;
	}

	@Override
	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}

	@Override
	public PrestamoBean getPrestamo() {
		return this.prestamo;
	}

	@Override
	public void setPrestamo(PrestamoBean prestamo) {
		this.prestamo = prestamo;
	}

	@Override
	public int getCantidadCuotasAtrasadas() {
		return this.cantidadCuotasAtrasadas;
	}

	@Override
	public void setCantidadCuotasAtrasadas(int cantidadCuotasAtrasadas) {
		this.cantidadCuotasAtrasadas = cantidadCuotasAtrasadas;		
	}
	
	
}