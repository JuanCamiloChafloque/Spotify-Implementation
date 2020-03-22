
package Vista;

import Modelo.Usuarioc;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class PanelSuscripcion extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel titulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblNickname;
    private JLabel lblPais;
    private JLabel lblTipo;
    private JLabel lblTarjeta;
    private TextField txtCodigo;
    private TextField txtNombre;
    private TextField txtApellido;
    private TextField txtNickname;
    private TextField txtTarjeta;
    private JComboBox cmbPais;
    private JComboBox cmbTipo;
    private JButton btnAceptar;
    
    //
    //CONSTANTES
    //
    private static final String ACEPTAR = "Aceptar";
    private static final String TIPO = "Tipo";
    
    //
    //RELACIONES
    //
    private DialogoSuscripcion dialogo;
    
    //
    //CONSTRUCTOR
    //
    public PanelSuscripcion(DialogoSuscripcion pDialogo){
        
        dialogo = pDialogo;
        
        setLayout(new BorderLayout());
        
        titulo = new JLabel("Suscripción Usuario");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(titulo);
        
        JPanel panDatos = new JPanel();
        panDatos.setLayout(new GridLayout(7, 2));
        TitledBorder borde = new TitledBorder("Datos Suscripción");
        panDatos.setBorder(borde);
        
        lblCodigo = new JLabel("Código:");
        lblNombre = new JLabel("Digite su Nombre");
        lblApellido = new JLabel("Digite su Apellido");
        lblNickname = new JLabel("Digite su Nickname");
        lblPais = new JLabel("Digite su País");
        lblTipo = new JLabel("Digite el Tipo de Suscripción");
        lblTarjeta = new JLabel("Digite el Número de Tarjeta");
        lblTarjeta.setVisible(false);
        
        txtCodigo = new TextField();
        actualizarPanelUsuario();
        txtCodigo.setEditable(false);
        txtNombre = new TextField();
        txtApellido = new TextField();
        txtNickname = new TextField();
        txtTarjeta = new TextField();
        txtTarjeta.setVisible(false);
        
        cmbPais = new JComboBox();
        Vector <String> listaPaises = dialogo.buscarPaises();
        cmbPais.setModel(new DefaultComboBoxModel<String>(listaPaises));
        cmbTipo = new JComboBox();
        cmbTipo.setActionCommand(TIPO);
        cmbTipo.addActionListener(this);
        Vector <String> listaTipos = dialogo.buscarSuscripciones();
        cmbTipo.setModel(new DefaultComboBoxModel<String>(listaTipos));
        
        panDatos.add(lblCodigo);
        panDatos.add(txtCodigo);
        panDatos.add(lblNombre);
        panDatos.add(txtNombre);
        panDatos.add(lblApellido);
        panDatos.add(txtApellido);
        panDatos.add(lblNickname);
        panDatos.add(txtNickname);
        panDatos.add(lblPais);
        panDatos.add(cmbPais);
        panDatos.add(lblTipo);
        panDatos.add(cmbTipo);
        panDatos.add(lblTarjeta);
        panDatos.add(txtTarjeta);
        
        
        btnAceptar = new JButton("Suscribirse");
        btnAceptar.setActionCommand(ACEPTAR);
        btnAceptar.addActionListener(this);
        
        add(panTitulo, BorderLayout.NORTH);
        add(panDatos, BorderLayout.CENTER);
        add(btnAceptar, BorderLayout.SOUTH);

    }
    
    //
    //MÉTODOS
    //
    public String getTxtCodigo(){
        return txtCodigo.getText();
    }
    
    public void setTxtCodigo(TextField pTxtCodigo){
        this.txtCodigo = pTxtCodigo;
    }
    
    public String getTxtNombre(){
        return txtNombre.getText();
    }
    
    public String getTxtApellido(){
        return txtApellido.getText();
    }
    
    public String getTxtNickname(){
        return txtNickname.getText();
    }
    
    public String getTxtTarjeta(){
        return txtTarjeta.getText();
    }
    
    public void setTxtTarjeta(TextField pTarjeta){
        this.txtTarjeta = pTarjeta;
    }
    
    public String getCmbPais(){
        return cmbPais.getSelectedItem().toString();
    }
    
    public String getCmbTipo(){
        return cmbTipo.getSelectedItem().toString();
    }
    
    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(ACEPTAR)){
            
            if(getTxtNombre().isEmpty() || getTxtApellido().isEmpty() || getTxtNickname().isEmpty()){
                JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", 0);

            }
            else if(txtTarjeta.isVisible() && getTxtTarjeta().isEmpty()){
                JOptionPane.showMessageDialog(null, "Digite el número de la tarjeta de credito", "Error", 0);
                
            }else{
                
                List<Usuarioc> usuarios = dialogo.buscarUsuarios();
                boolean encontrado = false;
                
                for(Usuarioc actual: usuarios){
                    if(actual.getNickname().equalsIgnoreCase(getTxtNickname())){
                        encontrado = true;
                    }
                }
                
                if(txtTarjeta.isVisible() && getTxtTarjeta().toString().length() != 16){
                    JOptionPane.showMessageDialog(null, "El número de tarjeta no es válido", "Error", 0);
                }else if(encontrado){
                    JOptionPane.showMessageDialog(null, "Ya hay un usuario registrado con ese Nickname", "Error", 0);
                }else{
                    if(getCmbTipo().equalsIgnoreCase("GRATUITA")){
                        setTxtTarjeta(new TextField(""));
                    }
                    dialogo.guardarUsuario(getTxtCodigo(), getTxtApellido(), getTxtNickname(), getTxtNombre(), getTxtTarjeta(), getCmbPais(), getCmbTipo());
                    dialogo.setVisible(false);
                }

            }

        }else if(evento.getActionCommand().equalsIgnoreCase(TIPO)){
            if(getCmbTipo().equalsIgnoreCase("FAMILIAR") || getCmbTipo().equalsIgnoreCase("INDIVIDUAL")){
                txtTarjeta.setVisible(true);
                lblTarjeta.setVisible(true);

            }else{
                txtTarjeta.setVisible(false);
                lblTarjeta.setVisible(false);
            }
        }
    }
    
    public void actualizarPanelUsuario(){
        BigDecimal ultimoCodigo = dialogo.buscarUltimoUsuario();
        setTxtCodigo(new TextField(ultimoCodigo.toString()));
    }
}
