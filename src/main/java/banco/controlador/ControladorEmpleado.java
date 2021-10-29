package banco.controlador;

import java.util.List;

public interface ControladorEmpleado extends ControladorBanco {

	public void cerrarSesion();

	public void salirAplicacion();

	/*
	 * Metodos que informan al controlador que debe mostrar una ventana en particular 
	 */
	public void mostrarPrestamoNuevo();

	public void mostrarPrestamoGestionCuotas();

	public void mostrarPrestamoClientesMorosos();

	/*
	 * Métodos para realizar las operaciones
	 */
	
	/**
	 * Informa al controlador que se ha ingresado un documento y se desea obtener información de dicho cliente
	 * para realizar un nuevo prestamo.
	 * 
	 * @param tipo documento en formato String
	 * @param DNI en formato String
	 */
	public void prestamoNuevoSeleccionaCliente(String p_tipo, String p_dni);

	/**
	 * Informa al controlador que se ha ingresado un monto. Con esta información se recupera la cantidad de meses disponibles.
	 * 
	 * @param monto
	 */
	public void prestamoNuevoIngresaMonto(String monto);

	/**
	 * Informa al controlador que se desea cambiar el cliente, el controlador simplemente informa a la ventana que limpie sus
	 * datos. Se podría haber evitado llamar al controlador y manejarlo directamente en el adaptador pero se informa al controlador
	 * por las dudas que el mismo o el modelo deban limpiar alguna variable de session.
	 * 
	 */
	public void prestamoNuevoCambiarCliente();

	/**
	 * Informa al controlador que se desea cambiar el monto, el controlador simplemente informa a la ventana que limpie sus
	 * datos. Se podría haber evitado llamar al controlador y manejarlo directamente en el adaptador pero se informa al controlador
	 * por las dudas que el mismo o el modelo deban limpiar alguna variable de session.
	 * 
	 */
	public void prestamoNuevoCambiarMonto();

	/** 
	 * Indica al controlador que se seleccionó una cantidad de meses para un nuevo prestamo y debe buscar la información sobre 
	 * la tasa, el interes y el valor de la cuota que le corresponde a dicho monto según la cantidad de meses.
	 * 
	 * @param monto
	 * @param cantidadMeses
	 */
	public void prestamoNuevoObtenerTasaPrestamo(String monto, String cantidadMeses);

	/**
	 * Solicita la creación del prestamo.
	 * 
	 * Los valores de la tasa, interes, cuota debe calcularlos nuevamente porque no debe confiar en lo que puede decirle la vista al respecto.
	 * 
	 * @param tipoDocumento
	 * @param dni
	 * @param monto
	 * @param cantidadMeses
	 */
	public void prestamoNuevoCrearPrestamo(String tipoDocumento, String dni, String monto, String cantidadMeses);

			
	
	public void pagoCuotaSeleccionaCliente(String p_tipo, String p_dni);

	public void pagoCuotaCambiarCliente();

	/**
	 * Solicita realizar el pago de las cuotas presentes en la lista.
	 * 
	 * Debe validar los datos ya que no guarda el controlador los datos antes provistos. Por las dudas, es mejor volver a verificarlos
	 * en caso que la ventana funcione erroneamente o haya alguna aplicación maligna del otro lado.
	 * 
	 * @param p_tipo tipo Documento
	 * @param p_dni
	 * @param nroPrestamo
	 * @param cuotasAPagar
	 */
	public void pagoCuotaPagar(String p_tipo, String p_dni, Integer nroPrestamo, List<Integer> cuotasAPagar);

}
