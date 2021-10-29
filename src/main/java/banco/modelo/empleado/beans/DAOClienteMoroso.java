package banco.modelo.empleado.beans;

import java.util.ArrayList;

public interface DAOClienteMoroso {

	/**
	 * Recupera los clientes morosos de acuerdo a la fecha actual.
	 * 
	 * @return 
	 * @throws Exception
	 */
	public ArrayList<ClienteMorosoBean> recuperarClientesMorosos() throws Exception;
	
}
