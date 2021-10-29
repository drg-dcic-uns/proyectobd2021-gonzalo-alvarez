package banco.modelo.empleado.beans;

import java.util.Date;

public interface PagoBean {
	/**
	 * @return the nroPrestamo
	 */
	int getNroPrestamo();

	/**
	 * @param nroPrestamo the nroPrestamo to set
	 */
	void setNroPrestamo(int nroPrestamo);

	/**
	 * @return the nroPago
	 */
	int getNroPago();

	/**
	 * @param nroPago the nroPago to set
	 */
	void setNroPago(int nroPago);

	/**
	 * @return the fechaVencimiento
	 */
	Date getFechaVencimiento();

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	void setFechaVencimiento(Date fechaVencimiento);

	/**
	 * @return the fechaPago
	 */
	Date getFechaPago();

	/**
	 * @param fechaPago the fechaPago to set
	 */
	void setFechaPago(Date fechaPago);
	

}
