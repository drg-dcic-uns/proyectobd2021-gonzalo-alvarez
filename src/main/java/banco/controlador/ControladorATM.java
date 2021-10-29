package banco.controlador;

import java.util.Date;

public interface ControladorATM extends ControladorBanco {
	
	/*
	 * El controlador se encargar de capturar todas las excepciones generadas por el modelo (o por él mismo)  y 
	 * solicitar a la ventana que informe un mensaje de error apropiado (this.ventana.informar(e.getMessage()) 
	 */
	
	/**
	 * Recibe la indicación de la vista que se seleccionó la opción que muestra el saldo.
	 */
	public void mostrarOpcionSaldo();

	/**
	 * Recibe la indicación de la vista que se quiere realizar una extracción.
	 */
	public void mostrarOpcionExtraccion();

	/**
	 * Indica al controlador que en la vista se desea realizar una extracción por el monto indicado.
	 * Debe solicitar al modelo que procese dicho valor y si es un valor válido, deberá solicitar 
	 * al usuario que confirme la operación.
	 *  
	 * @param monto String
	 */
	public void solicitudExtraccion(String monto);
	
	/**
	 * Recibe la indicación de la vista que el cliente conectado quiere realizar una transferencia.
	 */
	public void mostrarOpcionTransferencia();
	
	/**
	 * Indica al controlador que en la vista se desea realizar una transferencia por el monto indicado a la cuenta solicitada.
	 * Debe pedir al modelo que valide ambos parámetros y si es exitoso, deberá solicitar 
	 * al usuario que confirme la operación.
	 * 
	 * @param monto
	 * @param cuentaDestino
	 */
	public void solicitudTransferencia(String monto, String cuentaDestino);
	
	/**
	 * Recibe la indicación que en la vista se quiere mostrar la lista de ultimos movimientos del cliente conectado.
	 */
	public void mostrarOpcionUltimosMovimientos();

	/**
	 * Recibe la indicación que en la vista se quiere mostrar la lista de movimientos del cliente conectado.
	 * Muestra un panel inicial con la posibilidad de seleccionar el período de fechas sobre el cual
	 * se desea mostrar los movimientos.
	 * 
	 */
	public void mostrarMovimientosPorPeriodosInicial();

	/**
	 * Recibe la indicación que en la vista se quiere mostrar la lista de movimientos del cliente conectado de acuerdo al periodo seleccionado.
	 */
	public void mostrarOpcionMovimientosPorPeriodos(Date desde, Date hasta);



}
