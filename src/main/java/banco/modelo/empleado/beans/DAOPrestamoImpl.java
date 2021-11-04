package banco.modelo.empleado.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.utils.Fechas;

public class DAOPrestamoImpl implements DAOPrestamo {

	private static Logger logger = LoggerFactory.getLogger(DAOPrestamoImpl.class);
	
	private Connection conexion;
	
	public DAOPrestamoImpl(Connection c) {
		this.conexion = c;
	}
	
	
	@Override
	public void crearActualizarPrestamo(PrestamoBean prestamo) throws Exception {

		logger.info("Creación o actualizacion del prestamo.");
		logger.debug("meses : {}", prestamo.getCantidadMeses());
		logger.debug("monto : {}", prestamo.getMonto());
		logger.debug("tasa : {}", prestamo.getTasaInteres());
		logger.debug("interes : {}", prestamo.getInteres());
		logger.debug("cuota : {}", prestamo.getValorCuota());
		logger.debug("legajo : {}", prestamo.getLegajo());
		logger.debug("cliente : {}", prestamo.getNroCliente());
		
		/**
		 * TODO Crear o actualizar el Prestamo segun el PrestamoBean prestamo. 
		 *      Si prestamo tiene nroPrestamo es una actualizacion, si el nroPrestamo es null entonces es un nuevo prestamo.
		 * 
		 * @throws Exception deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores, ej.
		 *				logger.error("SQLException: " + ex.getMessage());
		 * 				logger.error("SQLState: " + ex.getSQLState());
		 *				logger.error("VendorError: " + ex.getErrorCode());
		 *		   pero luego deberá propagarla para que se encargue el controlador. 
		 */

	}

	@Override
	public PrestamoBean recuperarPrestamo(int nroPrestamo) throws Exception {
		/**
		 * TODO HECHO (TESTEAR) Obtiene el prestamo según el id nroPrestamo
		 * 
		 * @param nroPrestamo
		 * @return Un prestamo que corresponde a ese id o null
		 * @throws Exception si hubo algun problema de conexión
		 */	
		
		logger.info("Recupera el prestamo nro {}.", nroPrestamo);
		
		String sql = "SELECT * FROM prestamo WHERE nro_prestamo = ?";
		
		logger.debug("SELECT * FROM prestamo WHERE nro_prestamo = {}", nroPrestamo);

		PrestamoBean prestamo = null;
		
		try {
			PreparedStatement recuperar = conexion.prepareStatement(sql);
			recuperar.setInt(1, nroPrestamo);
			recuperar.execute();
			ResultSet rs = recuperar.getResultSet();
			
			if(rs.next()) {
				prestamo = new PrestamoBeanImpl();
				prestamo.setNroPrestamo(rs.getInt("nro_prestamo"));
		        prestamo.setFecha(rs.getDate("fecha"));
		        prestamo.setCantidadMeses(rs.getInt("cant_meses"));
		        prestamo.setMonto(rs.getDouble("monto"));
		        prestamo.setTasaInteres(rs.getDouble("tasa_interes"));
		        prestamo.setInteres(rs.getDouble("interes"));
		        prestamo.setValorCuota(rs.getDouble("valor_cuota"));
		        prestamo.setLegajo(rs.getInt("legajo"));
		        prestamo.setNroCliente(rs.getInt("nro_cliente"));
			}else {
				throw new Exception ("No se encontró el prestamo con número" + nroPrestamo);
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}
		
		return prestamo;
	}

}
