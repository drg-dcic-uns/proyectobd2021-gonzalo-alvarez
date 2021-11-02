package banco.modelo.atm;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.modelo.ModeloImpl;
import banco.utils.Fechas;


public class ModeloATMImpl extends ModeloImpl implements ModeloATM {
	
	private static Logger logger = LoggerFactory.getLogger(ModeloATMImpl.class);	

	private String tarjeta = null;   // mantiene la tarjeta del cliente actual
	private Integer codigoATM = null;
	/*
	 * La información del cajero ATM se recupera del archivo que se encuentra definido en ModeloATM.CONFIG
	 */
	public ModeloATMImpl() {
		logger.debug("Se crea el modelo ATM.");

		logger.debug("Recuperación de la información sobre el cajero");
		
		Properties prop = new Properties();
		try (FileInputStream file = new FileInputStream(ModeloATM.CONFIG))
		{
			logger.debug("Se intenta leer el archivo de propiedades {}",ModeloATM.CONFIG);
			prop.load(file);

			codigoATM = Integer.valueOf(prop.getProperty("atm.codigo.cajero"));

			logger.debug("Código cajero ATM: {}", codigoATM);
		}
		catch(Exception ex)
		{
        	logger.error("Se produjo un error al recuperar el archivo de propiedades {}.",ModeloATM.CONFIG); 
		}
		return;
	}

	@Override
	/** 
	 *      Código que autentica que exista una tarjeta con ese pin (el pin guardado en la BD está en MD5)
	 *      En caso exitoso deberá registrar la tarjeta en la propiedad tarjeta y retornar true.
	 *      Si la autenticación no es exitosa porque no coincide el pin o la tarjeta no existe deberá retornar falso
	 *      y si hubo algún otro error deberá producir una excepción.
	 */
	public boolean autenticarUsuarioAplicacion(String tarjeta, String pin) throws Exception{
		/*TODO HECHO autenticar*/
		boolean ret = false;
		logger.info("Se intenta autenticar la tarjeta {} con pin {}", tarjeta, pin);
		
		String sql = "SELECT nro_tarjeta, PIN FROM tarjeta WHERE nro_tarjeta =? and PIN = md5(?)";
		
		logger.debug("SELECT nro_tarjeta, PIN FROM tarjeta WHERE nro_tarjeta = {} and PIN = md5({})", tarjeta, pin);
		
		
		try {
			PreparedStatement autenticar = conexion.prepareStatement(sql);
			autenticar.setString(1, tarjeta);
			autenticar.setString(2, pin);
			autenticar.execute();
			ResultSet rs = autenticar.getResultSet();
			
			if(rs.next()) {
				this.tarjeta = tarjeta;
				ret = true;
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
			
		}
		return ret;
	}
	
	
	@Override
	public Double obtenerSaldo() throws Exception{
		/*TODO HECHO obtenerSaldo */
		logger.info("Se intenta obtener el saldo de cliente {}", 3);

		if (this.tarjeta == null ) {
			throw new Exception("El cliente no ingresó la tarjeta");
		}
		

		/** 
		 *  Obtiene el saldo.
		 *      Debe capturar la excepción SQLException y propagar una Exception más amigable.
		 */
		Double saldo_obtenido = null;
		
		String sql = "SELECT * FROM (Tarjeta JOIN trans_cajas_ahorro ON Tarjeta.nro_ca = trans_cajas_ahorro.nro_ca) WHERE nro_tarjeta = ? ORDER BY fecha DESC, hora DESC;";
		
		logger.debug("SELECT * FROM (Tarjeta JOIN trans_cajas_ahorro ON Tarjeta.nro_ca = trans_cajas_ahorro.nro_ca) WHERE nro_tarjeta = {} ORDER BY fecha DESC, hora DESC;",tarjeta);
		
		
		try {
			PreparedStatement obtener = conexion.prepareStatement(sql);
			obtener.setString(1, tarjeta);
			obtener.execute();
			ResultSet rs = obtener.getResultSet();
			
			if(rs.next()) {
				saldo_obtenido = rs.getDouble("saldo");
			} else {
				throw new Exception("No se pudo obtener el saldo");	
			}
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D.");
			
		}
		return saldo_obtenido;
	}	

	@Override
	public ArrayList<TransaccionCajaAhorroBean> cargarUltimosMovimientos() throws Exception {
		return this.cargarUltimosMovimientos(ModeloATM.ULTIMOS_MOVIMIENTOS_CANTIDAD);
	}	
	
	@Override
	public ArrayList<TransaccionCajaAhorroBean> cargarUltimosMovimientos(int cantidad) throws Exception
	{
		/*TODO HECHO cargarUltimosMovimientos*/
		logger.info("Busca las ultimas {} transacciones en la BD de la tarjeta {}",cantidad, Integer.valueOf(this.tarjeta.trim()));

		String sql = "SELECT fecha, hora, tipo, CONCAT('-', monto) AS monto,cod_caja, destino FROM (Tarjeta JOIN trans_cajas_ahorro ON Tarjeta.nro_ca = trans_cajas_ahorro.nro_ca) WHERE nro_tarjeta = ? LIMIT ?;";
		
		logger.debug("SELECT fecha, hora, tipo, CONCAT('-', monto) AS monto,cod_caja, destino FROM (Tarjeta JOIN trans_cajas_ahorro ON Tarjeta.nro_ca = trans_cajas_ahorro.nro_ca) WHERE nro_tarjeta = {} LIMIT {};",tarjeta,cantidad);
		

		ArrayList<TransaccionCajaAhorroBean> lista = new ArrayList<TransaccionCajaAhorroBean>();
		try {
			PreparedStatement cargar = conexion.prepareStatement(sql);
			cargar.setString(1, tarjeta);
			cargar.setInt(2, cantidad);
			cargar.execute();
			ResultSet rs = cargar.getResultSet();
			
			
			TransaccionCajaAhorroBean fila;
			while(rs.next()) {
				fila = new TransaccionCajaAhorroBeanImpl();
				fila.setTransaccionFechaHora(Fechas.convertirStringADate(rs.getString("Fecha"), rs.getString("Hora")));
				fila.setTransaccionTipo(rs.getString("tipo"));
				fila.setTransaccionMonto(rs.getDouble("monto"));
				
				String codCaja = rs.getString("cod_caja");
				Integer codigo = 0;
				if(!codCaja.equals("NULL"))
					codigo = Integer.parseInt(codCaja);
				fila.setTransaccionCodigoCaja(codigo);
				
				String numeroDestino = rs.getString("destino");
				Integer numeroD = 0;
				if(!numeroDestino.equals("NULL"))
					numeroD = Integer.parseInt(numeroDestino);
				fila.setCajaAhorroDestinoNumero(numeroD);	
				
				lista.add(fila);
			}
			/*
			} else {
				throw new Exception("No se pudo obtener el saldo");	
			}*/
			
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D."+ ex.getMessage());
			
		}
		return lista;
		
		
		/**
		 * TODO Deberá recuperar los ultimos movimientos del cliente, la cantidad está definida en el parámetro.
		 * 		Debe capturar la excepción SQLException y propagar una Exception más amigable. 
		 */
	}	
	
	@Override
	public ArrayList<TransaccionCajaAhorroBean> cargarMovimientosPorPeriodo(Date desde, Date hasta)
			throws Exception {

		/**
		 * TODO Deberá recuperar los ultimos movimientos del cliente que se han realizado entre las fechas indicadas.
		 * 		Debe capturar la excepción SQLException y propagar una Exception más amigable. 
		 * 		Debe generar excepción si las fechas son erroneas (ver descripción en interface)
		 */
				
		logger.info("Busca las ultimas transacciones en la BD de la tarjeta {} entre las fechas {} y {}",Integer.valueOf(this.tarjeta.trim()), desde, hasta);

		String sql = "SELECT fecha, hora, tipo, CONCAT('-', monto) AS monto,cod_caja, destino FROM (Tarjeta JOIN trans_cajas_ahorro ON Tarjeta.nro_ca = trans_cajas_ahorro.nro_ca) WHERE nro_tarjeta = ? and (fecha > ? and fecha < ?)";
		
		logger.debug("SELECT fecha, hora, tipo, CONCAT('-', monto) AS monto,cod_caja, destino FROM (Tarjeta JOIN trans_cajas_ahorro ON Tarjeta.nro_ca = trans_cajas_ahorro.nro_ca) WHERE nro_tarjeta = {} and (fecha > {} and fecha < {})",tarjeta,desde,hasta);
		

		ArrayList<TransaccionCajaAhorroBean> lista = new ArrayList<TransaccionCajaAhorroBean>();
		try {
			PreparedStatement cargar = conexion.prepareStatement(sql);
			cargar.setString(1, tarjeta);
			cargar.setDate(2, Fechas.convertirDateADateSQL(desde));
			cargar.setDate(3, Fechas.convertirDateADateSQL(hasta));
			cargar.execute();
			ResultSet rs = cargar.getResultSet();
			
			
			TransaccionCajaAhorroBean fila;
			while(rs.next()) {
				fila = new TransaccionCajaAhorroBeanImpl();
				fila.setTransaccionFechaHora(Fechas.convertirStringADate(rs.getString("Fecha"), rs.getString("Hora")));
				fila.setTransaccionTipo(rs.getString("tipo"));
				fila.setTransaccionMonto(rs.getDouble("monto"));
				
				String codCaja = rs.getString("cod_caja");
				Integer codigo = 0;
				if(!codCaja.equals("NULL"))
					codigo = Integer.parseInt(codCaja);
				fila.setTransaccionCodigoCaja(codigo);
				
				String numeroDestino = rs.getString("destino");
				Integer numeroD = 0;
				if(!numeroDestino.equals("NULL"))
					numeroD = Integer.parseInt(numeroDestino);
				fila.setCajaAhorroDestinoNumero(numeroD);	
				
				lista.add(fila);
			}
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			throw new Exception("Error inesperado al consultar la B.D."+ ex.getMessage());
		}
		return lista;
	}
	
	@Override
	public Double extraer(Double monto) throws Exception {
		logger.info("Realiza la extraccion de ${} sobre la cuenta", monto);
		
		/**
		 * TODO Deberá extraer de la cuenta del cliente el monto especificado (ya validado) y de obtener el saldo de la cuenta como resultado.
		 * 		Debe capturar la excepción SQLException y propagar una Exception más amigable. 
		 * 		Debe generar excepción si las propiedades codigoATM o tarjeta no tienen valores
		 */		
		
		String resultado = ModeloATM.EXTRACCION_EXITOSA;
		
		if (!resultado.equals(ModeloATM.EXTRACCION_EXITOSA)) {
			throw new Exception(resultado);
		}
		return this.obtenerSaldo();

	}

	
	@Override
	public int parseCuenta(String p_cuenta) throws Exception {
		
		logger.info("Intenta realizar el parsing de un codigo de cuenta {}", p_cuenta);

		/**
		 * TODO Verifica que el codigo de la cuenta sea valido. 
		 * 		Debe capturar la excepción SQLException y propagar una Exception más amigable. 
		 * 		Debe generar excepción si la cuenta es vacia, entero negativo o no puede generar el parsing.
		 * retorna la cuenta en formato int
		 */	
		
		logger.info("Encontró la cuenta en la BD.");
        return 1;
	}	
	
	@Override
	public Double transferir(Double monto, int cajaDestino) throws Exception {
		logger.info("Realiza la transferencia de ${} sobre a la cuenta {}", monto, cajaDestino);
		
		/**
		 * TODO Deberá extraer de la cuenta del cliente el monto especificado (ya validado) y de obtener el saldo de la cuenta como resultado.
		 * 		Debe capturar la excepción SQLException y propagar una Exception más amigable. 
		 * 		Debe generar excepción si las propiedades codigoATM o tarjeta no tienen valores
		 */		
		

		String resultado = ModeloATM.TRANSFERENCIA_EXITOSA;
		
		if (!resultado.equals(ModeloATM.TRANSFERENCIA_EXITOSA)) {
			throw new Exception(resultado);
		}
		return this.obtenerSaldo();
	}


	@Override
	public Double parseMonto(String p_monto) throws Exception {
		
		logger.info("Intenta realizar el parsing del monto {}", p_monto);
		
		if (p_monto == null) {
			throw new Exception("El monto no puede estar vacío");
		}

		try 
		{
			double monto = Double.parseDouble(p_monto);
			DecimalFormat df = new DecimalFormat("#.00");

			monto = Double.parseDouble(corregirComa(df.format(monto)));
			
			if(monto < 0)
			{
				throw new Exception("El monto no debe ser negativo.");
			}
			
			return monto;
		}		
		catch (NumberFormatException e)
		{
			throw new Exception("El monto no tiene un formato válido.");
		}	
	}

	private String corregirComa(String n)
	{
		String toReturn = "";
		
		for(int i = 0;i<n.length();i++)
		{
			if(n.charAt(i)==',')
			{
				toReturn = toReturn + ".";
			}
			else
			{
				toReturn = toReturn+n.charAt(i);
			}
		}
		
		return toReturn;
	}	
	
	

	
}
