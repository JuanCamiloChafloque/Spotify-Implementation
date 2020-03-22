
package Vista;

import Modelo.Cancion;
import Modelo.Cancionxinterprete;
import Modelo.Cancionxlista;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class PanelReproducir extends JPanel implements ActionListener{

    //
    //ATRIBUTOS
    //
    private JLabel lblTitulo;
    private JLabel lblNomCancion;
    private JLabel lblInterprete;
    private JLabel lblInterpretePrin;
    private JLabel lblAlbum;
    private JLabel lblDuracion;
    private TextField txtNomCancion;
    private TextField txtInterprete;
    private TextField txtInterpretePrin;
    private TextField txtAlbum;
    private TextField txtDuracion;
    private JButton btnReaccion;
    
    //
    //CONSTANTES
    //
    private static final String REACCION = "Reaccion";
    
    //
    //RELACIONES
    //
    private DialogoReproducir dialogo;
    
    //
    //CONSTRUCTOR
    //
    public PanelReproducir(DialogoReproducir pDialogo){
        
        dialogo = pDialogo;
        
        setLayout(new BorderLayout());
        
        lblTitulo = new JLabel("Datos Canción");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(lblTitulo);
        
        lblNomCancion = new JLabel("Titulo Canción: ");
        lblInterprete = new JLabel("Nombre Interprete: ");
        lblInterpretePrin = new JLabel("Nombre Interprete Principal: ");
        lblAlbum = new JLabel("Nombre: Albúm");
        lblDuracion = new JLabel("Duración: ");
        
        Cancion cancion = dialogo.getCancionSeleccionada(); 
        Cancionxinterprete cancionxinterprete = dialogo.getCancionInterpreteSeleccionada();
        
        txtNomCancion = new TextField(cancion.getTitulo());
        txtNomCancion.setEditable(false);
        txtInterprete = new TextField(cancionxinterprete.getInterprete().getNombreartistico());
        txtInterprete.setEditable(false);
        txtInterpretePrin = new TextField(cancionxinterprete.getCodprincipal().getNombreartistico());
        txtInterpretePrin.setEditable(false);
        txtAlbum = new TextField(cancion.getCodalbum().getNombrealbum());
        txtAlbum.setEditable(false);
        DateFormat formatoDuracion = new SimpleDateFormat("mm:ss");
        txtDuracion = new TextField(formatoDuracion.format(cancion.getDuracion()).toString());
        txtDuracion.setEditable(false);
        
        JPanel panDatos = new JPanel();
        panDatos.setLayout(new GridLayout(5,2));
        TitledBorder borde = new TitledBorder("Datos Canción");
        panDatos.setBorder(borde);
        
        panDatos.add(lblNomCancion);
        panDatos.add(txtNomCancion);
        panDatos.add(lblInterprete);
        panDatos.add(txtInterprete);
        panDatos.add(lblInterpretePrin);
        panDatos.add(txtInterpretePrin);
        panDatos.add(lblAlbum);
        panDatos.add(txtAlbum);
        panDatos.add(lblDuracion);
        panDatos.add(txtDuracion);
        
        
        boolean reaccion = dialogo.verificarLike();
        
        if(reaccion){
            btnReaccion = new JButton("No Me Gusta"); 
        }else{
            btnReaccion = new JButton("Me Gusta");
        }

        btnReaccion.addActionListener(this);
        btnReaccion.setActionCommand(REACCION);
        
        JPanel panBoton = new JPanel();
        panBoton.setLayout(new GridLayout(1,3));
        panBoton.add(new JLabel());
        panBoton.add(btnReaccion);
        panBoton.add(new JLabel());

        add(panTitulo, BorderLayout.NORTH);
        add(panDatos, BorderLayout.CENTER);
        add(panBoton, BorderLayout.SOUTH);

    }
    
    //
    //MÉTODOS
    //
    public void actionPerformed(ActionEvent evento) {
        
        if (evento.getActionCommand().equalsIgnoreCase(REACCION)){
            
            if(btnReaccion.getText().equalsIgnoreCase("Me Gusta")){
                
                dialogo.guardarLike();
                dialogo.setVisible(false);
            
            }else if(btnReaccion.getText().equalsIgnoreCase("No Me Gusta")){
                
                dialogo.eliminarLike();
                dialogo.setVisible(false);

            }
            
            
        }
        
    }
    
}
