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
		
		logger.info("Busca los clientes morosos.");
		
		DAOPrestamo daoPrestamo = new DAOPrestamoImpl(this.conexion);		
		DAOCliente daoCliente = new DAOClienteImpl(this.conexion);
		
		/**
		 * TODO Deberá recuperar un listado de clientes morosos los cuales consisten de un bean ClienteMorosoBeanImpl
		 *      deberá indicar para dicho cliente cual es el prestamo sobre el que está moroso y la cantidad de cuotas que 
		 *      tiene atrasadas. En todos los casos deberá generar excepciones que será capturadas por el controlador
		 *      si hay algún error que necesita ser informado al usuario. 
		 */
		
		/*
		 * Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.  
		 */
		ArrayList<ClienteMorosoBean> morosos = new ArrayList<ClienteMorosoBean>();
		PrestamoBean prestamo = null;
		ClienteBean cliente = null;
		
		ClienteMorosoBean moroso1 = new ClienteMorosoBeanImpl();
		prestamo = daoPrestamo.recuperarPrestamo(1); // El prestamo 1 tiene cuotas atrasadas - valor que deberá ser obtenido por la SQL
		cliente = daoCliente.recuperarCliente(prestamo.getNroCliente());
		moroso1.setCliente(cliente);
		moroso1.setPrestamo(prestamo);
		moroso1.setCantidadCuotasAtrasadas(2);  //valor que deberá ser obtenido por la SQL
		morosos.add(moroso1);

		ClienteMorosoBean moroso2 = new ClienteMorosoBeanImpl();
		prestamo = daoPrestamo.recuperarPrestamo(3); // El prestamo 3 tiene cuotas atrasadas - valor que deberá ser obtenido por la SQL
		cliente = daoCliente.recuperarCliente(prestamo.getNroCliente());
		moroso2.setCliente(cliente);
		moroso2.setPrestamo(prestamo);
		moroso2.setCantidadCuotasAtrasadas(6);  //valor que deberá ser obtenido por la SQL
		morosos.add(moroso2);
		
		return morosos;		
		// Fin datos estáticos de prueba.
		
	}

}

