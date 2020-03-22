
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Cancion;
import Modelo.Cancionxlista;
import Modelo.Listareproduccion;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class CancionxlistaJpaController implements Serializable {

    public CancionxlistaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancionxlista cancionxlista) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion codcancion = cancionxlista.getCodcancion();
            if (codcancion != null) {
                codcancion = em.getReference(codcancion.getClass(), codcancion.getCodcancion());
                cancionxlista.setCodcancion(codcancion);
            }
            Listareproduccion codlista = cancionxlista.getCodlista();
            if (codlista != null) {
                codlista = em.getReference(codlista.getClass(), codlista.getCodlista());
                cancionxlista.setCodlista(codlista);
            }
            em.persist(cancionxlista);
            if (codcancion != null) {
                codcancion.getCancionxlistaList().add(cancionxlista);
                codcancion = em.merge(codcancion);
            }
            if (codlista != null) {
                codlista.getCancionxlistaList().add(cancionxlista);
                codlista = em.merge(codlista);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancionxlista(cancionxlista.getCodregistro()) != null) {
                throw new PreexistingEntityException("Cancionxlista " + cancionxlista + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancionxlista cancionxlista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxlista persistentCancionxlista = em.find(Cancionxlista.class, cancionxlista.getCodregistro());
            Cancion codcancionOld = persistentCancionxlista.getCodcancion();
            Cancion codcancionNew = cancionxlista.getCodcancion();
            Listareproduccion codlistaOld = persistentCancionxlista.getCodlista();
            Listareproduccion codlistaNew = cancionxlista.getCodlista();
            if (codcancionNew != null) {
                codcancionNew = em.getReference(codcancionNew.getClass(), codcancionNew.getCodcancion());
                cancionxlista.setCodcancion(codcancionNew);
            }
            if (codlistaNew != null) {
                codlistaNew = em.getReference(codlistaNew.getClass(), codlistaNew.getCodlista());
                cancionxlista.setCodlista(codlistaNew);
            }
            cancionxlista = em.merge(cancionxlista);
            if (codcancionOld != null && !codcancionOld.equals(codcancionNew)) {
                codcancionOld.getCancionxlistaList().remove(cancionxlista);
                codcancionOld = em.merge(codcancionOld);
            }
            if (codcancionNew != null && !codcancionNew.equals(codcancionOld)) {
                codcancionNew.getCancionxlistaList().add(cancionxlista);
                codcancionNew = em.merge(codcancionNew);
            }
            if (codlistaOld != null && !codlistaOld.equals(codlistaNew)) {
                codlistaOld.getCancionxlistaList().remove(cancionxlista);
                codlistaOld = em.merge(codlistaOld);
            }
            if (codlistaNew != null && !codlistaNew.equals(codlistaOld)) {
                codlistaNew.getCancionxlistaList().add(cancionxlista);
                codlistaNew = em.merge(codlistaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = cancionxlista.getCodregistro();
                if (findCancionxlista(id) == null) {
                    throw new NonexistentEntityException("The cancionxlista with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxlista cancionxlista;
            try {
                cancionxlista = em.getReference(Cancionxlista.class, id);
                cancionxlista.getCodregistro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancionxlista with id " + id + " no longer exists.", enfe);
            }
            Cancion codcancion = cancionxlista.getCodcancion();
            if (codcancion != null) {
                codcancion.getCancionxlistaList().remove(cancionxlista);
                codcancion = em.merge(codcancion);
            }
            Listareproduccion codlista = cancionxlista.getCodlista();
            if (codlista != null) {
                codlista.getCancionxlistaList().remove(cancionxlista);
                codlista = em.merge(codlista);
            }
            em.remove(cancionxlista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancionxlista> findCancionxlistaEntities() {
        return findCancionxlistaEntities(true, -1, -1);
    }

    public List<Cancionxlista> findCancionxlistaEntities(int maxResults, int firstResult) {
        return findCancionxlistaEntities(false, maxResults, firstResult);
    }

    private List<Cancionxlista> findCancionxlistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancionxlista.class));
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

    public Cancionxlista findCancionxlista(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancionxlista.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionxlistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancionxlista> rt = cq.from(Cancionxlista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    public BigDecimal buscarUltimaCancionLista(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Cancionxlista> listasCancionLista = findCancionxlistaEntities();
        if (listasCancionLista.size() != 0){
            for(Cancionxlista actual: listasCancionLista){
                if(actual.getCodregistro().compareTo(mayor) == 1){
                    mayor = actual.getCodregistro();
                    ultimoCodigo = actual.getCodregistro();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(2999);
        }
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public void crearCancionLista(Cancion pCancion, Listareproduccion pLista){

        Cancionxlista listaCR = new Cancionxlista();
        listaCR.setCodregistro(buscarUltimaCancionLista());
        listaCR.setCodcancion(pCancion);
        listaCR.setCodlista(pLista);

        try {
            create(listaCR);
            JOptionPane.showMessageDialog(null, "Se agregó la canción a la lista de repoducción", "Agregar Canción a Lista", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar la canción a la lista de reproducción", "Error", 0);
        }

    }   

    
}
