
package Vista;

import Modelo.Cancion;
import Modelo.Listareproduccion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class PanelListaUsuario extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private String nombreCambio;
    private String ordenCambio;
    private String listaUsuarioSeleccionada;
    private JLabel titulo;
    private JScrollPane barraArrastre;
    private JTable listaBusqueda;
    private JButton btnModificarNombre;
    private JButton btnModificarOrden;
    
    //
    //CONSTANTES
    //
    private static final String MOD_NOMBRE = "Modificar Nombre";
    private static final String MOD_ORDEN = "Modificar Orden";
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelListaUsuario(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setBackground(new Color(153, 204, 153));
        setSize(500, 450);
        setLayout(new BorderLayout());
        
        
        titulo = new JLabel("Listas de Reproducción");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(titulo);
        
        btnModificarNombre = new JButton("Modificar Nombre");
        btnModificarNombre.setActionCommand(MOD_NOMBRE);
        btnModificarNombre.addActionListener(this);
        
        btnModificarOrden = new JButton("Modificar Orden");
        btnModificarOrden.setActionCommand(MOD_ORDEN);
        btnModificarOrden.addActionListener(this);
        
        JPanel panBoton = new JPanel();
        panBoton.setLayout(new GridLayout(1,2));
        panBoton.add(btnModificarNombre);
        panBoton.add(btnModificarOrden);
        
        crearListasReproduccion();
        
        add(panTitulo, BorderLayout.NORTH);
        add(barraArrastre, BorderLayout.CENTER);
        add(panBoton, BorderLayout.SOUTH);

    }
    //
    //MÉTODOS
    //
    public String getListaUsuarioSeleccionada(){
        return this.listaUsuarioSeleccionada;
    }
    
    public String getNombreCambio(){
        return this.nombreCambio;
    }

    public String getOrdenCambio(){
        return this.ordenCambio;
    }
    
    public void crearListasReproduccion(){
        
        List<Listareproduccion> listas = interfaz.getListasReproduccionUsuario();
        String[] datosTabla = {"Código", "Nombre", "Visibilidad", "Orden"};
        Vector<String> cabezera = new Vector<String>(Arrays.asList(datosTabla));
	Vector<String> datosListas;
	Vector<List<String>> datos = new Vector<List<String>>();
        
        for(Listareproduccion actual: listas){
            
            datosListas = new Vector<String>();
            datosListas.add(actual.getCodlista().toString());
            datosListas.add(actual.getNombrelista());
            datosListas.add(actual.getVisibilidad());
            datosListas.add(actual.getOrden());
            datos.add(datosListas);
        }
        
        barraArrastre = new JScrollPane();
        listaBusqueda = new JTable();
        
        barraArrastre.setViewportView(listaBusqueda);
        
        listaBusqueda.setModel(new DefaultTableModel(datos, cabezera));

    }

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(MOD_NOMBRE)){
            
            try{
                TableModel modelo = listaBusqueda.getModel();
                int fila = listaBusqueda.getSelectedRow();
                listaUsuarioSeleccionada = (String) modelo.getValueAt(fila, 0);
                nombreCambio = JOptionPane.showInputDialog(null, "Digite el nuevo nombre de la lista", "Modificar Lista", 1);
                interfaz.modificarNombreLista();
                interfaz.cerrarVentanaListaUsuario();
                
                
            }catch(Exception error){
                JOptionPane.showMessageDialog(null, "Seleccione una lista de reproducción", "Modificar Lista", 1);
            }
            
        }else if(evento.getActionCommand().equalsIgnoreCase(MOD_ORDEN)){
            
            try{
                TableModel modelo = listaBusqueda.getModel();
                int fila = listaBusqueda.getSelectedRow();
                listaUsuarioSeleccionada = (String) modelo.getValueAt(fila, 0);
                ordenCambio = JOptionPane.showInputDialog(null, "Digite el nuevo orden de la lista: Titulo, Genero, Album", "Modificar Lista", 1);
                interfaz.modificarOrdenLista();
                interfaz.cerrarVentanaListaUsuario();
                
                
            }catch(Exception error){
                JOptionPane.showMessageDialog(null, "Seleccione una lista de reproducción", "Modificar Lista", 1);
            }
  
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
