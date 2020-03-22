
package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelMenuAdministrador extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel tituloMenu;
    private JLabel tituloSeleccion;
    private JButton btnAgregarArtista;
    private JButton btnAgregarAlbum;
    
    //
    //CONSTANTES
    //
    private static final String ARTISTA = "Artista";
    private static final String ALBUM = "Album";
   
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelMenuAdministrador(InterfazGrafica pInterfaz){
        
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
        tituloSeleccion.setBounds(10, 120, 198, 80);
        add(tituloSeleccion);
        
        btnAgregarArtista = new JButton("Agregar Artista");
        btnAgregarArtista.setBackground(Color.LIGHT_GRAY);
        btnAgregarArtista.setBounds(139, 295, 212, 38);
        btnAgregarArtista.setActionCommand(ARTISTA);
        btnAgregarArtista.addActionListener(this);
        add(btnAgregarArtista);
        
        btnAgregarAlbum = new JButton("Agregar Albúm/EP");
        btnAgregarAlbum.setBackground(Color.LIGHT_GRAY);
        btnAgregarAlbum.setBounds(141, 246, 212, 38);
        btnAgregarAlbum.setActionCommand(ALBUM);
        btnAgregarAlbum.addActionListener(this);
        add(btnAgregarAlbum);

    }
    
    //
    //MÉTODOS
    //

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(ARTISTA)){
            interfaz.crearPanelArtista();
        }else if (evento.getActionCommand().equalsIgnoreCase(ALBUM)){
            interfaz.crearPanelAlbum();
        }
    }
    
}
