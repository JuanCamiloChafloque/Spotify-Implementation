
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
import Modelo.Empresar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class EmpresarJpaController implements Serializable {

    public EmpresarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresar empresar) throws PreexistingEntityException, Exception {
        if (empresar.getAlbumList() == null) {
            empresar.setAlbumList(new ArrayList<Album>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Album> attachedAlbumList = new ArrayList<Album>();
            for (Album albumListAlbumToAttach : empresar.getAlbumList()) {
                albumListAlbumToAttach = em.getReference(albumListAlbumToAttach.getClass(), albumListAlbumToAttach.getCodalbum());
                attachedAlbumList.add(albumListAlbumToAttach);
            }
            empresar.setAlbumList(attachedAlbumList);
            em.persist(empresar);
            for (Album albumListAlbum : empresar.getAlbumList()) {
                Empresar oldCodempresaOfAlbumListAlbum = albumListAlbum.getCodempresa();
                albumListAlbum.setCodempresa(empresar);
                albumListAlbum = em.merge(albumListAlbum);
                if (oldCodempresaOfAlbumListAlbum != null) {
                    oldCodempresaOfAlbumListAlbum.getAlbumList().remove(albumListAlbum);
                    oldCodempresaOfAlbumListAlbum = em.merge(oldCodempresaOfAlbumListAlbum);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresar(empresar.getCodempresa()) != null) {
                throw new PreexistingEntityException("Empresar " + empresar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresar empresar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresar persistentEmpresar = em.find(Empresar.class, empresar.getCodempresa());
            List<Album> albumListOld = persistentEmpresar.getAlbumList();
            List<Album> albumListNew = empresar.getAlbumList();
            List<String> illegalOrphanMessages = null;
            for (Album albumListOldAlbum : albumListOld) {
                if (!albumListNew.contains(albumListOldAlbum)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Album " + albumListOldAlbum + " since its codempresa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Album> attachedAlbumListNew = new ArrayList<Album>();
            for (Album albumListNewAlbumToAttach : albumListNew) {
                albumListNewAlbumToAttach = em.getReference(albumListNewAlbumToAttach.getClass(), albumListNewAlbumToAttach.getCodalbum());
                attachedAlbumListNew.add(albumListNewAlbumToAttach);
            }
            albumListNew = attachedAlbumListNew;
            empresar.setAlbumList(albumListNew);
            empresar = em.merge(empresar);
            for (Album albumListNewAlbum : albumListNew) {
                if (!albumListOld.contains(albumListNewAlbum)) {
                    Empresar oldCodempresaOfAlbumListNewAlbum = albumListNewAlbum.getCodempresa();
                    albumListNewAlbum.setCodempresa(empresar);
                    albumListNewAlbum = em.merge(albumListNewAlbum);
                    if (oldCodempresaOfAlbumListNewAlbum != null && !oldCodempresaOfAlbumListNewAlbum.equals(empresar)) {
                        oldCodempresaOfAlbumListNewAlbum.getAlbumList().remove(albumListNewAlbum);
                        oldCodempresaOfAlbumListNewAlbum = em.merge(oldCodempresaOfAlbumListNewAlbum);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = empresar.getCodempresa();
                if (findEmpresar(id) == null) {
                    throw new NonexistentEntityException("The empresar with id " + id + " no longer exists.");
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
            Empresar empresar;
            try {
                empresar = em.getReference(Empresar.class, id);
                empresar.getCodempresa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Album> albumListOrphanCheck = empresar.getAlbumList();
            for (Album albumListOrphanCheckAlbum : albumListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresar (" + empresar + ") cannot be destroyed since the Album " + albumListOrphanCheckAlbum + " in its albumList field has a non-nullable codempresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresar> findEmpresarEntities() {
        return findEmpresarEntities(true, -1, -1);
    }

    public List<Empresar> findEmpresarEntities(int maxResults, int firstResult) {
        return findEmpresarEntities(false, maxResults, firstResult);
    }

    private List<Empresar> findEmpresarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresar.class));
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

    public Empresar findEmpresar(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresar.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresar> rt = cq.from(Empresar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Vector<String> buscarEmpresas(){
        
        List<Empresar> empresas = findEmpresarEntities();
        Vector<String> listaEmpresas = new Vector<String>();
        
        for(Empresar actual: empresas){
            listaEmpresas.add(actual.getNombreempresa());
        }
        
        return listaEmpresas;
    }
    
}
