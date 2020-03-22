
package Vista;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;

public class DialogoArtista extends JDialog{

    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    private PanelArtista panelArtista;
    
    //
    //CONSTRUCTOR
    //
    public DialogoArtista(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setSize(550, 210);
	setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		
	panelArtista = new PanelArtista(this);
	add(panelArtista);
		
        setModal(true);
        setResizable(false);

    }
    
    //
    //MÉTODOS
    //
    public Vector<String> buscarPaises(){
        Vector<String> listaPaises = interfaz.buscarPaises();
        return listaPaises;
    }
    
    public void actualizarPanelArtista(){
        panelArtista.actualizarPanelArtista();
    }
    
    public BigDecimal buscarUltimoInterprete(){
        return interfaz.buscarUltimoInterprete();
    }
    
    public void guardarArtista(String pCodigo, String pNomReal, String pNomArtistico, String pPais){
        interfaz.guardarArtista(pCodigo, pNomReal, pNomArtistico, pPais);
    }
}
