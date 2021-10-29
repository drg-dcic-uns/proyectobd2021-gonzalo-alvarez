package banco.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.utils.Conexion;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class ModeloImpl implements Modelo {
	
	private static Logger logger = LoggerFactory.getLogger(ModeloImpl.class);	

	protected Connection conexion = null;

	@Override
	public boolean conectar(String username, String password) {
        this.conexion = Conexion.getConnection(username, password);        
    	return (this.conexion != null);	
	}

	@Override
	public void desconectar() {
		logger.info("Se desconecta la conexión a la BD.");
		Conexion.closeConnection(this.conexion);		
	}

	@Override
	public ResultSet consulta(String sql)
	{
		logger.info("Se intenta realizar la siguiente consulta {}",sql);
		try
		{
			Statement stmt = conexion.createStatement();			
			ResultSet rs = stmt.executeQuery(sql);
			
			return rs;
		}
		catch (SQLException ex){
		   logger.error("SQLException: " + ex.getMessage());
		   logger.error("SQLState: " + ex.getSQLState());
		   logger.error("VendorError: " + ex.getErrorCode());
		}
		return null;
	}	
	
	@Override
	public void actualizacion (String sql)
	{
		try
		{
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(sql);
			
			stmt.close();
		}
		catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
	}	
}
