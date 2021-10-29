package banco.modelo.empleado;

import java.util.ArrayList;
import java.util.List;

import banco.modelo.Modelo;
import banco.modelo.empleado.beans.ClienteBean;
import banco.modelo.empleado.beans.ClienteMorosoBean;
import banco.modelo.empleado.beans.EmpleadoBean;
import banco.modelo.empleado.beans.PagoBean;
import banco.modelo.empleado.beans.PrestamoBean;

public interface ModeloEmpleado extends Modelo {
	
	/**
	 * Verifica que el usuario con el que intenta realizar el login corresponde a un empleado del banco.
	 * Antes de poder autenticar deberá estar conectado a la BD con un usuario de BD. ver conectar/2
	 * Registra en una propiedad interna el legajo del empleado si es autenticado.
	 *  
	 * @param legajo
	 * @param password
	 * @return verdadero si el usuario tiene acceso o falso en caso contrario. 
	 * @throws Exception Produce una excepción si hay algún problema con los parámetros o de conexión.
	 */
	public boolean autenticarUsuarioAplicacion(String legajo, String password) throws Exception;
	
	/**
	 * Recupera el empleado que se ha logueado para realizar las operaciones.
	 * 
	 * @return EmpleadoBean o null en caso que no haya ningún empleado logueado.
	 * @throws Exception si ocurre algun problema de conexión.
	 */
	public EmpleadoBean obtenerEmpleadoLogueado() throws Exception;

	/**
	 * Recupera de la base de datos los tipos de documento en formato String
	 * 
	 * @return Lista de tipos de documento
	 */
	public ArrayList<String> obtenerTiposDocumento() throws Exception;	
	
	/**
	 * Busca la tasa correspondiente al monto con una cantidad de meses
	 * 
	 * @param monto
	 * @param cantidadMeses
	 * @return tasa
	 * @throws Exception Si hay un error de conexión o no encuentra la tasa en función del monto y cantidad de meses
	 */
	public double obtenerTasa(double monto, int cantidadMeses) throws Exception;

	/**
	 * Calcula el interes en función de los parametros dados
	 * 
	 * @param monto
	 * @param tasa
	 * @param cantidadMeses
	 * 
	 * @return interes
	 */
	public double obtenerInteres(double monto, double tasa, int cantidadMeses);

	/**
	 * Calcula el valor de la cuota a función de los datos dados
	 * @param monto
	 * @param interes
	 * @param cantidadMeses
	 * @return cuota
	 */
	public double obtenerValorCuota(double monto, double interes, int cantidadMeses);


	
	/**
	 * Recupera el cliente.  
	 *  
	 * @param tipoDoc
	 * @param nroDoc
	 * @return
	 * @throws Exception Si no existe o hay un error de conexión.
	 */
	public ClienteBean recuperarCliente(String tipoDoc, int nroDoc) throws Exception;

	/**
	 * Recupera los períodos (cantidad de meses) según el monto para el prestamo.
	 * 
	 * @param monto
	 * @return Una lista de cantidad de meses posibles
	 * @throws Exception si el monto no se encuentra en ningún rango.
	 */
	public ArrayList<Integer> obtenerCantidadMeses(double monto) throws Exception;
	
	/**
	 * Si el cliente tiene un prestamo vigente (cuotas impagas) retorna el nro_prestamo 
	 * sino tiene prestamo vigente retorna null.
	 * 
	 * @param nroCliente
	 * @return nro_prestamo vigente o null
	 * @throws Exception si hay algun problema con la conexión
	 */
	public Integer prestamoVigente(int nroCliente) throws Exception;

	/**
	 * Crea un nuevo prestamo
	 * 
	 * @param prestamo
	 * @throws Exception
	 */
	public void crearPrestamo(PrestamoBean prestamo) throws Exception;

	/**
	 * Recupera los pagos que tiene dicho prestamo
	 * 
	 * @param prestamo
	 * @throws Exception
	 */	
	public ArrayList<PagoBean> recuperarPagos(Integer prestamo) throws Exception;
	
	/**
	 * Obtiene el prestamo según el id nroPrestamo
	 * 
	 * @param nroPrestamo
	 * @return Un prestamo que corresponde a ese id o null
	 * @throws Exception si hubo algun problema de conexión
	 */
	public PrestamoBean recuperarPrestamo(int nroPrestamo) throws Exception;

	/**
	 * Realiza el pago de las cuotas para el cliente y prestamo dados.
	 * 
	 * @param tipo de dni que identifica al cliente
	 * @param dni que identifica al cliente
	 * @param nroPrestamo
	 * @param cuotasAPagar
	 * @throws Exception si hubo algun problema de conexión o algún problema al pagar las cuotas (quizas alguna ya estaba paga o no exista)
	 */
	public void pagarCuotas(String tipo, int dni, int nroPrestamo, List<Integer> cuotasAPagar) throws Exception;

	/**
	 * Recupera una lista de clientes morosos
	 * 
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<ClienteMorosoBean> recuperarClientesMorosos() throws Exception;
	
}
