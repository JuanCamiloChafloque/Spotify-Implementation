
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Usuarioc;
import Modelo.Cancionxlista;
import Modelo.Listareproduccion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class ListareproduccionJpaController implements Serializable {

    public ListareproduccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Listareproduccion listareproduccion) throws PreexistingEntityException, Exception {
        if (listareproduccion.getCancionxlistaList() == null) {
            listareproduccion.setCancionxlistaList(new ArrayList<Cancionxlista>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarioc codusuario = listareproduccion.getCodusuario();
            if (codusuario != null) {
                codusuario = em.getReference(codusuario.getClass(), codusuario.getCodusuario());
                listareproduccion.setCodusuario(codusuario);
            }
            List<Cancionxlista> attachedCancionxlistaList = new ArrayList<Cancionxlista>();
            for (Cancionxlista cancionxlistaListCancionxlistaToAttach : listareproduccion.getCancionxlistaList()) {
                cancionxlistaListCancionxlistaToAttach = em.getReference(cancionxlistaListCancionxlistaToAttach.getClass(), cancionxlistaListCancionxlistaToAttach.getCodregistro());
                attachedCancionxlistaList.add(cancionxlistaListCancionxlistaToAttach);
            }
            listareproduccion.setCancionxlistaList(attachedCancionxlistaList);
            em.persist(listareproduccion);
            if (codusuario != null) {
                codusuario.getListareproduccionList().add(listareproduccion);
                codusuario = em.merge(codusuario);
            }
            for (Cancionxlista cancionxlistaListCancionxlista : listareproduccion.getCancionxlistaList()) {
                Listareproduccion oldCodlistaOfCancionxlistaListCancionxlista = cancionxlistaListCancionxlista.getCodlista();
                cancionxlistaListCancionxlista.setCodlista(listareproduccion);
                cancionxlistaListCancionxlista = em.merge(cancionxlistaListCancionxlista);
                if (oldCodlistaOfCancionxlistaListCancionxlista != null) {
                    oldCodlistaOfCancionxlistaListCancionxlista.getCancionxlistaList().remove(cancionxlistaListCancionxlista);
                    oldCodlistaOfCancionxlistaListCancionxlista = em.merge(oldCodlistaOfCancionxlistaListCancionxlista);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findListareproduccion(listareproduccion.getCodlista()) != null) {
                throw new PreexistingEntityException("Listareproduccion " + listareproduccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Listareproduccion listareproduccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Listareproduccion persistentListareproduccion = em.find(Listareproduccion.class, listareproduccion.getCodlista());
            Usuarioc codusuarioOld = persistentListareproduccion.getCodusuario();
            Usuarioc codusuarioNew = listareproduccion.getCodusuario();
            List<Cancionxlista> cancionxlistaListOld = persistentListareproduccion.getCancionxlistaList();
            List<Cancionxlista> cancionxlistaListNew = listareproduccion.getCancionxlistaList();
            List<String> illegalOrphanMessages = null;
            for (Cancionxlista cancionxlistaListOldCancionxlista : cancionxlistaListOld) {
                if (!cancionxlistaListNew.contains(cancionxlistaListOldCancionxlista)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxlista " + cancionxlistaListOldCancionxlista + " since its codlista field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codusuarioNew != null) {
                codusuarioNew = em.getReference(codusuarioNew.getClass(), codusuarioNew.getCodusuario());
                listareproduccion.setCodusuario(codusuarioNew);
            }
            List<Cancionxlista> attachedCancionxlistaListNew = new ArrayList<Cancionxlista>();
            for (Cancionxlista cancionxlistaListNewCancionxlistaToAttach : cancionxlistaListNew) {
                cancionxlistaListNewCancionxlistaToAttach = em.getReference(cancionxlistaListNewCancionxlistaToAttach.getClass(), cancionxlistaListNewCancionxlistaToAttach.getCodregistro());
                attachedCancionxlistaListNew.add(cancionxlistaListNewCancionxlistaToAttach);
            }
            cancionxlistaListNew = attachedCancionxlistaListNew;
            listareproduccion.setCancionxlistaList(cancionxlistaListNew);
            listareproduccion = em.merge(listareproduccion);
            if (codusuarioOld != null && !codusuarioOld.equals(codusuarioNew)) {
                codusuarioOld.getListareproduccionList().remove(listareproduccion);
                codusuarioOld = em.merge(codusuarioOld);
            }
            if (codusuarioNew != null && !codusuarioNew.equals(codusuarioOld)) {
                codusuarioNew.getListareproduccionList().add(listareproduccion);
                codusuarioNew = em.merge(codusuarioNew);
            }
            for (Cancionxlista cancionxlistaListNewCancionxlista : cancionxlistaListNew) {
                if (!cancionxlistaListOld.contains(cancionxlistaListNewCancionxlista)) {
                    Listareproduccion oldCodlistaOfCancionxlistaListNewCancionxlista = cancionxlistaListNewCancionxlista.getCodlista();
                    cancionxlistaListNewCancionxlista.setCodlista(listareproduccion);
                    cancionxlistaListNewCancionxlista = em.merge(cancionxlistaListNewCancionxlista);
                    if (oldCodlistaOfCancionxlistaListNewCancionxlista != null && !oldCodlistaOfCancionxlistaListNewCancionxlista.equals(listareproduccion)) {
                        oldCodlistaOfCancionxlistaListNewCancionxlista.getCancionxlistaList().remove(cancionxlistaListNewCancionxlista);
                        oldCodlistaOfCancionxlistaListNewCancionxlista = em.merge(oldCodlistaOfCancionxlistaListNewCancionxlista);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = listareproduccion.getCodlista();
                if (findListareproduccion(id) == null) {
                    throw new NonexistentEntityException("The listareproduccion with id " + id + " no longer exists.");
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
            Listareproduccion listareproduccion;
            try {
                listareproduccion = em.getReference(Listareproduccion.class, id);
                listareproduccion.getCodlista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listareproduccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxlista> cancionxlistaListOrphanCheck = listareproduccion.getCancionxlistaList();
            for (Cancionxlista cancionxlistaListOrphanCheckCancionxlista : cancionxlistaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Listareproduccion (" + listareproduccion + ") cannot be destroyed since the Cancionxlista " + cancionxlistaListOrphanCheckCancionxlista + " in its cancionxlistaList field has a non-nullable codlista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarioc codusuario = listareproduccion.getCodusuario();
            if (codusuario != null) {
                codusuario.getListareproduccionList().remove(listareproduccion);
                codusuario = em.merge(codusuario);
            }
            em.remove(listareproduccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Listareproduccion> findListareproduccionEntities() {
        return findListareproduccionEntities(true, -1, -1);
    }

    public List<Listareproduccion> findListareproduccionEntities(int maxResults, int firstResult) {
        return findListareproduccionEntities(false, maxResults, firstResult);
    }

    private List<Listareproduccion> findListareproduccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listareproduccion.class));
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

    public Listareproduccion findListareproduccion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Listareproduccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getListareproduccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Listareproduccion> rt = cq.from(Listareproduccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public BigDecimal buscarUltimaLista(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Listareproduccion> listasReproduccion = findListareproduccionEntities();
        if (listasReproduccion.size() != 0){
            for(Listareproduccion actual: listasReproduccion){
                if(actual.getCodlista().compareTo(mayor) == 1){
                    mayor = actual.getCodlista();
                    ultimoCodigo = actual.getCodlista();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(1999);
        }
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public Listareproduccion crearLista(String pCodigo, String pNombre, String pVisibilidad, String pOrden, Usuarioc pUsuario){

        Listareproduccion listaR = new Listareproduccion();
        listaR.setCodlista(new BigDecimal(pCodigo));
        listaR.setNombrelista(pNombre);
        listaR.setOrden(pOrden);
        listaR.setVisibilidad(pVisibilidad);
        listaR.setCodusuario(pUsuario);

        try {
            create(listaR);
            JOptionPane.showMessageDialog(null, "La lista de reproducción se creó exitosamente", "Crear Lista", 1);
            return listaR;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo crear la lista de reproducción", "Error", 0);
            return null;
        }

    }  
    
    public void modificarNombreLista(BigDecimal pCodigo, String pNombre){
        
        List<Listareproduccion> listas = findListareproduccionEntities();
        Listareproduccion nueva = new Listareproduccion();
        
        for(Listareproduccion actual: listas){
            if(actual.getCodlista().compareTo(pCodigo) == 0){
                nueva = actual;
            }
        }
        
        nueva.setNombrelista(pNombre);
        try {
            edit(nueva);
            JOptionPane.showMessageDialog(null, "Se modificó el nombre de la lista", "Actualizar Lista", 1);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el nombre de la lista", "Error", 0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el nombre de la lista", "Error", 0);
        }

    }
    
    public void modificarOrdenLista(BigDecimal pCodigo, String pOrden){
        
        List<Listareproduccion> listas = findListareproduccionEntities();
        Listareproduccion nueva = new Listareproduccion();
        
        for(Listareproduccion actual: listas){
            if(actual.getCodlista().compareTo(pCodigo) == 0){
                nueva = actual;
            }
        }
        
        nueva.setOrden(pOrden);
        try {
            edit(nueva);
            JOptionPane.showMessageDialog(null, "Se modificó el orden de la lista", "Actualizar Lista", 1);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el orden de la lista", "Error", 0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el orden de la lista", "Error", 0);
        }

    }
    
}
