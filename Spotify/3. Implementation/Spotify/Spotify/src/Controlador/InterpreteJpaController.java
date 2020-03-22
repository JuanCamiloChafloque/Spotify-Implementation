
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
import Modelo.Cancionxinterprete;
import Modelo.Interprete;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class InterpreteJpaController implements Serializable {

    public InterpreteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Interprete interprete) throws PreexistingEntityException, Exception {
        if (interprete.getCancionxinterpreteList() == null) {
            interprete.setCancionxinterpreteList(new ArrayList<Cancionxinterprete>());
        }
        if (interprete.getCancionxinterpreteList1() == null) {
            interprete.setCancionxinterpreteList1(new ArrayList<Cancionxinterprete>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paisc codpais = interprete.getCodpais();
            if (codpais != null) {
                codpais = em.getReference(codpais.getClass(), codpais.getCodpais());
                interprete.setCodpais(codpais);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteList = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListCancionxinterpreteToAttach : interprete.getCancionxinterpreteList()) {
                cancionxinterpreteListCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListCancionxinterpreteToAttach.getClass(), cancionxinterpreteListCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteList.add(cancionxinterpreteListCancionxinterpreteToAttach);
            }
            interprete.setCancionxinterpreteList(attachedCancionxinterpreteList);
            List<Cancionxinterprete> attachedCancionxinterpreteList1 = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteList1CancionxinterpreteToAttach : interprete.getCancionxinterpreteList1()) {
                cancionxinterpreteList1CancionxinterpreteToAttach = em.getReference(cancionxinterpreteList1CancionxinterpreteToAttach.getClass(), cancionxinterpreteList1CancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteList1.add(cancionxinterpreteList1CancionxinterpreteToAttach);
            }
            interprete.setCancionxinterpreteList1(attachedCancionxinterpreteList1);
            em.persist(interprete);
            if (codpais != null) {
                codpais.getInterpreteList().add(interprete);
                codpais = em.merge(codpais);
            }
            for (Cancionxinterprete cancionxinterpreteListCancionxinterprete : interprete.getCancionxinterpreteList()) {
                Interprete oldInterpreteOfCancionxinterpreteListCancionxinterprete = cancionxinterpreteListCancionxinterprete.getInterprete();
                cancionxinterpreteListCancionxinterprete.setInterprete(interprete);
                cancionxinterpreteListCancionxinterprete = em.merge(cancionxinterpreteListCancionxinterprete);
                if (oldInterpreteOfCancionxinterpreteListCancionxinterprete != null) {
                    oldInterpreteOfCancionxinterpreteListCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListCancionxinterprete);
                    oldInterpreteOfCancionxinterpreteListCancionxinterprete = em.merge(oldInterpreteOfCancionxinterpreteListCancionxinterprete);
                }
            }
            for (Cancionxinterprete cancionxinterpreteList1Cancionxinterprete : interprete.getCancionxinterpreteList1()) {
                Interprete oldCodprincipalOfCancionxinterpreteList1Cancionxinterprete = cancionxinterpreteList1Cancionxinterprete.getCodprincipal();
                cancionxinterpreteList1Cancionxinterprete.setCodprincipal(interprete);
                cancionxinterpreteList1Cancionxinterprete = em.merge(cancionxinterpreteList1Cancionxinterprete);
                if (oldCodprincipalOfCancionxinterpreteList1Cancionxinterprete != null) {
                    oldCodprincipalOfCancionxinterpreteList1Cancionxinterprete.getCancionxinterpreteList1().remove(cancionxinterpreteList1Cancionxinterprete);
                    oldCodprincipalOfCancionxinterpreteList1Cancionxinterprete = em.merge(oldCodprincipalOfCancionxinterpreteList1Cancionxinterprete);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInterprete(interprete.getCodinterprete()) != null) {
                throw new PreexistingEntityException("Interprete " + interprete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interprete interprete) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interprete persistentInterprete = em.find(Interprete.class, interprete.getCodinterprete());
            Paisc codpaisOld = persistentInterprete.getCodpais();
            Paisc codpaisNew = interprete.getCodpais();
            List<Cancionxinterprete> cancionxinterpreteListOld = persistentInterprete.getCancionxinterpreteList();
            List<Cancionxinterprete> cancionxinterpreteListNew = interprete.getCancionxinterpreteList();
            List<Cancionxinterprete> cancionxinterpreteList1Old = persistentInterprete.getCancionxinterpreteList1();
            List<Cancionxinterprete> cancionxinterpreteList1New = interprete.getCancionxinterpreteList1();
            List<String> illegalOrphanMessages = null;
            for (Cancionxinterprete cancionxinterpreteListOldCancionxinterprete : cancionxinterpreteListOld) {
                if (!cancionxinterpreteListNew.contains(cancionxinterpreteListOldCancionxinterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxinterprete " + cancionxinterpreteListOldCancionxinterprete + " since its interprete field is not nullable.");
                }
            }
            for (Cancionxinterprete cancionxinterpreteList1OldCancionxinterprete : cancionxinterpreteList1Old) {
                if (!cancionxinterpreteList1New.contains(cancionxinterpreteList1OldCancionxinterprete)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxinterprete " + cancionxinterpreteList1OldCancionxinterprete + " since its codprincipal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codpaisNew != null) {
                codpaisNew = em.getReference(codpaisNew.getClass(), codpaisNew.getCodpais());
                interprete.setCodpais(codpaisNew);
            }
            List<Cancionxinterprete> attachedCancionxinterpreteListNew = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterpreteToAttach : cancionxinterpreteListNew) {
                cancionxinterpreteListNewCancionxinterpreteToAttach = em.getReference(cancionxinterpreteListNewCancionxinterpreteToAttach.getClass(), cancionxinterpreteListNewCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteListNew.add(cancionxinterpreteListNewCancionxinterpreteToAttach);
            }
            cancionxinterpreteListNew = attachedCancionxinterpreteListNew;
            interprete.setCancionxinterpreteList(cancionxinterpreteListNew);
            List<Cancionxinterprete> attachedCancionxinterpreteList1New = new ArrayList<Cancionxinterprete>();
            for (Cancionxinterprete cancionxinterpreteList1NewCancionxinterpreteToAttach : cancionxinterpreteList1New) {
                cancionxinterpreteList1NewCancionxinterpreteToAttach = em.getReference(cancionxinterpreteList1NewCancionxinterpreteToAttach.getClass(), cancionxinterpreteList1NewCancionxinterpreteToAttach.getCancionxinterpretePK());
                attachedCancionxinterpreteList1New.add(cancionxinterpreteList1NewCancionxinterpreteToAttach);
            }
            cancionxinterpreteList1New = attachedCancionxinterpreteList1New;
            interprete.setCancionxinterpreteList1(cancionxinterpreteList1New);
            interprete = em.merge(interprete);
            if (codpaisOld != null && !codpaisOld.equals(codpaisNew)) {
                codpaisOld.getInterpreteList().remove(interprete);
                codpaisOld = em.merge(codpaisOld);
            }
            if (codpaisNew != null && !codpaisNew.equals(codpaisOld)) {
                codpaisNew.getInterpreteList().add(interprete);
                codpaisNew = em.merge(codpaisNew);
            }
            for (Cancionxinterprete cancionxinterpreteListNewCancionxinterprete : cancionxinterpreteListNew) {
                if (!cancionxinterpreteListOld.contains(cancionxinterpreteListNewCancionxinterprete)) {
                    Interprete oldInterpreteOfCancionxinterpreteListNewCancionxinterprete = cancionxinterpreteListNewCancionxinterprete.getInterprete();
                    cancionxinterpreteListNewCancionxinterprete.setInterprete(interprete);
                    cancionxinterpreteListNewCancionxinterprete = em.merge(cancionxinterpreteListNewCancionxinterprete);
                    if (oldInterpreteOfCancionxinterpreteListNewCancionxinterprete != null && !oldInterpreteOfCancionxinterpreteListNewCancionxinterprete.equals(interprete)) {
                        oldInterpreteOfCancionxinterpreteListNewCancionxinterprete.getCancionxinterpreteList().remove(cancionxinterpreteListNewCancionxinterprete);
                        oldInterpreteOfCancionxinterpreteListNewCancionxinterprete = em.merge(oldInterpreteOfCancionxinterpreteListNewCancionxinterprete);
                    }
                }
            }
            for (Cancionxinterprete cancionxinterpreteList1NewCancionxinterprete : cancionxinterpreteList1New) {
                if (!cancionxinterpreteList1Old.contains(cancionxinterpreteList1NewCancionxinterprete)) {
                    Interprete oldCodprincipalOfCancionxinterpreteList1NewCancionxinterprete = cancionxinterpreteList1NewCancionxinterprete.getCodprincipal();
                    cancionxinterpreteList1NewCancionxinterprete.setCodprincipal(interprete);
                    cancionxinterpreteList1NewCancionxinterprete = em.merge(cancionxinterpreteList1NewCancionxinterprete);
                    if (oldCodprincipalOfCancionxinterpreteList1NewCancionxinterprete != null && !oldCodprincipalOfCancionxinterpreteList1NewCancionxinterprete.equals(interprete)) {
                        oldCodprincipalOfCancionxinterpreteList1NewCancionxinterprete.getCancionxinterpreteList1().remove(cancionxinterpreteList1NewCancionxinterprete);
                        oldCodprincipalOfCancionxinterpreteList1NewCancionxinterprete = em.merge(oldCodprincipalOfCancionxinterpreteList1NewCancionxinterprete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = interprete.getCodinterprete();
                if (findInterprete(id) == null) {
                    throw new NonexistentEntityException("The interprete with id " + id + " no longer exists.");
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
            Interprete interprete;
            try {
                interprete = em.getReference(Interprete.class, id);
                interprete.getCodinterprete();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interprete with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxinterprete> cancionxinterpreteListOrphanCheck = interprete.getCancionxinterpreteList();
            for (Cancionxinterprete cancionxinterpreteListOrphanCheckCancionxinterprete : cancionxinterpreteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Interprete (" + interprete + ") cannot be destroyed since the Cancionxinterprete " + cancionxinterpreteListOrphanCheckCancionxinterprete + " in its cancionxinterpreteList field has a non-nullable interprete field.");
            }
            List<Cancionxinterprete> cancionxinterpreteList1OrphanCheck = interprete.getCancionxinterpreteList1();
            for (Cancionxinterprete cancionxinterpreteList1OrphanCheckCancionxinterprete : cancionxinterpreteList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Interprete (" + interprete + ") cannot be destroyed since the Cancionxinterprete " + cancionxinterpreteList1OrphanCheckCancionxinterprete + " in its cancionxinterpreteList1 field has a non-nullable codprincipal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Paisc codpais = interprete.getCodpais();
            if (codpais != null) {
                codpais.getInterpreteList().remove(interprete);
                codpais = em.merge(codpais);
            }
            em.remove(interprete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Interprete> findInterpreteEntities() {
        return findInterpreteEntities(true, -1, -1);
    }

    public List<Interprete> findInterpreteEntities(int maxResults, int firstResult) {
        return findInterpreteEntities(false, maxResults, firstResult);
    }

    private List<Interprete> findInterpreteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interprete.class));
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

    public Interprete findInterprete(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Interprete.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterpreteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interprete> rt = cq.from(Interprete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public BigDecimal buscarUltimoInterprete(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Interprete> listaInterpretes = findInterpreteEntities();
        if (listaInterpretes.size() != 0){
            for(Interprete actual: listaInterpretes){
                if(actual.getCodinterprete().compareTo(mayor) == 1){
                    mayor = actual.getCodinterprete();
                    ultimoCodigo = actual.getCodinterprete();
                }
                
            } 
        }else{
            ultimoCodigo = new BigDecimal(599);
        }
        
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    
    public void crearArtista(String pCodigo, String pNomReal, String pNomArtistico, Paisc pPais){

        Interprete interprete = new Interprete();
        interprete.setNombreartistico(pNomArtistico);
        interprete.setNombrereal(pNomReal);
        interprete.setCodinterprete(new BigDecimal(pCodigo));
        interprete.setCodpais(pPais);
        try {
            create(interprete);
            JOptionPane.showMessageDialog(null, "El artista se agregó exitosamente", "Ingresar Artista", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el artista en la base de datos.", "Error", 0);
        }

    }    
    
}
