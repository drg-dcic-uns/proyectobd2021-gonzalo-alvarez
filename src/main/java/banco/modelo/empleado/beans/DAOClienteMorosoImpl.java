package banco.modelo.empleado.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOClienteMorosoImpl implements DAOClienteMoroso {

	private static Logger logger = LoggerFactory.getLogger(DAOClienteMorosoImpl.class);
	
	private Connection conexion;
	
	public DAOClienteMorosoImpl(Connection c) {
		this.conexion = c;
	}
	
	@Override
	public ArrayList<ClienteMorosoBean> recuperarClientesMorosos() throws Exception {
		/**
		 * TODO HECHO Deberá recuperar un listado de clientes morosos los cuales consisten de un bean ClienteMorosoBeanImpl
		 *      deberá indicar para dicho cliente cual es el prestamo sobre el que está moroso y la cantidad de cuotas que 
		 *      tiene atrasadas. En todos los casos deberá generar excepciones que será capturadas por el controlador
		 *      si hay algún error que necesita ser informado al usuario. 
		 */
		
		logger.info("Busca los clientes morosos.");
		
		String sql = "SELECT COUNT(*) AS cuotas_atrasadas, nro_prestamo"
				+ " FROM pago WHERE (fecha_venc < CURDATE() and fecha_pago IS NULL)"
				+ " GROUP BY nro_prestamo HAVING cuotas_atrasadas >= 2";
		
		logger.debug("SELECT COUNT(*) AS cuotas_atrasadas, nro_prestamo"
				+ " FROM pago WHERE (fecha_venc < CURDATE() and fecha_pago IS NULL)"
				+ " GROUP BY nro_prestamo HAVING cuotas_atrasadas >= 2");
		
		DAOPrestamo daoPrestamo = new DAOPrestamoImpl(this.conexion);		
		DAOCliente daoCliente = new DAOClienteImpl(this.conexion);
		PrestamoBean prestamo = null;
		ClienteBean cliente = null;
		
		ArrayList<ClienteMorosoBean> morosos = new ArrayList<ClienteMorosoBean>();
		
		try {
			PreparedStatement recuperar = conexion.prepareStatement(sql);
			recuperar.execute();
			ResultSet rs = recuperar.getResultSet();
			
			while(rs.next()) {
				ClienteMorosoBean fila = new ClienteMorosoBeanImpl();
				prestamo = daoPrestamo.recuperarPrestamo(rs.getInt("nro_prestamo"));
				cliente = daoCliente.recuperarCliente(prestamo.getNroCliente());
				fila.setCliente(cliente);
				fila.setPrestamo(prestamo);
				fila.setCantidadCuotasAtrasadas(rs.getInt("cuotas_atrasadas"));
				morosos.add(fila);
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D."+ ex.getMessage());
			
		}
				
		return morosos;
	}

}

