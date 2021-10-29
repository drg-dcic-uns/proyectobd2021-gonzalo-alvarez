package banco.modelo.empleado.beans;

import java.util.ArrayList;
import java.util.List;

public interface DAOPago {

	/**
	 * Recupera todos los pagos del prestamo (pagos e impagos)
	 * 
	 * @param nroPrestamo
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PagoBean> recuperarPagos(int nroPrestamo) throws Exception;

	/**
	 * Registra los pagos de cuotas definidos en cuotasAPagar. 
	 * 
	 * Realiza los pagos de forma transaccional, si ocurre un error produce un rollback.
	 * 
	 * @param nroCliente asume que esta validado
	 * @param nroPrestamo asume que está validado
	 * @param cuotasAPagar Debe verificar que la cuota no esté paga.
	 * @throws Exception Si hubo error en la conexión
	 */
	void registrarPagos(int nroCliente, int nroPrestamo, List<Integer> cuotasAPagar) throws Exception;
		
	
}
