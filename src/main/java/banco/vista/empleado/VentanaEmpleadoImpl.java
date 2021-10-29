package banco.vista.empleado;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banco.controlador.ControladorEmpleado;
import banco.modelo.empleado.beans.ClienteBean;
import banco.modelo.empleado.beans.ClienteMorosoBean;
import banco.modelo.empleado.beans.EmpleadoBean;
import banco.modelo.empleado.beans.PagoBean;
import banco.modelo.empleado.beans.PrestamoBean;
import banco.utils.Fechas;
import banco.utils.Parsing;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
public class VentanaEmpleadoImpl extends JFrame implements VentanaEmpleado {

	private static Logger logger = LoggerFactory.getLogger(VentanaEmpleadoImpl.class);

	private static final long serialVersionUID = 1L;
	
	public VentanaEmpleadoImpl() {		
		inicializar();
		this.frame.setVisible(true);
	}

	@Override
	public void registrarControlador(ControladorEmpleado controlador) {
		this.controlador = controlador;
	}
	
	@Override
	public void limpiarInformacionClientePrestamoNuevo() {
		lblPrestamoNuevoApellido.setText("");
		lblPrestamoNuevoNombre.setText("");
		lblPrestamoNuevoDireccion.setText("");
		lblPrestamoNuevoTel.setText("");
		lblPrestamoNuevoFechaNacimiento.setText("");
		txtPrestamoNuevoDNI.setText("");		
	}
	
	@Override
	public void limpiarInformacionPrestamoPrestamoNuevo() {
		cmbPrestamoNuevoCantidadMeses.removeAllItems();
		txtPrestamoNuevoMonto.setText("");
		limpiarTasaInteresCuota();
	}
	
	@Override
	public void limpiarInformacionClientePagoCuota() {
		lblPagoCuotaApellido.setText("");
		lblPagoCuotaNombre.setText("");
		lblPagoCuotaDireccion.setText("");
		lblPagoCuotaTel.setText("");
		lblPagoCuotaFechaNacimiento.setText("");
		txtPagoCuotasDNI.setText("");
		this.modeloTablaPagos.setRowCount(0);
	}	

	@Override
	public void limpiarInformacionPrestamoPagoCuota() {
		this.nroPrestamoPagoCuota = null;
		this.lblPagoCuotaPrestamoNro.setText("");
		this.lblPagoCuotaMonto.setText("");
		this.lblPagoCuotaCantidadDeMeses.setText("");
		this.lblPagoCuotaTasa.setText("");
		this.lblPagoCuotaInteres.setText("");
		this.lblPagoCuotaValorCuota.setText("");		
	}
	
	@Override
	public void mostrarPanel(String panel) {
		this.frameLayout.show(this.frame.getContentPane(), panel);
	}	
	
	@Override
	public void mostrarVentana() throws Exception {
		if (this.frame != null) {
			this.frame.setVisible(true);
		}
		else 
		{
			throw new Exception("Error la ventana no está disponible");			
		}		
	}

	@Override
	public void eliminarVentana() {
		logger.info("Eliminación de la ventana.");
		this.frame.dispose();
	}

	@Override
	public void informar(String mensaje) {
		logger.info("Crea una ventana modal informando sobre el mensaje.");
		JOptionPane.showMessageDialog(this.frame,mensaje);
	}

	
	private AbstractButton getMenuItemCerrarSesion() {
		return this.mntmCerrarSesion;
	}

	
	private AbstractButton getMenuItemSalir() {
		return this.mntmSalir;
	}

	
	private AbstractButton getOpcionPrestamoNuevo() {
		return this.mntmPrestamoNuevo;
	}

	
	private AbstractButton getOpcionPrestamoGestionarCuotas() {
		return this.mntmCuotas;
	}

	
	private AbstractButton getOpcionPrestamoClientesMorosos() {
		return this.mntmClientesMorosos;
	}
	

	public void mostrarEmpleadoLogueado(EmpleadoBean empleado) {
		logger.info("Se registra al empleado {}, {} con legajo {}", empleado.getApellido(), empleado.getNombre(), empleado.getLegajo());
		
		this.lblEmpleado.setText("Legajo: " + String.valueOf(empleado.getLegajo()) + " - " + 
								 empleado.getApellido() + ", " + empleado.getNombre());
		
	}	
	

	
	private JComboBox<String> getPrestamoNuevoTipoDNI(){
		return cmbPrestamoNuevoTipoDNI;
	}
	
	
	private JTextField getPrestamoNuevoCampoDNI() {
		return this.txtPrestamoNuevoDNI;
	}

	
	private AbstractButton getPrestamoNuevoSeleccionarCliente() {
		return this.btnPrestamoNuevoSeleccionarCliente;
	}

	
	private JComboBox<String> getCmbPrestamoNuevoCantidadMeses(){
		return cmbPrestamoNuevoCantidadMeses;
	}

	
	private AbstractButton getPrestamoNuevoCambiarCliente() {
		return btnPrestamoNuevoCambiarCliente;
	}

	
	private JTextField getPrestamoNuevoMonto() {
		return txtPrestamoNuevoMonto;
	}

	
	private AbstractButton getPrestamoNuevoIngresarMonto() {
		return btnPrestamoNuevoIngresarMonto;
	}

	
	private AbstractButton getPrestamoNuevoCambiarMonto() {
		return btnPrestamoNuevoCambiarMonto;
	}

	
	public void mostrarInformacionPrestamo(double tasa, double interes, double cuota) {
		this.lblPrestamoNuevoTasa.setText(String.valueOf(tasa));
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		lblPrestamoNuevoInteres.setText(Parsing.corregirComa(df.format(interes)));		
		lblPrestamoNuevoValorCuota.setText(Parsing.corregirComa(df.format(cuota)));		
	}

	
	private AbstractButton getPrestamoNuevoIngresarPrestamo() {
		return this.btnIngresarPrestamo;
	}

	@Override
	public void estadoSeleccionCliente() {
		btnPrestamoNuevoSeleccionarCliente.setEnabled(true);
		cmbPrestamoNuevoTipoDNI.setEnabled(true);
		txtPrestamoNuevoDNI.setEnabled(true);
		btnPrestamoNuevoCambiarCliente.setEnabled(false);

		btnPrestamoNuevoIngresarMonto.setEnabled(false);
		txtPrestamoNuevoMonto.setEnabled(false);
		txtPrestamoNuevoMonto.setEditable(false);
		btnPrestamoNuevoCambiarMonto.setEnabled(false);
		
		cmbPrestamoNuevoCantidadMeses.setEnabled(false);
		
		btnIngresarPrestamo.setEnabled(false);
		
		limpiarInformacionClientePrestamoNuevo();
		limpiarInformacionPrestamoPrestamoNuevo();
	}

	@Override
	public void estadoIngresoMonto() {
		btnPrestamoNuevoSeleccionarCliente.setEnabled(false);
		cmbPrestamoNuevoTipoDNI.setEnabled(false);
		txtPrestamoNuevoDNI.setEnabled(false);
		btnPrestamoNuevoCambiarCliente.setEnabled(true);

		btnPrestamoNuevoIngresarMonto.setEnabled(true);
		txtPrestamoNuevoMonto.setEnabled(true);
		txtPrestamoNuevoMonto.setEditable(true);
		btnPrestamoNuevoCambiarMonto.setEnabled(false);
		
		cmbPrestamoNuevoCantidadMeses.setEnabled(false);
		
		btnIngresarPrestamo.setEnabled(false);
		
		limpiarInformacionPrestamoPrestamoNuevo();
	}

	@Override
	public void estadoIngresoCantidadMeses() {
		btnPrestamoNuevoSeleccionarCliente.setEnabled(false);
		cmbPrestamoNuevoTipoDNI.setEnabled(false);
		txtPrestamoNuevoDNI.setEnabled(false);
		btnPrestamoNuevoCambiarCliente.setEnabled(true);

		btnPrestamoNuevoIngresarMonto.setEnabled(false);
		txtPrestamoNuevoMonto.setEnabled(false);
		txtPrestamoNuevoMonto.setEditable(false);
		btnPrestamoNuevoCambiarMonto.setEnabled(true);
		
		cmbPrestamoNuevoCantidadMeses.setEnabled(true);
		
		btnIngresarPrestamo.setEnabled(false);
		
		limpiarTasaInteresCuota();
	}

	@Override
	public void estadoIngresarPrestamo() {
		btnPrestamoNuevoSeleccionarCliente.setEnabled(false);
		cmbPrestamoNuevoTipoDNI.setEnabled(false);
		txtPrestamoNuevoDNI.setEnabled(false);
		btnPrestamoNuevoCambiarCliente.setEnabled(true);

		btnPrestamoNuevoIngresarMonto.setEnabled(false);
		txtPrestamoNuevoMonto.setEnabled(false);
		txtPrestamoNuevoMonto.setEditable(false);
		btnPrestamoNuevoCambiarMonto.setEnabled(true);
		
		cmbPrestamoNuevoCantidadMeses.setEnabled(true);
		
		btnIngresarPrestamo.setEnabled(true);
	}

	@Override
	public void poblarComboTipoDocumento(ArrayList<String> tiposDocumento) {
		logger.info("Carga el combo con los {} tipos de documento recuperados", tiposDocumento.size());
		
		this.cmbPrestamoNuevoTipoDNI.removeAllItems();
		this.cmbPagoCuotasTipoDNI.removeAllItems();
		for (String item: tiposDocumento) {
			this.cmbPrestamoNuevoTipoDNI.addItem(item);
			this.cmbPagoCuotasTipoDNI.addItem(item);
		}
	}

	@Override
	public void mostrarInformacionClientePrestamoNuevo(ClienteBean cliente) {
		lblPrestamoNuevoApellido.setText(cliente.getApellido());
		lblPrestamoNuevoNombre.setText(cliente.getNombre());
		lblPrestamoNuevoDireccion.setText(cliente.getDireccion());
		lblPrestamoNuevoTel.setText(cliente.getTelefono());
		lblPrestamoNuevoFechaNacimiento.setText(cliente.getFechaNacimiento().toLocaleString());
		
	}

	@Override
	public void poblarOpcionesCantidadMeses(ArrayList<Integer> cantidadMeses) {
		logger.info("Carga el combo con las cantidad de meses recuperadas. Total {} items", cantidadMeses.size());
		
		// Para que no dispare el action listener cuando actualizo el combo.
		this.cmbPrestamoNuevoCantidadMeses.removeAllItems();
		
		for (Integer item: cantidadMeses) {
			logger.debug("Ingresa el item: {}", item);
			this.cmbPrestamoNuevoCantidadMeses.addItem(String.valueOf(item));
		}
	}

	/*
	 * Metodos para el pago de cuotas 
	 * 
	 */
	
	
	private JTextField getCampoDNIPagoCuota() {
		return this.txtPagoCuotasDNI;
	}

	
	private JComboBox<String> getTipoDNIPagoCuota() {
		return this.cmbPagoCuotasTipoDNI;
	}

	
	private AbstractButton getSeleccionarClientePagoCuota() {
		return this.btnPagoCuotaSeleccionarCliente;
	}

	
	private AbstractButton getCambiarClientePagoCuota() {
		return this.btnPagoCuotaCambiarCliente;
	}


	
	private AbstractButton getConfirmarPagoCuota() {
		return this.btnConfirmarPago;
	}
	
	@Override
	public void mostrarInformacionClientePagoCuota(ClienteBean cliente) {
		lblPagoCuotaApellido.setText(cliente.getApellido());
		lblPagoCuotaNombre.setText(cliente.getNombre());
		lblPagoCuotaDireccion.setText(cliente.getDireccion());
		lblPagoCuotaTel.setText(cliente.getTelefono());
		lblPagoCuotaFechaNacimiento.setText(cliente.getFechaNacimiento().toLocaleString());		
	}

	@Override
	public void mostrarInformacionPrestamoPagoCuota(PrestamoBean prestamo) {
		this.nroPrestamoPagoCuota = prestamo.getNroPrestamo();		
		this.lblPagoCuotaPrestamoNro.setText(String.valueOf(prestamo.getNroPrestamo()));
		this.lblPagoCuotaMonto.setText(String.valueOf(prestamo.getMonto()));
		this.lblPagoCuotaCantidadDeMeses.setText(String.valueOf(prestamo.getCantidadMeses()));
		this.lblPagoCuotaTasa.setText(String.valueOf(prestamo.getTasaInteres()));
		this.lblPagoCuotaInteres.setText(String.valueOf(prestamo.getInteres()));
		this.lblPagoCuotaValorCuota.setText(String.valueOf(prestamo.getValorCuota()));
	}
	
	
	@Override
	public void pagoCuotaEstadoSeleccionCliente() {
		btnPagoCuotaSeleccionarCliente.setEnabled(true);
		cmbPagoCuotasTipoDNI.setEnabled(true);
		txtPagoCuotasDNI.setEnabled(true);
		btnPagoCuotaCambiarCliente.setEnabled(false);

		btnConfirmarPago.setEnabled(false);
		
		this.limpiarInformacionClientePagoCuota();
		this.limpiarInformacionPrestamoPagoCuota();
	}

	@Override
	public void pagoCuotaEstadoSeleccionCuotas() {
		btnPagoCuotaSeleccionarCliente.setEnabled(false);
		cmbPagoCuotasTipoDNI.setEnabled(false);
		txtPagoCuotasDNI.setEnabled(false);
		btnPagoCuotaCambiarCliente.setEnabled(true);

		btnConfirmarPago.setEnabled(true);
	}

	@Override
	public void mostrarPagos(ArrayList<PagoBean> pagos) {
		logger.info("Muestra una lista con {} pagos", pagos.size());
		
		this.modeloTablaPagos.setRowCount(0);
		
		for (PagoBean t: pagos) {
			
			String[] fila = new String[modeloTablaPagos.getColumnCount()];
			
			fila[modeloTablaPagos.findColumn(VentanaEmpleado.TABLA_PAGOS_NRO_PAGO)] = String.valueOf(t.getNroPago());
			fila[modeloTablaPagos.findColumn(VentanaEmpleado.TABLA_PAGOS_FECHA_VENCIMIENTO)] = Fechas.convertirDateAString(t.getFechaVencimiento());
			fila[modeloTablaPagos.findColumn(VentanaEmpleado.TABLA_PAGOS_FECHA_PAGO)] = Fechas.convertirDateAString(t.getFechaPago());
			
			modeloTablaPagos.addRow(fila); 		
		}
	}

	@Override
	public List<Integer> obtenerCuotasAPagar() {
		logger.info("Busca en la tabla las cuotas seleccionadas a pagar");
		
		List<Integer> cuotasSeleccionadas = null;
		
		if(modeloTablaPagos.getRowCount()>0)
		{
			cuotasSeleccionadas = new ArrayList<Integer>();
			for(int i=0;i<modeloTablaPagos.getRowCount();i++)
			{
				Object o = modeloTablaPagos.getValueAt(i, modeloTablaPagos.findColumn(VentanaEmpleado.TABLA_PAGOS_SELECCIONAR));
				if (o != null) {
					if(Boolean.valueOf(o.toString()))
					{
						cuotasSeleccionadas.add(Integer.valueOf(modeloTablaPagos.getValueAt(i, modeloTablaPagos.findColumn(VentanaEmpleado.TABLA_PAGOS_NRO_PAGO)).toString()));
					}
				}
			}
		}
		
		logger.info("Se recuperaron {} cuotas a pagar de la tabla.", cuotasSeleccionadas.size());
		
		return cuotasSeleccionadas;
	}

	
	private int getNroPrestamoPagoCuota() {
		return nroPrestamoPagoCuota;
	}

	@Override
	public void mostrarClientesMorosos(ArrayList<ClienteMorosoBean> clientesMorosos) {
		logger.info("Muestra una lista con {} clientes morosos", clientesMorosos.size());
		
		this.modeloTablaMorosos.setRowCount(0);
		
		for (ClienteMorosoBean t: clientesMorosos) {
			
			String[] fila = new String[modeloTablaMorosos.getColumnCount()];
			
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_NRO_CLIENTE)] = String.valueOf(t.getCliente().getNroCliente());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_TIPO_DOC)] = String.valueOf(t.getCliente().getTipoDocumento());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_NRO_DOC)] = String.valueOf(t.getCliente().getNroDocumento());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_NOMBRE)] = String.valueOf(t.getCliente().getNombre());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_APELLIDO)] = String.valueOf(t.getCliente().getApellido());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_NRO_PRESTAMO)] = String.valueOf(t.getPrestamo().getNroPrestamo());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_MONTO)] = String.valueOf(t.getPrestamo().getMonto());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_CANTIDAD_MESES)] = String.valueOf(t.getPrestamo().getCantidadMeses());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_VALOR_CUOTA)] = String.valueOf(t.getPrestamo().getValorCuota());
			fila[modeloTablaMorosos.findColumn(VentanaEmpleado.TABLA_MOROSOS_CUOTAS_ATRASADAS)] = String.valueOf(t.getCantidadCuotasAtrasadas());			
			
			modeloTablaMorosos.addRow(fila); 		
		}
	}
	
	/*
	 * Propieades y métodos privados o protegidos
	 */	
	protected ControladorEmpleado controlador;
	protected VentanaEmpleado ventana;
	
	protected JFrame frame;
	protected CardLayout frameLayout;

	protected JMenuItem mntmCerrarSesion;
	protected JMenuItem mntmSalir;	

	protected JMenuItem mntmPrestamoNuevo;
	protected JMenuItem mntmCuotas;
	protected JMenuItem mntmClientesMorosos;
	
	protected JLabel lblEmpleado;	
		
	/*
	 * Campos para nuevo prestamo
	 */
	protected JTextField txtPrestamoNuevoDNI;
	protected JComboBox<String> cmbPrestamoNuevoTipoDNI;	
	protected JButton btnPrestamoNuevoSeleccionarCliente;
	protected JButton btnPrestamoNuevoCambiarCliente;
	
	protected JTextField txtPrestamoNuevoMonto;
	protected JButton btnPrestamoNuevoIngresarMonto;
	protected JButton btnPrestamoNuevoCambiarMonto;	
	protected JComboBox<String> cmbPrestamoNuevoCantidadMeses;	
	protected JLabel lblPrestamoNuevoTasa;
	protected JLabel lblPrestamoNuevoInteres;
	protected JLabel lblPrestamoNuevoValorCuota;
	
	protected JLabel lblPrestamoNuevoDireccion;
	protected JLabel lblPrestamoNuevoTel;
	protected JLabel lblPrestamoNuevoFechaNacimiento;
	protected JLabel lblPrestamoNuevoApellido;
	protected JLabel lblPrestamoNuevoNombre;
	
	protected JButton btnIngresarPrestamo;	
	
	/*
	 * Campos de pago de cuotas
	 */
	protected DefaultTableModel modeloTablaPagos;
	protected JComboBox<String> cmbPagoCuotasTipoDNI;
	protected JTextField txtPagoCuotasDNI;
	protected JButton btnPagoCuotaSeleccionarCliente;
	protected JButton btnPagoCuotaCambiarCliente;
	protected JLabel lblPagoCuotaNombre;
	protected JLabel lblPagoCuotaApellido;
	protected JLabel lblPagoCuotaFechaNacimiento;
	protected JLabel lblPagoCuotaTel;
	protected JLabel lblPagoCuotaDireccion;
	protected JButton btnConfirmarPago;

	private Integer nroPrestamoPagoCuota = null;
	protected JLabel lblPagoCuotaPrestamoNro;
	protected JLabel lblPagoCuotaMonto;
	protected JLabel lblPagoCuotaCantidadDeMeses;
	protected JLabel lblPagoCuotaTasa;
	protected JLabel lblPagoCuotaInteres;
	protected JLabel lblPagoCuotaValorCuota;	
	
	/*
	 * Lista de Morosos
	 */
	protected DefaultTableModel modeloTablaMorosos;	
		

	private void inicializar()
	{ 
		logger.debug("Inicializacion de la ventana del Empleado");
		
		this.frame = new JFrame();
		this.frame.setResizable(false);
		this.frame.setTitle("Administracion de Prestamos");
		this.frame.setBounds(100, 100, 852, 575);
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.frameLayout = new CardLayout();
		this.frame.getContentPane().setLayout(this.frameLayout);

		this.lblEmpleado = new JLabel();
		this.lblEmpleado.setFont(new Font("Arial", Font.BOLD, 13));
		this.lblEmpleado.setHorizontalAlignment(SwingConstants.LEFT);
		
		this.frame.setJMenuBar(this.crearMenuOpciones());

		this.frame.getContentPane().add(this.crearPanelVacio(),VentanaEmpleado.VACIO);
		this.frame.getContentPane().add(this.crearPanelNuevoPrestamo(),VentanaEmpleado.PRESTAMO_NUEVO);
		this.frame.getContentPane().add(this.crearPanelGestionCuotas(),VentanaEmpleado.PRESTAMO_CUOTAS);
		this.frame.getContentPane().add(this.crearPanelClientesMorosos(),VentanaEmpleado.PRESTAMO_MOROSOS);
		
		logger.debug("Se registran los listeners.");
		this.registrarEventos();			
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
		
		this.getOpcionPrestamoNuevo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de consulta de saldo");
				controlador.mostrarPrestamoNuevo();
			};
		});
		
		this.getOpcionPrestamoGestionarCuotas().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de extracción");
				controlador.mostrarPrestamoGestionCuotas();
			};
		});
		
		this.getOpcionPrestamoClientesMorosos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ejecuta el listener de transferencia");
				controlador.mostrarPrestamoClientesMorosos();
			};
		});			
		
		/*
		 * 
		 * Nuevo Prestamo
		 * 
		 */
		this.getPrestamoNuevoSeleccionarCliente().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Selecciona el cliente");
				controlador.prestamoNuevoSeleccionaCliente((String) getPrestamoNuevoTipoDNI().getSelectedItem(),
															getPrestamoNuevoCampoDNI().getText());
			};
		});		
		
		this.getPrestamoNuevoCampoDNI().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Selecciona el cliente");
				controlador.prestamoNuevoSeleccionaCliente((String) getPrestamoNuevoTipoDNI().getSelectedItem(),
															getPrestamoNuevoCampoDNI().getText());
			};
		});

		this.getPrestamoNuevoCambiarCliente().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Solicita cambiar el monto.");
				controlador.prestamoNuevoCambiarCliente();
			};
		});		

		this.getPrestamoNuevoMonto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ingresa un monto para obtener información de un potencial prestamo");
				controlador.prestamoNuevoIngresaMonto(getPrestamoNuevoMonto().getText());
			};
		});		

		this.getPrestamoNuevoIngresarMonto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ingresa un monto para obtener información de un potencial prestamo");
				controlador.prestamoNuevoIngresaMonto(getPrestamoNuevoMonto().getText());
			};
		});		
		
		this.getPrestamoNuevoCambiarMonto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Solicita cambiar el monto.");
				controlador.prestamoNuevoCambiarMonto();
			};
		});		
		
		this.getCmbPrestamoNuevoCantidadMeses().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getCmbPrestamoNuevoCantidadMeses().getSelectedItem() != null) {
					logger.info("Cambia la opcion del mes a {} con monto {}.",(String) getCmbPrestamoNuevoCantidadMeses().getSelectedItem(),getPrestamoNuevoMonto().getText());				
					controlador.prestamoNuevoObtenerTasaPrestamo(getPrestamoNuevoMonto().getText(), (String) getCmbPrestamoNuevoCantidadMeses().getSelectedItem());
				}
			};
		});

		this.getPrestamoNuevoIngresarPrestamo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Solicita la creación de un nuevo prestamo.");
				controlador.prestamoNuevoCrearPrestamo((String) getPrestamoNuevoTipoDNI().getSelectedItem(), getPrestamoNuevoCampoDNI().getText(), getPrestamoNuevoMonto().getText(), (String) getCmbPrestamoNuevoCantidadMeses().getSelectedItem());
			};
		});
		
		/*
		 * 
		 * Pago de Cuotas
		 * 
		 */
		this.getSeleccionarClientePagoCuota().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Selecciona el cliente");
				controlador.pagoCuotaSeleccionaCliente((String) getTipoDNIPagoCuota().getSelectedItem(),
															getCampoDNIPagoCuota().getText());
			};
		});		
		
		this.getCampoDNIPagoCuota().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Selecciona el cliente");
				controlador.pagoCuotaSeleccionaCliente((String) getTipoDNIPagoCuota().getSelectedItem(),
															getCampoDNIPagoCuota().getText());
			};
		});

		this.getCambiarClientePagoCuota().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Solicita cambiar el monto.");
				controlador.pagoCuotaCambiarCliente();
			};
		});	
		
		this.getConfirmarPagoCuota().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Solicita pagar cuotas.");
				controlador.pagoCuotaPagar((String) getTipoDNIPagoCuota().getSelectedItem(),
											getCampoDNIPagoCuota().getText(),
											getNroPrestamoPagoCuota(), 
											obtenerCuotasAPagar());				
			}
		});		
	}	

	private JMenuBar crearMenuOpciones() {
		JMenuBar barraDeMenu = new JMenuBar();
		JMenu menuOpciones=new JMenu("Menu");
		menuOpciones.setFont(new Font("Segoe UI", Font.BOLD, 17));
		barraDeMenu.add(menuOpciones);

		this.mntmPrestamoNuevo = new JMenuItem("Nuevo Prestamo");
		this.mntmPrestamoNuevo.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmPrestamoNuevo);

		this.mntmCuotas = new JMenuItem("Gestionar Cuotas de Prestamo");
		this.mntmCuotas.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmCuotas);

		this.mntmClientesMorosos = new JMenuItem("Listado de Clientes Morosos");
		this.mntmClientesMorosos.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmClientesMorosos);
		
		menuOpciones.add(new JSeparator());
		
		this.mntmCerrarSesion = new JMenuItem("Cerrar Sesion");
		this.mntmCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmCerrarSesion);

		this.mntmSalir = new JMenuItem("Salir");
		this.mntmSalir.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuOpciones.add(this.mntmSalir);
		
		return barraDeMenu;		
	}
	
	private JPanel crearPanelBotoneraVolver() {

		JPanel panelBotonera = new JPanel();		
		panelBotonera.setLayout(new FlowLayout(FlowLayout.RIGHT));

	
		panelBotonera.add(lblEmpleado);
		
		JButton btnVolverMenuOpciones = new JButton("Cerrar");
		panelBotonera.add(btnVolverMenuOpciones);
		
		btnVolverMenuOpciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(VentanaEmpleado.VACIO);
			}
		});
		
		return panelBotonera;
	}		

	private Component crearPanelVacio() {
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.setVisible(false);		
		
		return panelPpal;			
	}

	private Component crearPanelNuevoPrestamo() {
		
		JPanel panelCreacionPrestamos = new JPanel();
		panelCreacionPrestamos.setLayout(new GridLayout(2, 2, 2, 2));
		
		JPanel panelDNI = new JPanel();
		panelDNI.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCreacionPrestamos.add(panelDNI);
		panelDNI.setLayout(new GridLayout(2, 1, 0, 0));
		this.cmbPrestamoNuevoTipoDNI = new JComboBox<String>();

		JPanel panelTipoDNI = new JPanel();
		JLabel lblTipoDni = new JLabel("Tipo Documento:");
		panelTipoDNI.add(lblTipoDni);
		panelTipoDNI.add(this.cmbPrestamoNuevoTipoDNI);

		this.txtPrestamoNuevoDNI = new JTextField();
		txtPrestamoNuevoDNI.setColumns(10);		

		JPanel panelNumeroDNI = new JPanel();
		JLabel lblNumeroDNI = new JLabel("DNI:");
		panelNumeroDNI.add(lblNumeroDNI);
		panelNumeroDNI.add(this.txtPrestamoNuevoDNI);
		
		this.btnPrestamoNuevoSeleccionarCliente = new JButton("Seleccionar");
		this.btnPrestamoNuevoCambiarCliente = new JButton("Cambiar");
		this.btnPrestamoNuevoCambiarCliente.setEnabled(false);
		
		JPanel panelSeleccionarCliente = new JPanel();
		panelSeleccionarCliente.add(this.btnPrestamoNuevoSeleccionarCliente);
		panelSeleccionarCliente.add(this.btnPrestamoNuevoCambiarCliente);

		JPanel panelIngresoDNI = new JPanel();
		panelDNI.add(panelIngresoDNI);
		panelIngresoDNI.setLayout(new GridLayout(3, 1, 0, 0));
		panelIngresoDNI.add(panelTipoDNI);
		panelIngresoDNI.add(panelNumeroDNI);
		panelIngresoDNI.add(panelSeleccionarCliente);
		
		/*
		 * 
		 * Panel sobre información del prestamo: Tasa, interes, valor de cuota
		 * 
		 */
		
		JPanel panelInfoPrestamos = new JPanel();
		panelInfoPrestamos.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCreacionPrestamos.add(panelInfoPrestamos);
		panelInfoPrestamos.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInformacionPrestamo = new JLabel("Informacion Prestamo");
		lblInformacionPrestamo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacionPrestamo.setFont(new Font("Arial", Font.BOLD, 15));
		panelInfoPrestamos.add(lblInformacionPrestamo, BorderLayout.NORTH);
		
		JPanel panelInformacionPrestamo = new JPanel();
		panelInfoPrestamos.add(panelInformacionPrestamo, BorderLayout.CENTER);
		panelInformacionPrestamo.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel panelMonto = new JPanel();
		panelInformacionPrestamo.add(panelMonto);
		
		JLabel lblMonto = new JLabel("Monto:");
		panelMonto.add(lblMonto);
		
		this.txtPrestamoNuevoMonto = new JTextField();
		this.txtPrestamoNuevoMonto.setEditable(false);
		panelMonto.add(this.txtPrestamoNuevoMonto);
		this.txtPrestamoNuevoMonto.setColumns(10);
		
		this.btnPrestamoNuevoIngresarMonto = new JButton("Ingresar");
		this.btnPrestamoNuevoIngresarMonto.setEnabled(false);
		panelMonto.add(this.btnPrestamoNuevoIngresarMonto);
		
		this.btnPrestamoNuevoCambiarMonto = new JButton("Cambiar");
		this.btnPrestamoNuevoCambiarMonto.setEnabled(false);
		panelMonto.add(this.btnPrestamoNuevoCambiarMonto);
		
		JPanel panelMeses = new JPanel();
		panelInformacionPrestamo.add(panelMeses);
		
		JLabel lblCantidadDeMeses = new JLabel("Cantidad de Meses:");
		panelMeses.add(lblCantidadDeMeses);
		
		this.cmbPrestamoNuevoCantidadMeses = new JComboBox<String>();
		this.cmbPrestamoNuevoCantidadMeses.setEnabled(false);
		panelMeses.add(this.cmbPrestamoNuevoCantidadMeses);
		
		JPanel panelTasa = new JPanel();
		panelInformacionPrestamo.add(panelTasa);
		panelTasa.setLayout(null);
		
		JLabel lblTasa = new JLabel("Tasa:");
		lblTasa.setBounds(137, 13, 33, 16);
		panelTasa.add(lblTasa);
		
		this.lblPrestamoNuevoTasa = new JLabel("");
		this.lblPrestamoNuevoTasa.setBounds(182, 13, 85, 16);
		panelTasa.add(this.lblPrestamoNuevoTasa);
		
		JPanel panelInteres = new JPanel();
		panelInformacionPrestamo.add(panelInteres);
		panelInteres.setLayout(null);
		
		JLabel lblInteres = new JLabel("Interes:");
		lblInteres.setBounds(135, 13, 45, 16);
		panelInteres.add(lblInteres);
		
		this.lblPrestamoNuevoInteres = new JLabel("");
		this.lblPrestamoNuevoInteres.setBounds(192, 13, 87, 16);
		panelInteres.add(this.lblPrestamoNuevoInteres);
		
		JPanel panelValorCuota = new JPanel();
		panelInformacionPrestamo.add(panelValorCuota);
		panelValorCuota.setLayout(null);
		
		JLabel lblValorCuota = new JLabel("Valor Cuota:");
		lblValorCuota.setBounds(120, 13, 78, 16);
		panelValorCuota.add(lblValorCuota);
		
		this.lblPrestamoNuevoValorCuota = new JLabel("");
		this.lblPrestamoNuevoValorCuota.setBounds(210, 13, 90, 16);
		panelValorCuota.add(this.lblPrestamoNuevoValorCuota);

		/*
		 * 
		 * Panel sobre información del cliente
		 * 
		 */
	
		JPanel panelInfoCliente = new JPanel();
		panelInfoCliente.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCreacionPrestamos.add(panelInfoCliente);
		panelInfoCliente.setLayout(null);
		
		JLabel lblDatosCliente = new JLabel("Datos Cliente");
		lblDatosCliente.setFont(new Font("Arial", Font.BOLD, 15));
		lblDatosCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosCliente.setBounds(157, 13, 102, 14);
		panelInfoCliente.add(lblDatosCliente);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(12, 40, 56, 16);
		panelInfoCliente.add(lblNombre);
		
		this.lblPrestamoNuevoNombre = new JLabel("");
		this.lblPrestamoNuevoNombre.setBounds(80, 40, 322, 16);
		panelInfoCliente.add(this.lblPrestamoNuevoNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(12, 69, 56, 16);
		panelInfoCliente.add(lblApellido);
		
		this.lblPrestamoNuevoApellido = new JLabel("");
		this.lblPrestamoNuevoApellido.setBounds(80, 69, 322, 16);
		panelInfoCliente.add(this.lblPrestamoNuevoApellido);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setBounds(12, 98, 130, 16);
		panelInfoCliente.add(lblFechaDeNacimiento);
		
		this.lblPrestamoNuevoFechaNacimiento = new JLabel("");
		this.lblPrestamoNuevoFechaNacimiento.setBounds(147, 98, 255, 16);
		panelInfoCliente.add(this.lblPrestamoNuevoFechaNacimiento);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(12, 127, 56, 16);
		panelInfoCliente.add(lblTelefono);
		
		this.lblPrestamoNuevoTel = new JLabel("");
		this.lblPrestamoNuevoTel.setBounds(86, 127, 316, 16);
		panelInfoCliente.add(this.lblPrestamoNuevoTel);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(12, 156, 68, 16);
		panelInfoCliente.add(lblDireccion);
		
		this.lblPrestamoNuevoDireccion = new JLabel("");
		this.lblPrestamoNuevoDireccion.setBounds(80, 156, 320, 16);
		panelInfoCliente.add(this.lblPrestamoNuevoDireccion);
		
		/*
		 * 
		 * Boton de ingresar prestamo
		 * 
		 */
		
		JPanel panelIngresoPrestamo = new JPanel();
		panelIngresoPrestamo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCreacionPrestamos.add(panelIngresoPrestamo);
		panelIngresoPrestamo.setLayout(null);
		
		this.btnIngresarPrestamo = new JButton("Ingresar Prestamo");
		this.btnIngresarPrestamo.setEnabled(false);
		this.btnIngresarPrestamo.setFont(new Font("Arial", Font.BOLD, 15));
		this.btnIngresarPrestamo.setBounds(127, 99, 172, 45);
		panelIngresoPrestamo.add(this.btnIngresarPrestamo);
		
		/*
		 *
		 * 
		 */
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panelCreacionPrestamos, BorderLayout.CENTER);
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;		
	}
	
	private void limpiarTasaInteresCuota() {
		lblPrestamoNuevoValorCuota.setText("");
		lblPrestamoNuevoInteres.setText("");
		lblPrestamoNuevoTasa.setText("");
	}

	private Component crearPanelGestionCuotas() {
		/*
		 * Seleccion Cliente
		 */
		JPanel panelIngresoDNI2 = new JPanel();
		JLabel lblTipoDni2 = new JLabel("Tipo Documento:");
		panelIngresoDNI2.add(lblTipoDni2);
		this.cmbPagoCuotasTipoDNI = new JComboBox<String>();
		panelIngresoDNI2.add(this.cmbPagoCuotasTipoDNI);
		
		JPanel panelNumeroDNI2 = new JPanel();
		JLabel lblNumeroDNI2 = new JLabel("DNI:");
		panelNumeroDNI2.add(lblNumeroDNI2);
		
		this.txtPagoCuotasDNI = new JTextField();
		panelNumeroDNI2.add(this.txtPagoCuotasDNI);
		this.txtPagoCuotasDNI.setColumns(10);
		
		JPanel panelSeleccionarCliente2 = new JPanel();
		this.btnPagoCuotaSeleccionarCliente = new JButton("Seleccionar");
		panelSeleccionarCliente2.add(this.btnPagoCuotaSeleccionarCliente);
		
		this.btnPagoCuotaCambiarCliente = new JButton("Cambiar");
		this.btnPagoCuotaCambiarCliente.setEnabled(false);
		panelSeleccionarCliente2.add(this.btnPagoCuotaCambiarCliente);
		
		JPanel panelDNI2 = new JPanel();
		panelDNI2.setBorder(null);
		panelDNI2.setLayout(new GridLayout(3, 1, 0, 0));
		
		panelDNI2.add(panelIngresoDNI2);
		panelDNI2.add(panelNumeroDNI2);
		panelDNI2.add(panelSeleccionarCliente2);

		JPanel panelIngresoCliente2 = new JPanel();
		panelIngresoCliente2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelIngresoCliente2.setLayout(new GridLayout(1, 1, 0, 0));
		panelIngresoCliente2.add(panelDNI2);
		
		/*
		 * 
		 * Panel sobre información del cliente
		 * 
		 */				
		
		JPanel panelInfoCliente2 = new JPanel();
		panelInfoCliente2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoCliente2.setLayout(null);
		
		JLabel lblDatosCliente2 = new JLabel("Datos Cliente");
		lblDatosCliente2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosCliente2.setFont(new Font("Arial", Font.BOLD, 15));
		lblDatosCliente2.setBounds(157, 13, 102, 14);
		panelInfoCliente2.add(lblDatosCliente2);
		
		JLabel lblNombre2 = new JLabel("Nombre:");
		lblNombre2.setBounds(12, 40, 56, 16);
		panelInfoCliente2.add(lblNombre2);
		
		this.lblPagoCuotaNombre = new JLabel("");
		this.lblPagoCuotaNombre.setBounds(80, 40, 322, 16);
		panelInfoCliente2.add(this.lblPagoCuotaNombre);
		
		JLabel lblApellido2 = new JLabel("Apellido:");
		lblApellido2.setBounds(12, 69, 56, 16);
		panelInfoCliente2.add(lblApellido2);
		
		this.lblPagoCuotaApellido = new JLabel("");
		this.lblPagoCuotaApellido.setBounds(80, 69, 322, 16);
		panelInfoCliente2.add(this.lblPagoCuotaApellido);
		
		JLabel lblFN2 = new JLabel("Fecha de Nacimiento:");
		lblFN2.setBounds(12, 98, 130, 16);
		panelInfoCliente2.add(lblFN2);
		
		this.lblPagoCuotaFechaNacimiento = new JLabel("");
		this.lblPagoCuotaFechaNacimiento.setBounds(147, 98, 255, 16);
		panelInfoCliente2.add(this.lblPagoCuotaFechaNacimiento);
		
		JLabel lblTel2 = new JLabel("Telefono:");
		lblTel2.setBounds(12, 127, 56, 16);
		panelInfoCliente2.add(lblTel2);
		
		this.lblPagoCuotaTel = new JLabel("");
		this.lblPagoCuotaTel.setBounds(86, 127, 316, 16);
		panelInfoCliente2.add(this.lblPagoCuotaTel);
		
		JLabel lblDireccion2 = new JLabel("Direccion:");
		lblDireccion2.setBounds(12, 156, 68, 16);
		panelInfoCliente2.add(lblDireccion2);
		
		this.lblPagoCuotaDireccion = new JLabel("");
		this.lblPagoCuotaDireccion.setBounds(80, 156, 320, 16);
		panelInfoCliente2.add(this.lblPagoCuotaDireccion);
		
		/*
		 * 
		 * Panel sobre información del prestamo: Tasa, interes, valor de cuota
		 * 
		 */
		
		JPanel panelInfoPrestamos = new JPanel();
		panelInfoPrestamos.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelInfoPrestamos.setLayout(null);
		
		JLabel lblInformacionPrestamo = new JLabel("Informacion Prestamo");
		lblInformacionPrestamo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacionPrestamo.setFont(new Font("Arial", Font.BOLD, 15));
		lblInformacionPrestamo.setBounds(100, 13, 180, 14);
		panelInfoPrestamos.add(lblInformacionPrestamo);
		
		JLabel lblPrestamoNro = new JLabel("Prestamo Nro:");
		lblPrestamoNro.setBounds(12, 40, 90, 16);		
		this.lblPagoCuotaPrestamoNro = new JLabel("");
		this.lblPagoCuotaPrestamoNro.setBounds(106, 40, 322, 16);
		panelInfoPrestamos.add(lblPrestamoNro);
		panelInfoPrestamos.add(this.lblPagoCuotaPrestamoNro);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(12, 70, 50, 16);		
		this.lblPagoCuotaMonto = new JLabel("");
		this.lblPagoCuotaMonto.setBounds(80, 70, 322, 16);
		panelInfoPrestamos.add(lblMonto);
		panelInfoPrestamos.add(this.lblPagoCuotaMonto);

		JLabel lblCantidadDeMeses = new JLabel("Meses:");
		lblCantidadDeMeses.setBounds(12, 100, 50, 16);		
		this.lblPagoCuotaCantidadDeMeses = new JLabel("");
		this.lblPagoCuotaCantidadDeMeses.setBounds(66, 100, 322, 16);
		panelInfoPrestamos.add(lblCantidadDeMeses);
		panelInfoPrestamos.add(this.lblPagoCuotaCantidadDeMeses);

		JLabel lblTasa = new JLabel("Tasa:");
		lblTasa.setBounds(12, 130, 50, 16);
		this.lblPagoCuotaTasa = new JLabel("");
		this.lblPagoCuotaTasa.setBounds(66, 130, 322, 16);
		panelInfoPrestamos.add(lblTasa);
		panelInfoPrestamos.add(this.lblPagoCuotaTasa);		

		JLabel lblInteres = new JLabel("Interes:");
		lblInteres.setBounds(12, 160, 56, 16);
		this.lblPagoCuotaInteres = new JLabel("");
		this.lblPagoCuotaInteres.setBounds(72, 160, 322, 16);
		panelInfoPrestamos.add(lblInteres);
		panelInfoPrestamos.add(this.lblPagoCuotaInteres);
			
		JLabel lblValorCuota = new JLabel("Valor Cuota:");
		lblValorCuota.setBounds(12, 190, 80, 16);
		this.lblPagoCuotaValorCuota = new JLabel("");
		this.lblPagoCuotaValorCuota.setBounds(96, 190, 322, 16);
		panelInfoPrestamos.add(lblValorCuota);
		panelInfoPrestamos.add(this.lblPagoCuotaValorCuota);

		/*
		 * 
		 * Boton de confirmar pago
		 * 
		 */		
		this.btnConfirmarPago = new JButton("Confirmar Pago");
		this.btnConfirmarPago.setBounds(144, 185, 130, 28);
		this.btnConfirmarPago.setEnabled(false);
		panelInfoCliente2.add(btnConfirmarPago);

		/*
		 * 
		 * Tabla de pagos
		 * 
		 */
		modeloTablaPagos = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
				
				if (column == 0) {

					String pago = (String) this.getValueAt(row, this.findColumn(VentanaEmpleado.TABLA_PAGOS_FECHA_PAGO));
					
					if (pago == null) {
						return true;
					}
				}
				
		        return false;
		    }
			
			public Class<?> getColumnClass(int column){
				if(column==0)
				{
					return Boolean.class;
				}
				else
					return String.class;
		      }
		};
		
		modeloTablaPagos.addColumn(VentanaEmpleado.TABLA_PAGOS_SELECCIONAR);
		modeloTablaPagos.addColumn(VentanaEmpleado.TABLA_PAGOS_NRO_PAGO);
		modeloTablaPagos.addColumn(VentanaEmpleado.TABLA_PAGOS_FECHA_VENCIMIENTO);
		modeloTablaPagos.addColumn(VentanaEmpleado.TABLA_PAGOS_FECHA_PAGO);
		
		JTable tablaPagos = new JTable(modeloTablaPagos){
			
			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
		    {
				Component c = super.prepareRenderer(renderer, row, column);
	            Color foreground, background;
	            Color seleccion = new Color(219, 219, 219);
	            Color color = new Color(255, 232, 219);

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
	            
	            /*
	             * Deshabilitar los checkbox si no son editables
	             */
	            
	            if (column == 0) {
            		c.setEnabled(modeloTablaPagos.isCellEditable(row, column));
	            }
	            
	            return c;
		    }
		};
		
		tablaPagos.getTableHeader().setReorderingAllowed(true);
		
		JPanel panelListaPagos = new JPanel();
		panelListaPagos.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelListaPagos.setLayout(new GridLayout(1, 1, 0, 0));
		panelListaPagos.add(new JScrollPane(tablaPagos));


		/*
		 * Panel central
		 */
		JPanel panelPagoCuotas = new JPanel();
		panelPagoCuotas.setLayout(new GridLayout(2, 2, 2, 2));
		panelPagoCuotas.add(panelIngresoCliente2);
		panelPagoCuotas.add(panelInfoPrestamos);
		panelPagoCuotas.add(panelInfoCliente2);
		panelPagoCuotas.add(panelListaPagos);

		
		/*
		 * Panel principal
		 */
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panelPagoCuotas, BorderLayout.CENTER);		
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;		
	}
	
	private Component crearPanelClientesMorosos() {
		
		modeloTablaMorosos = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_NRO_CLIENTE);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_TIPO_DOC);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_NRO_DOC);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_NOMBRE);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_APELLIDO);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_NRO_PRESTAMO);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_MONTO);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_CANTIDAD_MESES);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_VALOR_CUOTA);
		modeloTablaMorosos.addColumn(VentanaEmpleado.TABLA_MOROSOS_CUOTAS_ATRASADAS);
		
		//Se agrega el modelo a la tabla
		JTable tablaMorosos = new JTable(modeloTablaMorosos) {
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			    {
					Component c = super.prepareRenderer(renderer, row, column);
		            Color foreground, background;
		            Color color = new Color(255, 232, 219);
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
		tablaMorosos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaMorosos.setAutoCreateRowSorter(true);
						
		JPanel panelMorosos = new JPanel();
		panelMorosos.setLayout(new GridLayout(1, 1, 0, 0));
		panelMorosos.add(new JScrollPane(tablaMorosos));		
		
		JPanel panelPpal = new JPanel();
		panelPpal.setLayout(new BorderLayout(0, 0));
		panelPpal.add(panelMorosos, BorderLayout.CENTER);		
		panelPpal.add(this.crearPanelBotoneraVolver(), BorderLayout.SOUTH);		
		panelPpal.setVisible(false);		
		
		return panelPpal;		
	}

}
