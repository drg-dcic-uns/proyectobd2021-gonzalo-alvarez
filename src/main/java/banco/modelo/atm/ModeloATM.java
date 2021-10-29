package banco.modelo.atm;

import java.util.ArrayList;
import java.util.Date;

import banco.modelo.Modelo;

public interface ModeloATM extends Modelo {
	
	public final static int ULTIMOS_MOVIMIENTOS_CANTIDAD = 15;
	
	public static final String CONFIG = "cfg/config.properties";
	
	/**
	 * Mensaje que retorna el SP <b>extraer</b> cuando tiene exito.
	 */
	public static final String EXTRACCION_EXITOSA = "Extraccion Exitosa";

	/**
	 * Mensaje que retorna el SP <b>transferir</b> cuando tiene exito.
	 */
	public static final String TRANSFERENCIA_EXITOSA = "Transferencia Exitosa";
	
	/**
	 * Verifica que el usuario con el que intenta realizar el login corresponde a un cliente del banco.
	 * Antes de poder autenticar deberá estar conectado a la BD con un usuario de BD. ver conectar/2
	 *  
	 * @param tarjeta
	 * @param pin string plano que debe convertirse a MD5 para consultar en la BD
	 * @return boolean verdadero si autentica exitosamente y falso si no exite la tarjeta, o el pin no corresponde, 
	 * o si se produce una excepción en la conexión. 
	 */
	public boolean autenticarUsuarioAplicacion(String tarjeta, String pin) throws Exception;
	
	/**
	  * Método encargado de obtener el saldo del cliente
	  * 
	  * @throws Exception Si no hay una tarjeta autenticada 
	  */
	public Double obtenerSaldo() throws Exception;

	/**
	 * Recupera los ultimas transacciones realizadas por el cliente. 
	 * La cantidad máxima de transacciones a recuperar se especifica en el parámetro cantidad.
	 * 
	 * @param cantidad
	 */
	public ArrayList<TransaccionCajaAhorroBean> cargarUltimosMovimientos(int cantidad) throws Exception;
	
	/**
	 * Recupera los ultimas transacciones realizadas por el cliente.
	 * La cantidad máxima de transacciones dependerá del valor por defecto (ModeloATM.ULTIMOS_MOVIMIENTOS_CANTIDAD)
	 * @return 
	 */
	public ArrayList<TransaccionCajaAhorroBean> cargarUltimosMovimientos() throws Exception;
	

	/**
	 * Recupera las transacciones del cliente en el periodo indicado.
	 * 
	 * @param desde Inicio del periodo
	 * @param hasta Fin del periodo
	 * @return ArrayList<TransaccionCajaAhorroBean> lista de transacciones que corresponden a dicho periodo [desde,hasta]
	 * @throws Exception Si alguna de las fechas son nulas, si la fecha desde es mayor a la fecha hasta, si la fecha hasta
	 * es mayor a la fecha actual o si se produce algún error de conexión
	 */
	public ArrayList<TransaccionCajaAhorroBean> cargarMovimientosPorPeriodo(Date desde, Date hasta) throws Exception;
		
	/**
	 * Verifica que el monto pueda convertirse de String a Double
	 *    
	 * @param monto
	 * @return Double retorna el monto luego del parse
	 * @throws Exception Si no puede realizar el parsing
	 */
	public Double parseMonto(String monto) throws Exception;
	
	/**
	  * Método encargado de extraer de la cuenta del cliente el monto especificado (ya validado) 
	  * y de obtener el saldo de la cuenta como resultado.
	  * 
	  * @param monto 
	  * @return Saldo de la cuenta luego de realizar la extracción si fue exitosa.
	  * @throws Exception Si el monto es superior al disponible en la cuenta o algún error de conexión. 
	  */
	public Double extraer(Double monto) throws Exception;

	/**
	  * Método encargado de transferir de la cuenta del cliente el monto especificado (ya validado) 
	  * a la cuenta destino
	  * y de obtener el saldo de la cuenta como resultado.
	  *
	  * @param monto	  
	  * @param cajaDestino
	  * @return Saldo de la cuenta luego de realizar la transferencia si fue exitosa.
	  * @throws Exception Si el monto es superior al disponible en la cuenta o algún error de conexión. 
	  */
	public Double transferir(Double monto, int cajaDestino) throws Exception;

	/**
	 * Verifica que la cuenta sea un entero (natural) valido y que exista la cuenta en la BD.
	 * Utilizado en operaciones como solicitudTransferencia
	 * 
	 * @param p_cuenta
	 * @return int (natural) correspondiente a una cuenta valida.
	 * @throws Exception Si no pudo hacer el parsing o la cuenta no existía.
	 */
	public int parseCuenta(String p_cuenta) throws Exception;
	
}