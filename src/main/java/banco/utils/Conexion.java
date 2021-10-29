package banco.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class Conexion {

	private static Logger logger = LoggerFactory.getLogger(Conexion.class);	
	
    private static String url;    
    private static String driverName;   
    private static Connection con;
    private static String urlstring;

	public static Connection getConnection(String usuario, String password) {
		try { 	
				Class.forName(Conexion.getDriverName());
	            try {
	            	
	            	logger.debug("Parametros de conexion: url= {}, user={}, pass={}", Conexion.getUrlstring(), usuario, password);
	            	
	                con = DriverManager.getConnection(Conexion.getUrlstring(), 
	                								  usuario, 
	                								  password);
	                
	            	logger.info("Se establece la conexión con la BD");	            	
	                
	            } catch (SQLException ex) {	            	
	            	logger.error("Error al crear la conexión con la base de datos."); 
	            	logger.debug("SQLException: {}",ex.getMessage());
	            	logger.debug("SQLState: {}", ex.getSQLState());
	            	logger.debug("VendorError: {}", ex.getErrorCode());
	            }
		} catch (ClassNotFoundException ex) {
			logger.error("Driver not found."); 
		}
	    return con;
    }	
    
    /**
     * Inicializa los parámetros de conexión con los valores definidos en el archivo de propiedades pasado como parámetro
     * 
     * @param property Archivo de propiedades con la ruta
     */
	public static void inicializar(String propertyFile)	
	{
		logger.debug("Recuperación de los datos para la conexión con la BD");
		
		Properties prop = new Properties();
		try
		{
			logger.debug("Se intenta leer el archivo de propiedades {}", propertyFile);
			
			FileInputStream file=new FileInputStream(propertyFile);			
			prop.load(file);
			
			logger.debug("se cargó exitosamente");

			Conexion.setDriverName(prop.getProperty("driverName"));
			Conexion.setUrl(prop.getProperty("libreria", "jdbc") + ":" +
					  		prop.getProperty("motor", "mysql") + "://" +
					  		prop.getProperty("servidor", "localhost") + ":" +
					  		prop.getProperty("puerto"));
			
			Conexion.setUrlstring(Conexion.getUrl() + "/" + prop.getProperty("base_de_datos") + prop.getProperty("parametro_aux1"));

			logger.debug("Parámetros de conexión: {}", Conexion.getUrl() + "/" + prop.getProperty("base_de_datos") + prop.getProperty("parametro_aux1"));
		}
		catch(Exception ex)
		{
        	logger.error("Se produjo un error al recuperar el archivo de propiedades de la BD."); 
		}
		return;
	}

    public static void closeConnection(Connection conn) {
        try {
        	logger.info("Se intenta cerrar la conexión activa");
            if (null != conn) {
                conn.close();
                conn = null;
            }
        } catch (SQLException ex) {
        	logger.error("Error al cerrar la conexión con la base de datos."); 
        	logger.debug("SQLException: {}",ex.getMessage());
        	logger.debug("SQLState: {}", ex.getSQLState());
        	logger.debug("VendorError: {}", ex.getErrorCode());
        }
    }

    public static void closeResultset(ResultSet rs) {
        try {
        	logger.info("Se intenta cerrar el resultSet");
            if (null != rs) {
                rs.close();
                rs = null;
            }
        } catch (SQLException ex) {
        	logger.error("Error al cerrar el resultSet."); 
        	logger.debug("SQLException: {}",ex.getMessage());
        	logger.debug("SQLState: {}", ex.getSQLState());
        	logger.debug("VendorError: {}", ex.getErrorCode());
        }
    }

    public static void closePreparedStatement(PreparedStatement pstmt) {
        try {
        	logger.info("Se intenta cerrar la consulta preparada.");
            if (null != pstmt) {
                pstmt.close();
                pstmt = null;
            }
        } catch (SQLException ex) {
        	logger.error("Error al cerrar la consulta preparada."); 
        	logger.debug("SQLException: {}",ex.getMessage());
        	logger.debug("SQLState: {}", ex.getSQLState());
        	logger.debug("VendorError: {}", ex.getErrorCode());
        }
    }

    public static void closeStatement(Statement stmt) {
        try {
        	logger.info("Se intenta cerrar la sentencia.");
            if (null != stmt) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException ex) {
        	logger.error("Error al cerrar la sentencia."); 
        	logger.debug("SQLException: {}",ex.getMessage());
        	logger.debug("SQLState: {}", ex.getSQLState());
        	logger.debug("VendorError: {}", ex.getErrorCode());
        }
    }	
	/*
	 *  Setters y Getters
	 */	
	
	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		Conexion.url = url;
	}

	public static String getDriverName() {
		return driverName;
	}

	public static void setDriverName(String driverName) {
		Conexion.driverName = driverName;
	}

	public static String getUrlstring() {
		return urlstring;
	}

	public static void setUrlstring(String urlstring) {
		Conexion.urlstring = urlstring;
	}

}
