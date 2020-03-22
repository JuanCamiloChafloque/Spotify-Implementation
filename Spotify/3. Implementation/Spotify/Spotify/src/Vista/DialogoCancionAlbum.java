
package Vista;

import Modelo.Album;
import Modelo.Cancion;
import java.util.List;
import javax.swing.JDialog;


public class DialogoCancionAlbum extends JDialog{
    
    //
    //RELACIONES
    //
    InterfazGrafica interfaz;
    PanelCancionAlbum panelCancionAlbum;
    
    //
    //CONSTRUCTOR
    //
    public DialogoCancionAlbum(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setSize(450, 450);
	setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        
        panelCancionAlbum = new PanelCancionAlbum(this);
	add(panelCancionAlbum);
		
        setModal(true);
        setResizable(false);
    }
    
    public List<Cancion> getListaCanciones(){
        return interfaz.getListaCanciones();
    }
    
    public Album getAlbum(){
        return interfaz.getAlbum();
    }
    
    public void actualizarPanelAlbum(){
        interfaz.cerrarVentanaAlbum();
    }
    
    public void mostrarPanelAdministrador(){
        interfaz.asignarVentanaAdministrador();
    }
}
