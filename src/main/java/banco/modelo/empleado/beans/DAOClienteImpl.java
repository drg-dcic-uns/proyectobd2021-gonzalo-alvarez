package banco.modelo.empleado.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.utils.Fechas;

public class DAOClienteImpl implements DAOCliente {

	private static Logger logger = LoggerFactory.getLogger(DAOClienteImpl.class);
	
	private Connection conexion;
	
	public DAOClienteImpl(Connection c) {
		this.conexion = c;
	}
	
	@Override
	public ClienteBean recuperarCliente(String tipoDoc, int nroDoc) throws Exception {
		
		/**
		 * TODO HECHO Recuperar el cliente que tenga un documento que se corresponda con los parámetros recibidos.  
		 *		Deberá generar o propagar una excepción si no existe dicho cliente o hay un error de conexión.		
		 */
		
		/*
		 * Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.  
		 */


		logger.info("recupera el cliente con documento de tipo {} y nro {}.", tipoDoc, nroDoc);
		
		String sql = "SELECT * FROM cliente WHERE tipo_doc = ? and nro_doc = ?";
		
		logger.debug("SELECT * FROM cliente WHERE tipo_doc = {} and nro_doc = {}", tipoDoc, nroDoc);

		ClienteBean cliente = null;
		
		try {
			PreparedStatement autenticar = conexion.prepareStatement(sql);
			autenticar.setString(1, tipoDoc);
			autenticar.setInt(2, nroDoc);
			autenticar.execute();
			ResultSet rs = autenticar.getResultSet();
			
			if(rs.next()) {
				cliente = new ClienteBeanImpl();
				cliente.setNroCliente(rs.getInt("nro_cliente"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellido(rs.getString("apellido"));
				cliente.setTipoDocumento(rs.getString("tipo_doc"));
				cliente.setNroDocumento(rs.getInt("nro_doc"));
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setTelefono(rs.getString("telefono"));
				cliente.setFechaNacimiento(rs.getDate("fecha_nac"));
			}else {
				throw new Exception ("No se encontró el cliente con " + tipoDoc + ":" + nroDoc);
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
		}
		
		return cliente;		

	}

	@Override
	public ClienteBean recuperarCliente(Integer nroCliente) throws Exception {
		logger.info("recupera el cliente por nro de cliente.");
		
		/**
		 * TODO Recuperar el cliente que tenga un número de cliente de acuerdo al parámetro recibido.  
		 *		Deberá generar o propagar una excepción si no existe dicho cliente o hay un error de conexión.		
		 */
		
		/*
		 * Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.  
		 */
		
		ClienteBean cliente = new ClienteBeanImpl();
		cliente.setNroCliente(3);
		cliente.setApellido("Apellido3");
		cliente.setNombre("Nombre3");
		cliente.setTipoDocumento("DNI");
		cliente.setNroDocumento(3);
		cliente.setDireccion("Direccion3");
		cliente.setTelefono("0291-3333333");
		cliente.setFechaNacimiento(Fechas.convertirStringADate("1983-03-03","13:30:00"));
		
		return cliente;		
		// Fin datos estáticos de prueba.

	}

}
