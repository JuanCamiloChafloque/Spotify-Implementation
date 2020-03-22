
package Vista;

import Modelo.Album;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class PanelAlbum extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel lblCodigo;
    private JLabel lblTitulo;
    private JLabel lblTipo;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblEmpresa;
    private TextField txtCodigo;
    private TextField txtNombre;
    private TextField txtFecha;
    private JComboBox cmbEmpresa;
    private JComboBox cmbTipo;
    private JButton btnCrearAlbum;
    private JButton btnAgregarCancion;
    private JButton btnVerAlbum;
    
    //
    //CONSTANTES
    //
    private static int INGRESO = 0;
    private static final String CREAR = "Crear";
    private static final String CANCION = "Cancion";
    private static final String VER = "Ver";
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelAlbum(InterfazGrafica pInterfaz) {
        
        interfaz = pInterfaz;
        
        setBackground(new Color(153, 204, 153));
        setSize(500, 450);
        setLayout(new BorderLayout());
        
      
        lblTitulo = new JLabel("Crear Albúm/EP");
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTitulo.setForeground(Color.BLACK);
        
        JPanel panTitulo = new JPanel();
        panTitulo.add(lblTitulo);
        
        JPanel panAlbum = new JPanel();
        panAlbum.setLayout(new GridLayout(5,2, 20, 20));
        TitledBorder borde = new TitledBorder("Datos Albúm");
        panAlbum.setBorder(borde);
        
        lblCodigo = new JLabel("Codigo del Album");
        lblTipo = new JLabel("Seleccione el Tipo");
        lblNombre = new JLabel("Digite el nombre del albúm");
        lblFecha = new JLabel("Digite la fecha: DD/MM/YYYY");
        lblEmpresa = new JLabel("Seleccione la empresa productora");
        
        txtCodigo = new TextField();
        actualizarPanelAlbum();
        txtCodigo.setEditable(false);
        txtNombre = new TextField();
        txtFecha = new TextField();
        
        cmbEmpresa = new JComboBox();
        
        cmbTipo = new JComboBox();
        Vector <String> listaAlbum = new Vector<String>();
        listaAlbum.add("Album");
        listaAlbum.add("EP");
        cmbTipo.setModel(new DefaultComboBoxModel<String>(listaAlbum));
        
        panAlbum.add(lblCodigo);
        panAlbum.add(txtCodigo);
        panAlbum.add(lblTipo);
        panAlbum.add(cmbTipo);
        panAlbum.add(lblNombre);
        panAlbum.add(txtNombre);
        panAlbum.add(lblFecha);
        panAlbum.add(txtFecha);
        panAlbum.add(lblEmpresa);
        panAlbum.add(cmbEmpresa);
        
        JPanel panBotones = new JPanel();
        panBotones.setLayout(new GridLayout(1,3));
        
        btnCrearAlbum = new JButton("Crear Albúm");
        btnCrearAlbum.setActionCommand(CREAR);
        btnCrearAlbum.addActionListener(this);
        btnAgregarCancion = new JButton("Agregar Canción");
        btnAgregarCancion.setVisible(false);
        btnAgregarCancion.setActionCommand(CANCION);
        btnAgregarCancion.addActionListener(this);
        btnVerAlbum = new JButton("Confirmar y ver");
        btnVerAlbum.setVisible(false);
        btnVerAlbum.setActionCommand(VER);
        btnVerAlbum.addActionListener(this);
        
        panBotones.add(btnAgregarCancion);
        panBotones.add(btnCrearAlbum);
        panBotones.add(btnVerAlbum);
        
        add(panTitulo, BorderLayout.NORTH);
        add(panAlbum, BorderLayout.CENTER);
        add(panBotones, BorderLayout.SOUTH);
        
    }
    
    //
    //MÉTODOS
    //
    public String getTxtCodigo(){
        return this.txtCodigo.getText();
    }
    
    public void setTxtCodigo(TextField pCodigo){
        this.txtCodigo =  pCodigo;
    }
    
    public String getTxtNombre(){
        return this.txtNombre.getText();
    }
    
    public void setTxtNombre(TextField pNombre){
        this.txtNombre = pNombre;
    }
    
    public String getTxtFecha(){
        return this.txtFecha.getText();
    }
    
    public void setTxtFecha(TextField pFecha){
        this.txtFecha = pFecha;
    }
    
    public String getCmbTipo(){
        return this.cmbTipo.getSelectedItem().toString();
    }
    
    public String getCmbEmpresa(){
        return this.cmbEmpresa.getSelectedItem().toString();
    }
    
    public void setModel(){
        Vector<String> listaEmpresas = interfaz.buscarEmpresas();
        cmbEmpresa.setModel(new DefaultComboBoxModel<String>(listaEmpresas));
    }

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(CREAR)){
            
            INGRESO++;
            
            if(INGRESO > 1){
                JOptionPane.showMessageDialog(null, "El albúm esta siendo creado. Ingrese las canciones del album", "Error", 0);
            
            }else if(getTxtNombre().isEmpty() || getTxtFecha().isEmpty()){
                JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", 0);
                
            }else{
                try {
                    interfaz.guardarAlbum(getTxtCodigo(), getTxtNombre(), getTxtFecha(), getCmbTipo(), getCmbEmpresa());
                    btnAgregarCancion.setVisible(true);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error en la fecha de lanzamiento", "Error", 0);
                }
            }
            

        }else if(evento.getActionCommand().equalsIgnoreCase(CANCION)){
            interfaz.crearPanelCancion();
            btnVerAlbum.setVisible(true);
            
            
        }else if(evento.getActionCommand().equalsIgnoreCase(VER)){
            INGRESO = 0;
            interfaz.confirmarCambiosAlbum();
            interfaz.confirmarCambiosCancion();
            interfaz.confirmarCambiosCanInterprete();
            interfaz.crearPanelCancionAlbum();
        }
    }
    
    public void actualizarPanelAlbum(){
        BigDecimal ultimoCodigo = interfaz.buscarUltimoAlbum();
        setTxtCodigo(new TextField(ultimoCodigo.toString()));
    }
    
}

