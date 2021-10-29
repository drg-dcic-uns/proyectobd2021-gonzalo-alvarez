package banco.vista.login;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.controlador.ControladorLogin;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class VentanaLoginImpl extends JFrame implements VentanaLogin, ItemListener {

	private static Logger logger = LoggerFactory.getLogger(VentanaLoginImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaLoginImpl()
	{
		logger.info("Creación de la ventana de login");
				
		this.inicializar();
	}

	@Override
	public void eliminarVentana() {
		logger.info("Eliminación de la ventana de login.");
		this.dispose();
	}

	@Override
	public void informar(String mensaje) {
		logger.info("Crea una ventana modal informando: {}.", mensaje);
		
		JOptionPane.showMessageDialog(null,mensaje);
	}

	@Override
	public void mostrarVentana() throws Exception {
		if (this != null) {
			this.setVisible(true);
		}
		else 
		{
			throw new Exception("Error la ventana no está disponible");			
		}		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// Implementa la funcionalidad de cambio de selección de usuario
		String opcion = (String) e.getItem();
		
		if (opcion.equals("ATM")) 
		{
			this.loginLayout.show(this.panelLogin, "atm");	
		} 
		else if (opcion.equals("Empleado")) 
		{
			this.loginLayout.show(this.panelLogin, "empleado");	
		} 
	}


	@Override
	public void registrarControlador(ControladorLogin controlador) {
		this.controlador = controlador;
	}
	
	@Override
	public void poblarComboTipoUsuario(List<String> nombresUsuarios) {
		this.getComboTipoUsuario().removeAllItems();		
		for (String item: nombresUsuarios) {
			this.getComboTipoUsuario().addItem(item);
		}
	}	
	
	
	private String getUsuarioSeleccionado() {
		return (String) this.getComboTipoUsuario().getSelectedItem();
	}


	private String getUserName() {

		String username = null;
		
		if (this.getUsuarioSeleccionado().equals("Administrador")) 
		{
			username = (String) this.getCampoAdminUsername().getText();
		} 
		else if (this.getUsuarioSeleccionado().equals("Empleado")) 
		{
			username = (String) this.getCampoEmpleadoUsername().getText();
		} 
		else if (this.getUsuarioSeleccionado().equals("ATM")) 
		{
			username = (String) this.getCampoATMUsername().getText();		
		} 
		
		return username;
	}

	
	private char[] getPassword() {		

		char[] password = null;
		
		if (this.getUsuarioSeleccionado().equals("Administrador")) 
		{
			password = this.getCampoAdminPassword().getPassword();
		} 
		else if (this.getUsuarioSeleccionado().equals("Empleado")) 
		{
			password = this.getCampoEmpleadoPassword().getPassword();
		} 
		else if (this.getUsuarioSeleccionado().equals("ATM")) 
		{
			password = this.getCampoATMPassword().getPassword();
		} 
		
		return password;
		
	}
	
	/*
	 * Propiedades y metodos privados y protegidos
	 * 
	 * 
	 */
	protected ControladorLogin controlador;
	 
	
	protected JPanel mainPanel;	

	protected JComboBox<String> comboTipoUsuario;
	protected JPanel panelLogin;	
	protected CardLayout loginLayout;	
	
	// Card Admin	
	protected JTextField campoAdminUsername;
	protected JPasswordField campoAdminPassword;

	// Card ATM
	protected JTextField campoATMUsername;
	protected JPasswordField campoATMPassword;

	// Card Empleado
	protected JTextField campoEmpleadoUsername;
	protected JPasswordField campoEmpleadoPassword;
	
	protected JButton btnAceptarLogin;	
	protected JButton btnCancelarLogin;
	
	/**
	  * Método encargado de inicializar todos los componentes de la ventana para logguearse
	  * 
	  * BorderLayout
	  * 
	  * +--------------------------------------+
	  * |               PAGE_START             |
	  * +--------------+----------+------------+
	  * |              |          |            |
	  * |  LINE_START  |  CENTER  |  LINE_END  |
	  * |              |          |            |
	  * +--------------+----------+------------+
	  * |               PAGE_END               |
	  * +--------------+----------+------------+
	  * 
	  */
	private void inicializar()
	{
		this.setType(Type.POPUP);
		this.setTitle("Ingreso al Sistema");
		this.setResizable(false);
		this.setBounds(100, 100, 406, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
				
		this.mainPanel.add(this.crearPanelTipoUsuario(), BorderLayout.PAGE_START);
		this.mainPanel.add(this.crearPanelLogin(), BorderLayout.CENTER);		
		this.mainPanel.add(this.crearPanelButtons(), BorderLayout.PAGE_END);

		logger.debug("Se registran los listeners.");
		this.registrarEventos();
		
		this.setContentPane(this.mainPanel);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Crea el panel de la botonera 
	 * 
	 * @return JPanel
	 */
	private JPanel crearPanelButtons() {
		
		JPanel panelButtons = new JPanel();
		
		btnAceptarLogin = new JButton("Aceptar");
		panelButtons.add(btnAceptarLogin);

		btnCancelarLogin = new JButton("Cancelar");
		panelButtons.add(btnCancelarLogin);
		
		return panelButtons;
	}

	/**
	 * Panel que permite seleccionar el tipo de usuario que se va a loguear
	 * 
	 * @return
	 */
	private JPanel crearPanelTipoUsuario() {
		
		JPanel panelTipoLogin = new JPanel();
		((FlowLayout) panelTipoLogin.getLayout()).setHgap(25);		
		
		comboTipoUsuario = new JComboBox<String>();
		panelTipoLogin.add(comboTipoUsuario);
		
		comboTipoUsuario.addItemListener(this);		
		
		return panelTipoLogin;
	}
	
	/**
	 * Panel que permite ingresar los datos según el tipo de usuario.
	 * 
	 * Si el usuario es
	 * Admin: Solicita usuario y password.
	 * Cliente (ATM): Solicita número de tarjeta y PIN
	 * Empleado: Solicita el legajo y password.
	 * 
	 * @return
	 */
	private JPanel crearPanelLogin() {
		
		this.loginLayout = new CardLayout();
		
		this.panelLogin = new JPanel();
		this.panelLogin.setLayout(this.loginLayout);
		
		this.panelLogin.add(this.crearPanelLoginAdmin(),"admin");
		this.panelLogin.add(this.crearPanelLoginATM(),"atm");
		this.panelLogin.add(this.crearPanelLoginEmpleado(),"empleado");
		
		return this.panelLogin;
	}
	
	
	private JPanel crearPanelLoginAdmin() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panelFila1 = new JPanel();
		((FlowLayout) panelFila1.getLayout()).setHgap(25);		

		JLabel lblUsername = new JLabel("Usuario:");
		this.campoAdminUsername = new JTextField();
		this.campoAdminUsername.setColumns(10);

		panelFila1.add(lblUsername);
		panelFila1.add(this.campoAdminUsername);
		
		JPanel panelFila2 = new JPanel();
				
		JLabel lblPasswordLogin = new JLabel("Contraseña:");
				
		this.campoAdminPassword = new JPasswordField();
		this.campoAdminPassword.setColumns(10);

		panelFila2.add(lblPasswordLogin);		
		panelFila2.add(this.campoAdminPassword);
		
		panel.add(panelFila1);
		panel.add(panelFila2);
		
		return panel;
	}
	
	private JPanel crearPanelLoginATM() {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panelFila1 = new JPanel();
		((FlowLayout) panelFila1.getLayout()).setHgap(25);		

		JLabel lblUsername = new JLabel("Nro Tarjeta:");
		this.campoATMUsername = new JTextField();
		this.campoATMUsername.setColumns(10);

		panelFila1.add(lblUsername);
		panelFila1.add(this.campoATMUsername);
		
		JPanel panelFila2 = new JPanel();
				
		JLabel lblPasswordLogin = new JLabel("PIN:");
				
		this.campoATMPassword = new JPasswordField();
		this.campoATMPassword.setColumns(10);

		panelFila2.add(lblPasswordLogin);		
		panelFila2.add(this.campoATMPassword);
		
		panel.add(panelFila1);
		panel.add(panelFila2);
		
		return panel;
	}
	
	private JPanel crearPanelLoginEmpleado() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panelFila1 = new JPanel();
		((FlowLayout) panelFila1.getLayout()).setHgap(25);		

		JLabel lblUsername = new JLabel("Legajo:");
		this.campoEmpleadoUsername = new JTextField();
		this.campoEmpleadoUsername.setColumns(10);

		panelFila1.add(lblUsername);
		panelFila1.add(this.campoEmpleadoUsername);
		
		JPanel panelFila2 = new JPanel();
				
		JLabel lblPasswordLogin = new JLabel("Contraseña:");
				
		this.campoEmpleadoPassword = new JPasswordField();
		this.campoEmpleadoPassword.setColumns(10);

		panelFila2.add(lblPasswordLogin);		
		panelFila2.add(this.campoEmpleadoPassword);
		
		panel.add(panelFila1);
		panel.add(panelFila2);
		
		return panel;
	}
	
	/*
	 * Setters y Getters
	 * 
	 */

	protected JTextField getCampoAdminUsername() {
		return campoAdminUsername;
	}

	protected JPasswordField getCampoAdminPassword() {
		return campoAdminPassword;
	}

	protected JTextField getCampoATMUsername() {
		return campoATMUsername;
	}

	protected JPasswordField getCampoATMPassword() {
		return campoATMPassword;
	}

	protected JTextField getCampoEmpleadoUsername() {
		return campoEmpleadoUsername;
	}

	protected JPasswordField getCampoEmpleadoPassword() {
		return campoEmpleadoPassword;
	}

	protected JComboBox<String> getComboTipoUsuario() {
		return comboTipoUsuario;
	}

	protected void setComboTipoUsuario(JComboBox<String> comboTipoUsuario) {
		this.comboTipoUsuario = comboTipoUsuario;
	}

	protected JButton getBtnAceptarLogin() {
		return btnAceptarLogin;
	}

	protected JButton getBtnCancelarLogin() {
		return btnCancelarLogin;
	}

	/*
	 * Metodos para los listener
	 * 
	 * 
	 */
	protected void registrarEventos() {
		this.getCampoATMUsername().addActionListener(this.getIngresarListener());		
		this.getCampoATMPassword().addActionListener(this.getIngresarListener());

		this.getCampoEmpleadoUsername().addActionListener(this.getIngresarListener());		
		this.getCampoEmpleadoPassword().addActionListener(this.getIngresarListener());

		this.getBtnAceptarLogin().addActionListener(this.getIngresarListener());		
		this.getBtnCancelarLogin().addActionListener(this.getCancelarListener());		
	}
	
	protected ActionListener getIngresarListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if (getUsuarioSeleccionado().equals("Empleado")) 
				{
					logger.info("Intenta ingresar como Empleado");
	            	controlador.ingresarComoEmpleado(getUserName(),getPassword());
				} 
				else if (getUsuarioSeleccionado().equals("ATM")) 
				{
					logger.info("Intenta ingresar como Cliente");				
	            	controlador.ingresarComoCliente(getUserName(),getPassword());					
				} 
				else  
				{ 
					logger.error("Intenta ingresar con un valor erroneo de usuario");
				}				
            }
        };
	}

	protected ActionListener getCancelarListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
            }
        };
	}	
}
