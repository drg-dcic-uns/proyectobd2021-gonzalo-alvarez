package banco.vista.empleado;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import banco.controlador.ControladorEmpleado;
import banco.modelo.empleado.beans.ClienteBean;
import banco.modelo.empleado.beans.ClienteMorosoBean;
import banco.modelo.empleado.beans.EmpleadoBean;
import banco.modelo.empleado.beans.PagoBean;
import banco.modelo.empleado.beans.PrestamoBean;
import banco.vista.Ventana;

public interface VentanaEmpleado extends Ventana {

	public final static String VACIO = "vacio";
	public final static String PRESTAMO_NUEVO = "prestamoNuevo";
	public final static String PRESTAMO_CUOTAS = "gestionCuotas";
	public final static String PRESTAMO_MOROSOS = "clientesMorosos";
	
	public final static String TABLA_PAGOS_SELECCIONAR = "seleccionar";
	public final static String TABLA_PAGOS_NRO_PAGO = "nro. pago";
	public final static String TABLA_PAGOS_FECHA_VENCIMIENTO = "vencimiento";
	public final static String TABLA_PAGOS_FECHA_PAGO = "pagado";	

	public final static String TABLA_MOROSOS_NRO_CLIENTE = "nro_cliente";
	public final static String TABLA_MOROSOS_TIPO_DOC = "tipo_doc";
	public final static String TABLA_MOROSOS_NRO_DOC = "nro_doc";
	public final static String TABLA_MOROSOS_NOMBRE = "nombre";
	public final static String TABLA_MOROSOS_APELLIDO = "apellido";
	public final static String TABLA_MOROSOS_NRO_PRESTAMO = "nro_prestamo";
	public final static String TABLA_MOROSOS_MONTO = "monto";
	public final static String TABLA_MOROSOS_CANTIDAD_MESES = "cant_meses";
	public final static String TABLA_MOROSOS_VALOR_CUOTA = "valor_cuota";
	public final static String TABLA_MOROSOS_CUOTAS_ATRASADAS = "cuotas_atrasadas";
	
	
	public void mostrarPanel(String panel);
	
	/**
	 * Establece quien es el controlador donde deberá realizar las solicitudes
	 * 
	 * @param controlador
	 */
	public void registrarControlador(ControladorEmpleado controlador);
	
	/**
	 * Muestra en la ventana la información sobre el empleado que está logueado.
	 * 
	 * @param empleado objeto de tipo EmpleadoBean 
	 */
	public void mostrarEmpleadoLogueado(EmpleadoBean empleado);	
	
	/*
	 * Métodos que implementan el diagrama de Estados que habilita y deshabilita componentes
	 */
	
	/**
	 * Habilita componentes para permitir ingresar el tipo y número de DNI.
	 * Deshabilita todos los demás componentes.  
	 */
	public void estadoSeleccionCliente();
	
	/**
	 * Habilita componentes para permitir ingresar el monto.
	 * Deshabilita todos los demás componentes.  
	 */
	public void estadoIngresoMonto();
	
	/**
	 * Habilita componentes para permitir ingresar la cantidad de meses del prestamo.
	 * Deshabilita todos los demás componentes.  
	 */
	public void estadoIngresoCantidadMeses();
	
	/**
	 * Habilita componentes para solicitar el prestamo.
	 * Deshabilita todos los demás componentes.  
	 */
	public void estadoIngresarPrestamo();
	
	
	/**
	 * Limpia los campos del panel de nuevo prestamo
	 */
	public void limpiarInformacionClientePrestamoNuevo();
	public void limpiarInformacionPrestamoPrestamoNuevo();

	
	/**
	 * Muestra la información sobre el prestamo en la ventana de Nuevo Prestamo 
	 * 
	 * @param tasa
	 * @param interes
	 * @param cuota
	 */
	public void mostrarInformacionPrestamo(double tasa, double interes, double cuota);

	/**
	 * Recibe una lista de tipos de documento y los carga en el combo de tipo documento de prestamo nuevo y pago cuotas.
	 * 
	 * @param obtenerTiposDocumento
	 */
	public void poblarComboTipoDocumento(ArrayList<String> tiposDocumento);
	
	/**
	 * Muestra en la ventana de prestamo nuevo la información del cliente.
	 * 
	 * @param cliente
	 */
	public void mostrarInformacionClientePrestamoNuevo(ClienteBean cliente);
	
	/**
	 * Informa a la ventana que debe poblar el componente (combobox) con la información recuperada.
	 * 
	 * @param obtenerCantidadMeses
	 */
	public void poblarOpcionesCantidadMeses(ArrayList<Integer> cantidadMeses);
	
	
	/**
	 * Muestra en la ventana de pago de cuotas nuevo la información del cliente.
	 * 
	 * @param cliente
	 */
	public void mostrarInformacionClientePagoCuota(ClienteBean cliente);
	
	
	
	/**
	 * Habilita componentes para permitir ingresar el tipo y número de DNI.
	 * Deshabilita todos los demás componentes.  
	 */
	public void pagoCuotaEstadoSeleccionCliente();
	
	/**
	 * Habilita componentes para permitir seleccionar las cuotas a pagar.
	 * Deshabilita todos los demás componentes.  
	 */
	public void pagoCuotaEstadoSeleccionCuotas();
	
	/**
	 * Limpia los campos del panel de cliente en pago cuota
	 */	
	public void limpiarInformacionClientePagoCuota();
	
	/**
	 * Muestra la lista de pagos (cuotas) correspondientes a un prestamo vigente
	 * 
	 * @param pagos 
	 */
	public void mostrarPagos(ArrayList<PagoBean> pagos);
	
	/**
	 * Recupera del componente (tabla) que muestra las cuotas disponibles a pagar,
	 * las que el usuario seleccionó.
	 * 
	 * @return Lista de nro_pago
	 */
	public List<Integer> obtenerCuotasAPagar();
	
	/**
	 * Muestra la información del prestamo
	 * 
	 * @param prestamo
	 */
	public void mostrarInformacionPrestamoPagoCuota(PrestamoBean prestamo);
	
	public void limpiarInformacionPrestamoPagoCuota();

	
	public void mostrarClientesMorosos(ArrayList<ClienteMorosoBean> clientesMorosos);
	
}
