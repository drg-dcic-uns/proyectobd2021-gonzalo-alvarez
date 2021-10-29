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
		
		logger.info("Recupera el prestamo nro {}.", nroPrestamo);
		
		/**
		 * TODO Obtiene el prestamo según el id nroPrestamo
		 * 
		 * @param nroPrestamo
		 * @return Un prestamo que corresponde a ese id o null
		 * @throws Exception si hubo algun problema de conexión
		 */		

		/*
		 * Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.  
		 * Retorna un PretamoBean con información del prestamo nro 4
		 */
		PrestamoBean prestamo = null;
			
		prestamo = new PrestamoBeanImpl();
		prestamo.setNroPrestamo(4);
		prestamo.setFecha(Fechas.convertirStringADate("2021-04-05"));
		prestamo.setCantidadMeses(6);
		prestamo.setMonto(20000);
		prestamo.setTasaInteres(24);
		prestamo.setInteres(2400);
		prestamo.setValorCuota(3733.33);
		prestamo.setLegajo(2);
		prestamo.setNroCliente(2);
   	
		return prestamo;
		// Fin datos estáticos de prueba.
	}

}
