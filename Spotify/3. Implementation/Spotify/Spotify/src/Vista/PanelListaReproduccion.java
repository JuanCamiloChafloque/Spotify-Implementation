
package Vista;

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


public class PanelListaReproduccion extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel lblTitulo;
    private JScrollPane barraArrastre;
    private JTable listaListas;
    private JButton btnCrear;
    private JButton btnDuracion;
    private JButton btnCantidad;
    private JButton btnAgregar;
    private String listaSeleccionada;
    
    //
    //CONSTANTES
    //
    private static final String CREAR = "Crear";
    private static final String DURACION = "Duracion";
    private static final String CANTIDAD = "Cantidad";
    private static final String AGREGAR = "Agregar";
    
    //
    //RELACIONES
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelListaReproduccion(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setBackground(new Color(153, 204, 153));
        setSize(500, 400);
        setLayout(new BorderLayout());

        lblTitulo = new JLabel("Resultados Listas Reproducción");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(lblTitulo);
        
        btnCrear = new JButton("Crear Lista");
        btnCrear.setActionCommand(CREAR);
        btnCrear.addActionListener(this);
        
        btnDuracion = new JButton("Duración Lista");
        btnDuracion.setActionCommand(DURACION);
        btnDuracion.addActionListener(this);
        
        btnCantidad = new JButton("Cantidad");
        btnCantidad.setActionCommand(CANTIDAD);
        btnCantidad.addActionListener(this);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.setActionCommand(AGREGAR);
        btnAgregar.addActionListener(this);
        
        JPanel panBotones = new JPanel();
        panBotones.setLayout(new GridLayout(1,4));
        panBotones.add(btnDuracion);
        panBotones.add(btnCrear);
        panBotones.add(btnAgregar);
        panBotones.add(btnCantidad);
        
        crearListasReproduccion();
        
        add(panTitulo, BorderLayout.NORTH);
        add(barraArrastre, BorderLayout.CENTER);
        add(panBotones, BorderLayout.SOUTH);
        
    }
    
    //
    //MÉTODOS
    //
    public String getListaSeleccionada(){
        return this.listaSeleccionada;
    }
    
    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(CREAR)){
            interfaz.crearPanelCrearLista();
        
        }else if(evento.getActionCommand().equalsIgnoreCase(DURACION)){
            
            try{
                TableModel modelo = listaListas.getModel();
                int fila = listaListas.getSelectedRow();
                listaSeleccionada = (String) modelo.getValueAt(fila, 0);
                String duracion = interfaz.getDuracionCancionesLista();
                JOptionPane.showMessageDialog(null, "La duración de canciones en la lista es: " + duracion, "Duración Canciones", 1);
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Seleccione una lista de reproducción", "Duración Lista", 1);
            }
            
        }else if(evento.getActionCommand().equalsIgnoreCase(CANTIDAD)){
            
            try{
                TableModel modelo = listaListas.getModel();
                int fila = listaListas.getSelectedRow();
                listaSeleccionada = (String) modelo.getValueAt(fila, 0);
                int cantidad = interfaz.getCantidadCancionesLista();
                JOptionPane.showMessageDialog(null, "La cantidad de canciones en la lista es: " + cantidad, "Cantidad Canciones", 1);
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Seleccione una lista de reproducción", "Cantidad Canciones", 1);
            }
            
        }else if(evento.getActionCommand().equalsIgnoreCase(AGREGAR)){
            
            try{
                TableModel modelo = listaListas.getModel();
                int fila = listaListas.getSelectedRow();
                listaSeleccionada = (String) modelo.getValueAt(fila, 0);
                interfaz.actualizarLista();
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Seleccione una lista de reproducción", "Cantidad Canciones", 1);
            }
            
        }
        
    }
    
    public void crearListasReproduccion(){
        
        List<Listareproduccion> listasReproduccion = interfaz.getListasReproduccionUsuario();
        String[] datosTabla = {"Nombre Lista", "Visibilidad", "Orden"};
        Vector<String> cabezera = new Vector<String>(Arrays.asList(datosTabla));
	Vector<String> datosLista;
	Vector<List<String>> datos = new Vector<List<String>>();
        
        for(Listareproduccion actual: listasReproduccion){
            
            datosLista = new Vector<String>();
            datosLista.add(actual.getNombrelista());
            datosLista.add(actual.getVisibilidad());
            datosLista.add(actual.getOrden());
            
            datos.add(datosLista);
        }
        
        barraArrastre = new JScrollPane();
        listaListas = new JTable();
        
        barraArrastre.setViewportView(listaListas);
        
        listaListas.setModel(new DefaultTableModel(datos, cabezera));

    }    
}
