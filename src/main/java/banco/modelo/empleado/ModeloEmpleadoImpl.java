package banco.modelo.empleado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.modelo.ModeloImpl;
import banco.modelo.atm.TransaccionCajaAhorroBean;
import banco.modelo.atm.TransaccionCajaAhorroBeanImpl;
import banco.modelo.empleado.beans.ClienteBean;
import banco.modelo.empleado.beans.ClienteMorosoBean;
import banco.modelo.empleado.beans.DAOCliente;
import banco.modelo.empleado.beans.DAOClienteImpl;
import banco.modelo.empleado.beans.DAOClienteMoroso;
import banco.modelo.empleado.beans.DAOClienteMorosoImpl;
import banco.modelo.empleado.beans.DAOEmpleado;
import banco.modelo.empleado.beans.DAOEmpleadoImpl;
import banco.modelo.empleado.beans.DAOPago;
import banco.modelo.empleado.beans.DAOPagoImpl;
import banco.modelo.empleado.beans.DAOPrestamo;
import banco.modelo.empleado.beans.DAOPrestamoImpl;
import banco.modelo.empleado.beans.EmpleadoBean;
import banco.modelo.empleado.beans.PagoBean;
import banco.modelo.empleado.beans.PrestamoBean;
import banco.utils.Fechas;

public class ModeloEmpleadoImpl extends ModeloImpl implements ModeloEmpleado {

	private static Logger logger = LoggerFactory.getLogger(ModeloEmpleadoImpl.class);	

	
	private Integer legajo = null;
	
	public ModeloEmpleadoImpl() {
		logger.debug("Se crea el modelo Empleado.");
	}
	

	@Override
	public boolean autenticarUsuarioAplicacion(String legajo, String password) throws Exception {
		logger.info("Se intenta autenticar el legajo {} con password {}", legajo, password);
		/** 
		 * TODO HECHO Código que autentica que exista un legajo de empleado y que el password corresponda a ese legajo
		 *      (el password guardado en la BD está en MD5) 
		 *      En caso exitoso deberá registrar el legajo en la propiedad legajo y retornar true.
		 *      Si la autenticación no es exitosa porque el legajo no es válido o el password es incorrecto
		 *      deberá retornar falso y si hubo algún otro error deberá producir una excepción.
		 */
		boolean ret = false;
		logger.info("Se intenta autenticar el legajo {} con contraseña {}", legajo, password);
		
		String sql = "SELECT legajo, password FROM empleado WHERE legajo =? and password = md5(?)";
		
		logger.debug("SELECT legajo, password FROM empleado WHERE legajo = {} and password = md5({})", legajo, password);
		
		PreparedStatement autenticar = null;
		try {
			autenticar = conexion.prepareStatement(sql);
			autenticar.setString(1, legajo);
			autenticar.setString(2, password);
			autenticar.execute();
			ResultSet rs = autenticar.getResultSet();
			
			if(rs.next()) {
				this.legajo = Integer.parseInt(legajo);
				ret = true;
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
			
		}finally {
			autenticar.close();
		}
		return ret;
	}
	
	@Override
	public EmpleadoBean obtenerEmpleadoLogueado() throws Exception {
		logger.info("Solicita al DAO un empleado con legajo {}", this.legajo);
		if (this.legajo == null) {
			logger.info("No hay un empleado logueado.");
			throw new Exception("No hay un empleado logueado. La sesión terminó.");
		}
		
		DAOEmpleado dao = new DAOEmpleadoImpl(this.conexion);
		return dao.recuperarEmpleado(this.legajo);
	}	
	
	@Override
	public ArrayList<String> obtenerTiposDocumento() throws Exception{
		/** 
		 * TODO HECHO  Debe retornar una lista de strings con los tipos de documentos. 
		 *      Deberia propagar una excepción si hay algún error en la consulta.
		 */
		
		logger.info("recupera los tipos de documentos.");
		
		String sql = "SELECT DISTINCT tipo_doc FROM empleado";
		
		logger.debug("SELECT DISTINCT tipo_doc FROM empleado");
		ArrayList<String> tipos = new ArrayList<String>();
		PreparedStatement obtener = null;
		try {
			obtener = conexion.prepareStatement(sql);
			obtener.execute();
			ResultSet rs = obtener.getResultSet();

			while(rs.next()) {
				tipos.add(rs.getString("tipo_doc"));
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D."+ ex.getMessage());
		}finally {
			obtener.close();
		}
		
		return tipos;
	}	

	@Override
	public double obtenerTasa(double monto, int cantidadMeses) throws Exception {


		/** 
		 * TODO HECHO Debe buscar la tasa correspondiente según el monto y la cantidadMeses. 
		 *      Deberia propagar una excepción si hay algún error de conexión o 
		 *      no encuentra el monto dentro del [monto_inf,monto_sup] y la cantidadMeses.
		 */

		logger.info("Busca la tasa correspondiente al monto {} con una cantidad de meses {}", monto, cantidadMeses);
		
		String sql = "SELECT tasa FROM tasa_prestamo WHERE ? > monto_inf and ? < monto_sup and periodo = ?";
		
		logger.debug("SELECT tasa FROM tasa_prestamo WHERE {} > monto_inf and {} < monto_sup and periodo = {}", monto, monto, cantidadMeses);
		
		double tasa = 0;
		PreparedStatement autenticar = null;
		try {
			autenticar = conexion.prepareStatement(sql);
			autenticar.setDouble(1, monto);
			autenticar.setDouble(2, monto);
			autenticar.setInt(3, cantidadMeses);
			autenticar.execute();
			ResultSet rs = autenticar.getResultSet();
			
			if(rs.next()) {
				tasa = rs.getDouble("tasa");
			}else {
				throw new Exception("No se pudo obtener la tasa para ese monto y esa cantidad de meses");	
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
			
		}finally {
			autenticar.close();
		}
		return tasa;
		
		
	}

	@Override
	public double obtenerInteres(double monto, double tasa, int cantidadMeses) {
		return (monto * tasa * cantidadMeses) / 1200;
	}


	@Override
	public double obtenerValorCuota(double monto, double interes, int cantidadMeses) {
		return (monto + interes) / cantidadMeses;
	}
		

	@Override
	public ClienteBean recuperarCliente(String tipoDoc, int nroDoc) throws Exception {
		DAOCliente dao = new DAOClienteImpl(this.conexion);
		return dao.recuperarCliente(tipoDoc, nroDoc);
	}


	@Override
	public ArrayList<Integer> obtenerCantidadMeses(double monto) throws Exception {

		/** 
		 * TODO HECHO Debe buscar los períodos disponibles según el monto. 
		 *      Deberia propagar una excepción si hay algún error de conexión o 
		 *      no encuentra el monto dentro del [monto_inf,monto_sup].
		 */

		logger.info("recupera los períodos (cantidad de meses) según el monto {} para el prestamo.", monto);

		String sql = "SELECT periodo FROM tasa_prestamo WHERE ? >= monto_inf and ? <= monto_sup";
		
		logger.debug("SELECT periodo FROM tasa_prestamo WHERE {} > monto_inf and {} < monto_sup",monto);
		
		PreparedStatement cargar = null;
		ArrayList<Integer> cantMeses = new ArrayList<Integer>();
		try {
			cargar = conexion.prepareStatement(sql);
			cargar.setDouble(1, monto);
			cargar.setDouble(2, monto);
			cargar.execute();
			ResultSet rs = cargar.getResultSet();
			if(rs.next() == false)
				throw new Exception ("No hay ningun periodo disponible para ese monto");
			
			do {
				cantMeses.add(rs.getInt("Periodo"));
			} while(rs.next());
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D."+ ex.getMessage());
		} finally {
			cargar.close();
		}
		
		return cantMeses;
	}

	@Override	
	public Integer prestamoVigente(int nroCliente) throws Exception 
	{
		/** 
		 * TODO HECHO Busca algún prestamo del cliente que tenga cuotas sin pagar (vigente) retornando el nro_prestamo
		 *      si no existe prestamo del cliente o todos están pagos retorna null.
		 *      Si hay una excepción la propaga con un mensaje apropiado.
		 **/
		
		logger.info("Verifica si el cliente {} tiene algun prestamo que tienen cuotas por pagar.", nroCliente);
		Integer ret = null;
		
		String sql = "SELECT nro_prestamo FROM pago NATURAL JOIN prestamo WHERE fecha_pago IS NULL and nro_cliente = ?";
		
		logger.debug("SELECT nro_prestamo FROM pago NATURAL JOIN prestamo WHERE fecha_pago IS NULL and nro_cliente = {}", nroCliente);
		
		PreparedStatement autenticar = null;
		try {
			autenticar = conexion.prepareStatement(sql);
			autenticar.setInt(1, nroCliente);
			autenticar.execute();
			ResultSet rs = autenticar.getResultSet();
			
			if(rs.next()) {
				ret = rs.getInt("nro_prestamo");
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
			
		}finally {
			autenticar.close();
		}
		return ret;
	}


	@Override
	public void crearPrestamo(PrestamoBean prestamo) throws Exception {
		logger.info("Crea un nuevo prestamo.");
		
		if (this.legajo == null) {
			throw new Exception("No hay un empleado registrado en el sistema que se haga responsable por este prestamo.");
		}
		else 
		{
			logger.info("Actualiza el prestamo con el legajo {}",this.legajo);
			prestamo.setLegajo(this.legajo);
			
			DAOPrestamo dao = new DAOPrestamoImpl(this.conexion);		
			dao.crearActualizarPrestamo(prestamo);
		}
	}
	
	@Override
	public PrestamoBean recuperarPrestamo(int nroPrestamo) throws Exception {
		logger.info("Busca el prestamo número {}", nroPrestamo);
		
		DAOPrestamo dao = new DAOPrestamoImpl(this.conexion);		
		return dao.recuperarPrestamo(nroPrestamo);
	}
	
	@Override
	public ArrayList<PagoBean> recuperarPagos(Integer prestamo) throws Exception {
		logger.info("Solicita la busqueda de pagos al modelo sobre el prestamo {}.", prestamo);
		
		DAOPago dao = new DAOPagoImpl(this.conexion);		
		return dao.recuperarPagos(prestamo);
	}
	

	@Override
	public void pagarCuotas(String p_tipo, int p_dni, int nroPrestamo, List<Integer> cuotasAPagar) throws Exception {
		
		// Valida que sea un cliente que exista sino genera una excepción
		ClienteBean c = this.recuperarCliente(p_tipo.trim(), p_dni);

		// Valida el prestamo
		if (nroPrestamo != this.prestamoVigente(c.getNroCliente())) {
			throw new Exception ("El nro del prestamo no coincide con un prestamo vigente del cliente");
		}

		if (cuotasAPagar.size() == 0) {
			throw new Exception ("Debe seleccionar al menos una cuota a pagar.");
		}
		
		DAOPago dao = new DAOPagoImpl(this.conexion);
		dao.registrarPagos(c.getNroCliente(), nroPrestamo, cuotasAPagar);		
	}


	@Override
	public ArrayList<ClienteMorosoBean> recuperarClientesMorosos() throws Exception {
		logger.info("Modelo solicita al DAO que busque los clientes morosos");
		DAOClienteMoroso dao = new DAOClienteMorosoImpl(this.conexion);
		return dao.recuperarClientesMorosos();	
	}
	

	
}
