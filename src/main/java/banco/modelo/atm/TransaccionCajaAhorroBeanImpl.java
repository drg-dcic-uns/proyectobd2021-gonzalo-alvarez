package banco.modelo.atm;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.utils.Fechas;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class TransaccionCajaAhorroBeanImpl implements Serializable, TransaccionCajaAhorroBean {

	private static Logger logger = LoggerFactory.getLogger(TransaccionCajaAhorroBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private int cajaAhorroNumero;
	private double cajaAhorroSaldo;
	private int transaccionNumero;
	private Date transaccionFechaHora;
	private String transaccionTipo;  // débito, extracción, transferencia, depósito
	private double transaccionMonto;
	private int transaccionCodigoCaja; // Caja donde se realizó la transacción
	private int cajaAhorroDestinoNumero; 
	private int clienteNro;
	private int clienteDoc;
	private String clienteNombre;
	private String clienteApellido;

	public TransaccionCajaAhorroBeanImpl() {		
	}

	@Override
	public int getCajaAhorroNumero() {
		return cajaAhorroNumero;
	}

	@Override
	public void setCajaAhorroNumero(int cajaAhorroNumero) {
		this.cajaAhorroNumero = cajaAhorroNumero;
	}

	@Override
	public double getCajaAhorroSaldo() {
		return cajaAhorroSaldo;
	}

	@Override
	public void setCajaAhorroSaldo(double cajaAhorroSaldo) {
		this.cajaAhorroSaldo = cajaAhorroSaldo;
	}

	@Override
	public int getTransaccionNumero() {
		return transaccionNumero;
	}

	@Override
	public void setTransaccionNumero(int transaccionNumero) {
		this.transaccionNumero = transaccionNumero;
	}

	@Override
	public Date getTransaccionFechaHora() {
		return transaccionFechaHora;
	}

	@Override
	public void setTransaccionFechaHora(Date transaccionFechaHora) {
		this.transaccionFechaHora = transaccionFechaHora;
	}

	@Override
	public String getTransaccionTipo() {
		return transaccionTipo;
	}

	@Override
	public void setTransaccionTipo(String transaccionTipo) {
		this.transaccionTipo = transaccionTipo;
	}

	@Override
	public double getTransaccionMonto() {
		return transaccionMonto;
	}

	@Override
	public void setTransaccionMonto(double transaccionMonto) {
		this.transaccionMonto = transaccionMonto;
	}

	@Override
	public int getTransaccionCodigoCaja() {
		return transaccionCodigoCaja;
	}

	@Override
	public void setTransaccionCodigoCaja(int transaccionCodigoCaja) {
		this.transaccionCodigoCaja = transaccionCodigoCaja;
	}

	@Override
	public int getCajaAhorroDestinoNumero() {
		return cajaAhorroDestinoNumero;
	}

	@Override
	public void setCajaAhorroDestinoNumero(int cajaAhorroDestinoNumero) {
		this.cajaAhorroDestinoNumero = cajaAhorroDestinoNumero;
	}

	@Override
	public int getClienteNro() {
		return clienteNro;
	}

	@Override
	public void setClienteNro(int clienteNro) {
		this.clienteNro = clienteNro;
	}

	@Override
	public int getClienteDoc() {
		return clienteDoc;
	}

	@Override
	public void setClienteDoc(int clienteDoc) {
		this.clienteDoc = clienteDoc;
	}

	@Override
	public String getClienteNombre() {
		return clienteNombre;
	}

	@Override
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	@Override
	public String getClienteApellido() {
		return clienteApellido;
	}

	@Override
	public void setClienteApellido(String clienteApellido) {
		this.clienteApellido = clienteApellido;
	}

	@Override
	public String getTransaccionFecha() {
		return Fechas.convertirDateAString(this.transaccionFechaHora);
	}

	@Override
	public String getTransaccionHora() {
		return Fechas.convertirDateAHoraString(this.transaccionFechaHora);
	}
	
	
	
}
