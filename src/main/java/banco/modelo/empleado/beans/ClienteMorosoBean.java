package banco.modelo.empleado.beans;

public interface ClienteMorosoBean {

	public ClienteBean getCliente();
	public void setCliente(ClienteBean cliente);
	
	public PrestamoBean getPrestamo();
	public void setPrestamo(PrestamoBean prestamo);
	
	public int getCantidadCuotasAtrasadas();
	public void setCantidadCuotasAtrasadas(int cantidadCuotasAtrasadas);
}
