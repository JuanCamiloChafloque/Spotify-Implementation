
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Paisc;
import Modelo.Suscripcion;
import Modelo.Usuariolikes;
import java.util.ArrayList;
import java.util.List;
import Modelo.Usuarioxreproduccion;
import Modelo.Listareproduccion;
import Modelo.Usuarioc;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class UsuariocJpaController implements Serializable {
    
    //
    //RELACIONES
    //
    private Usuarioc usuarioActivo;

    public UsuariocJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarioc usuarioc) throws PreexistingEntityException, Exception {
        if (usuarioc.getUsuariolikesList() == null) {
            usuarioc.setUsuariolikesList(new ArrayList<Usuariolikes>());
        }
        if (usuarioc.getUsuarioxreproduccionList() == null) {
            usuarioc.setUsuarioxreproduccionList(new ArrayList<Usuarioxreproduccion>());
        }
        if (usuarioc.getListareproduccionList() == null) {
            usuarioc.setListareproduccionList(new ArrayList<Listareproduccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paisc codpais = usuarioc.getCodpais();
            if (codpais != null) {
                codpais = em.getReference(codpais.getClass(), codpais.getCodpais());
                usuarioc.setCodpais(codpais);
            }
            Suscripcion tiposuscripcion = usuarioc.getTiposuscripcion();
            if (tiposuscripcion != null) {
                tiposuscripcion = em.getReference(tiposuscripcion.getClass(), tiposuscripcion.getTiposuscripcion());
                usuarioc.setTiposuscripcion(tiposuscripcion);
            }
            List<Usuariolikes> attachedUsuariolikesList = new ArrayList<Usuariolikes>();
            for (Usuariolikes usuariolikesListUsuariolikesToAttach : usuarioc.getUsuariolikesList()) {
                usuariolikesListUsuariolikesToAttach = em.getReference(usuariolikesListUsuariolikesToAttach.getClass(), usuariolikesListUsuariolikesToAttach.getCodregistro());
                attachedUsuariolikesList.add(usuariolikesListUsuariolikesToAttach);
            }
            usuarioc.setUsuariolikesList(attachedUsuariolikesList);
            List<Usuarioxreproduccion> attachedUsuarioxreproduccionList = new ArrayList<Usuarioxreproduccion>();
            for (Usuarioxreproduccion usuarioxreproduccionListUsuarioxreproduccionToAttach : usuarioc.getUsuarioxreproduccionList()) {
                usuarioxreproduccionListUsuarioxreproduccionToAttach = em.getReference(usuarioxreproduccionListUsuarioxreproduccionToAttach.getClass(), usuarioxreproduccionListUsuarioxreproduccionToAttach.getCodregistro());
                attachedUsuarioxreproduccionList.add(usuarioxreproduccionListUsuarioxreproduccionToAttach);
            }
            usuarioc.setUsuarioxreproduccionList(attachedUsuarioxreproduccionList);
            List<Listareproduccion> attachedListareproduccionList = new ArrayList<Listareproduccion>();
            for (Listareproduccion listareproduccionListListareproduccionToAttach : usuarioc.getListareproduccionList()) {
                listareproduccionListListareproduccionToAttach = em.getReference(listareproduccionListListareproduccionToAttach.getClass(), listareproduccionListListareproduccionToAttach.getCodlista());
                attachedListareproduccionList.add(listareproduccionListListareproduccionToAttach);
            }
            usuarioc.setListareproduccionList(attachedListareproduccionList);
            em.persist(usuarioc);
            if (codpais != null) {
                codpais.getUsuariocList().add(usuarioc);
                codpais = em.merge(codpais);
            }
            if (tiposuscripcion != null) {
                tiposuscripcion.getUsuariocList().add(usuarioc);
                tiposuscripcion = em.merge(tiposuscripcion);
            }
            for (Usuariolikes usuariolikesListUsuariolikes : usuarioc.getUsuariolikesList()) {
                Usuarioc oldCodusuarioOfUsuariolikesListUsuariolikes = usuariolikesListUsuariolikes.getCodusuario();
                usuariolikesListUsuariolikes.setCodusuario(usuarioc);
                usuariolikesListUsuariolikes = em.merge(usuariolikesListUsuariolikes);
                if (oldCodusuarioOfUsuariolikesListUsuariolikes != null) {
                    oldCodusuarioOfUsuariolikesListUsuariolikes.getUsuariolikesList().remove(usuariolikesListUsuariolikes);
                    oldCodusuarioOfUsuariolikesListUsuariolikes = em.merge(oldCodusuarioOfUsuariolikesListUsuariolikes);
                }
            }
            for (Usuarioxreproduccion usuarioxreproduccionListUsuarioxreproduccion : usuarioc.getUsuarioxreproduccionList()) {
                Usuarioc oldCodusuarioOfUsuarioxreproduccionListUsuarioxreproduccion = usuarioxreproduccionListUsuarioxreproduccion.getCodusuario();
                usuarioxreproduccionListUsuarioxreproduccion.setCodusuario(usuarioc);
                usuarioxreproduccionListUsuarioxreproduccion = em.merge(usuarioxreproduccionListUsuarioxreproduccion);
                if (oldCodusuarioOfUsuarioxreproduccionListUsuarioxreproduccion != null) {
                    oldCodusuarioOfUsuarioxreproduccionListUsuarioxreproduccion.getUsuarioxreproduccionList().remove(usuarioxreproduccionListUsuarioxreproduccion);
                    oldCodusuarioOfUsuarioxreproduccionListUsuarioxreproduccion = em.merge(oldCodusuarioOfUsuarioxreproduccionListUsuarioxreproduccion);
                }
            }
            for (Listareproduccion listareproduccionListListareproduccion : usuarioc.getListareproduccionList()) {
                Usuarioc oldCodusuarioOfListareproduccionListListareproduccion = listareproduccionListListareproduccion.getCodusuario();
                listareproduccionListListareproduccion.setCodusuario(usuarioc);
                listareproduccionListListareproduccion = em.merge(listareproduccionListListareproduccion);
                if (oldCodusuarioOfListareproduccionListListareproduccion != null) {
                    oldCodusuarioOfListareproduccionListListareproduccion.getListareproduccionList().remove(listareproduccionListListareproduccion);
                    oldCodusuarioOfListareproduccionListListareproduccion = em.merge(oldCodusuarioOfListareproduccionListListareproduccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioc(usuarioc.getCodusuario()) != null) {
                throw new PreexistingEntityException("Usuarioc " + usuarioc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarioc usuarioc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarioc persistentUsuarioc = em.find(Usuarioc.class, usuarioc.getCodusuario());
            Paisc codpaisOld = persistentUsuarioc.getCodpais();
            Paisc codpaisNew = usuarioc.getCodpais();
            Suscripcion tiposuscripcionOld = persistentUsuarioc.getTiposuscripcion();
            Suscripcion tiposuscripcionNew = usuarioc.getTiposuscripcion();
            List<Usuariolikes> usuariolikesListOld = persistentUsuarioc.getUsuariolikesList();
            List<Usuariolikes> usuariolikesListNew = usuarioc.getUsuariolikesList();
            List<Usuarioxreproduccion> usuarioxreproduccionListOld = persistentUsuarioc.getUsuarioxreproduccionList();
            List<Usuarioxreproduccion> usuarioxreproduccionListNew = usuarioc.getUsuarioxreproduccionList();
            List<Listareproduccion> listareproduccionListOld = persistentUsuarioc.getListareproduccionList();
            List<Listareproduccion> listareproduccionListNew = usuarioc.getListareproduccionList();
            List<String> illegalOrphanMessages = null;
            for (Usuariolikes usuariolikesListOldUsuariolikes : usuariolikesListOld) {
                if (!usuariolikesListNew.contains(usuariolikesListOldUsuariolikes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuariolikes " + usuariolikesListOldUsuariolikes + " since its codusuario field is not nullable.");
                }
            }
            for (Usuarioxreproduccion usuarioxreproduccionListOldUsuarioxreproduccion : usuarioxreproduccionListOld) {
                if (!usuarioxreproduccionListNew.contains(usuarioxreproduccionListOldUsuarioxreproduccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarioxreproduccion " + usuarioxreproduccionListOldUsuarioxreproduccion + " since its codusuario field is not nullable.");
                }
            }
            for (Listareproduccion listareproduccionListOldListareproduccion : listareproduccionListOld) {
                if (!listareproduccionListNew.contains(listareproduccionListOldListareproduccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Listareproduccion " + listareproduccionListOldListareproduccion + " since its codusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codpaisNew != null) {
                codpaisNew = em.getReference(codpaisNew.getClass(), codpaisNew.getCodpais());
                usuarioc.setCodpais(codpaisNew);
            }
            if (tiposuscripcionNew != null) {
                tiposuscripcionNew = em.getReference(tiposuscripcionNew.getClass(), tiposuscripcionNew.getTiposuscripcion());
                usuarioc.setTiposuscripcion(tiposuscripcionNew);
            }
            List<Usuariolikes> attachedUsuariolikesListNew = new ArrayList<Usuariolikes>();
            for (Usuariolikes usuariolikesListNewUsuariolikesToAttach : usuariolikesListNew) {
                usuariolikesListNewUsuariolikesToAttach = em.getReference(usuariolikesListNewUsuariolikesToAttach.getClass(), usuariolikesListNewUsuariolikesToAttach.getCodregistro());
                attachedUsuariolikesListNew.add(usuariolikesListNewUsuariolikesToAttach);
            }
            usuariolikesListNew = attachedUsuariolikesListNew;
            usuarioc.setUsuariolikesList(usuariolikesListNew);
            List<Usuarioxreproduccion> attachedUsuarioxreproduccionListNew = new ArrayList<Usuarioxreproduccion>();
            for (Usuarioxreproduccion usuarioxreproduccionListNewUsuarioxreproduccionToAttach : usuarioxreproduccionListNew) {
                usuarioxreproduccionListNewUsuarioxreproduccionToAttach = em.getReference(usuarioxreproduccionListNewUsuarioxreproduccionToAttach.getClass(), usuarioxreproduccionListNewUsuarioxreproduccionToAttach.getCodregistro());
                attachedUsuarioxreproduccionListNew.add(usuarioxreproduccionListNewUsuarioxreproduccionToAttach);
            }
            usuarioxreproduccionListNew = attachedUsuarioxreproduccionListNew;
            usuarioc.setUsuarioxreproduccionList(usuarioxreproduccionListNew);
            List<Listareproduccion> attachedListareproduccionListNew = new ArrayList<Listareproduccion>();
            for (Listareproduccion listareproduccionListNewListareproduccionToAttach : listareproduccionListNew) {
                listareproduccionListNewListareproduccionToAttach = em.getReference(listareproduccionListNewListareproduccionToAttach.getClass(), listareproduccionListNewListareproduccionToAttach.getCodlista());
                attachedListareproduccionListNew.add(listareproduccionListNewListareproduccionToAttach);
            }
            listareproduccionListNew = attachedListareproduccionListNew;
            usuarioc.setListareproduccionList(listareproduccionListNew);
            usuarioc = em.merge(usuarioc);
            if (codpaisOld != null && !codpaisOld.equals(codpaisNew)) {
                codpaisOld.getUsuariocList().remove(usuarioc);
                codpaisOld = em.merge(codpaisOld);
            }
            if (codpaisNew != null && !codpaisNew.equals(codpaisOld)) {
                codpaisNew.getUsuariocList().add(usuarioc);
                codpaisNew = em.merge(codpaisNew);
            }
            if (tiposuscripcionOld != null && !tiposuscripcionOld.equals(tiposuscripcionNew)) {
                tiposuscripcionOld.getUsuariocList().remove(usuarioc);
                tiposuscripcionOld = em.merge(tiposuscripcionOld);
            }
            if (tiposuscripcionNew != null && !tiposuscripcionNew.equals(tiposuscripcionOld)) {
                tiposuscripcionNew.getUsuariocList().add(usuarioc);
                tiposuscripcionNew = em.merge(tiposuscripcionNew);
            }
            for (Usuariolikes usuariolikesListNewUsuariolikes : usuariolikesListNew) {
                if (!usuariolikesListOld.contains(usuariolikesListNewUsuariolikes)) {
                    Usuarioc oldCodusuarioOfUsuariolikesListNewUsuariolikes = usuariolikesListNewUsuariolikes.getCodusuario();
                    usuariolikesListNewUsuariolikes.setCodusuario(usuarioc);
                    usuariolikesListNewUsuariolikes = em.merge(usuariolikesListNewUsuariolikes);
                    if (oldCodusuarioOfUsuariolikesListNewUsuariolikes != null && !oldCodusuarioOfUsuariolikesListNewUsuariolikes.equals(usuarioc)) {
                        oldCodusuarioOfUsuariolikesListNewUsuariolikes.getUsuariolikesList().remove(usuariolikesListNewUsuariolikes);
                        oldCodusuarioOfUsuariolikesListNewUsuariolikes = em.merge(oldCodusuarioOfUsuariolikesListNewUsuariolikes);
                    }
                }
            }
            for (Usuarioxreproduccion usuarioxreproduccionListNewUsuarioxreproduccion : usuarioxreproduccionListNew) {
                if (!usuarioxreproduccionListOld.contains(usuarioxreproduccionListNewUsuarioxreproduccion)) {
                    Usuarioc oldCodusuarioOfUsuarioxreproduccionListNewUsuarioxreproduccion = usuarioxreproduccionListNewUsuarioxreproduccion.getCodusuario();
                    usuarioxreproduccionListNewUsuarioxreproduccion.setCodusuario(usuarioc);
                    usuarioxreproduccionListNewUsuarioxreproduccion = em.merge(usuarioxreproduccionListNewUsuarioxreproduccion);
                    if (oldCodusuarioOfUsuarioxreproduccionListNewUsuarioxreproduccion != null && !oldCodusuarioOfUsuarioxreproduccionListNewUsuarioxreproduccion.equals(usuarioc)) {
                        oldCodusuarioOfUsuarioxreproduccionListNewUsuarioxreproduccion.getUsuarioxreproduccionList().remove(usuarioxreproduccionListNewUsuarioxreproduccion);
                        oldCodusuarioOfUsuarioxreproduccionListNewUsuarioxreproduccion = em.merge(oldCodusuarioOfUsuarioxreproduccionListNewUsuarioxreproduccion);
                    }
                }
            }
            for (Listareproduccion listareproduccionListNewListareproduccion : listareproduccionListNew) {
                if (!listareproduccionListOld.contains(listareproduccionListNewListareproduccion)) {
                    Usuarioc oldCodusuarioOfListareproduccionListNewListareproduccion = listareproduccionListNewListareproduccion.getCodusuario();
                    listareproduccionListNewListareproduccion.setCodusuario(usuarioc);
                    listareproduccionListNewListareproduccion = em.merge(listareproduccionListNewListareproduccion);
                    if (oldCodusuarioOfListareproduccionListNewListareproduccion != null && !oldCodusuarioOfListareproduccionListNewListareproduccion.equals(usuarioc)) {
                        oldCodusuarioOfListareproduccionListNewListareproduccion.getListareproduccionList().remove(listareproduccionListNewListareproduccion);
                        oldCodusuarioOfListareproduccionListNewListareproduccion = em.merge(oldCodusuarioOfListareproduccionListNewListareproduccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuarioc.getCodusuario();
                if (findUsuarioc(id) == null) {
                    throw new NonexistentEntityException("The usuarioc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarioc usuarioc;
            try {
                usuarioc = em.getReference(Usuarioc.class, id);
                usuarioc.getCodusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioc with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuariolikes> usuariolikesListOrphanCheck = usuarioc.getUsuariolikesList();
            for (Usuariolikes usuariolikesListOrphanCheckUsuariolikes : usuariolikesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarioc (" + usuarioc + ") cannot be destroyed since the Usuariolikes " + usuariolikesListOrphanCheckUsuariolikes + " in its usuariolikesList field has a non-nullable codusuario field.");
            }
            List<Usuarioxreproduccion> usuarioxreproduccionListOrphanCheck = usuarioc.getUsuarioxreproduccionList();
            for (Usuarioxreproduccion usuarioxreproduccionListOrphanCheckUsuarioxreproduccion : usuarioxreproduccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarioc (" + usuarioc + ") cannot be destroyed since the Usuarioxreproduccion " + usuarioxreproduccionListOrphanCheckUsuarioxreproduccion + " in its usuarioxreproduccionList field has a non-nullable codusuario field.");
            }
            List<Listareproduccion> listareproduccionListOrphanCheck = usuarioc.getListareproduccionList();
            for (Listareproduccion listareproduccionListOrphanCheckListareproduccion : listareproduccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarioc (" + usuarioc + ") cannot be destroyed since the Listareproduccion " + listareproduccionListOrphanCheckListareproduccion + " in its listareproduccionList field has a non-nullable codusuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Paisc codpais = usuarioc.getCodpais();
            if (codpais != null) {
                codpais.getUsuariocList().remove(usuarioc);
                codpais = em.merge(codpais);
            }
            Suscripcion tiposuscripcion = usuarioc.getTiposuscripcion();
            if (tiposuscripcion != null) {
                tiposuscripcion.getUsuariocList().remove(usuarioc);
                tiposuscripcion = em.merge(tiposuscripcion);
            }
            em.remove(usuarioc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarioc> findUsuariocEntities() {
        return findUsuariocEntities(true, -1, -1);
    }

    public List<Usuarioc> findUsuariocEntities(int maxResults, int firstResult) {
        return findUsuariocEntities(false, maxResults, firstResult);
    }

    private List<Usuarioc> findUsuariocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarioc.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuarioc findUsuarioc(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarioc.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarioc> rt = cq.from(Usuarioc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

        public Usuarioc getUsuarioActivo(){
        return this.usuarioActivo;
    }
    
    public void setUsuarioActivo(String pUsuario){
        
        usuarioActivo = new Usuarioc();
        usuarioActivo = null;
        
        List<Usuarioc> usuarios = findUsuariocEntities();
        for(Usuarioc actual: usuarios){
            if(actual.getNickname().equalsIgnoreCase(pUsuario)){
                usuarioActivo = actual;
            }   
        }
        
        if(usuarioActivo == null){
            JOptionPane.showMessageDialog(null, "El usuario ingresado no existe en el sistema. Inicie sesión nuevamente", "Error", 0);
        
        }else{
            JOptionPane.showMessageDialog(null, "Bienvenido " + pUsuario, "SpotDeezer", 1);
        }
   
    }
    
    public BigDecimal buscarUltimoUsuario(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Usuarioc> listaUsuarios = findUsuariocEntities();
        if (listaUsuarios.size() != 0){
            for(Usuarioc actual: listaUsuarios){
                if(actual.getCodusuario().compareTo(mayor) == 1){
                    mayor = actual.getCodusuario();
                    ultimoCodigo = actual.getCodusuario();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(899);
        }
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public void crearUsuario(String pCodigo, String pApellido, String pNickname, String pNombre, String pTarjeta, Paisc pPais, Suscripcion pTipo){

        Usuarioc usuario = new Usuarioc();
        
        usuario.setCodusuario(new BigDecimal(pCodigo));
        usuario.setNombre(pNombre);
        usuario.setApellido(pApellido);
        usuario.setNickname(pNickname);
        usuario.setCodpais(pPais);
        usuario.setTiposuscripcion(pTipo);
        if(pTarjeta.equals("")){
            usuario.setNumtarjeta(null);
        }else{
            usuario.setNumtarjeta(Long.parseLong(pTarjeta));
        }

        try {
            create(usuario);
            JOptionPane.showMessageDialog(null, "El usuario se agregó exitosamente", "Ingresar Usuario", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario en la base de datos.", "Error", 0);
        }

    }   
    
}
