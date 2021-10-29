package banco.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.modelo.UsuarioBean;
import banco.modelo.ModeloLogin;
import banco.modelo.atm.ModeloATM;
import banco.modelo.atm.ModeloATMImpl;
import banco.modelo.empleado.ModeloEmpleado;
import banco.modelo.empleado.ModeloEmpleadoImpl;
import banco.vista.atm.VentanaATM;
import banco.vista.atm.VentanaATMImpl;
import banco.vista.empleado.VentanaEmpleado;
import banco.vista.empleado.VentanaEmpleadoImpl;
import banco.vista.login.VentanaLogin;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA 
public class ControladorLoginImpl implements ControladorLogin { 
	
	private static Logger logger = LoggerFactory.getLogger(ControladorLoginImpl.class);	
	
	private VentanaLogin ventana; 
	private ModeloLogin modelo;	
	
	public ControladorLoginImpl(VentanaLogin ventana, ModeloLogin theModel) {
		this.ventana = ventana;	
		this.modelo = theModel;
		
		try {
			logger.info("Se inicia la carga de parametros para conectar con la BD.");
			this.modelo.iniciarConexion();
			
			logger.info("Se inicializa la ventana de login.");
			
			logger.debug("se registra el controlador.");
			this.ventana.registrarControlador(this);
			
			logger.debug("se muestra la ventana.");
			this.ventana.mostrarVentana();
			
			logger.debug("se carga el combo con los usuarios.");
			this.ventana.poblarComboTipoUsuario(this.modelo.obtenerNombresUsuarios());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	

	@Override
	public void ingresarComoEmpleado(String legajo, char[] password) {
		logger.debug("Intenta ingresar como Empleado con legajo {}, password {}", legajo, String.valueOf(password));
		
		logger.debug("Recupera el usuario de BD para {}","Empleado");
		UsuarioBean usuario = this.modelo.obtenerUsuario("Empleado");

		if (usuario != null) {
			
			logger.debug("usuario {}, password {}",usuario.getUsername(), usuario.getPassword());
			
			ModeloEmpleado modeloEmpleado = new ModeloEmpleadoImpl();
			
			if (modeloEmpleado.conectar(usuario.getUsername(), usuario.getPassword())) {
			
				try {
					if (modeloEmpleado.autenticarUsuarioAplicacion(legajo, String.valueOf(password))) {

						logger.info("Empleado {} autenticado",legajo);
						
						VentanaEmpleado ventanaEmpleado = new VentanaEmpleadoImpl();
						ControladorEmpleado controladorEmpleado = new ControladorEmpleadoImpl(ventanaEmpleado, modeloEmpleado);
						
						logger.info("Transfiere el control al nuevo controlador");
						controladorEmpleado.ejecutar();
						
						logger.info("Informa a la vista que puede eliminar la ventana de login.");					
						this.ventana.eliminarVentana();
					}
					else
					{
						logger.error("Hubo un error en la autenticación.");
						this.ventana.informar("El usuario o contraseña ingresados son incorrectos.");
					}
				} catch (Exception e) {
					logger.error("Hubo un error en la autenticación.");
					this.ventana.informar(e.getMessage());
				}
			}
			else
			{
				logger.error("No se pudo conectar a la BD.");
				this.ventana.informar("Error en la conexión con la BD.");
			}			
		}
		else
		{
			logger.error("No se pudo recuperar la información del empleado.");
			this.ventana.informar("Error en el acceso a la información del empleado.");
		}		
	}

	@Override
	public void ingresarComoCliente(String tarjeta, char[] pin) {
		
		logger.debug("Intenta ingresar como Cliente a un ATM, tarjeta {}, PIN {}", tarjeta, pin);
		
		logger.debug("Recupera el usuario de BD para {}","Cliente");
		UsuarioBean usuario = this.modelo.obtenerUsuario("ATM");

		if (usuario != null) {
			
			logger.debug("usuario {}, password {}",usuario.getUsername(), usuario.getPassword());
			
			ModeloATM modeloATM = new ModeloATMImpl();
			
			if (modeloATM.conectar(usuario.getUsername(), usuario.getPassword())) {
			
				if (modeloATM.autenticarUsuarioAplicacion(tarjeta, String.valueOf(pin))) {

					logger.info("Usuario {} autenticado","Cliente");
				
					VentanaATM ventanaATM = new VentanaATMImpl();
					ControladorATM controladorATM = new ControladorATMImpl(ventanaATM, modeloATM);
					
					logger.info("Transfiere el control al nuevo controlador");
					controladorATM.ejecutar();
					
					logger.info("Informa a la vista que puede eliminar la ventana de login.");					
					this.ventana.eliminarVentana();
					
				}
				else
				{
					logger.error("Hubo un error en la autenticación.");
					this.ventana.informar("El usuario o contraseña ingresados son incorrectos.");
				}
			}
			else
			{
				logger.error("No se pudo conectar a la BD.");
				this.ventana.informar("Error en la conexión con la BD.");
			}			
		}
		else
		{
			logger.error("No se pudo recuperar la información del usuario ATM.");
			this.ventana.informar("Error en el acceso a la información del usuario ATM.");
		}			
		
	}
	
}