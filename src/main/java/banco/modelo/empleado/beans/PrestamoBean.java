package banco.modelo.empleado.beans;

import java.util.Date;

public interface PrestamoBean {
	
	/**
	 * @return the nroPrestamo o null
	 */
	Integer getNroPrestamo();

	/**
	 * @param nroPrestamo the nroPrestamo to set
	 */
	void setNroPrestamo(Integer nroPrestamo);

	/**
	 * @return the fecha
	 */
	Date getFecha();

	/**
	 * @param fecha
	 */
	void setFecha(Date fecha);

	/**
	 * @return the cantidadMeses
	 */
	int getCantidadMeses();

	/**
	 * @param CantidadMeses
	 */
	void setCantidadMeses(int cantidadMeses);
		
	/**
	 * @return the monto
	 */
	double getMonto();

	/**
	 * @param monto
	 */
	void setMonto(double monto);
		
	/**
	 * @return the tasaInteres
	 */
	double getTasaInteres();

	/**
	 * @param monto
	 */
	void setTasaInteres(double tasaInteres);
	
	/**
	 * @return the interes
	 */
	double getInteres();

	/**
	 * @param interes
	 */
	void setInteres(double interes);
	
	/**
	 * @return the valorCuota
	 */
	double getValorCuota();

	/**
	 * @param valorCuota
	 */
	void setValorCuota(double valorCuota);	
	
	/**
	 * @return legajo
	 */
	Integer getLegajo();

	/**
	 * @param legajo
	 */
	void setLegajo(Integer legajo);

	// Aqui en lugar de un int puede ir un objeto ClienteBean
	/**
	 * @return nroCliente
	 */
	Integer getNroCliente();

	/**
	 * @param nroCliente
	 */
	void setNroCliente(Integer nroCliente);

}
