
package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PanelMenuUsuario extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel tituloMenu;
    private JLabel tituloSeleccion;
    private JButton btnSuscribirse;
    private TextField txtBusqueda;
    private JButton btnBusqueda;
    private JButton btnIngresar;
    private JButton btnVerListas;
    
    //
    //CONSTANTES
    //
    private static int INGRESO = 0;
    private static final String SUSCRIBIRSE = "Suscribirse";
    private static final String INGRESAR = "Ingresar";
    private static final String BUSQUEDA = "Busqueda";
    private static final String LISTA = "Lista";
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelMenuUsuario(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setBackground(new Color(153, 204, 153));
        setSize(500, 450);
        setLayout(null);
        
        tituloMenu = new JLabel("SpotDeezer");
        tituloMenu.setFont(new Font("Tahoma", Font.PLAIN, 30));
        tituloMenu.setForeground(Color.BLACK);
        tituloMenu.setBounds(167, 29, 198, 80);
        add(tituloMenu);
        
        tituloSeleccion = new JLabel("Seleccione una opción");
        tituloSeleccion.setFont(new Font("Tahoma", Font.BOLD, 15));
        tituloSeleccion.setForeground(Color.WHITE);
	tituloSeleccion.setBounds(10, 93, 198, 80);
        add(tituloSeleccion);
        
        btnBusqueda = new JButton("Buscar Canción");
        btnBusqueda.setBackground(Color.LIGHT_GRAY);
	btnBusqueda.setBounds(139, 228, 212, 29);
        btnBusqueda.setActionCommand(BUSQUEDA);
        btnBusqueda.addActionListener(this);
        add(btnBusqueda);
        
        btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setBackground(Color.LIGHT_GRAY);
        btnIngresar.setBounds(30, 307, 212, 29);
        btnIngresar.setActionCommand(INGRESAR);
        btnIngresar.addActionListener(this);
        add(btnIngresar);
        
        
        btnSuscribirse = new JButton("Nuevo Usuario");
        btnSuscribirse.setBackground(Color.LIGHT_GRAY);
	btnSuscribirse.setBounds(245, 307, 212, 29);
        btnSuscribirse.setActionCommand(SUSCRIBIRSE);
        btnSuscribirse.addActionListener(this);
        add(btnSuscribirse);
        
        btnVerListas = new JButton("Listas de Reproducción");
        btnVerListas.setBackground(Color.LIGHT_GRAY);
        btnVerListas.setBounds(139, 258, 212, 29);
        btnVerListas.setActionCommand(LISTA);
        btnVerListas.addActionListener(this);
        add(btnVerListas);
        
        txtBusqueda = new TextField();
	txtBusqueda.setBounds(139, 203, 212, 20);
	add(txtBusqueda);
        
    }
    
    //
    //MÉTODOS
    //
    public String getTxtBusqueda(){
        return this.txtBusqueda.getText();
    }
    
    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(SUSCRIBIRSE)){
            
            interfaz.crearPanelSuscripcion();
            
        }else if(evento.getActionCommand().equalsIgnoreCase(BUSQUEDA)){
            
            if(INGRESO == 0){
                JOptionPane.showMessageDialog(null, "No hay nigún usuario conectado", "Inicio Sesión", 1);
            }else if(getTxtBusqueda().isEmpty()){
                JOptionPane.showMessageDialog(null, "Digite la busqueda", "Realizar Búsqueda", 1);
              
            }else{
                interfaz.crearPanelBusqueda();
            }

        }else if(evento.getActionCommand().equalsIgnoreCase(INGRESAR)){
            INGRESO++;
            String usuario = JOptionPane.showInputDialog("Ingrese su usuario", null);
            interfaz.activarUsuario(usuario);
            
        }else if(evento.getActionCommand().equalsIgnoreCase(LISTA)){
            if(INGRESO == 0){
                JOptionPane.showMessageDialog(null, "No hay nigún usuario conectado", "Inicio Sesión", 1);
                
            }else{
                interfaz.crearPanelListaUsuario();
            }
        }
    }
}
