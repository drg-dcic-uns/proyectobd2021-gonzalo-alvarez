package banco.controlador;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.modelo.ModeloLogin;
import banco.modelo.ModeloLoginImpl;
import banco.modelo.empleado.ModeloEmpleado;
import banco.modelo.empleado.beans.ClienteBean;
import banco.modelo.empleado.beans.PagoBean;
import banco.modelo.empleado.beans.PrestamoBean;
import banco.modelo.empleado.beans.PrestamoBeanImpl;
import banco.utils.Parsing;
import banco.vista.empleado.VentanaEmpleado;
import banco.vista.login.VentanaLogin;
import banco.vista.login.VentanaLoginImpl;

public class ControladorEmpleadoImpl implements ControladorEmpleado {

	private static Logger logger = LoggerFactory.getLogger(ControladorEmpleadoImpl.class);
	
	private VentanaEmpleado ventana;
	private ModeloEmpleado modelo;
	
	public ControladorEmpleadoImpl(VentanaEmpleado ventana, ModeloEmpleado modelo) {
		logger.debug("Se crea el controlador del Empleado.");		
		this.ventana = ventana;
		this.modelo = modelo;
		this.ventana.registrarControlador(this);		
	}
	
	@Override
	public void ejecutar() {
		try {
			this.ventana.mostrarEmpleadoLogueado(this.modelo.obtenerEmpleadoLogueado());
			this.ventana.poblarComboTipoDocumento(this.modelo.obtenerTiposDocumento());	
			logger.debug("muestra la ventana");
			this.ventana.mostrarVentana();
		} catch (Exception e) {
			String s = "Se produjo un error al intentar crear la ventana";
			logger.error(e.getMessage());
			this.ventana.informar(e.getMessage());
		}
	}

	@Override
	public void salirAplicacion() {
		logger.info("Saliendo de la aplicación.");
		this.modelo.desconectar();
		this.ventana.eliminarVentana();
		System.exit(0);
	}

	@Override
	public void cerrarSesion() {
		logger.info("Cerrando la sesión.");
		this.modelo.desconectar();
		this.ventana.eliminarVentana();

		logger.info("Creando la ventana de login.");
		ModeloLogin modeloLogin = new ModeloLoginImpl();  
		VentanaLogin ventanaLogin = new VentanaLoginImpl();
		@SuppressWarnings("unused")
		ControladorLogin controlador = new ControladorLoginImpl(ventanaLogin, modeloLogin);
	}

	@Override
	public void mostrarPrestamoNuevo() {
		logger.info("Muestra el panel de nuevo prestamo");
		this.ventana.mostrarPanel(VentanaEmpleado.PRESTAMO_NUEVO);
		this.ventana.estadoSeleccionCliente();
	}

	@Override
	public void mostrarPrestamoGestionCuotas() {
		logger.info("Muestra el panel de gestión de cuotas");
		this.ventana.mostrarPanel(VentanaEmpleado.PRESTAMO_CUOTAS);
		this.ventana.pagoCuotaEstadoSeleccionCliente();
	}

	@Override
	public void mostrarPrestamoClientesMorosos() {
		logger.info("Muestra el panel de lista de clientes morosos");
		this.ventana.mostrarPanel(VentanaEmpleado.PRESTAMO_MOROSOS);
		try {
			this.ventana.mostrarClientesMorosos(this.modelo.recuperarClientesMorosos());
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar("Hubo un problema para recuperar los clientes morosos.");
		}
	}

	@Override
	public void prestamoNuevoSeleccionaCliente(String p_tipo, String p_dni) {
		logger.info("Solicita seleccionar un cliente con documento {} {} para realizar el préstamo.",p_tipo ,p_dni);
		try {
			
			ClienteBean c = this.modelo.recuperarCliente(p_tipo.trim(), Integer.parseInt(p_dni));
			
			this.ventana.mostrarInformacionClientePrestamoNuevo(c);
				
			Integer prestamo = this.modelo.prestamoVigente(c.getNroCliente());
			
			if (prestamo != null) {

				logger.info("El cliente ya tiene un prestamo vigente. Prestamo {}", String.valueOf(prestamo));
				this.ventana.informar("El cliente ya tiene un prestamo vigente. Prestamo Nro:" + String.valueOf(prestamo));
					
			} else {
				
				logger.info("Cambia de estado para permitir ingresar un monto");
				this.ventana.estadoIngresoMonto();
					
			}				

		} catch (NumberFormatException e) {
			logger.warn("Se produjo una excepción al realizar el parsing");
			this.ventana.informar(e.getMessage());
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar(e.getMessage());
		}			
	}

	@Override
	public void prestamoNuevoIngresaMonto(String p_monto) {
		logger.info("Solicita información sobre un monto.");

		try {
			logger.info("Se solicita parsing del monto");			
			double monto = Parsing.parseMonto(p_monto);
			
			this.ventana.poblarOpcionesCantidadMeses(this.modelo.obtenerCantidadMeses(monto));
			
			logger.info("Cambia de estado para permitir ingresar el período");
			this.ventana.estadoIngresoCantidadMeses();
						
		} catch (NumberFormatException e) {
			logger.warn("Se produjo una excepción al realizar el parsing");
			this.ventana.informar(e.getMessage());
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar(e.getMessage());
		}			
	}

	@Override
	public void prestamoNuevoCambiarCliente() {
		logger.info("Solicita cambiar el cliente.");
		this.ventana.limpiarInformacionClientePrestamoNuevo();
		this.ventana.limpiarInformacionPrestamoPrestamoNuevo();

		logger.info("Cambia de estado para permitir ingresar un cliente");
		this.ventana.estadoSeleccionCliente();
	}

	@Override
	public void prestamoNuevoCambiarMonto() {
		logger.info("Solicita cambiar el monto.");
		this.ventana.limpiarInformacionPrestamoPrestamoNuevo();

		logger.info("Cambia de estado para permitir ingresar un monto");
		this.ventana.estadoIngresoMonto();		
	}

	@Override
	public void prestamoNuevoObtenerTasaPrestamo(String p_monto, String p_cantidadMeses) {
		logger.info("Solicita obtener información sobre el prestamo.");

		try {
			logger.info("Se solicita al modelo validar y parse del monto");
			double monto = Parsing.parseMonto(p_monto);	

			logger.info("Se solicita al modelo validar y parse de la cantidad de meses");
			int cantidadMeses = Integer.parseUnsignedInt(p_cantidadMeses);

			double tasa = this.modelo.obtenerTasa(monto, cantidadMeses);
			double interes = this.modelo.obtenerInteres(monto, tasa, cantidadMeses);
			double cuota = this.modelo.obtenerValorCuota(monto, interes, cantidadMeses);
			
			this.ventana.mostrarInformacionPrestamo(tasa, interes, cuota);
			
			logger.info("Cambia de estado para permitir crear el prestamo.");
			this.ventana.estadoIngresarPrestamo();		
			
		} catch (NumberFormatException e) {
			logger.warn("Se produjo una excepción al realizar el parsing");
			this.ventana.informar(e.getMessage());
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}",e.getMessage());
			this.ventana.informar(e.getMessage());
		}		
	}

	@Override
	public void prestamoNuevoCrearPrestamo(String tipoDocumento, String p_dni, String p_monto, String p_cantidadMeses) {
		logger.info("Solicita realizar el prestamo para el cliente {}:{} de un monto de {} en un periodo de {} meses", tipoDocumento, p_dni, p_monto, p_cantidadMeses);

		try {
			// Debe realizar todas las validaciones nuevamente porque la llamada desde el controlador puede
			// haber modificado alguno de los datos previos.
			
			ClienteBean c = this.modelo.recuperarCliente(tipoDocumento.trim(), Integer.parseInt(p_dni));
			
			logger.info("Verifica que el cliente {} no tenga un prestamo vigente.", c.getNroCliente());
			
			Integer prestamo = this.modelo.prestamoVigente(c.getNroCliente());			
			if (prestamo != null) {
				throw new Exception("El cliente ya tiene un prestamo vigente. Prestamo Nro:" + String.valueOf(prestamo));
			}
			double monto = Parsing.parseMonto(p_monto);	
			int cantidadMeses = Integer.parseUnsignedInt(p_cantidadMeses);

			logger.info("Busca la tasa, interes y cuota para un prestamo de {} pesos a {} meses", monto, cantidadMeses);
			
			double tasa = this.modelo.obtenerTasa(monto, cantidadMeses);
			double interes = this.modelo.obtenerInteres(monto, tasa, cantidadMeses);
			double cuota = this.modelo.obtenerValorCuota(monto, interes, cantidadMeses);
			
			PrestamoBean prestamoBean = new PrestamoBeanImpl();
			prestamoBean.setNroCliente(c.getNroCliente());
			prestamoBean.setCantidadMeses(cantidadMeses);
			prestamoBean.setMonto(monto);
			prestamoBean.setTasaInteres(tasa);
			prestamoBean.setInteres(interes);
			prestamoBean.setValorCuota(cuota);
			//prestamoBean.setLegajo(legajo);    El legajo lo asigna el modelo que mantiene el estado
			
			this.modelo.crearPrestamo(prestamoBean);
			
			this.ventana.informar("Prestamo Registrado Correctamente.");
			this.ventana.estadoSeleccionCliente();
			
		} catch (NumberFormatException e) {
			logger.warn("Se produjo una excepción al realizar el parsing");
			this.ventana.informar(e.getMessage());
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}",e.getMessage());
			this.ventana.informar(e.getMessage());
		}	
	}

	
	@Override
	public void pagoCuotaSeleccionaCliente(String p_tipo, String p_dni) {
		logger.info("Solicita seleccionar un cliente con documento {} {} para realizar el préstamo.",p_tipo ,p_dni);
		try {
			
			ClienteBean c = this.modelo.recuperarCliente(p_tipo.trim(), Integer.parseInt(p_dni));
			
			this.ventana.mostrarInformacionClientePagoCuota(c);
			
			Integer nroPrestamo = this.modelo.prestamoVigente(c.getNroCliente());
			
			if (nroPrestamo == null) {

				logger.info("El cliente no tiene un prestamo vigente.");
				this.ventana.informar("El cliente no tiene un prestamo vigente.");
					
			} else {
				
				PrestamoBean prestamo = this.modelo.recuperarPrestamo(nroPrestamo);
				if (prestamo == null) {
					throw new Exception ("No se encontró el prestamo asociado al nro " + String.valueOf(nroPrestamo));
				}
				this.ventana.mostrarInformacionPrestamoPagoCuota(prestamo);
				
				ArrayList<PagoBean> pagos = this.modelo.recuperarPagos(nroPrestamo);
				logger.info("Se recuperaron {} pagos", pagos.size());
				this.ventana.mostrarPagos(pagos);
				
				logger.info("Cambia de estado para permitir pagar cuotas");
				this.ventana.pagoCuotaEstadoSeleccionCuotas();
					
			}				
			
		} catch (NumberFormatException e) {
			logger.warn("Se produjo una excepción al realizar el parsing");
			this.ventana.informar("El número de DNI no tiene un formato válido");
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar(e.getMessage());
		}
		
	}

	@Override
	public void pagoCuotaCambiarCliente() {
		logger.info("Solicita cambiar el cliente.");
		this.ventana.limpiarInformacionClientePagoCuota();

		logger.info("Cambia de estado para permitir ingresar un cliente");
		this.ventana.pagoCuotaEstadoSeleccionCliente();
	}

	@Override
	public void pagoCuotaPagar(String p_tipo, String p_dni, Integer p_nroPrestamo, List<Integer> cuotasAPagar) {
		logger.info("Solicita pagar {} cuotas del prestamo {} del cliente {} {}.",cuotasAPagar.size(), p_nroPrestamo,p_tipo,p_dni);
		
		try {
			int dni = Integer.parseInt(p_dni);
			
			if (p_nroPrestamo == null) {
				throw new Exception ("El número del prestamo no tiene un formato reconocible");
			}
			
			this.modelo.pagarCuotas(p_tipo.trim(), dni, p_nroPrestamo, cuotasAPagar);
			
			this.ventana.informar("Se han pagado exitosamente " + String.valueOf(cuotasAPagar.size()) + " cuotas.");

			ArrayList<PagoBean> pagos = this.modelo.recuperarPagos(p_nroPrestamo);
			logger.info("Se recuperaron {} pagos", pagos.size());
			this.ventana.mostrarPagos(pagos);			
			
		} catch (NumberFormatException e) {
			logger.warn("Se produjo una excepción al realizar el parsing");
			this.ventana.informar("El número de DNI no tiene un formato válido");
		} catch (Exception e) {
			logger.warn("Se produjo una excepción {}", e.getMessage());
			this.ventana.informar(e.getMessage());
		}
		
	}

}
