package Vista;

import Controlador.AlbumJpaController;
import Controlador.AuditoriaJpaController;
import Controlador.CancionJpaController;
import Controlador.CancionxinterpreteJpaController;
import Controlador.CancionxlistaJpaController;
import Controlador.EmpresarJpaController;
import Controlador.GeneroJpaController;
import Controlador.InterpreteJpaController;
import Controlador.ListareproduccionJpaController;
import Controlador.PaiscJpaController;
import Controlador.SuscripcionJpaController;
import Controlador.UsuariocJpaController;
import Controlador.UsuariolikesJpaController;
import Controlador.UsuarioxreproduccionJpaController;
import Controlador.exceptions.NonexistentEntityException;
import Modelo.Album;
import Modelo.Cancion;
import Modelo.Cancionxinterprete;
import Modelo.Cancionxlista;
import Modelo.Empresar;
import Modelo.Genero;
import Modelo.Interprete;
import Modelo.Listareproduccion;
import Modelo.Paisc;
import Modelo.Suscripcion;
import Modelo.Usuarioc;
import Modelo.Usuariolikes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.text.DateFormatter;

public class InterfazGrafica extends JFrame{

    
    //
    //ATRIBUTOS
    //
    private JFrame menu;
    private JTabbedPane ventanas;
    
    //
    //RELACIONES
    //
    private static InterpreteJpaController controladorInterprete;
    private static PaiscJpaController controladorPais;
    private static SuscripcionJpaController controladorSuscripcion;
    private static UsuariocJpaController controladorUsuario;
    private static AlbumJpaController controladorAlbum;
    private static CancionJpaController controladorCancion;
    private static CancionxinterpreteJpaController controladorCancionInterprete;
    private static EmpresarJpaController controladorEmpresa;
    private static GeneroJpaController controladorGenero;
    private static ListareproduccionJpaController controladorLista;
    private static CancionxlistaJpaController controladorCancionLista;
    private static UsuariolikesJpaController controladorLikes;
    private static UsuarioxreproduccionJpaController controladorReproduccion;
    private static AuditoriaJpaController controladorAuditoria;
    private DialogoArtista dialogoArtista;
    private DialogoCancion dialogoCancion;
    private DialogoCancionAlbum dialogoCancionAlbum;
    private DialogoSuscripcion dialogoSuscripcion;
    private DialogoCrearLista dialogoCrearLista;
    private DialogoReproducir dialogoReproducir;
    private PanelMenu panelMenu;
    private PanelMenuUsuario panelUsuario;
    private PanelMenuAdministrador panelAdministrador;
    private PanelAlbum panelAlbum;
    private PanelBusqueda panelBusqueda;
    private PanelListaReproduccion panelListaReproduccion;
    private PanelListaUsuario panelListaUsuario;
    
    //
    //CONSTRUCTOR
    //
    public InterfazGrafica(){
        
        menu = new JFrame();
        menu.getContentPane().setBackground(Color.WHITE);
        menu.setSize(500,400);
        menu.setTitle("SpotDeezer");
        menu.getContentPane().setLayout(new BorderLayout());
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
	ventanas = new JTabbedPane(JTabbedPane.TOP);
	ventanas.setBounds(10, 35, 664, 515);
        
        panelMenu = new PanelMenu(this);
        ventanas.addTab("Menu Principal", null, panelMenu, null);
        
        panelAdministrador = new PanelMenuAdministrador(this);
        ventanas.addTab("Menu Administrador", null, panelAdministrador, null);
        
        panelUsuario = new PanelMenuUsuario(this);
        ventanas.addTab("Menu Usuario", null, panelUsuario, null);
        
        menu.getContentPane().add(ventanas, BorderLayout.CENTER);		
	menu.setResizable(false);
	menu.setVisible(true);
        
    }
    
    //
    //MÉTODOS
    //
    public static void main(String[] args) {
        
        new InterfazGrafica();
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoFinalBDPU");
            controladorPais = new PaiscJpaController(emf);
            controladorInterprete = new InterpreteJpaController(emf);
            controladorSuscripcion = new SuscripcionJpaController(emf);
            controladorUsuario = new UsuariocJpaController(emf);
            controladorEmpresa = new EmpresarJpaController(emf);
            controladorAlbum = new AlbumJpaController(emf);
            controladorGenero = new GeneroJpaController(emf);
            controladorCancion = new CancionJpaController(emf);
            controladorCancionInterprete = new CancionxinterpreteJpaController(emf);
            controladorLista = new ListareproduccionJpaController(emf);
            controladorCancionLista = new CancionxlistaJpaController(emf);
            controladorLikes = new UsuariolikesJpaController(emf);
            controladorReproduccion = new UsuarioxreproduccionJpaController(emf);
            controladorAuditoria = new AuditoriaJpaController(emf);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al crear el Entity Manager", "Error", 0);
        }
    }
    public void cerrarVentanaAlbum(){
        ventanas.remove(panelAlbum);
    }
    
    public void cerrarVentanaLista(){
        ventanas.remove(panelListaReproduccion);
    }
    
    public void cerrarVentanaListaUsuario(){
        ventanas.remove(panelListaUsuario);
    }
    
    public void asignarVentanaAdministrador(){
        ventanas.setSelectedComponent(panelAdministrador);
    }
 
    public void asignarVentanaUsuario(){
        ventanas.setSelectedComponent(panelUsuario);
    }
    
    public void crearPanelArtista(){
        dialogoArtista = new DialogoArtista(this);
        dialogoArtista.setLocationRelativeTo(this);
        dialogoArtista.setVisible(true);
    }
    
    public void crearPanelAlbum(){
        panelAlbum = new PanelAlbum(this);
        ventanas.addTab("Ingresar Albúm/EP" ,null, panelAlbum, null);
        panelAlbum.setModel();
        ventanas.setSelectedComponent(panelAlbum);
    }
    
    public void crearPanelBusqueda(){
        panelBusqueda = new PanelBusqueda(this);
        ventanas.addTab("Resultados Busqueda", null, panelBusqueda, null);
        ventanas.setSelectedComponent(panelBusqueda);
    }
    
    public void crearPanelListaReproduccion(){
        panelListaReproduccion = new PanelListaReproduccion(this);
        ventanas.addTab("Listas de Reproducción", null, panelListaReproduccion, null);
        ventanas.setSelectedComponent(panelListaReproduccion);
    }
    
    public void crearPanelListaUsuario(){
        panelListaUsuario = new PanelListaUsuario(this);
        ventanas.addTab("Listas Usuario", null, panelListaUsuario, null);
        ventanas.setSelectedComponent(panelListaUsuario);
        
    }
    
    public void crearPanelCancion(){
        dialogoCancion = new DialogoCancion(this);
        dialogoCancion.setLocationRelativeTo(this);
        dialogoCancion.setVisible(true);
    }
    
    public void crearPanelCancionAlbum(){
        dialogoCancionAlbum = new DialogoCancionAlbum(this);
        dialogoCancionAlbum.setLocationRelativeTo(this);
        dialogoCancionAlbum.setVisible(true);
    }
    
    public void crearPanelSuscripcion(){
        dialogoSuscripcion = new DialogoSuscripcion(this);
        dialogoSuscripcion.setLocationRelativeTo(this);
        dialogoSuscripcion.setVisible(true);
    }
    
    public void crearPanelCrearLista(){
        dialogoCrearLista = new DialogoCrearLista(this);
        dialogoCrearLista.setLocationRelativeTo(this);
        dialogoCrearLista.setVisible(true);
    }
    
    public void crearPanelReproducir(){
        dialogoReproducir = new DialogoReproducir(this);
        dialogoReproducir.setLocationRelativeTo(this);
        dialogoReproducir.setVisible(true);        
    }
    
    public boolean buscarInterprete(String pNombre){
        
        List<Interprete> interpretes = controladorInterprete.findInterpreteEntities();
        for(Interprete actual: interpretes){
            if(actual.getNombreartistico().equalsIgnoreCase(pNombre)){
                return true;
            }
        }
        
        return false;
        
    }
    
    public Vector<String> buscarPaises(){
        Vector<String> listaPaises = controladorPais.buscarPaises();
        return listaPaises; 
    }
    
    public Vector<String> buscarSuscripciones(){
        Vector<String> listaTipos = controladorSuscripcion.buscarSuscripciones();
        return listaTipos; 
    }
    
    public Vector<String> buscarEmpresas(){
        Vector<String> empresas = controladorEmpresa.buscarEmpresas();
        return empresas; 
    }
    
    public Vector<String> buscarGeneros(){
        Vector<String> listaGeneros = controladorGenero.buscarGeneros();
        return listaGeneros;
    }
    
    public BigDecimal buscarUltimoInterprete(){
        return controladorInterprete.buscarUltimoInterprete();
    }
    
    public BigDecimal buscarUltimoUsuario(){
        return controladorUsuario.buscarUltimoUsuario();
    }
    
    public BigDecimal buscarUltimoAlbum(){
        return controladorAlbum.buscarUltimoAlbum();
    }
    
    public BigDecimal buscarUltimaCancion(){
        return controladorCancion.buscarUltimaCancion();
    }
    
    public BigDecimal buscarUltimaLista(){
        return controladorLista.buscarUltimaLista();
    }
    
    public List<Usuarioc> buscarUsuarios(){
        return controladorUsuario.findUsuariocEntities();
    }
    
    public void guardarArtista(String pCodigo, String pNomReal, String pNomArtistico, String pPais){
        
        List<Paisc> listaPaises = controladorPais.findPaiscEntities();
        Paisc paisArtista = null;
        
        for(Paisc actual: listaPaises){
            if(actual.getNombrepais().equalsIgnoreCase(pPais)){
                paisArtista = actual;
            }
        }
        controladorInterprete.crearArtista(pCodigo, pNomReal, pNomArtistico, paisArtista);
        controladorAuditoria.crearAuditoria("Artista", 'I');
    }
    
    public void guardarUsuario(String pCodigo, String pApellido, String pNickname, String pNombre, String pTarjeta, String pPais, String pTipo){
        
        List<Paisc> listaPaises = controladorPais.findPaiscEntities();
        Paisc paisUsuario = null;
        List<Suscripcion> tipoSuscripcion = controladorSuscripcion.findSuscripcionEntities();
        Suscripcion tipo = null;
        
        for(Paisc actual: listaPaises){
            if(actual.getNombrepais().equalsIgnoreCase(pPais)){
                paisUsuario = actual;
            }
        }
        
        for(Suscripcion actual: tipoSuscripcion){
            if(actual.getTiposuscripcion().equalsIgnoreCase(pTipo)){
                tipo = actual;
            }
        }
        
        controladorUsuario.crearUsuario(pCodigo, pApellido, pNickname, pNombre, pTarjeta, paisUsuario, tipo);
    }
    
    public void guardarLista(String pCodigo, String pNombre, String pVisibilidad, String pOrden){
        
        String cancion = panelBusqueda.getCancionSeleccionada();
        Cancion cancionLista = null;
        int cont = 0;
        Usuarioc usuarioActual = controladorUsuario.getUsuarioActivo();
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        List<Listareproduccion> listas = controladorLista.findListareproduccionEntities();
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionLista = actual;
            }
        }
        
        for(Listareproduccion actual: listas){
            if(actual.getNombrelista().equalsIgnoreCase(pNombre) && actual.getCodusuario().getCodusuario().compareTo(usuarioActual.getCodusuario()) == 0){
                cont++;
            }
        }
        
        if(cont == 0){
            Listareproduccion pLista = controladorLista.crearLista(pCodigo, pNombre, pVisibilidad, pOrden, controladorUsuario.getUsuarioActivo());
            controladorCancionLista.crearCancionLista(cancionLista, pLista);
            
        }else{
            JOptionPane.showMessageDialog(null, "El usuario ya tiene una lista con ese nombre", "Error", 0);
        }
    }
    
    public void actualizarLista(){
        
        String cancion = panelBusqueda.getCancionSeleccionada();
        String listaR = panelListaReproduccion.getListaSeleccionada();
        int contador = 0;
        Cancion cancionLista = null;
        Listareproduccion listaUsuario = null;
        Usuarioc usuarioActual = controladorUsuario.getUsuarioActivo();
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        List<Listareproduccion> listas = controladorLista.findListareproduccionEntities();
        List<Cancionxlista> cancionesxlista = controladorCancionLista.findCancionxlistaEntities();
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionLista = actual;
            }
        }
        
        for(Listareproduccion actual: listas){
            if(actual.getNombrelista().equalsIgnoreCase(listaR) && actual.getCodusuario().getCodusuario().compareTo(usuarioActual.getCodusuario()) == 0){
                listaUsuario = actual;
            }
        }
        
        for(Cancionxlista actual: cancionesxlista){
            if(actual.getCodcancion().getCodcancion().compareTo(cancionLista.getCodcancion()) == 0 && actual.getCodlista().getCodlista().compareTo(listaUsuario.getCodlista()) == 0){
                contador++;
            }
            
        }
        
        if(contador > 0){
            JOptionPane.showMessageDialog(null, "La canción ya se encuentra en la lista de reproducción seleccionada", "Agregar Canción", 1);
        
        }else{
            controladorCancionLista.crearCancionLista(cancionLista, listaUsuario);
        }

    }
    
    public void guardarAlbum(String pCodigo, String pNombre, String pFecha, String pTipo, String pEmpresa) throws ParseException{
        
        List<Empresar> listaEmpresas = controladorEmpresa.findEmpresarEntities();
        Empresar empresaAlbum = null;
        
        for(Empresar actual: listaEmpresas){
            if(actual.getNombreempresa().equalsIgnoreCase(pEmpresa)){
                empresaAlbum = actual;
            }
        }
        
        Date fechaAlbum = new SimpleDateFormat("dd/MM/yyyy").parse(pFecha);
        
        controladorAlbum.crearAlbum(pCodigo, pNombre, fechaAlbum, pTipo, empresaAlbum);
        if(pTipo.equalsIgnoreCase("Album")){
            controladorAuditoria.crearAuditoria("Album", 'I');
        }else if(pTipo.equalsIgnoreCase("EP")){
            controladorAuditoria.crearAuditoria("EP", 'I');
        }

    }
    
    public void guardarCancion(String pCodigo, String pNombre, String pInterprete, String pInterpretePrin, String pDuracion, String pGenero) throws ParseException{
        
        BigDecimal codAlbum = new BigDecimal(panelAlbum.getTxtCodigo());
        List<Interprete> interpretes = controladorInterprete.findInterpreteEntities();
        List<Genero> generos = controladorGenero.findGeneroEntities();
        Album album = controladorAlbum.getAlbum();
        Genero generoCancion = null;
        Interprete interprete = null;
        Interprete interpretePrin = null;
        
        for(Genero actual: generos){
            if(actual.getNombregenero().equalsIgnoreCase(pGenero)){
                generoCancion = actual;
            }
        }
        
        for(Interprete actual: interpretes){
            if(actual.getNombreartistico().equalsIgnoreCase(pInterprete)){
                interprete = actual;
            }
            if(actual.getNombreartistico().equalsIgnoreCase(pInterpretePrin)){
                interpretePrin = actual;
            }
        }
        
        Date duracionCancion = new SimpleDateFormat("mm:ss").parse(pDuracion);

        Cancion cancion = controladorCancion.crearCancion(pCodigo, pNombre, duracionCancion, album, generoCancion);
        controladorCancionInterprete.crearCancionInterprete(cancion, interprete, interpretePrin);
        controladorAuditoria.crearAuditoria("Cancion", 'I');
       
    }
    
    public void guardarAuditoria(String pEntidad, char pOperacion){
        controladorAuditoria.crearAuditoria(pEntidad, pOperacion);
    }
    
    public void guardarReproduccion(){
        
        String cancion = panelBusqueda.getCancionSeleccionada();
        Cancion cancionSeleccionada = null;
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionSeleccionada = actual;
            }
        }
        
        controladorReproduccion.crearReproduccion(cancionSeleccionada, controladorUsuario.getUsuarioActivo());
        
    }
    
    public void guardarLike(){
        
        String cancion = panelBusqueda.getCancionSeleccionada();
        Cancion cancionSeleccionada = null;
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionSeleccionada = actual;
            }
        }

        controladorLikes.crearLike(cancionSeleccionada, controladorUsuario.getUsuarioActivo());
        

    }
    
    public void eliminarLike(){
        
        BigDecimal pCodigo = new BigDecimal(0);
        String cancion = panelBusqueda.getCancionSeleccionada();
        Cancion cancionSeleccionada = null;
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionSeleccionada = actual;
            }
        }
        
        List<Usuariolikes> likes = controladorLikes.findUsuariolikesEntities();
        
        for(Usuariolikes actual: likes){
            if(actual.getCodusuario().getCodusuario().compareTo(controladorUsuario.getUsuarioActivo().getCodusuario()) == 0 && actual.getCodcancion().getCodcancion().compareTo(cancionSeleccionada.getCodcancion()) == 0){
                pCodigo = actual.getCodregistro();
            }
        }
        
        try {
            controladorLikes.destroy(pCodigo);
            JOptionPane.showMessageDialog(null, "Se eliminó el like de la canción", "Likes Canción", 1);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el like", "Error", 0);
        }
        
    }
    
    public boolean verificarLike(){
        
        boolean dioLike = false;
                String cancion = panelBusqueda.getCancionSeleccionada();
        Cancion cancionSeleccionada = null;
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionSeleccionada = actual;
            }
        }
        
        List<Usuariolikes> likes = controladorLikes.findUsuariolikesEntities();
        
        for(Usuariolikes actual: likes){
            if(actual.getCodusuario().getCodusuario().compareTo(controladorUsuario.getUsuarioActivo().getCodusuario()) == 0 && actual.getCodcancion().getCodcancion().compareTo(cancionSeleccionada.getCodcancion()) == 0){
                dioLike = true;
            }
        }
        
        return dioLike;
        
    }
    
    
    public void confirmarCambiosCancion(){
        controladorCancion.confirmarCambiosCanciones();
    }
    
    public void confirmarCambiosCanInterprete(){
        controladorCancionInterprete.confirmarCambiosCanInterprete();
    }
    
    public List<Cancion> getListaCanciones(){
        return controladorCancion.getLista();
    }
    
    public List<Cancion> getListaBusqueda(String pBusqueda){
        return controladorCancion.busquedaCanciones(pBusqueda);
    }
    
    public Album getAlbum(){
        return controladorAlbum.getAlbum();
    }
    
    public int getCantidadCancionesLista(){
        
        String listaActual = panelListaReproduccion.getListaSeleccionada();
        int contador = 0;
        List<Listareproduccion> lista = controladorLista.findListareproduccionEntities();
        List<Cancionxlista> listaCancionLista = controladorCancionLista.findCancionxlistaEntities();
        Listareproduccion listaR = null;
        
        for(Listareproduccion actual: lista){
            if(actual.getNombrelista().equalsIgnoreCase(listaActual) && actual.getCodusuario().getCodusuario().compareTo(controladorUsuario.getUsuarioActivo().getCodusuario()) == 0){
                listaR = actual;
            }
        }
        
        for(Cancionxlista actual: listaCancionLista){
            if(actual.getCodlista().getCodlista().compareTo(listaR.getCodlista()) == 0){
                contador++;
            }
        }
        
        return contador;

    }
    
    public Cancion getCancionSeleccionada(){
        
        String cancion = panelBusqueda.getCancionSeleccionada();
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        Cancion cancionLista = null;
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionLista = actual;
            }
        }
        
        return cancionLista;
        
    }
    
    public Cancionxinterprete getCancionInterpreteSeleccionada(){
        
        String cancion = panelBusqueda.getCancionSeleccionada();
        List<Cancion> canciones = controladorCancion.findCancionEntities();
        List<Cancionxinterprete> cancionListaInterprete = controladorCancionInterprete.findCancionxinterpreteEntities();
        Cancion cancionLista = null;
        Cancionxinterprete cancionInterprete = null;
        
        for(Cancion actual: canciones){
            if(actual.getTitulo().equalsIgnoreCase(cancion)){
                cancionLista = actual;
            }
        }
        
        for(Cancionxinterprete actual: cancionListaInterprete){
            if(actual.getCancion().getCodcancion().compareTo(cancionLista.getCodcancion()) == 0){
                cancionInterprete = actual;
            }
        }
        
        return cancionInterprete;

    }
    
    public String getDuracionCancionesLista(){
        
        String listaActual = panelListaReproduccion.getListaSeleccionada();
        List<Listareproduccion> lista = controladorLista.findListareproduccionEntities();
        List<Cancionxlista> listaCancionLista = controladorCancionLista.findCancionxlistaEntities();
        DateFormat formatoDuracion = new SimpleDateFormat("mm:ss");
        
        Listareproduccion listaR = null;
        
        for(Listareproduccion actual: lista){
            if(actual.getNombrelista().equalsIgnoreCase(listaActual) && actual.getCodusuario().getCodusuario().compareTo(controladorUsuario.getUsuarioActivo().getCodusuario()) == 0){
                listaR = actual;
            }
        }
        
        for(Cancionxlista actual: listaCancionLista){
            if(actual.getCodlista().getCodlista().compareTo(listaR.getCodlista()) == 0){
                
                formatoDuracion.format(actual.getCodcancion().getDuracion()).toString();
            }
        }
        
        return formatoDuracion.toString();
        
        
    }
    
    public List<Listareproduccion> getListasReproduccionUsuario(){
        
        List<Listareproduccion> listas = controladorLista.findListareproduccionEntities();
        List<Listareproduccion> listasUsuario = new ArrayList<Listareproduccion>();
        Usuarioc usuarioActual = controladorUsuario.getUsuarioActivo();
        
        for(Listareproduccion actual: listas){
            if(actual.getCodusuario().getCodusuario().compareTo(usuarioActual.getCodusuario()) == 0){
                listasUsuario.add(actual);
            }
        }
        
        return listasUsuario;
        
    }
    
    public String getTxtBusqueda(){
        return panelUsuario.getTxtBusqueda();
    }
    
    public Interprete getInterpretePrincipal(Cancion pCancion){
        
        List<Cancionxinterprete> canciones = controladorCancionInterprete.findCancionxinterpreteEntities();
        
        for(Cancionxinterprete actual: canciones){
            
            if(actual.getCancion().getCodcancion().compareTo(pCancion.getCodcancion()) == 0){
                return actual.getCodprincipal();
            }
        }  
        return null;
    }
    
    public BigDecimal getUltimoCodigo(){
        List<Cancion> canciones = controladorCancion.getLista();
        BigDecimal ultimo = new BigDecimal(0);
        
        
        for(Cancion actual: canciones){
            ultimo = actual.getCodcancion();
        }
        
        return ultimo;
    }
    
    public void cancelarCambiosCancion(){
        controladorCancion.cancelarCambiosCanciones();
    }
    
    public void confirmarCambiosAlbum(){
        controladorAlbum.confirmarCambiosAlbum();
    }
    
    public void activarUsuario(String pUsuario){
        controladorUsuario.setUsuarioActivo(pUsuario);
    }
    
    public void modificarNombreLista(){

        controladorLista.modificarNombreLista(new BigDecimal(panelListaUsuario.getListaUsuarioSeleccionada()), panelListaUsuario.getNombreCambio());
    }
    
    public void modificarOrdenLista(){
       
        controladorLista.modificarOrdenLista(new BigDecimal(panelListaUsuario.getListaUsuarioSeleccionada()), panelListaUsuario.getOrdenCambio());
        
    }
    
}



