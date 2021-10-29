package banco.controlador;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.modelo.ModeloLogin;
import banco.modelo.ModeloLoginImpl;
import banco.modelo.atm.ModeloATM;
import banco.modelo.atm.TransaccionCajaAhorroBean;
import banco.vista.atm.VentanaATM;
import banco.vista.login.VentanaLogin;
import banco.vista.login.VentanaLoginImpl;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class ControladorATMImpl implements ControladorATM {

	private static Logger logger = LoggerFactory.getLogger(ControladorATMImpl.class);	

	private VentanaATM ventana;
	private ModeloATM modelo;
	
	public ControladorATMImpl(VentanaATM ventana, ModeloATM modelo) {
		logger.debug("Se crea el controlador ATM.");		
		this.ventana = ventana;
		this.modelo = modelo;
		
		this.ventana.registrarControlador(this);
	}

	@Override
	public void ejecutar() {
		try {			
			this.ventana.mostrarVentana();
		} catch (Exception e) {
			String s = "Se produjo un error al intentar crear la ventana";
			logger.error(s);
			this.ventana.informar(s);
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
	public void mostrarOpcionSaldo() {
		logger.info("Muestra el panel de consulta de saldo");		
		this.ventana.mostrarPanel(VentanaATM.SALDO);
		
		try {
			logger.info("Consulta al modelo por el saldo del cliente");
			Double saldo = this.modelo.obtenerSaldo();

			logger.info("Muestra el saldo del cliente");
			this.ventana.mostrarSaldo(saldo);		

		} catch (Exception e) {
			this.ventana.informar(e.getMessage());
		}		
	}

	@Override
	public void mostrarOpcionUltimosMovimientos() {
		logger.info("Muestra el panel de ultimos movimientos");		
		this.ventana.mostrarPanel(VentanaATM.ULTMOVIMIENTOS);
		
		try {
			logger.info("Consulta al modelo por ultimos movimientos");
			ArrayList<TransaccionCajaAhorroBean> lista = this.modelo.cargarUltimosMovimientos();

			logger.info("Muestra la lista de movimientos");
			this.ventana.mostrarUltimosMovimientos(lista);		

		} catch (Exception e) {
			this.ventana.informar(e.getMessage());
		}
				
	}

	@Override
	public void mostrarMovimientosPorPeriodosInicial() {
		logger.info("Muestra el panel de movimientos por período");
		this.ventana.mostrarPanel(VentanaATM.MOVXPERIODO);
	}	
	
	
	@Override
	public void mostrarOpcionMovimientosPorPeriodos(Date desde, Date hasta) {
		try {
			logger.info("Consulta al modelo por ultimos movimientos");
			ArrayList<TransaccionCajaAhorroBean> lista = this.modelo.cargarMovimientosPorPeriodo(desde,hasta);

			logger.info("Muestra la lista de movimientos");
			this.ventana.mostrarMovimientosPorPeriodo(lista);		

		} catch (Exception e) {
			this.ventana.informar(e.getMessage());
		}
		
	}

	@Override
	public void mostrarOpcionExtraccion() {
		logger.info("Muestra el panel de extracción");
		this.ventana.mostrarPanel(VentanaATM.EXTRACCION);
	}

	
	@Override
	public void solicitudExtraccion(String p_monto) {
		logger.info("Controlador solicita al modelo realizar la extracción");
		
			
		try {
			logger.info("Se solicita al modelo validar y parse del monto");
			Double monto = this.modelo.parseMonto(p_monto);	

			logger.info("Se solicita al usuario la confirmación la extracción");
			if (this.ventana.confirmaExtraccion()) {
				Double saldo = this.modelo.extraer(monto);
				
				this.ventana.informarSaldo(saldo);
			}			
		} catch (Exception e) {
			logger.warn("Se produjo una excepción al realizar la extracción");
			this.ventana.informar(e.getMessage());
		} finally {
			this.ventana.limpiarValoresExtraccion();			
		}
	}

	@Override
	public void mostrarOpcionTransferencia() {
		logger.info("Muestra el panel de transferencia");
		this.ventana.mostrarPanel(VentanaATM.TRANSFERENCIA);
	}
	
	@Override
	public void solicitudTransferencia(String p_monto, String p_cuentaDestino) {
		logger.info("Controlador solicita al modelo realizar la transferencia");
		
		try {
			logger.info("Se solicita al modelo validar y parse del monto");
			Double monto = this.modelo.parseMonto(p_monto);	
			int cuentaDestino = this.modelo.parseCuenta(p_cuentaDestino);			

			logger.info("Se solicita al usuario la confirmación de la transferencia");
			if (this.ventana.confirmaTransferencia()) {
				Double saldo = this.modelo.transferir(monto, cuentaDestino);
				
				this.ventana.informarSaldo(saldo);
			}			
		} catch (Exception e) {
			logger.warn("Se produjo una excepción al realizar la extracción");
			this.ventana.informar(e.getMessage());
		} finally {
			this.ventana.limpiarValoresTransferencia();
		}
		
	}
}
