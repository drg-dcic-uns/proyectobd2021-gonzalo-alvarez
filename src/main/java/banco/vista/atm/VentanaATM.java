package banco.vista.atm;

import java.util.ArrayList;
import banco.controlador.ControladorATM;
import banco.modelo.atm.TransaccionCajaAhorroBean;
import banco.vista.Ventana;

public interface VentanaATM extends Ventana {
	
	
	/**
	 * Informa a la vista quien es su controlador de Login
	 * 
	 * @param controlador
	 */
	public void registrarControlador(ControladorATM controlador);	
	
	public final static String OPCIONES = "opciones";
	public final static String SALDO = "saldo";
	public final static String EXTRACCION = "extraccion";
	public final static String TRANSFERENCIA = "transferencia";
	public final static String ULTMOVIMIENTOS = "ultmovimientos";
	public final static String MOVXPERIODO = "movxperiodo";

	/**
	 * muestra la información del cliente conectado que corresponde a la opcion seleccionada 
	 * 
	 * @param opcion las opciones validas son "saldo", "extraccion", "transferencia", "ultmovimientos" y "movxperiodo"
	 * 
	 * Puede utilizar las constantes VentanaATM.SALDO, VentanaATM.EXTRACCION, VentanaATM.TRANSFERENCIA, VentanaATM.ULTMOVIMIENTOS y VentanaATM.MOVXPERIODO.
	 */
	public void mostrarPanel(String opcion);
	
	/**
	 * Muestra el saldo actula de la caja de ahorro
	 * @param saldo
	 */		
	public void mostrarSaldo(double saldo);
	
	
	/**
	 * Muestra en una tabla la lista de las últimas transaciones realizadas sobre la caja de ahorro
	 * @param lista
	 */
	public void mostrarUltimosMovimientos(ArrayList<TransaccionCajaAhorroBean> lista);
	
	
	/**
	 * Mostrar una lista de movimientos por Período
	 * @param lista
	 */	
	public void mostrarMovimientosPorPeriodo(ArrayList<TransaccionCajaAhorroBean> lista);	

	
	/**
	 * Solicita al usuario que confirme que desea realizar la extracción
	 * @return
	 */
	public boolean confirmaExtraccion();
	
	/**
	 * Elimina los valores de todas las componentes requeridas para realizar una extracción, dejando un estado inicial consistente. 
	 */
	public void limpiarValoresExtraccion();
	
	/**
	 * Muestra el saldo de la cuenta como resultado de una transacción de extracción existosa.
	 * 
	 * @param saldo
	 */
	public void informarSaldo(Double saldo);

	/**
	 * Solicita al usuario que confirme que desea realizar la transferencia
	 * @return
	 */
	public boolean confirmaTransferencia();
	
	/**
	 * Elimina de todas las componentes los valores seleccionados por el usuario, dejando un estado inicial consistente.
	 *  
	 */
	public void limpiarValoresTransferencia();
	
}
