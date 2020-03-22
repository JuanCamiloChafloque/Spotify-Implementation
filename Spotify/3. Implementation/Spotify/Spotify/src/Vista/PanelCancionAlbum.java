
package Vista;

import Modelo.Album;
import Modelo.Cancion;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelCancionAlbum extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel lblTipo;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblEmpresa;
    private TextField txtTipo;
    private TextField txtNombre;
    private TextField txtFecha;
    private TextField txtEmpresa;
    private JScrollPane barraArrastre;
    private JTable listaCanciones;
    private JButton btnAceptar;
    
    //
    //CONSTANTES
    //
    private static final String ACEPTAR = "Aceptar";
    
    //
    //RELACIONES
    //
    DialogoCancionAlbum dialogo;
    
    //
    //CONSTRUCTOR
    //
    public PanelCancionAlbum(DialogoCancionAlbum pDialogo){
        
        dialogo = pDialogo;
        
        setLayout(new BorderLayout());
        
        JPanel panDatos = new JPanel();
        panDatos.setLayout(new GridLayout(4,2));
        TitledBorder borde = new TitledBorder("Datos Albúm");
        panDatos.setBorder(borde);
        
        lblTipo = new JLabel("Tipo: ");
        lblNombre = new JLabel("Nombre del Albúm: ");
        lblFecha = new JLabel("Fecha de Lanzamiento: ");
        lblEmpresa = new JLabel("Empresa Productora: ");
        
        Album album = dialogo.getAlbum();
        
        txtTipo = new TextField((album.getTipo()));
        txtNombre = new TextField(album.getNombrealbum());
        txtEmpresa = new TextField(album.getCodempresa().getNombreempresa());
        DateFormat formatoAnio = new SimpleDateFormat("dd/MM/yyyy");
        txtFecha = new TextField(formatoAnio.format(album.getFechalanzamiento()).toString());
        
        txtTipo.setEditable(false);
        txtNombre.setEditable(false);
        txtFecha.setEditable(false);
        txtEmpresa.setEditable(false);
        
        panDatos.add(lblTipo);
        panDatos.add(txtTipo);
        panDatos.add(lblNombre);
        panDatos.add(txtNombre);
        panDatos.add(lblFecha);
        panDatos.add(txtFecha);
        panDatos.add(lblEmpresa);
        panDatos.add(txtEmpresa);
        
        
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(this);
        btnAceptar.setActionCommand(ACEPTAR);
        
        crearListaCanciones();
        
        add(panDatos, BorderLayout.NORTH);
        add(barraArrastre, BorderLayout.CENTER);
        add(btnAceptar, BorderLayout.SOUTH);
    }   
      
    //
    //MÉTODOS
    //  
    public void setTxtTipo(TextField pTipo){
        this.txtTipo = pTipo;
        txtNombre.setEditable(false);
    }
    
    public void setTxtNombre(TextField pNombre){
        this.txtNombre = pNombre;
        txtNombre.setEditable(false);
    }
    
    public void setTxtFecha(TextField pFecha){
        this.txtFecha = pFecha;
        txtFecha.setEditable(false);
    }
    
    public void setTxtEmpresa(TextField pEmpresa){
        this.txtEmpresa = pEmpresa;
        txtEmpresa.setEditable(false);
        
    }
    
    
    public void crearListaCanciones(){
        
        List<Cancion> canciones = dialogo.getListaCanciones();
        String[] datosTabla = {"codCanción", "Titulo", "Duracion", "Genero"};
        Vector<String> cabezera = new Vector<String>(Arrays.asList(datosTabla));
	Vector<String> datosUsuarios;
	Vector<List<String>> datos = new Vector<List<String>>();
        
        for(Cancion actual: canciones){
            
            datosUsuarios = new Vector<String>();
            datosUsuarios.add(actual.getCodcancion().toString());
            datosUsuarios.add(actual.getTitulo());
            DateFormat formatoDuracion = new SimpleDateFormat("mm:ss");
            datosUsuarios.add(formatoDuracion.format(actual.getDuracion()).toString());
            datosUsuarios.add(actual.getCodgenero().getNombregenero());
            
            datos.add(datosUsuarios);
        }
        
        barraArrastre = new JScrollPane();
        listaCanciones = new JTable();
        
        barraArrastre.setViewportView(listaCanciones);
        
        listaCanciones.setModel(new DefaultTableModel(datos, cabezera));

    }

    @Override
    public void actionPerformed(ActionEvent evento) {
       
        if(evento.getActionCommand().equalsIgnoreCase(ACEPTAR)){
            
            dialogo.actualizarPanelAlbum();
            dialogo.mostrarPanelAdministrador();
            dialogo.setVisible(false);

        }
    }
}
