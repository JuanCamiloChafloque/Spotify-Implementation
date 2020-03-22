
package Vista;

import Modelo.Usuarioc;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;


public class DialogoSuscripcion extends JDialog{
    
        //
    //RELACIONES
    //
    InterfazGrafica interfaz;
    PanelSuscripcion panelSuscripcion;
    
    //
    //CONSTRUCTOR
    //
    public DialogoSuscripcion(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setSize(450, 250);
	setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        
        panelSuscripcion = new PanelSuscripcion(this);
	add(panelSuscripcion);
		
        setModal(true);
        setResizable(false);
    }
    
    public BigDecimal buscarUltimoUsuario(){
        return interfaz.buscarUltimoUsuario();
    }
    
    public Vector<String> buscarPaises(){
        Vector<String> listaPaises = interfaz.buscarPaises();
        return listaPaises;
    }
    
    public Vector<String> buscarSuscripciones(){
        Vector<String> listaTipos = interfaz.buscarSuscripciones();
        return listaTipos;
    }
    
    public List<Usuarioc> buscarUsuarios(){
        return interfaz.buscarUsuarios();
    }
    
    public void guardarUsuario(String pCodigo, String pApellido, String pNickname, String pNombre,  String pTarjeta, String pPais, String pTipo){
        interfaz.guardarUsuario(pCodigo, pApellido, pNickname, pNombre, pTarjeta, pPais, pTipo);
    }
    
}