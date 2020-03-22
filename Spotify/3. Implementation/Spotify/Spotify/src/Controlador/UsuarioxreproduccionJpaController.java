
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Cancion;
import Modelo.Usuarioc;
import Modelo.Usuarioxreproduccion;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class UsuarioxreproduccionJpaController implements Serializable {

    public UsuarioxreproduccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarioxreproduccion usuarioxreproduccion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion codcancion = usuarioxreproduccion.getCodcancion();
            if (codcancion != null) {
                codcancion = em.getReference(codcancion.getClass(), codcancion.getCodcancion());
                usuarioxreproduccion.setCodcancion(codcancion);
            }
            Usuarioc codusuario = usuarioxreproduccion.getCodusuario();
            if (codusuario != null) {
                codusuario = em.getReference(codusuario.getClass(), codusuario.getCodusuario());
                usuarioxreproduccion.setCodusuario(codusuario);
            }
            em.persist(usuarioxreproduccion);
            if (codcancion != null) {
                codcancion.getUsuarioxreproduccionList().add(usuarioxreproduccion);
                codcancion = em.merge(codcancion);
            }
            if (codusuario != null) {
                codusuario.getUsuarioxreproduccionList().add(usuarioxreproduccion);
                codusuario = em.merge(codusuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioxreproduccion(usuarioxreproduccion.getCodregistro()) != null) {
                throw new PreexistingEntityException("Usuarioxreproduccion " + usuarioxreproduccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarioxreproduccion usuarioxreproduccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarioxreproduccion persistentUsuarioxreproduccion = em.find(Usuarioxreproduccion.class, usuarioxreproduccion.getCodregistro());
            Cancion codcancionOld = persistentUsuarioxreproduccion.getCodcancion();
            Cancion codcancionNew = usuarioxreproduccion.getCodcancion();
            Usuarioc codusuarioOld = persistentUsuarioxreproduccion.getCodusuario();
            Usuarioc codusuarioNew = usuarioxreproduccion.getCodusuario();
            if (codcancionNew != null) {
                codcancionNew = em.getReference(codcancionNew.getClass(), codcancionNew.getCodcancion());
                usuarioxreproduccion.setCodcancion(codcancionNew);
            }
            if (codusuarioNew != null) {
                codusuarioNew = em.getReference(codusuarioNew.getClass(), codusuarioNew.getCodusuario());
                usuarioxreproduccion.setCodusuario(codusuarioNew);
            }
            usuarioxreproduccion = em.merge(usuarioxreproduccion);
            if (codcancionOld != null && !codcancionOld.equals(codcancionNew)) {
                codcancionOld.getUsuarioxreproduccionList().remove(usuarioxreproduccion);
                codcancionOld = em.merge(codcancionOld);
            }
            if (codcancionNew != null && !codcancionNew.equals(codcancionOld)) {
                codcancionNew.getUsuarioxreproduccionList().add(usuarioxreproduccion);
                codcancionNew = em.merge(codcancionNew);
            }
            if (codusuarioOld != null && !codusuarioOld.equals(codusuarioNew)) {
                codusuarioOld.getUsuarioxreproduccionList().remove(usuarioxreproduccion);
                codusuarioOld = em.merge(codusuarioOld);
            }
            if (codusuarioNew != null && !codusuarioNew.equals(codusuarioOld)) {
                codusuarioNew.getUsuarioxreproduccionList().add(usuarioxreproduccion);
                codusuarioNew = em.merge(codusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuarioxreproduccion.getCodregistro();
                if (findUsuarioxreproduccion(id) == null) {
                    throw new NonexistentEntityException("The usuarioxreproduccion with id " + id + " no longer exists.");
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
            Usuarioxreproduccion usuarioxreproduccion;
            try {
                usuarioxreproduccion = em.getReference(Usuarioxreproduccion.class, id);
                usuarioxreproduccion.getCodregistro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioxreproduccion with id " + id + " no longer exists.", enfe);
            }
            Cancion codcancion = usuarioxreproduccion.getCodcancion();
            if (codcancion != null) {
                codcancion.getUsuarioxreproduccionList().remove(usuarioxreproduccion);
                codcancion = em.merge(codcancion);
            }
            Usuarioc codusuario = usuarioxreproduccion.getCodusuario();
            if (codusuario != null) {
                codusuario.getUsuarioxreproduccionList().remove(usuarioxreproduccion);
                codusuario = em.merge(codusuario);
            }
            em.remove(usuarioxreproduccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarioxreproduccion> findUsuarioxreproduccionEntities() {
        return findUsuarioxreproduccionEntities(true, -1, -1);
    }

    public List<Usuarioxreproduccion> findUsuarioxreproduccionEntities(int maxResults, int firstResult) {
        return findUsuarioxreproduccionEntities(false, maxResults, firstResult);
    }

    private List<Usuarioxreproduccion> findUsuarioxreproduccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarioxreproduccion.class));
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

    public Usuarioxreproduccion findUsuarioxreproduccion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarioxreproduccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioxreproduccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarioxreproduccion> rt = cq.from(Usuarioxreproduccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
    public BigDecimal getUltimaReproduccion(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Usuarioxreproduccion> reproducciones = findUsuarioxreproduccionEntities();
        if (reproducciones.size() != 0){
            for(Usuarioxreproduccion actual: reproducciones){
                if(actual.getCodregistro().compareTo(mayor) == 1){
                    mayor = actual.getCodregistro();
                    ultimoCodigo = actual.getCodregistro();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(3999);
        }
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public void crearReproduccion(Cancion pCancion, Usuarioc pUsuario){
        
        Usuarioxreproduccion reproduccion = new Usuarioxreproduccion();
        reproduccion.setCodcancion(pCancion);
        reproduccion.setCodusuario(pUsuario);
        reproduccion.setCodregistro(getUltimaReproduccion());
        
        try {
            create(reproduccion);
            JOptionPane.showMessageDialog(null, "El usuario reprodujo la canción", "Reproducción", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al reproducir la canción", "Error", 0);
        }
        
    }
    
}
