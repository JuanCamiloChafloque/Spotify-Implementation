
package Vista;

import Modelo.Cancion;
import Modelo.Cancionxinterprete;
import javax.swing.JDialog;


public class DialogoReproducir extends JDialog{
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    private PanelReproducir panelReproducir;
    
    //
    //CONSTRUCTOR
    //
    public DialogoReproducir(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setSize(450, 250);
	setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        
        panelReproducir = new PanelReproducir(this);
	add(panelReproducir);
		
        setModal(true);
        setResizable(false);
    }
    
    public Cancion getCancionSeleccionada(){
        return interfaz.getCancionSeleccionada();
    }
    
    public Cancionxinterprete getCancionInterpreteSeleccionada(){
        return interfaz.getCancionInterpreteSeleccionada();
    }
    
    public void guardarLike(){
        interfaz.guardarLike();
    }
    
    public void eliminarLike(){
        interfaz.eliminarLike();
    }
    
    public boolean verificarLike(){
        return interfaz.verificarLike();
    }
    
}
