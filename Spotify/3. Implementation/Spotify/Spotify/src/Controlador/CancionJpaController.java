
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Album;
import Modelo.Cancion;
import Modelo.Genero;
import Modelo.Cancionxinterprete;
import java.util.ArrayList;
import java.util.List;
import Modelo.Cancionxlista;
import Modelo.Usuarioxreproduccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class CancionJpaController implements Serializable {

    //
    //RELACIONES
    //
    private List<Cancion> listaCanciones = new ArrayList<Cancion>();
    
    public CancionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancion cancion) throws PreexistingEntityException, Exception {
        if (cancion.getCancionxinterpreteList() == null) {
            cancion.setCancionxinterpreteList(new ArrayList<Cancionxinterprete>());
        }
        if (cancion.getCancionxlistaList() == null) {
            cancion.setCancionxlistaList(new ArrayList<Cancionxlista>());
        }
        if (cancion.getUsuarioxreproduccionList() == null) {
            cancion.setUsuarioxreproduccionList(new ArrayList<Usuarioxreproduccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album codalbum = cancion.getCodalbum();
            if (codalbum != null) {
                codalbum = em.getReference(codalbum.getClass(), codalbum.getCodalbum());
                cancion.setCodalbum(codalbum);
            }
            Genero codgenero = cancion.getCodgenero();
            if (codgenero != null) {
                codgenero = em.getReference(codgenero.getClass(), codgenero.getCodgenero());
                cancion.setCodgenero(codgenero);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteList = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListCancionxinterpreteToAttach : cancion.getCancionxinterpreteList()) {
                cancionxinterpreteListCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListCancionxinterpreteToAttach.getClass(), cancionxinterpreteListCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteList.add(cancionxinterpreteListCancionxinterpreteToAttach);
            }
            cancion.setCancionxinterpreteList(attachedCancionxinterpreteList);
            List<Cancionxlista> attachedCancionxlistaList = new ArrayList<Cancionxlista>();
            for (Cancionxlista cancionxlistaListCancionxlistaToAttach : cancion.getCancionxlistaList()) {
                cancionxlistaListCancionxlistaToAttach = em.getReference(cancionxlistaListCancionxlistaToAttach.getClass(), cancionxlistaListCancionxlistaToAttach.getCodregistro());
                attachedCancionxlistaList.add(cancionxlistaListCancionxlistaToAttach);
            }
            cancion.setCancionxlistaList(attachedCancionxlistaList);
            List<Usuarioxreproduccion> attachedUsuarioxreproduccionList = new ArrayList<Usuarioxreproduccion>();
            for (Usuarioxreproduccion usuarioxreproduccionListUsuarioxreproduccionToAttach : cancion.getUsuarioxreproduccionList()) {
                usuarioxreproduccionListUsuarioxreproduccionToAttach = em.getReference(usuarioxreproduccionListUsuarioxreproduccionToAttach.getClass(), usuarioxreproduccionListUsuarioxreproduccionToAttach.getCodregistro());
                attachedUsuarioxreproduccionList.add(usuarioxreproduccionListUsuarioxreproduccionToAttach);
            }
            cancion.setUsuarioxreproduccionList(attachedUsuarioxreproduccionList);
            em.persist(cancion);
            if (codalbum != null) {
                codalbum.getCancionList().add(cancion);
                codalbum = em.merge(codalbum);
            }
            if (codgenero != null) {
                codgenero.getCancionList().add(cancion);
                codgenero = em.merge(codgenero);
            }
            for (Cancionxinterprete cancionxinterpreteListCancionxinterprete : cancion.getCancionxinterpreteList()) {
                Cancion oldCancionOfCancionxinterpreteListCancionxinterprete = cancionxinterpreteListCancionxinterprete.getCancion();
                cancionxinterpreteListCancionxinterprete.setCancion(cancion);
                cancionxinterpreteListCancionxinterprete = em.merge(cancionxinterpreteListCancionxinterprete);
                if (oldCancionOfCancionxinterpreteListCancionxinterprete != null) {
                    oldCancionOfCancionxinterpreteListCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListCancionxinterprete);
                    oldCancionOfCancionxinterpreteListCancionxinterprete = em.merge(oldCancionOfCancionxinterpreteListCancionxinterprete);
                }
            }
            for (Cancionxlista cancionxlistaListCancionxlista : cancion.getCancionxlistaList()) {
                Cancion oldCodcancionOfCancionxlistaListCancionxlista = cancionxlistaListCancionxlista.getCodcancion();
                cancionxlistaListCancionxlista.setCodcancion(cancion);
                cancionxlistaListCancionxlista = em.merge(cancionxlistaListCancionxlista);
                if (oldCodcancionOfCancionxlistaListCancionxlista != null) {
                    oldCodcancionOfCancionxlistaListCancionxlista.getCancionxlistaList().remove(cancionxlistaListCancionxlista);
                    oldCodcancionOfCancionxlistaListCancionxlista = em.merge(oldCodcancionOfCancionxlistaListCancionxlista);
                }
            }
            for (Usuarioxreproduccion usuarioxreproduccionListUsuarioxreproduccion : cancion.getUsuarioxreproduccionList()) {
                Cancion oldCodcancionOfUsuarioxreproduccionListUsuarioxreproduccion = usuarioxreproduccionListUsuarioxreproduccion.getCodcancion();
                usuarioxreproduccionListUsuarioxreproduccion.setCodcancion(cancion);
                usuarioxreproduccionListUsuarioxreproduccion = em.merge(usuarioxreproduccionListUsuarioxreproduccion);
                if (oldCodcancionOfUsuarioxreproduccionListUsuarioxreproduccion != null) {
                    oldCodcancionOfUsuarioxreproduccionListUsuarioxreproduccion.getUsuarioxreproduccionList().remove(usuarioxreproduccionListUsuarioxreproduccion);
                    oldCodcancionOfUsuarioxreproduccionListUsuarioxreproduccion = em.merge(oldCodcancionOfUsuarioxreproduccionListUsuarioxreproduccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancion(cancion.getCodcancion()) != null) {
                throw new PreexistingEntityException("Cancion " + cancion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancion cancion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion persistentCancion = em.find(Cancion.class, cancion.getCodcancion());
            Album codalbumOld = persistentCancion.getCodalbum();
            Album codalbumNew = cancion.getCodalbum();
            Genero codgeneroOld = persistentCancion.getCodgenero();
            Genero codgeneroNew = cancion.getCodgenero();
            List<Cancionxinterprete> cancionxinterpreteListOld = persistentCancion.getCancionxinterpreteList();
            List<Cancionxinterprete> cancionxinterpreteListNew = cancion.getCancionxinterpreteList();
            List<Cancionxlista> cancionxlistaListOld = persistentCancion.getCancionxlistaList();
            List<Cancionxlista> cancionxlistaListNew = cancion.getCancionxlistaList();
            List<Usuarioxreproduccion> usuarioxreproduccionListOld = persistentCancion.getUsuarioxreproduccionList();
            List<Usuarioxreproduccion> usuarioxreproduccionListNew = cancion.getUsuarioxreproduccionList();
            List<String> illegalOrphanMessages = null;
            for (Cancionxinterprete cancionxinterpreteListOldCancionxinterprete : cancionxinterpreteListOld) {
                if (!cancionxinterpreteListNew.contains(cancionxinterpreteListOldCancionxinterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxinterprete " + cancionxinterpreteListOldCancionxinterprete + " since its cancion field is not nullable.");
                }
            }
            for (Cancionxlista cancionxlistaListOldCancionxlista : cancionxlistaListOld) {
                if (!cancionxlistaListNew.contains(cancionxlistaListOldCancionxlista)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxlista " + cancionxlistaListOldCancionxlista + " since its codcancion field is not nullable.");
                }
            }
            for (Usuarioxreproduccion usuarioxreproduccionListOldUsuarioxreproduccion : usuarioxreproduccionListOld) {
                if (!usuarioxreproduccionListNew.contains(usuarioxreproduccionListOldUsuarioxreproduccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarioxreproduccion " + usuarioxreproduccionListOldUsuarioxreproduccion + " since its codcancion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codalbumNew != null) {
                codalbumNew = em.getReference(codalbumNew.getClass(), codalbumNew.getCodalbum());
                cancion.setCodalbum(codalbumNew);
            }
            if (codgeneroNew != null) {
                codgeneroNew = em.getReference(codgeneroNew.getClass(), codgeneroNew.getCodgenero());
                cancion.setCodgenero(codgeneroNew);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteListNew = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterpreteToAttach : cancionxinterpreteListNew) {
                cancionxinterpreteListNewCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListNewCancionxinterpreteToAttach.getClass(), cancionxinterpreteListNewCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteListNew.add(cancionxinterpreteListNewCancionxinterpreteToAttach);
            }
            cancionxinterpreteListNew = attachedCancionxinterpreteListNew;
            cancion.setCancionxinterpreteList(cancionxinterpreteListNew);
            List<Cancionxlista> attachedCancionxlistaListNew = new ArrayList<Cancionxlista>();
            for (Cancionxlista cancionxlistaListNewCancionxlistaToAttach : cancionxlistaListNew) {
                cancionxlistaListNewCancionxlistaToAttach = em.getReference(cancionxlistaListNewCancionxlistaToAttach.getClass(), cancionxlistaListNewCancionxlistaToAttach.getCodregistro());
                attachedCancionxlistaListNew.add(cancionxlistaListNewCancionxlistaToAttach);
            }
            cancionxlistaListNew = attachedCancionxlistaListNew;
            cancion.setCancionxlistaList(cancionxlistaListNew);
            List<Usuarioxreproduccion> attachedUsuarioxreproduccionListNew = new ArrayList<Usuarioxreproduccion>();
            for (Usuarioxreproduccion usuarioxreproduccionListNewUsuarioxreproduccionToAttach : usuarioxreproduccionListNew) {
                usuarioxreproduccionListNewUsuarioxreproduccionToAttach = em.getReference(usuarioxreproduccionListNewUsuarioxreproduccionToAttach.getClass(), usuarioxreproduccionListNewUsuarioxreproduccionToAttach.getCodregistro());
                attachedUsuarioxreproduccionListNew.add(usuarioxreproduccionListNewUsuarioxreproduccionToAttach);
            }
            usuarioxreproduccionListNew = attachedUsuarioxreproduccionListNew;
            cancion.setUsuarioxreproduccionList(usuarioxreproduccionListNew);
            cancion = em.merge(cancion);
            if (codalbumOld != null && !codalbumOld.equals(codalbumNew)) {
                codalbumOld.getCancionList().remove(cancion);
                codalbumOld = em.merge(codalbumOld);
            }
            if (codalbumNew != null && !codalbumNew.equals(codalbumOld)) {
                codalbumNew.getCancionList().add(cancion);
                codalbumNew = em.merge(codalbumNew);
            }
            if (codgeneroOld != null && !codgeneroOld.equals(codgeneroNew)) {
                codgeneroOld.getCancionList().remove(cancion);
                codgeneroOld = em.merge(codgeneroOld);
            }
            if (codgeneroNew != null && !codgeneroNew.equals(codgeneroOld)) {
                codgeneroNew.getCancionList().add(cancion);
                codgeneroNew = em.merge(codgeneroNew);
            }
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterprete : cancionxinterpreteListNew) {
                if (!cancionxinterpreteListOld.contains(cancionxinterpreteListNewCancionxinterprete)) {
                    Cancion oldCancionOfCancionxinterpreteListNewCancionxinterprete = cancionxinterpreteListNewCancionxinterprete.getCancion();
                    cancionxinterpreteListNewCancionxinterprete.setCancion(cancion);
                    cancionxinterpreteListNewCancionxinterprete = em.merge(cancionxinterpreteListNewCancionxinterprete);
                    if (oldCancionOfCancionxinterpreteListNewCancionxinterprete != null && !oldCancionOfCancionxinterpreteListNewCancionxinterprete.equals(cancion)) {
                        oldCancionOfCancionxinterpreteListNewCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListNewCancionxinterprete);
                        oldCancionOfCancionxinterpreteListNewCancionxinterprete = em.merge(oldCancionOfCancionxinterpreteListNewCancionxinterprete);
                    }
                }
            }
            for (Cancionxlista cancionxlistaListNewCancionxlista : cancionxlistaListNew) {
                if (!cancionxlistaListOld.contains(cancionxlistaListNewCancionxlista)) {
                    Cancion oldCodcancionOfCancionxlistaListNewCancionxlista = cancionxlistaListNewCancionxlista.getCodcancion();
                    cancionxlistaListNewCancionxlista.setCodcancion(cancion);
                    cancionxlistaListNewCancionxlista = em.merge(cancionxlistaListNewCancionxlista);
                    if (oldCodcancionOfCancionxlistaListNewCancionxlista != null && !oldCodcancionOfCancionxlistaListNewCancionxlista.equals(cancion)) {
                        oldCodcancionOfCancionxlistaListNewCancionxlista.getCancionxlistaList().remove(cancionxlistaListNewCancionxlista);
                        oldCodcancionOfCancionxlistaListNewCancionxlista = em.merge(oldCodcancionOfCancionxlistaListNewCancionxlista);
                    }
                }
            }
            for (Usuarioxreproduccion usuarioxreproduccionListNewUsuarioxreproduccion : usuarioxreproduccionListNew) {
                if (!usuarioxreproduccionListOld.contains(usuarioxreproduccionListNewUsuarioxreproduccion)) {
                    Cancion oldCodcancionOfUsuarioxreproduccionListNewUsuarioxreproduccion = usuarioxreproduccionListNewUsuarioxreproduccion.getCodcancion();
                    usuarioxreproduccionListNewUsuarioxreproduccion.setCodcancion(cancion);
                    usuarioxreproduccionListNewUsuarioxreproduccion = em.merge(usuarioxreproduccionListNewUsuarioxreproduccion);
                    if (oldCodcancionOfUsuarioxreproduccionListNewUsuarioxreproduccion != null && !oldCodcancionOfUsuarioxreproduccionListNewUsuarioxreproduccion.equals(cancion)) {
                        oldCodcancionOfUsuarioxreproduccionListNewUsuarioxreproduccion.getUsuarioxreproduccionList().remove(usuarioxreproduccionListNewUsuarioxreproduccion);
                        oldCodcancionOfUsuarioxreproduccionListNewUsuarioxreproduccion = em.merge(oldCodcancionOfUsuarioxreproduccionListNewUsuarioxreproduccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = cancion.getCodcancion();
                if (findCancion(id) == null) {
                    throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.");
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
            Cancion cancion;
            try {
                cancion = em.getReference(Cancion.class, id);
                cancion.getCodcancion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxinterprete> cancionxinterpreteListOrphanCheck = cancion.getCancionxinterpreteList();
            for (Cancionxinterprete cancionxinterpreteListOrphanCheckCancionxinterprete : cancionxinterpreteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Cancionxinterprete " + cancionxinterpreteListOrphanCheckCancionxinterprete + " in its cancionxinterpreteList field has a non-nullable cancion field.");
            }
            List<Cancionxlista> cancionxlistaListOrphanCheck = cancion.getCancionxlistaList();
            for (Cancionxlista cancionxlistaListOrphanCheckCancionxlista : cancionxlistaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Cancionxlista " + cancionxlistaListOrphanCheckCancionxlista + " in its cancionxlistaList field has a non-nullable codcancion field.");
            }
            List<Usuarioxreproduccion> usuarioxreproduccionListOrphanCheck = cancion.getUsuarioxreproduccionList();
            for (Usuarioxreproduccion usuarioxreproduccionListOrphanCheckUsuarioxreproduccion : usuarioxreproduccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Usuarioxreproduccion " + usuarioxreproduccionListOrphanCheckUsuarioxreproduccion + " in its usuarioxreproduccionList field has a non-nullable codcancion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Album codalbum = cancion.getCodalbum();
            if (codalbum != null) {
                codalbum.getCancionList().remove(cancion);
                codalbum = em.merge(codalbum);
            }
            Genero codgenero = cancion.getCodgenero();
            if (codgenero != null) {
                codgenero.getCancionList().remove(cancion);
                codgenero = em.merge(codgenero);
            }
            em.remove(cancion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancion> findCancionEntities() {
        return findCancionEntities(true, -1, -1);
    }

    public List<Cancion> findCancionEntities(int maxResults, int firstResult) {
        return findCancionEntities(false, maxResults, firstResult);
    }

    private List<Cancion> findCancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancion.class));
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

    public Cancion findCancion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancion> rt = cq.from(Cancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public List<Cancion> getLista(){
        return this.listaCanciones;
    }
    
    public BigDecimal buscarUltimaCancion(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Cancion> listaCanciones = findCancionEntities();
        if (listaCanciones.size() != 0){
            for(Cancion actual: listaCanciones){
                if(actual.getCodcancion().compareTo(mayor) == 1){
                    mayor = actual.getCodcancion();
                    ultimoCodigo = actual.getCodcancion();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(999);
        }
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public Cancion crearCancion(String pCodigo, String pNombre, Date pDuracion, Album pAlbum, Genero pGenero){
        
        
        Cancion cancion = new Cancion();
        cancion.setCodcancion(new BigDecimal(pCodigo));
        cancion.setTitulo(pNombre);
        cancion.setCodalbum(pAlbum);
        cancion.setCodgenero(pGenero);
        cancion.setDuracion(pDuracion);
        
        listaCanciones.add(cancion);
        
        return cancion;
    }
    
    public List<Cancion> busquedaCanciones(String pBusqueda){
        
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT e FROM Cancion e WHERE e.titulo LIKE CONCAT( '%', :pBusq, '%') ");
        consulta.setParameter("pBusq", pBusqueda);
        
        List<Cancion> resultado = consulta.getResultList();
        
        return resultado;
        
    }
    
    public void confirmarCambiosCanciones(){
        
        try {
            for(Cancion actual: listaCanciones){
                create(actual);
            }
        JOptionPane.showMessageDialog(null, "Las canciones se agregaron exitosamente al album", "Ingresar Canciones", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudieron agregar las canciones al album.", "Error", 0);
            
            
        }
        
    }
    
    public void cancelarCambiosCanciones(){
        
        listaCanciones.clear();
        
    }
    
}
