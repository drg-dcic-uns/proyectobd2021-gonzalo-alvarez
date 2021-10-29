package banco;

import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.controlador.ControladorLogin;
import banco.controlador.ControladorLoginImpl;
import banco.modelo.ModeloLogin;
import banco.modelo.ModeloLoginImpl;
import banco.vista.login.VentanaLogin;
import banco.vista.login.VentanaLoginImpl;


// CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class Banco {

	private static Logger logger = LoggerFactory.getLogger(Banco.class);
	
	/**
	 * Iniciar la aplicación
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					logger.debug("Se incia la aplicación");
					
					ModeloLogin modelo = new ModeloLoginImpl();  
					VentanaLogin ventana = new VentanaLoginImpl();
					@SuppressWarnings("unused")
					ControladorLogin controlador = new ControladorLoginImpl(ventana, modelo);
					
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
