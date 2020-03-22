
package Vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class PanelCrearLista extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblVisbilidad;
    private JLabel lblOrden;
    private TextField txtCodigo;
    private TextField txtNombre;
    private JComboBox cmbOrden;
    private JComboBox cmbVisibilidad;
    private JButton btnAceptar;
    
    //
    //CONSTANTES
    //
    private static final String ACEPTAR = "Aceptar";
    
    //
    //RELACIONES
    private DialogoCrearLista dialogo;
    
    //
    //CONSTRUCTOR
    //
    public PanelCrearLista(DialogoCrearLista pDialogo){
        
        dialogo = pDialogo;
        
        setLayout(new BorderLayout());
        
        lblTitulo = new JLabel("Agregar Lista");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(lblTitulo);
        
        
        lblCodigo = new JLabel("Ingrese el Código de la Lista.");
        lblNombre = new JLabel("Ingrese el Nombre de la Lista.");
        lblVisbilidad = new JLabel("Seleccione la visibilidad de la Lista.");
        lblOrden = new JLabel("Seleccione el orden de la lista");
        
        txtCodigo = new TextField();
        actualizarPanelLista();
        txtCodigo.setEditable(false);
        txtNombre = new TextField();
        
        cmbOrden = new JComboBox();
        Vector <String> orden = new Vector<String>();
        orden.add("Titulo");
        orden.add("Genero");
        orden.add("Album");
        cmbOrden.setModel(new DefaultComboBoxModel<String>(orden));
        
        
        cmbVisibilidad = new JComboBox();
        Vector <String> visibilidad = new Vector<String>();
        visibilidad.add("Publica");
        visibilidad.add("Privada");
        cmbVisibilidad.setModel(new DefaultComboBoxModel<String>(visibilidad));
        
        JPanel panDatos = new JPanel();
        panDatos.setLayout(new GridLayout(4,2));
        TitledBorder borde = new TitledBorder("Datos Lista Reproducción");
        panDatos.setBorder(borde);
        
        panDatos.add(lblCodigo);
        panDatos.add(txtCodigo);
        panDatos.add(lblNombre);
        panDatos.add(txtNombre);
        panDatos.add(lblVisbilidad);
        panDatos.add(cmbVisibilidad);
        panDatos.add(lblOrden);
        panDatos.add(cmbOrden);
        
        btnAceptar = new JButton("Crear Lista");
        btnAceptar.setActionCommand(ACEPTAR);
        btnAceptar.addActionListener(this);
        
        add(panTitulo, BorderLayout.NORTH);
        add(panDatos, BorderLayout.CENTER);
        add(btnAceptar, BorderLayout.SOUTH);
        
    }
    
    //
    //MÉTODOS
    //
    public void setTxtCodigo(TextField pCodigo){
        this.txtCodigo = pCodigo;
    }
    
    public String getTxtCodigo(){
        return this.txtCodigo.getText();
    }
    
    public String getTxtNombre(){
        return this.txtNombre.getText();
    }
    
    public String getCmbOrden(){
        return this.cmbOrden.getSelectedItem().toString();
    }
    
    public String getCmbVisibilidad(){
        return this.cmbVisibilidad.getSelectedItem().toString();
    }
    
    
    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(ACEPTAR)){
            dialogo.guardarLista(getTxtCodigo(), getTxtNombre(), getCmbVisibilidad(), getCmbOrden());
            dialogo.cerrarPanelLista();
            dialogo.setVisible(false);
        }
        
    }
    
    public void actualizarPanelLista(){
        BigDecimal ultimoCodigo = dialogo.buscarUltimaLista();
        setTxtCodigo(new TextField(ultimoCodigo.toString()));
    }
    
}
