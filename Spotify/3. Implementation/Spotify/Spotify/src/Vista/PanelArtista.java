/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelArtista extends JPanel implements ActionListener{
    
    //
    //ATRIBUTOS
    //
    private JLabel titulo;
    private JLabel lblCodigo;
    private JLabel lblNomArtistico;
    private JLabel lblNomReal;
    private JLabel lblPais;
    private TextField txtCodigo;
    private TextField txtNomArtistico;
    private TextField txtNomReal;
    private JComboBox cmbPais;
    private JButton btnAceptar;
    
    //
    //CONSTANTES
    //
    private static final String ACEPTAR = "Aceptar";
    
    //
    //RELACIONES
    //
    private DialogoArtista dialogo;
    
    //
    //CONSTRUCTOR
    //
    public PanelArtista(DialogoArtista pDialogo){
        
        setLayout(new BorderLayout());
        
        dialogo = pDialogo;

        titulo = new JLabel("Digite los datos del Artista");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        JPanel panTitulo = new JPanel();
        panTitulo.add(titulo);
        
        lblCodigo = new JLabel("Código del Interprete");
        lblNomArtistico = new JLabel("Ingrese el nombre Artistico");
        lblNomReal = new JLabel("Ingrese el nombre Real");
        lblPais = new JLabel("Ingrese el país de Nacimiento");
        
        txtCodigo = new TextField();
        actualizarPanelArtista();
        txtCodigo.setEditable(false);
        txtNomArtistico = new TextField();
        txtNomReal = new TextField();
        
        cmbPais = new JComboBox();
        Vector <String> listaPaises = dialogo.buscarPaises();
        cmbPais.setModel(new DefaultComboBoxModel<String>(listaPaises));
        
        btnAceptar = new JButton("Guardar");
        btnAceptar.setActionCommand(ACEPTAR);
        btnAceptar.addActionListener(this);
        
        JPanel panArtista = new JPanel();
        panArtista.setLayout(new GridLayout(4,2));
        TitledBorder borde = new TitledBorder("Datos Artista");
        panArtista.setBorder(borde);
        panArtista.add(lblCodigo);
        panArtista.add(txtCodigo);
        panArtista.add(lblNomArtistico);
        panArtista.add(txtNomArtistico);
        panArtista.add(lblNomReal);
        panArtista.add(txtNomReal);        
        panArtista.add(lblPais);
        panArtista.add(cmbPais);

        add(panTitulo, BorderLayout.NORTH);
        add(panArtista, BorderLayout.CENTER);
        add(btnAceptar, BorderLayout.SOUTH);
        
    }
    
    //
    //MÉTODOS
    //
    public String getTxtCodigo(){
        return txtCodigo.getText();
    }
    
    public void setTxtCodigo(TextField pTxtCodigo){
        this.txtCodigo = pTxtCodigo;
    }
    
    public String getTxtNomArtistico(){
        return txtNomArtistico.getText();
    }
    
    public String getTxtNomReal(){
        return txtNomReal.getText();
    }
    
    public String getCmbPais(){
        return cmbPais.getSelectedItem().toString();
    }

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getActionCommand().equalsIgnoreCase(ACEPTAR)){
            if(getTxtNomReal().isEmpty() || getTxtNomArtistico().isEmpty()){
                JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", 0);
            }
            else{
                dialogo.guardarArtista(getTxtCodigo(), getTxtNomReal(), getTxtNomArtistico(), getCmbPais());
                dialogo.setVisible(false);
            }
        }
    }
    
    public void actualizarPanelArtista(){
        BigDecimal ultimoCodigo = dialogo.buscarUltimoInterprete();
        setTxtCodigo(new TextField(ultimoCodigo.toString()));
    }
}
