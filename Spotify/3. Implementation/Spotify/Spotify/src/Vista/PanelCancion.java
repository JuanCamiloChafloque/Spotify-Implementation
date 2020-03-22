
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class PanelCancion extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel titulo;
    private JLabel lblCodigo;
    private JLabel lblInterprete;
    private JLabel lblInterpretePrin;
    private JLabel lblNomCancion;
    private JLabel lblGenero;
    private JLabel lblDuracion;
    private TextField txtCodigo;
    private TextField txtInterprete;
    private TextField txtInterpretePrin;
    private TextField txtNomCancion;
    private TextField txtDuracion;
    private JComboBox cmbGenero;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    //
    //CONSTANTES
    //
    private static final String ACEPTAR = "Aceptar";
    private static final String CANCELAR = "Cancelar";
    private static int VEZ = 0;
    
    //
    //RELACIONES
    //
    DialogoCancion dialogo;
    
    //
    //CONSTRUCTOR
    //
    public PanelCancion(DialogoCancion pDialogo){
        
        dialogo = pDialogo;
        
        setLayout(new BorderLayout());
        
        titulo = new JLabel("Agregar Canción");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(titulo);
        
        lblCodigo = new JLabel("Digite el Código de la Canción");
        lblInterprete = new JLabel("Digite el Interprete");
        lblInterpretePrin = new JLabel("Digite el Interprete Principal");
        lblNomCancion = new JLabel("Digite el Titulo de la Canción");
        lblGenero = new JLabel("Seleccione el Género de la Canción");
        lblDuracion = new JLabel("Digite la duración: MM:SS");
        
        txtCodigo = new TextField();
        actualizarPanelCancion();
        txtCodigo.setEditable(false);
        txtInterprete = new TextField();
        txtInterpretePrin = new TextField();
        txtNomCancion = new TextField();
        txtDuracion = new TextField();
        
        cmbGenero = new JComboBox();
        Vector<String> generos = dialogo.buscarGeneros();
        cmbGenero.setModel(new DefaultComboBoxModel<String>(generos));
        
        JPanel panDatos = new JPanel();
        panDatos.setLayout(new GridLayout(6,2));
        TitledBorder borde = new TitledBorder("Datos Canción");
        panDatos.setBorder(borde);
        
        panDatos.add(lblCodigo);
        panDatos.add(txtCodigo);
        panDatos.add(lblInterprete);
        panDatos.add(txtInterprete);
        panDatos.add(lblInterpretePrin);
        panDatos.add(txtInterpretePrin);
        panDatos.add(lblNomCancion);
        panDatos.add(txtNomCancion);
        panDatos.add(lblGenero);
        panDatos.add(cmbGenero);
        panDatos.add(lblDuracion);
        panDatos.add(txtDuracion);
        
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setActionCommand(ACEPTAR);
        btnAceptar.addActionListener(this);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand(CANCELAR);
        btnCancelar.addActionListener(this);
        
        JPanel panBotones = new JPanel();
        panBotones.setLayout(new GridLayout(1,2));
        panBotones.add(btnAceptar);
        panBotones.add(btnCancelar);
        
        
        add(panTitulo, BorderLayout.NORTH);
        add(panDatos, BorderLayout.CENTER);
        add(panBotones, BorderLayout.SOUTH);
        
    }
    
    //
    //MÉTODOS
    //  
    public String getTxtCodigo(){
        return this.txtCodigo.getText();
    }
    
    public void setTxtCodigo(TextField pCodigo){
        this.txtCodigo = pCodigo;
    }
    
    public String getTxtInterprete(){
        return this.txtInterprete.getText();
    }
    
    public String getTxtInterpretePrin(){
        return this.txtInterpretePrin.getText();
    }
    
    public String getTxtNomCancion(){
        return this.txtNomCancion.getText();
    }
    
    public String getTxtDuracion(){
        return this.txtDuracion.getText();
    }
    
    public String getCmbGenero(){
        return this.cmbGenero.getSelectedItem().toString();
    }
    

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(ACEPTAR)){
            
            if(getTxtNomCancion().isEmpty() || getTxtDuracion().isEmpty()){
                JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", 0);
            }else{
                boolean existeInterprete = dialogo.buscarInterprete(getTxtInterprete());
                boolean existePrin = dialogo.buscarInterprete(getTxtInterpretePrin());
                
                if(existeInterprete == false){
                    JOptionPane.showMessageDialog(null, "El artista digitado no existe", "Error", 0);
                }else if(existePrin == false){
                    JOptionPane.showMessageDialog(null, "El artista principal digitado no existe", "Error", 0);
                }else{
                    dialogo.guardarCancion(getTxtCodigo(), getTxtNomCancion(), getTxtInterprete(), getTxtInterpretePrin(), getTxtDuracion(), getCmbGenero());
                    VEZ++;
                    dialogo.setVisible(false);
                }
            }
        }else if(evento.getActionCommand().equalsIgnoreCase(CANCELAR)){
                
            dialogo.cancelarCambiosCancion();
            JOptionPane.showMessageDialog(null, "Los datos del album y canciones ingresados fueron cancelados", "Ingresar Datos", 1);
            JOptionPane.showMessageDialog(null, "Ingrese los datos nuevamente", "Ingresar Datos", 1);
            dialogo.mostrarPanelAdministrador();
            dialogo.setVisible(false);
        }
    }
    
    public void actualizarPanelCancion(){
        if(VEZ == 0){
            BigDecimal ultimoCodigo = dialogo.buscarUltimaCancion();
            setTxtCodigo(new TextField(ultimoCodigo.toString())); 
        }else{
            BigDecimal encontrado = dialogo.getUltimoCodigo();
            BigDecimal ultimo = encontrado.add(new BigDecimal(1));
            txtCodigo.setEditable(true);
            setTxtCodigo(new TextField(ultimo.toString()));
            txtCodigo.setEditable(false);
        }

    }
}
