package banco.modelo.empleado.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.utils.Fechas;


public class DAOPagoImpl implements DAOPago {

	private static Logger logger = LoggerFactory.getLogger(DAOPagoImpl.class);
	
	private Connection conexion;
	
	public DAOPagoImpl(Connection c) {
		this.conexion = c;
	}

	@Override
	public ArrayList<PagoBean> recuperarPagos(int nroPrestamo) throws Exception {
		/** 
		 * TODO HECHO Recupera todos los pagos del prestamo (pagos e impagos) del prestamo nroPrestamo
		 * 	    Si ocurre algún error deberá propagar una excepción.
		 */
		logger.info("Inicia la recuperacion de los pagos del prestamo {}", nroPrestamo);
		
		String sql = "SELECT * FROM pago WHERE nro_prestamo = ?";
		
		logger.debug("SELECT * FROM pago WHERE nro_prestamo = {}", nroPrestamo);
		
		ArrayList<PagoBean> lista = new ArrayList<PagoBean>();
		PreparedStatement obtener = null;
		try {
			obtener = conexion.prepareStatement(sql);
			obtener.setInt(1, nroPrestamo);
			obtener.execute();
			ResultSet rs = obtener.getResultSet();
			
			while(rs.next()) {
				PagoBean fila = new PagoBeanImpl();
				fila.setNroPrestamo(rs.getInt("nro_prestamo"));
				fila.setNroPago(rs.getInt("nro_pago"));
				fila.setFechaVencimiento(rs.getDate("fecha_venc"));
				fila.setFechaPago(rs.getDate("fecha_pago"));
				lista.add(fila);
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D."+ ex.getMessage());
			
		}finally {
			obtener.close();
		}
		return lista;
	}

	@Override
	public void registrarPagos(int nroCliente, int nroPrestamo, List<Integer> cuotasAPagar)  throws Exception {


		/**
		 * TODO HECHO Registra los pagos de cuotas definidos en cuotasAPagar.
		 * 
		 * nroCliente asume que esta validado
		 * nroPrestamo asume que está validado
		 * cuotasAPagar Debe verificar que las cuotas a pagar no estén pagas (fecha_pago = NULL)
		 * @throws Exception Si hubo error en la conexión
		 */		
		//Haremos uso del procedure pagarCuota(IN prestamo INT, IN pago INT);
		
		logger.info("Inicia el pago de las {} cuotas del prestamo {}", cuotasAPagar.size(), nroPrestamo);
		
		String sql = "call pagarCuota(?,?)";
		PreparedStatement crear = null;
		try {
			for(int i = 0; i < cuotasAPagar.size() ; i++){
				logger.debug("call pagarCuota({},{})", nroPrestamo, cuotasAPagar.get(i));
				crear = conexion.prepareStatement(sql);
				crear.setDouble(1,nroPrestamo);
				crear.setInt(2,cuotasAPagar.get(i));
				crear.executeUpdate();
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}finally {
			crear.close();
		}
	}
	
}
