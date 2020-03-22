/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelMenu extends JPanel implements ActionListener{
    
    
    //
    //ATRIBUTOS
    //
    private JLabel tituloMenu;
    private JButton btnAdministrador;
    private JButton btnUsuario;
    
    //
    //CONSTANTES 
    //
    private static final String ADMINISTRADOR = "Administrador";
    private static final String USUARIO = "Usuario";
    
    //
    //RELACIONES
    //
    private InterfazGrafica interfaz;
    
    //
    //CONSTRUCTOR
    //
    public PanelMenu(InterfazGrafica pInterfaz){
        
        interfaz = pInterfaz;
        
        setBackground(new Color(153, 204, 153));
        setSize(500, 450);
        setLayout(null);
        
        tituloMenu = new JLabel("SpotDeezer");
        tituloMenu.setFont(new Font("Tahoma", Font.PLAIN, 30));
        tituloMenu.setForeground(Color.BLACK);
        tituloMenu.setBounds(169, 31, 198, 80);
        add(tituloMenu);
        
        btnAdministrador = new JButton("Entrar como Administrador");
        btnAdministrador.setBackground(Color.LIGHT_GRAY);
        btnAdministrador.setBounds(141, 218, 212, 38);
        btnAdministrador.addActionListener(this);
        btnAdministrador.setActionCommand(ADMINISTRADOR);
        add(btnAdministrador);
        
        btnUsuario = new JButton("Entrar como Usuario");
        btnUsuario.setBackground(Color.LIGHT_GRAY);
        btnUsuario.setBounds(141, 164, 212, 38);
        btnUsuario.addActionListener(this);
        btnUsuario.setActionCommand(USUARIO);
        add(btnUsuario);

    }
    
    //
    //MÉTODOS
    //
    public void actionPerformed(ActionEvent evento) {
        
        if (evento.getActionCommand().equalsIgnoreCase(ADMINISTRADOR)){
            interfaz.asignarVentanaAdministrador();
        }else if(evento.getActionCommand().equalsIgnoreCase(USUARIO)){
            interfaz.asignarVentanaUsuario();
        }
        
        
    }
    
}
