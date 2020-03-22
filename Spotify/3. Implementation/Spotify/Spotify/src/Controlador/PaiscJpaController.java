
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Interprete;
import Modelo.Paisc;
import java.util.ArrayList;
import java.util.List;
import Modelo.Usuarioc;
import java.math.BigDecimal;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PaiscJpaController implements Serializable {

    public PaiscJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paisc paisc) throws PreexistingEntityException, Exception {
        if (paisc.getInterpreteList() == null) {
            paisc.setInterpreteList(new ArrayList<Interprete>());
        }
        if (paisc.getUsuariocList() == null) {
            paisc.setUsuariocList(new ArrayList<Usuarioc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Interprete> attachedInterpreteList = new ArrayList<Interprete>();
            for (Interprete interpreteListInterpreteToAttach : paisc.getInterpreteList()) {
                interpreteListInterpreteToAttach = em.getReference(interpreteListInterpreteToAttach.getClass(), interpreteListInterpreteToAttach.getCodinterprete());
                attachedInterpreteList.add(interpreteListInterpreteToAttach);
            }
            paisc.setInterpreteList(attachedInterpreteList);
            List<Usuarioc> attachedUsuariocList = new ArrayList<Usuarioc>();
            for (Usuarioc usuariocListUsuariocToAttach : paisc.getUsuariocList()) {
                usuariocListUsuariocToAttach = em.getReference(usuariocListUsuariocToAttach.getClass(), usuariocListUsuariocToAttach.getCodusuario());
                attachedUsuariocList.add(usuariocListUsuariocToAttach);
            }
            paisc.setUsuariocList(attachedUsuariocList);
            em.persist(paisc);
            for (Interprete interpreteListInterprete : paisc.getInterpreteList()) {
                Paisc oldCodpaisOfInterpreteListInterprete = interpreteListInterprete.getCodpais();
                interpreteListInterprete.setCodpais(paisc);
                interpreteListInterprete = em.merge(interpreteListInterprete);
                if (oldCodpaisOfInterpreteListInterprete != null) {
                    oldCodpaisOfInterpreteListInterprete.getInterpreteList().remove(interpreteListInterprete);
                    oldCodpaisOfInterpreteListInterprete = em.merge(oldCodpaisOfInterpreteListInterprete);
                }
            }
            for (Usuarioc usuariocListUsuarioc : paisc.getUsuariocList()) {
                Paisc oldCodpaisOfUsuariocListUsuarioc = usuariocListUsuarioc.getCodpais();
                usuariocListUsuarioc.setCodpais(paisc);
                usuariocListUsuarioc = em.merge(usuariocListUsuarioc);
                if (oldCodpaisOfUsuariocListUsuarioc != null) {
                    oldCodpaisOfUsuariocListUsuarioc.getUsuariocList().remove(usuariocListUsuarioc);
                    oldCodpaisOfUsuariocListUsuarioc = em.merge(oldCodpaisOfUsuariocListUsuarioc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPaisc(paisc.getCodpais()) != null) {
                throw new PreexistingEntityException("Paisc " + paisc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paisc paisc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paisc persistentPaisc = em.find(Paisc.class, paisc.getCodpais());
            List<Interprete> interpreteListOld = persistentPaisc.getInterpreteList();
            List<Interprete> interpreteListNew = paisc.getInterpreteList();
            List<Usuarioc> usuariocListOld = persistentPaisc.getUsuariocList();
            List<Usuarioc> usuariocListNew = paisc.getUsuariocList();
            List<String> illegalOrphanMessages = null;
            for (Interprete interpreteListOldInterprete : interpreteListOld) {
                if (!interpreteListNew.contains(interpreteListOldInterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Interprete " + interpreteListOldInterprete + " since its codpais field is not nullable.");
                }
            }
            for (Usuarioc usuariocListOldUsuarioc : usuariocListOld) {
                if (!usuariocListNew.contains(usuariocListOldUsuarioc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarioc " + usuariocListOldUsuarioc + " since its codpais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Interprete> attachedInterpreteListNew = new ArrayList<Interprete>();
            for (Interprete interpreteListNewInterpreteToAttach : interpreteListNew) {
                interpreteListNewInterpreteToAttach = em.getReference(interpreteListNewInterpreteToAttach.getClass(), interpreteListNewInterpreteToAttach.getCodinterprete());
                attachedInterpreteListNew.add(interpreteListNewInterpreteToAttach);
            }
            interpreteListNew = attachedInterpreteListNew;
            paisc.setInterpreteList(interpreteListNew);
            List<Usuarioc> attachedUsuariocListNew = new ArrayList<Usuarioc>();
            for (Usuarioc usuariocListNewUsuariocToAttach : usuariocListNew) {
                usuariocListNewUsuariocToAttach = em.getReference(usuariocListNewUsuariocToAttach.getClass(), usuariocListNewUsuariocToAttach.getCodusuario());
                attachedUsuariocListNew.add(usuariocListNewUsuariocToAttach);
            }
            usuariocListNew = attachedUsuariocListNew;
            paisc.setUsuariocList(usuariocListNew);
            paisc = em.merge(paisc);
            for (Interprete interpreteListNewInterprete : interpreteListNew) {
                if (!interpreteListOld.contains(interpreteListNewInterprete)) {
                    Paisc oldCodpaisOfInterpreteListNewInterprete = interpreteListNewInterprete.getCodpais();
                    interpreteListNewInterprete.setCodpais(paisc);
                    interpreteListNewInterprete = em.merge(interpreteListNewInterprete);
                    if (oldCodpaisOfInterpreteListNewInterprete != null && !oldCodpaisOfInterpreteListNewInterprete.equals(paisc)) {
                        oldCodpaisOfInterpreteListNewInterprete.getInterpreteList().remove(interpreteListNewInterprete);
                        oldCodpaisOfInterpreteListNewInterprete = em.merge(oldCodpaisOfInterpreteListNewInterprete);
                    }
                }
            }
            for (Usuarioc usuariocListNewUsuarioc : usuariocListNew) {
                if (!usuariocListOld.contains(usuariocListNewUsuarioc)) {
                    Paisc oldCodpaisOfUsuariocListNewUsuarioc = usuariocListNewUsuarioc.getCodpais();
                    usuariocListNewUsuarioc.setCodpais(paisc);
                    usuariocListNewUsuarioc = em.merge(usuariocListNewUsuarioc);
                    if (oldCodpaisOfUsuariocListNewUsuarioc != null && !oldCodpaisOfUsuariocListNewUsuarioc.equals(paisc)) {
                        oldCodpaisOfUsuariocListNewUsuarioc.getUsuariocList().remove(usuariocListNewUsuarioc);
                        oldCodpaisOfUsuariocListNewUsuarioc = em.merge(oldCodpaisOfUsuariocListNewUsuarioc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = paisc.getCodpais();
                if (findPaisc(id) == null) {
                    throw new NonexistentEntityException("The paisc with id " + id + " no longer exists.");
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
            Paisc paisc;
            try {
                paisc = em.getReference(Paisc.class, id);
                paisc.getCodpais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paisc with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Interprete> interpreteListOrphanCheck = paisc.getInterpreteList();
            for (Interprete interpreteListOrphanCheckInterprete : interpreteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paisc (" + paisc + ") cannot be destroyed since the Interprete " + interpreteListOrphanCheckInterprete + " in its interpreteList field has a non-nullable codpais field.");
            }
            List<Usuarioc> usuariocListOrphanCheck = paisc.getUsuariocList();
            for (Usuarioc usuariocListOrphanCheckUsuarioc : usuariocListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paisc (" + paisc + ") cannot be destroyed since the Usuarioc " + usuariocListOrphanCheckUsuarioc + " in its usuariocList field has a non-nullable codpais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(paisc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paisc> findPaiscEntities() {
        return findPaiscEntities(true, -1, -1);
    }

    public List<Paisc> findPaiscEntities(int maxResults, int firstResult) {
        return findPaiscEntities(false, maxResults, firstResult);
    }

    private List<Paisc> findPaiscEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paisc.class));
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

    public Paisc findPaisc(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paisc.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaiscCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paisc> rt = cq.from(Paisc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Vector<String> buscarPaises(){
        
        List<Paisc> paises = findPaiscEntities();
        Vector<String> listaPaises = new Vector<String>();
        
        for(Paisc actual: paises){
            listaPaises.add(actual.getNombrepais());
        }
        
        return listaPaises;
    }

    
}
