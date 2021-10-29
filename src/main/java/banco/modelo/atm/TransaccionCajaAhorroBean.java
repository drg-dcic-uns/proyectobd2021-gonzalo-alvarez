package banco.modelo.atm;

import java.util.Date;

public interface TransaccionCajaAhorroBean {

	public String getTransaccionFecha();
	public String getTransaccionHora();
	
	/**
	 * @return the cajaAhorroNumero
	 */
	int getCajaAhorroNumero();

	/**
	 * @param cajaAhorroNumero the cajaAhorroNumero to set
	 */
	void setCajaAhorroNumero(int cajaAhorroNumero);

	/**
	 * @return the cajaAhorroSaldo
	 */
	double getCajaAhorroSaldo();

	/**
	 * @param cajaAhorroSaldo the cajaAhorroSaldo to set
	 */
	void setCajaAhorroSaldo(double cajaAhorroSaldo);

	/**
	 * @return the transaccionNumero
	 */
	int getTransaccionNumero();

	/**
	 * @param transaccionNumero the transaccionNumero to set
	 */
	void setTransaccionNumero(int transaccionNumero);

	/**
	 * @return the transaccionFechaHora
	 */
	Date getTransaccionFechaHora();

	/**
	 * @param transaccionFechaHora the transaccionFechaHora to set
	 */
	void setTransaccionFechaHora(Date transaccionFechaHora);

	/**
	 * @return the transaccionTipo
	 */
	String getTransaccionTipo();

	/**
	 * @param transaccionTipo the transaccionTipo to set
	 */
	void setTransaccionTipo(String transaccionTipo);

	/**
	 * @return the transaccionMonto
	 */
	double getTransaccionMonto();

	/**
	 * @param transaccionMonto the transaccionMonto to set
	 */
	void setTransaccionMonto(double transaccionMonto);

	/**
	 * @return the transaccionCodigoCaja
	 */
	int getTransaccionCodigoCaja();

	/**
	 * @param transaccionCodigoCaja the transaccionCodigoCaja to set
	 */
	void setTransaccionCodigoCaja(int transaccionCodigoCaja);

	/**
	 * @return the cajaAhorroDestinoNumero
	 */
	int getCajaAhorroDestinoNumero();

	/**
	 * @param cajaAhorroDestinoNumero the cajaAhorroDestinoNumero to set
	 */
	void setCajaAhorroDestinoNumero(int cajaAhorroDestinoNumero);

	/**
	 * @return the clienteNro
	 */
	int getClienteNro();

	/**
	 * @param clienteNro the clienteNro to set
	 */
	void setClienteNro(int clienteNro);

	/**
	 * @return the clienteDoc
	 */
	int getClienteDoc();

	/**
	 * @param clienteDoc the clienteDoc to set
	 */
	void setClienteDoc(int clienteDoc);

	/**
	 * @return the clienteNombre
	 */
	String getClienteNombre();

	/**
	 * @param clienteNombre the clienteNombre to set
	 */
	void setClienteNombre(String clienteNombre);

	/**
	 * @return the clienteApellido
	 */
	String getClienteApellido();

	/**
	 * @param clienteApellido the clienteApellido to set
	 */
	void setClienteApellido(String clienteApellido);

}