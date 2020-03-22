
package Vista;

import Modelo.Cancion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class PanelBusqueda extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private String cancionSeleccionada;
    private JLabel titulo;
    private JScrollPane barraArrastre;
    private JTable listaBusqueda;
    private JButton btnAgregar;
    private JButton btnReproducir;
    
    //
    //CONSTANTES
    //
    private static final String AGREGAR = "Agregar";
    private static final String REPRODUCIR = "Reproducir";
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelBusqueda(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setBackground(new Color(153, 204, 153));
        setSize(500, 450);
        setLayout(new BorderLayout());
        
        
        titulo = new JLabel("Resultados Búsqueda");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(titulo);
        
        btnAgregar = new JButton("Agregar a Lista");
        btnAgregar.setActionCommand(AGREGAR);
        btnAgregar.addActionListener(this);
        
        btnReproducir = new JButton("Reproducir");
        btnReproducir.setActionCommand(REPRODUCIR);
        btnReproducir.addActionListener(this);
        
        JPanel panBoton = new JPanel();
        panBoton.setLayout(new GridLayout(1,4));
        panBoton.add(new JLabel());
        panBoton.add(btnAgregar);
        panBoton.add(btnReproducir);
        panBoton.add(new JLabel());
        
        crearListaBusqueda();
        
        add(panTitulo, BorderLayout.NORTH);
        add(barraArrastre, BorderLayout.CENTER);
        add(panBoton, BorderLayout.SOUTH);

    }
    //
    //MÉTODOS
    //
    public String getCancionSeleccionada(){
        return this.cancionSeleccionada;
    }
    
    public void crearListaBusqueda(){
        
        List<Cancion> canciones = interfaz.getListaBusqueda(interfaz.getTxtBusqueda());
        String[] datosTabla = {"Titulo", "Album", "Interprete Principal"};
        Vector<String> cabezera = new Vector<String>(Arrays.asList(datosTabla));
	Vector<String> datosCanciones;
	Vector<List<String>> datos = new Vector<List<String>>();
        
        for(Cancion actual: canciones){
            
            datosCanciones = new Vector<String>();
            datosCanciones.add(actual.getTitulo());
            datosCanciones.add(actual.getCodalbum().getNombrealbum());
            datosCanciones.add(interfaz.getInterpretePrincipal(actual).getNombreartistico());
            datos.add(datosCanciones);
        }
        
        barraArrastre = new JScrollPane();
        listaBusqueda = new JTable();
        
        barraArrastre.setViewportView(listaBusqueda);
        
        listaBusqueda.setModel(new DefaultTableModel(datos, cabezera));

    }

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(AGREGAR)){
            
            try{
                TableModel modelo = listaBusqueda.getModel();
                int fila = listaBusqueda.getSelectedRow();
                cancionSeleccionada = (String) modelo.getValueAt(fila, 0);
                interfaz.crearPanelListaReproduccion();
                
            }catch(Exception error){
                JOptionPane.showMessageDialog(null, "Seleccione una canción", "Agregar a Lista", 1);
            }
            
        }else if(evento.getActionCommand().equalsIgnoreCase(REPRODUCIR)){
            
            try{
                TableModel modelo = listaBusqueda.getModel();
                int fila = listaBusqueda.getSelectedRow();
                cancionSeleccionada = (String) modelo.getValueAt(fila, 0);
                interfaz.guardarReproduccion();
                interfaz.crearPanelReproducir(); 
 
            }catch(Exception error){
                JOptionPane.showMessageDialog(null, "Seleccione una canción", "Agregar a Lista", 1);
            }
  
        }
    }
    
    
    
}
