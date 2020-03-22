
package Vista;

import java.math.BigDecimal;
import javax.swing.JDialog;


public class DialogoCrearLista extends JDialog{
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    private PanelCrearLista panelCrearLista;
    
    //
    //CONSTRUCTOR
    //
    public DialogoCrearLista(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setSize(450, 250);
	setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        
        panelCrearLista = new PanelCrearLista(this);
	add(panelCrearLista);
		
        setModal(true);
        setResizable(false);
    }
    
    public BigDecimal buscarUltimaLista(){
        return interfaz.buscarUltimaLista();
    }
    
    public void guardarLista(String pCodigo, String pNombre, String pVisibilidad, String pOrden){
        interfaz.guardarLista(pCodigo, pNombre, pVisibilidad, pOrden);
        
    }

    public void cerrarPanelLista(){
        interfaz.cerrarVentanaLista();
    }
    
}
