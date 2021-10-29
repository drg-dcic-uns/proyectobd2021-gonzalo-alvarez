package banco.vista.atm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toedter.calendar.JDateChooser;

import banco.controlador.ControladorATM;
import banco.modelo.atm.TransaccionCajaAhorroBean;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class VentanaATMImpl extends JFrame implements VentanaATM {

	private static Logger logger = LoggerFactory.getLogger(VentanaATMImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaATMImpl() 
	{
		logger.debug("Se crea la ventana de ATM");

		frmAtm = this;		
		
        amountDisplayFormat = NumberFormat.getCurrencyInstance();
        amountDisplayFormat.setMinimumFractionDigits(2);
        amountEditFormat = NumberFormat.getNumberInstance();
		
		inicializar();
	}

	@Override
	public void registrarControlador(ControladorATM controlador) {
		this.controlador = controlador;
	}

	@Override
	public void mostrarPanel(String opcion) {
		this.frmAtmLayout.show(this.frmAtm.getContentPane(), opcion);
	}

	@Override
	public void mostrarVentana() throws Exception {
		if (this.frmAtm != null) {
			this.frmAtm.setVisible(true);
		}
		else 
		{
			throw new Exception("Error la ventana no está disponible");			
		}		
	}

	@Override
	public void eliminarVentana() {
		logger.info("Eliminación de la ventana.");
		
		this.frmAtm.dispose();
	}

	@Override
	public void informar(String mensaje) {
		logger.info("Crea una ventana modal informando sobre el mensaje.");
		
		JOptionPane.showMessageDialog(null,mensaje);
	}	
	
	
	@Override
	public void limpiarValoresExtraccion() {
		this.getBtnExtraer().setEnabled(true);
		this.getTxtMontoExtraccion().setEditable(true);
		this.setTxtMontoExtraccion("");		
	}

	
	@Override	
	public void limpiarValoresTransferencia() {
		this.getBtnTransferir().setEnabled(true);
		this.getTxtMontoTransferencia().setEditable(true);
		this.getTxtCuentaDestino().setEditable(true);			
		this.getTxtMontoTransferencia().setText("");
		this.getTxtCuentaDestino().setText("");			
	}
	

	
	/*
	 * Propiedades y metodos privados y protegidos
	 * 
	 * 
	 */
	
	protected ControladorATM controlador;
	protected VentanaATM ventana;
	
	protected JFrame frmAtm;
	protected CardLayout frmAtmLayout;
	
	protected JTable tablaMov,tablaMP;
	protected String tarjeta;
	protected JDateChooser dateChooserDesde, dateChooserHasta;
	protected DefaultTableModel ultMovimientosTableModel, movPeriodoTableModel;
	protected JFormattedTextField txtSaldo;
	protected JTextField txtMontoExtraccion;
	protected JTextField txtCuentaDestino, txtMontoTransferencia;
	protected JButton btnExtraer,btnTransferir;
	protected JTabbedPane pestanias;
	
	protected JButton btnOpcionConsultaSaldo;	
	protected JButton btnOpcionExtraccion;	
	protected JButton btnOpcionTransferencias;	
	protected JButton btnOpcionUltimosMovimientos;	
	protected JButton btnOpcionMovimientosPorPeriodos;	
	
	protected JButton btnBuscarMovPeriodo;
	
	protected JMenuItem mntmCerrarSesion;
	protected JMenuItem mntmSalir;	
	
    private NumberFormat amountDisplayFormat;
    private NumberFormat amountEditFormat;	
	

	
	/**
	  * Método encargado de inicializar todos los componentes de la ventana ATM
	  */
	private void inicializar()
	{
		frmAtm.setTitle("Cajero Automático (ATM)");
		frmAtm.setResizable(false);
		frmAtm.setBounds(100, 100, 800, 455);
		frmAtm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.frmAtmLayout = new CardLayout();
		this.frmAtm.getContentPane().setLayout(this.frmAtmLayout);
	
		this.frmAtm.setJMenuBar(this.crearMenuOpciones());		
		this.frmAtm.getContentPane().add(this.crearMenuPrincipal(),VentanaATM.OPCIONES);
		this.frmAtm.getContentPane().add(this.crearPanelConsultaSaldo(),VentanaATM.SALDO);
		this.frmAtm.getContentPane().add(this.crearPanelExtraccion(),VentanaATM.EXTRACCION);
		this.frmAtm.getContentPane().add(this.crearPanelTransferencias(),VentanaATM.TRANSFERENCIA);
		this.frmAtm.getContentPane().add(this.crearPanelUltimosMovimientos(),VentanaATM.ULTMOVIMIENTOS);
		this.frmAtm.getContentPane().add(this.crearPanelMovimientosPorPeriodos(),VentanaATM.MOVXPERIODO);		

		logger.debug("Se registran los listeners.");
		this.registrarEventos();			
		
		// El controlador se encarga de hacerla visible		

	}	
	
	private void registrarEventos() {
		this.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.cerrarSesion();
			}
		});
		
		this.getMenuItemSalir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.salirAplicacion();
			}
		});

		/*
		 * 
		 * Menu de opciones
		 * 
		 */
		logger.debug("Se inicializa los listeners del menu de opciones");
		
		this.getBtnOpcionConsultaSaldo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de consulta de saldo");
				controlador.mostrarOpcionSaldo();
			};
		});
		
		this.getBtnOpcionExtraccion().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de extracción");
				controlador.mostrarOpcionExtraccion();
			};
		});
		
		this.getBtnOpcionTransferencias().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de transferencia");
				controlador.mostrarOpcionTransferencia();
			};
		});			

		this.getBtnOpcionUltimosMovimientos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de ultimos movimientos");
				controlador.mostrarOpcionUltimosMovimientos();
			};
		});			

		this.getBtnOpcionMovimientosPorPeriodos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de movimientos por periodo");
				controlador.mostrarMovimientosPorPeriodosInicial();
			};
		});			
		
		/*
		 * 
		 * Operaciones
		 *  
		 */
		logger.debug("Se inicializa los listeners de los botones de las operaciones");
		
		this.getBtnBuscarMovPeriodo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de buscar movimientos por periodo");
				
				Date desde = getPeriodoDesde();
				Date hasta = getPeriodoHasta();
				logger.info("Periodo {} a {}", desde!=null?desde.toString():"null", hasta!=null?hasta.toString():"null");
				
				controlador.mostrarOpcionMovimientosPorPeriodos(desde,hasta);
			};
		});	
		
		this.getBtnExtraer().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Ejecuta el listener de realizar una extracción");

				getBtnExtraer().setEnabled(false);
				getTxtMontoExtraccion().setEditable(false);
				controlador.solicitudExtraccion(getTxtMontoExtraccion().getText());
			}
		});
		
		this.getBtnTransferir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Ejecuta el listener de realizar una extracción");

				getBtnTransferir().setEnabled(false);
				getTxtMontoTransferencia().setEditable(false);
				getTxtCuentaDestino().setEditable(false);
				controlador.solicitudTransferencia(getTxtMontoTransferencia().getText(), getTxtCuentaDestino().getText());
			}
		});		
	}
		
	private JMenuBar crearMenuOpciones() {
		JMenuBar barraDeMenuATM = new JMenuBar();
		JMenu menuOpciones=new JMenu("Menu");
		menuOpciones.setFont(new Font("Segoe UI", Font.BOLD, 17));
		
		
		this.mntmCerrarSesion = new JMenuItem("Cerrar Sesion");
		this.mntmCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmCerrarSesion);
		
		this.mntmSalir = new JMenuItem("Salir");
		this.mntmSalir.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmSalir);
		
		barraDeMenuATM.add(menuOpciones);
		return barraDeMenuATM;		
	}
	
	
	private JPanel crearMenuPrincipal() {
		
		JPanel panelMenuPrincipal = new JPanel();
		panelMenuPrincipal.setLayout(new GridLayout(3, 2, 10, 5));
		
		JPanel panelConsultaSaldo = new JPanel();
		panelMenuPrincipal.add(panelConsultaSaldo);
		panelConsultaSaldo.setLayout(null);
		this.btnOpcionConsultaSaldo = new JButton("Consultar Saldo");
		this.btnOpcionConsultaSaldo.setBackground(SystemColor.controlHighlight);
		this.btnOpcionConsultaSaldo.setFont(new Font("Arial", Font.BOLD, 16));
		this.btnOpcionConsultaSaldo.setBounds(104, 35, 179, 62);
		panelConsultaSaldo.add(this.btnOpcionConsultaSaldo);
		
		JPanel panelExtraccion = new JPanel();
		panelMenuPrincipal.add(panelExtraccion);
		panelExtraccion.setLayout(null);
		this.btnOpcionExtraccion = new JButton("Extracción");
		this.btnOpcionExtraccion.setBackground(SystemColor.controlHighlight);
		this.btnOpcionExtraccion.setFont(new Font("Arial", Font.BOLD, 16));
		this.btnOpcionExtraccion.setBounds(104, 35, 179, 62);
		panelExtraccion.add(btnOpcionExtraccion);

		JPanel panelTransferencias = new JPanel();
		panelMenuPrincipal.add(panelTransferencias);
		panelTransferencias.setLayout(null);
		this.btnOpcionTransferencias = new JButton("Transferencia");
		this.btnOpcionTransferencias.setBackground(SystemColor.controlHighlight);
		this.btnOpcionTransferencias.setFont(new Font("Arial", Font.BOLD, 16));
		this.btnOpcionTransferencias.setBounds(104, 35, 179, 62);
		panelTransferencias.add(btnOpcionTransferencias);

		JPanel panelUltimosMovimientos = new JPanel();
		panelMenuPrincipal.add(panelUltimosMovimientos);
		panelUltimosMovimientos.setLayout(null);
		this.btnOpcionUltimosMovimientos = new JButton("Últimos movimientos");
		this.btnOpcionUltimosMovimientos.setBackground(SystemColor.controlHighlight);
		this.btnOpcionUltimosMovimientos.setFont(new Font("Arial", Font.BOLD, 16));
		this.btnOpcionUltimosMovimientos.setBounds(104, 35, 179, 62);
		panelUltimosMovimientos.add(btnOpcionUltimosMovimientos);

		JPanel panelMovimientosPeriodo = new JPanel();
		panelMenuPrincipal.add(panelMovimientosPeriodo);
		panelMovimientosPeriodo.setLayout(null);
		this.btnOpcionMovimientosPorPeriodos = new JButton("Movimientos por período");
		this.btnOpcionMovimientosPorPeriodos.setBackground(SystemColor.controlHighlight);
		this.btnOpcionMovimientosPorPeriodos.setFont(new Font("Arial", Font.BOLD, 16));
		this.btnOpcionMovimientosPorPeriodos.setBounds(104, 35, 179, 62);
		panelMovimientosPeriodo.add(btnOpcionMovimientosPorPeriodos);

		return panelMenuPrincipal;
	}
	
	private JPanel crearPanelBotoneraVolver() {

		JPanel panelBotonera = new JPanel();		
		panelBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton btnVolverMenuOpciones = new JButton("Volver al menú");
		panelBotonera.add(btnVolverMenuOpciones);
		
		btnVolverMenuOpciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(VentanaATM.OPCIONES);
			}
		});
		
		return panelBotonera;
	}	
	
	private JPanel crearPanelConsultaSaldo() {
		logger.info("Ingresa a crear el panel para {}", "consulta de saldo");
		
		JPanel panelSaldo = new JPanel();		
		panelSaldo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSaldoActual = new JLabel("Saldo Actual:");
		panelSaldo.add(lblSaldoActual);

		txtSaldo =  new JFormattedTextField(
                new DefaultFormatterFactory(
                        new NumberFormatter(amountDisplayFormat),
                        new NumberFormatter(amountDisplayFormat),
                        new NumberFormatter(amountEditFormat)));
		
		txtSaldo.setEditable(false);
        txtSaldo.setColumns(10);		
		panelSaldo.add(txtSaldo);
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panelSaldo, BorderLayout.CENTER);	
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;			
		
	}

	private JPanel crearPanelExtraccion() {
		logger.info("Ingresa a crear el panel para {}", "Extracción");
		
		JPanel panel_Extraer = new JPanel();
		panel_Extraer.setLayout(null);
		
		txtMontoExtraccion = new JTextField();
		txtMontoExtraccion.setBounds(123, 155, 116, 22);
		panel_Extraer.add(txtMontoExtraccion);
		txtMontoExtraccion.setColumns(10);
		
		btnExtraer = new JButton("Extraer");
		btnExtraer.setBounds(289, 146, 116, 40);
		

		
		panel_Extraer.add(btnExtraer);
		
		JLabel lblIngreseMontoExtraccion = new JLabel("Ingrese Monto:");
		lblIngreseMontoExtraccion.setBounds(28, 158, 93, 16);
		panel_Extraer.add(lblIngreseMontoExtraccion);		
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panel_Extraer, BorderLayout.CENTER);	
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;			
	}

	private JPanel crearPanelTransferencias() {
		logger.info("Ingresa a crear el panel para {}", "Transferencias");
		
		JPanel panel_Transferencia = new JPanel();
		
		panel_Transferencia.setLayout(null);
		
		txtCuentaDestino = new JTextField();
		txtCuentaDestino.setBounds(173, 102, 116, 22);
		panel_Transferencia.add(txtCuentaDestino);
		txtCuentaDestino.setColumns(10);
		
		txtMontoTransferencia = new JTextField();
		txtMontoTransferencia.setBounds(121, 153, 116, 22);
		panel_Transferencia.add(txtMontoTransferencia);
		txtMontoTransferencia.setColumns(10);
		
		btnTransferir = new JButton("Transferir");
		btnTransferir.setBounds(289, 146, 116, 36);
		
		panel_Transferencia.add(btnTransferir);
		
		JLabel lblCajaDeAhorro = new JLabel("Caja de Ahorro Destino:");
		lblCajaDeAhorro.setBounds(23, 105, 144, 16);
		panel_Transferencia.add(lblCajaDeAhorro);
		
		JLabel lblIngreseMontoTransferencia = new JLabel("Ingrese Monto:");
		lblIngreseMontoTransferencia.setBounds(22, 156, 87, 16);
		panel_Transferencia.add(lblIngreseMontoTransferencia);		
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panel_Transferencia, BorderLayout.CENTER);	
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;		
	}

	private JPanel crearPanelUltimosMovimientos() {
		logger.info("Ingresa a crear el panel para {}", "consulta de Ultimos Movimientos");

		JPanel panelUltMovimientos = new JPanel();
		panelUltMovimientos.setLayout(new GridLayout(1, 1, 0, 0));
		
		//Se crea un modelo para los ultimos movimientos del cliente
		ultMovimientosTableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		//Se insertan las columnas en el modelo que se mostrarán en la tabla de ultimos movimientos del cliente
		ultMovimientosTableModel.addColumn("fecha");
		ultMovimientosTableModel.addColumn("hora");
		ultMovimientosTableModel.addColumn("tipo");
		ultMovimientosTableModel.addColumn("monto");
		ultMovimientosTableModel.addColumn("cod_caja");
		ultMovimientosTableModel.addColumn("destino");
		
		//Se agrega el modelo a la tabla
		tablaMov = new JTable(ultMovimientosTableModel){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
		    {
				Component c = super.prepareRenderer(renderer, row, column);
	            Color foreground, background;
	            Color color = new Color(221, 255, 219);
	            Color seleccion = new Color(219, 219, 219);

	            if (isRowSelected(row)) {
	                foreground = Color.black;
	                background = seleccion;
	            } else {
	                if (row % 2 != 0) {
	                    foreground = Color.black;
	                    background = Color.white;
	                } else {
	                    foreground = Color.black;
	                    background = color;
	                }
	            }
	            c.setForeground(foreground);
	            c.setBackground(background);
	            
	            return c;
		    }
		};
		tablaMov.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaMov.getTableHeader().setReorderingAllowed(false);
		
		panelUltMovimientos.add(new JScrollPane(tablaMov));
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panelUltMovimientos, BorderLayout.CENTER);	
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;
	}

	private JPanel crearPanelMovimientosPorPeriodos() {
		logger.info("Ingresa a crear el panel para {}", "consulta de Movimientos Por Períodos");
		
		JPanel panelSeleccionFecha = new JPanel();
		panelSeleccionFecha.setLayout(new GridLayout(1, 3, 20, 5));
		
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setHorizontalAlignment(SwingConstants.CENTER);
		
		dateChooserDesde = new JDateChooser();
		dateChooserDesde.add(lblDesde, BorderLayout.NORTH);
		panelSeleccionFecha.add(dateChooserDesde);;
		
		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setHorizontalAlignment(SwingConstants.CENTER);
		
		dateChooserHasta = new JDateChooser();
		dateChooserHasta.add(lblHasta, BorderLayout.NORTH);
		panelSeleccionFecha.add(dateChooserHasta);
		
		JPanel panelBuscar = new JPanel();
		
		btnBuscarMovPeriodo = new JButton("Buscar");
		panelBuscar.add(btnBuscarMovPeriodo);
		panelSeleccionFecha.add(panelBuscar);
		
		JPanel panelTablaMP = new JPanel();
		panelTablaMP.setLayout(new GridLayout(1, 1, 0, 0));
		
		//Define un modelo para la tabla de movimientos por periodo
		movPeriodoTableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		//Agrega las columnas en el modelo de la informacion que se va a mostrar en la tabla de movimientos por periodo
		movPeriodoTableModel.addColumn("fecha");
		movPeriodoTableModel.addColumn("hora");
		movPeriodoTableModel.addColumn("tipo");
		movPeriodoTableModel.addColumn("monto");
		movPeriodoTableModel.addColumn("cod_caja");
		movPeriodoTableModel.addColumn("destino");
		
		//Agrega el modelo a la tabla
		tablaMP = new JTable(movPeriodoTableModel){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
		    {
				Component c = super.prepareRenderer(renderer, row, column);
	            Color foreground, background;
	            Color color = new Color(221, 255, 219);
	            Color seleccion = new Color(219, 219, 219);

	            if (isRowSelected(row)) {
	                foreground = Color.black;
	                background = seleccion;
	            } else {
	                if (row % 2 != 0) {
	                    foreground = Color.black;
	                    background = Color.white;
	                } else {
	                    foreground = Color.black;
	                    background = color;
	                }
	            }
	            c.setForeground(foreground);
	            c.setBackground(background);
	            
	            return c;
		    }
		};
		tablaMP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaMP.getTableHeader().setReorderingAllowed(true);
		tablaMP.setVisible(false);
		
		panelTablaMP.add(new JScrollPane(tablaMP));
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panelSeleccionFecha, BorderLayout.NORTH);
		panelPpal.add(panelTablaMP, BorderLayout.CENTER);	
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;
	}
	
	
	
	protected JMenuItem getMenuItemCerrarSesion() {
		return this.mntmCerrarSesion;
	}

	
	protected JMenuItem getMenuItemSalir() {
		return this.mntmSalir;
	}

	
	protected JButton getBtnExtraer() {
		return btnExtraer;
	}

	
	protected JButton getBtnTransferir() {
		return btnTransferir;
	}
	
	
	protected JButton getBtnBuscarMovPeriodo() {
		return btnBuscarMovPeriodo;
	}	

	
	protected JButton getBtnOpcionConsultaSaldo() {
		return btnOpcionConsultaSaldo;
	}

	
	protected JButton getBtnOpcionExtraccion() {
		return btnOpcionExtraccion;
	}

	
	protected JButton getBtnOpcionTransferencias() {
		return btnOpcionTransferencias;
	}


	
	protected JButton getBtnOpcionUltimosMovimientos() {
		return btnOpcionUltimosMovimientos;
	}

	
	protected JButton getBtnOpcionMovimientosPorPeriodos() {
		return btnOpcionMovimientosPorPeriodos;
	}

	
	protected Date getPeriodoDesde() {
		return dateChooserDesde.getDate();
	}

	
	protected Date getPeriodoHasta() {
		return dateChooserHasta.getDate();
	}	
	
	protected JTextField getTxtMontoExtraccion() {
		return txtMontoExtraccion;
	}

	
	protected void setTxtMontoExtraccion(String value) {
		this.txtMontoExtraccion.setText(value);
	}
	
	
	protected JTextField getTxtMontoTransferencia() {
		return txtMontoTransferencia;
	}	
	
	
	protected JTextField getTxtCuentaDestino() {
		return txtCuentaDestino;
	}
	
	
	public void mostrarSaldo(double saldo) {
		this.txtSaldo.setText(String.valueOf(saldo));
		
	}

	@Override
	public void mostrarUltimosMovimientos(ArrayList<TransaccionCajaAhorroBean> lista) {

		logger.info("Muestra una lista con {} transacciones", lista.size());
		
		ultMovimientosTableModel.setRowCount(0);
		
		for (TransaccionCajaAhorroBean t: lista) {
			String[] fila = new String[ultMovimientosTableModel.getColumnCount()];
			
			fila[ultMovimientosTableModel.findColumn("fecha")] = t.getTransaccionFecha();
			fila[ultMovimientosTableModel.findColumn("hora")] = t.getTransaccionHora();			
			fila[ultMovimientosTableModel.findColumn("tipo")] = t.getTransaccionTipo();
			fila[ultMovimientosTableModel.findColumn("monto")] = String.valueOf(t.getTransaccionMonto()); // formatear
			fila[ultMovimientosTableModel.findColumn("cod_caja")] = String.valueOf(t.getTransaccionCodigoCaja());
			fila[ultMovimientosTableModel.findColumn("destino")] = String.valueOf(t.getCajaAhorroDestinoNumero());			
			
	 	   	ultMovimientosTableModel.addRow(fila); 		
		}
	}

	@Override
	public void mostrarMovimientosPorPeriodo(ArrayList<TransaccionCajaAhorroBean> lista) {
		
		logger.info("Muestra una lista con {} transacciones", lista.size());
		
		movPeriodoTableModel.setRowCount(0);
		
		for (TransaccionCajaAhorroBean t: lista) {
			String[] fila = new String[movPeriodoTableModel.getColumnCount()];
			
			fila[movPeriodoTableModel.findColumn("fecha")] = t.getTransaccionFecha();
			fila[movPeriodoTableModel.findColumn("hora")] = t.getTransaccionHora();			
			fila[movPeriodoTableModel.findColumn("tipo")] = t.getTransaccionTipo();
			fila[movPeriodoTableModel.findColumn("monto")] = String.valueOf(t.getTransaccionMonto()); // formatear
			fila[movPeriodoTableModel.findColumn("cod_caja")] = String.valueOf(t.getTransaccionCodigoCaja());
			fila[movPeriodoTableModel.findColumn("destino")] = String.valueOf(t.getCajaAhorroDestinoNumero());			
			
			movPeriodoTableModel.addRow(fila); 		
		}
		
		tablaMP.setVisible(true);
	}

	@Override
	public boolean confirmaExtraccion() {
		
		int seleccion = JOptionPane.showOptionDialog(btnExtraer,
													"Por favor, Confirme la Extracción de $" + this.getTxtMontoExtraccion().getText(), 
													"Confirmar Extracción",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													new Object[] { "Confirmar", "Cancelar"},
													"Cancelar" );

		logger.debug("Selecciono la opcion {}", seleccion);
		return (seleccion == 0);
	}

	@Override
	public void informarSaldo(Double saldo) {
		String saldoActual = " $ " + String.valueOf(saldo);
		JOptionPane.showMessageDialog(btnExtraer,"Extracción Exitosa, su saldo actual es de:" + saldoActual);
	}

	
	@Override
	public boolean confirmaTransferencia() {
		
		int seleccion = JOptionPane.showOptionDialog(btnTransferir,
													"Por favor, Confirme la Transferencia a la Caja de Ahorro " + this.getTxtCuentaDestino().getText() + " de $" + this.getTxtMontoTransferencia().getText(), 
													"Confirmar Transferencia",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													new Object[] { "Confirmar", "Cancelar"},
													"Cancelar" );

		logger.debug("Selecciono la opcion {}", seleccion);
		return (seleccion == 0);
	}

	
}
