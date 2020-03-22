
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import Modelo.Suscripcion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Usuarioc;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class SuscripcionJpaController implements Serializable {

    public SuscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suscripcion suscripcion) throws PreexistingEntityException, Exception {
        if (suscripcion.getUsuariocList() == null) {
            suscripcion.setUsuariocList(new ArrayList<Usuarioc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuarioc> attachedUsuariocList = new ArrayList<Usuarioc>();
            for (Usuarioc usuariocListUsuariocToAttach : suscripcion.getUsuariocList()) {
                usuariocListUsuariocToAttach = em.getReference(usuariocListUsuariocToAttach.getClass(), usuariocListUsuariocToAttach.getCodusuario());
                attachedUsuariocList.add(usuariocListUsuariocToAttach);
            }
            suscripcion.setUsuariocList(attachedUsuariocList);
            em.persist(suscripcion);
            for (Usuarioc usuariocListUsuarioc : suscripcion.getUsuariocList()) {
                Suscripcion oldTiposuscripcionOfUsuariocListUsuarioc = usuariocListUsuarioc.getTiposuscripcion();
                usuariocListUsuarioc.setTiposuscripcion(suscripcion);
                usuariocListUsuarioc = em.merge(usuariocListUsuarioc);
                if (oldTiposuscripcionOfUsuariocListUsuarioc != null) {
                    oldTiposuscripcionOfUsuariocListUsuarioc.getUsuariocList().remove(usuariocListUsuarioc);
                    oldTiposuscripcionOfUsuariocListUsuarioc = em.merge(oldTiposuscripcionOfUsuariocListUsuarioc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuscripcion(suscripcion.getTiposuscripcion()) != null) {
                throw new PreexistingEntityException("Suscripcion " + suscripcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suscripcion suscripcion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion persistentSuscripcion = em.find(Suscripcion.class, suscripcion.getTiposuscripcion());
            List<Usuarioc> usuariocListOld = persistentSuscripcion.getUsuariocList();
            List<Usuarioc> usuariocListNew = suscripcion.getUsuariocList();
            List<String> illegalOrphanMessages = null;
            for (Usuarioc usuariocListOldUsuarioc : usuariocListOld) {
                if (!usuariocListNew.contains(usuariocListOldUsuarioc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarioc " + usuariocListOldUsuarioc + " since its tiposuscripcion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuarioc> attachedUsuariocListNew = new ArrayList<Usuarioc>();
            for (Usuarioc usuariocListNewUsuariocToAttach : usuariocListNew) {
                usuariocListNewUsuariocToAttach = em.getReference(usuariocListNewUsuariocToAttach.getClass(), usuariocListNewUsuariocToAttach.getCodusuario());
                attachedUsuariocListNew.add(usuariocListNewUsuariocToAttach);
            }
            usuariocListNew = attachedUsuariocListNew;
            suscripcion.setUsuariocList(usuariocListNew);
            suscripcion = em.merge(suscripcion);
            for (Usuarioc usuariocListNewUsuarioc : usuariocListNew) {
                if (!usuariocListOld.contains(usuariocListNewUsuarioc)) {
                    Suscripcion oldTiposuscripcionOfUsuariocListNewUsuarioc = usuariocListNewUsuarioc.getTiposuscripcion();
                    usuariocListNewUsuarioc.setTiposuscripcion(suscripcion);
                    usuariocListNewUsuarioc = em.merge(usuariocListNewUsuarioc);
                    if (oldTiposuscripcionOfUsuariocListNewUsuarioc != null && !oldTiposuscripcionOfUsuariocListNewUsuarioc.equals(suscripcion)) {
                        oldTiposuscripcionOfUsuariocListNewUsuarioc.getUsuariocList().remove(usuariocListNewUsuarioc);
                        oldTiposuscripcionOfUsuariocListNewUsuarioc = em.merge(oldTiposuscripcionOfUsuariocListNewUsuarioc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = suscripcion.getTiposuscripcion();
                if (findSuscripcion(id) == null) {
                    throw new NonexistentEntityException("The suscripcion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion suscripcion;
            try {
                suscripcion = em.getReference(Suscripcion.class, id);
                suscripcion.getTiposuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suscripcion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuarioc> usuariocListOrphanCheck = suscripcion.getUsuariocList();
            for (Usuarioc usuariocListOrphanCheckUsuarioc : usuariocListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Usuarioc " + usuariocListOrphanCheckUsuarioc + " in its usuariocList field has a non-nullable tiposuscripcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(suscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Suscripcion> findSuscripcionEntities() {
        return findSuscripcionEntities(true, -1, -1);
    }

    public List<Suscripcion> findSuscripcionEntities(int maxResults, int firstResult) {
        return findSuscripcionEntities(false, maxResults, firstResult);
    }

    private List<Suscripcion> findSuscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suscripcion.class));
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

    public Suscripcion findSuscripcion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suscripcion> rt = cq.from(Suscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Vector<String> buscarSuscripciones(){
        
        List<Suscripcion> suscripciones = findSuscripcionEntities();
        Vector<String> tiposSuscripciones = new Vector<String>();
        
        for(Suscripcion actual: suscripciones){
            tiposSuscripciones.add(actual.getTiposuscripcion());
        }
        
        return tiposSuscripciones;
    }

}
