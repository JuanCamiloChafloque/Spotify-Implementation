
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import Modelo.Album;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Empresar;
import Modelo.Cancion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class AlbumJpaController implements Serializable {
    
    //
    //RELACIONES
    //
    private Album album;

    public AlbumJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Album album) throws PreexistingEntityException, Exception {
        if (album.getCancionList() == null) {
            album.setCancionList(new ArrayList<Cancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresar codempresa = album.getCodempresa();
            if (codempresa != null) {
                codempresa = em.getReference(codempresa.getClass(), codempresa.getCodempresa());
                album.setCodempresa(codempresa);
            }
            List<Cancion> attachedCancionList = new ArrayList<Cancion>();
            for (Cancion cancionListCancionToAttach : album.getCancionList()) {
                cancionListCancionToAttach = em.getReference(cancionListCancionToAttach.getClass(), cancionListCancionToAttach.getCodcancion());
                attachedCancionList.add(cancionListCancionToAttach);
            }
            album.setCancionList(attachedCancionList);
            em.persist(album);
            if (codempresa != null) {
                codempresa.getAlbumList().add(album);
                codempresa = em.merge(codempresa);
            }
            for (Cancion cancionListCancion : album.getCancionList()) {
                Album oldCodalbumOfCancionListCancion = cancionListCancion.getCodalbum();
                cancionListCancion.setCodalbum(album);
                cancionListCancion = em.merge(cancionListCancion);
                if (oldCodalbumOfCancionListCancion != null) {
                    oldCodalbumOfCancionListCancion.getCancionList().remove(cancionListCancion);
                    oldCodalbumOfCancionListCancion = em.merge(oldCodalbumOfCancionListCancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlbum(album.getCodalbum()) != null) {
                throw new PreexistingEntityException("Album " + album + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Album album) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album persistentAlbum = em.find(Album.class, album.getCodalbum());
            Empresar codempresaOld = persistentAlbum.getCodempresa();
            Empresar codempresaNew = album.getCodempresa();
            List<Cancion> cancionListOld = persistentAlbum.getCancionList();
            List<Cancion> cancionListNew = album.getCancionList();
            List<String> illegalOrphanMessages = null;
            for (Cancion cancionListOldCancion : cancionListOld) {
                if (!cancionListNew.contains(cancionListOldCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancion " + cancionListOldCancion + " since its codalbum field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codempresaNew != null) {
                codempresaNew = em.getReference(codempresaNew.getClass(), codempresaNew.getCodempresa());
                album.setCodempresa(codempresaNew);
            }
            List<Cancion> attachedCancionListNew = new ArrayList<Cancion>();
            for (Cancion cancionListNewCancionToAttach : cancionListNew) {
                cancionListNewCancionToAttach = em.getReference(cancionListNewCancionToAttach.getClass(), cancionListNewCancionToAttach.getCodcancion());
                attachedCancionListNew.add(cancionListNewCancionToAttach);
            }
            cancionListNew = attachedCancionListNew;
            album.setCancionList(cancionListNew);
            album = em.merge(album);
            if (codempresaOld != null && !codempresaOld.equals(codempresaNew)) {
                codempresaOld.getAlbumList().remove(album);
                codempresaOld = em.merge(codempresaOld);
            }
            if (codempresaNew != null && !codempresaNew.equals(codempresaOld)) {
                codempresaNew.getAlbumList().add(album);
                codempresaNew = em.merge(codempresaNew);
            }
            for (Cancion cancionListNewCancion : cancionListNew) {
                if (!cancionListOld.contains(cancionListNewCancion)) {
                    Album oldCodalbumOfCancionListNewCancion = cancionListNewCancion.getCodalbum();
                    cancionListNewCancion.setCodalbum(album);
                    cancionListNewCancion = em.merge(cancionListNewCancion);
                    if (oldCodalbumOfCancionListNewCancion != null && !oldCodalbumOfCancionListNewCancion.equals(album)) {
                        oldCodalbumOfCancionListNewCancion.getCancionList().remove(cancionListNewCancion);
                        oldCodalbumOfCancionListNewCancion = em.merge(oldCodalbumOfCancionListNewCancion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = album.getCodalbum();
                if (findAlbum(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
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
            Album album;
            try {
                album = em.getReference(Album.class, id);
                album.getCodalbum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The album with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancion> cancionListOrphanCheck = album.getCancionList();
            for (Cancion cancionListOrphanCheckCancion : cancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Album (" + album + ") cannot be destroyed since the Cancion " + cancionListOrphanCheckCancion + " in its cancionList field has a non-nullable codalbum field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresar codempresa = album.getCodempresa();
            if (codempresa != null) {
                codempresa.getAlbumList().remove(album);
                codempresa = em.merge(codempresa);
            }
            em.remove(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Album.class));
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

    public Album findAlbum(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Album.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Album> rt = cq.from(Album.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public Album getAlbum(){
        return this.album;
    }
    
    public BigDecimal buscarUltimoAlbum(){
       
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Album> listaAlbum = findAlbumEntities();
        if (listaAlbum.size() != 0){
            for(Album actual: listaAlbum){
                if (actual.getCodalbum().compareTo(mayor) == 1){
                    mayor = actual.getCodalbum();
                    ultimoCodigo = actual.getCodalbum();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(299);
        }
        
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public void crearAlbum(String pCodigo, String pNombre, Date pFecha, String pTipo, Empresar pEmpresa){
        
        album = new Album();
        
        album.setCodalbum(new BigDecimal(pCodigo));
        album.setNombrealbum(pNombre);
        album.setFechalanzamiento(pFecha);
        album.setTipo(pTipo);
        album.setCodempresa(pEmpresa);

    }
    
    public void confirmarCambiosAlbum(){
        
        try {
            create(album);
            JOptionPane.showMessageDialog(null, "El album se creo exitosamente", "Ingresar Album", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al crear el album", "Error", 0);
        }

    }

    
}
