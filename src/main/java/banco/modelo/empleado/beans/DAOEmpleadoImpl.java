package banco.modelo.empleado.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOEmpleadoImpl implements DAOEmpleado {

	private static Logger logger = LoggerFactory.getLogger(DAOEmpleadoImpl.class);
	
	private Connection conexion;
	
	public DAOEmpleadoImpl(Connection c) {
		this.conexion = c;
	}


	@Override
	public EmpleadoBean recuperarEmpleado(int legajo) throws Exception {
		/**
		 * TODO HECHO Debe recuperar los datos del empleado que corresponda al legajo pasado como parámetro.
		 *      Si no existe deberá retornar null y 
		 *      De ocurre algun error deberá generar una excepción.		 * 
		 */		

		logger.info("recupera el empleado que corresponde al legajo {}.", legajo);
		
		String sql = "SELECT * FROM empleado WHERE legajo = ?";
		
		logger.debug("SELECT * FROM empleado WHERE legajo = {}", legajo);

		EmpleadoBean empleado = null;
		PreparedStatement recuperar = null;
		try {
			recuperar = conexion.prepareStatement(sql);
			recuperar.setInt(1, legajo);
			recuperar.execute();
			ResultSet rs = recuperar.getResultSet();
			
			if(rs.next()) {
				empleado = new EmpleadoBeanImpl();
				empleado.setLegajo(rs.getInt("legajo"));
				empleado.setApellido(rs.getString("apellido"));
				empleado.setNombre(rs.getString("nombre"));
				empleado.setTipoDocumento(rs.getString("tipo_doc"));
				empleado.setNroDocumento(rs.getInt("nro_doc"));
				empleado.setDireccion(rs.getString("direccion"));
				empleado.setTelefono(rs.getString("telefono"));
				empleado.setCargo(rs.getString("cargo"));
				empleado.setPassword(rs.getString("password")); // select md5(9);
				empleado.setNroSucursal(rs.getInt("nro_suc"));
			}else {
				throw new Exception ("No se encontró el empleado con numero de legajo" + legajo);
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}finally {
			recuperar.close();
		}
		
		return empleado;
	}

}
