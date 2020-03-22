
package Vista;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class DialogoCancion extends JDialog{

    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    private PanelCancion panelCancion;
    
    //
    //CONSTRUCTOR
    //
    public DialogoCancion(InterfazGrafica pInterfaz){
        interfaz = pInterfaz;
        
        setSize(450, 250);
	setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		
	panelCancion = new PanelCancion(this);
	add(panelCancion);
		
        setModal(true);
        setResizable(false);
    }
    
    public Vector<String> buscarGeneros(){
        return interfaz.buscarGeneros();
    }
    
    public BigDecimal buscarUltimaCancion(){
        return interfaz.buscarUltimaCancion();
    }
    
    public boolean buscarInterprete(String pNombre){
        return interfaz.buscarInterprete(pNombre);
    }
    
    public void guardarCancion(String pCodigo, String pNombre, String pInterprete, String pInterpretePrin, String pDuracion, String pGenero){
        try {
            interfaz.guardarCancion(pCodigo, pNombre, pInterprete, pInterpretePrin, pDuracion, pGenero);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error en la duración de la canción", "Error", 0);
        }
    }
    
    public void cancelarCambiosCancion(){
        interfaz.cancelarCambiosCancion();
    }
    
    public BigDecimal getUltimoCodigo(){
        return interfaz.getUltimoCodigo();
    }
    
    public void mostrarPanelAdministrador(){
        interfaz.asignarVentanaAdministrador();
    }
}
