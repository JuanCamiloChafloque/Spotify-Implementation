
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
import Modelo.Usuariolikes;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;


public class UsuariolikesJpaController implements Serializable {

    public UsuariolikesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuariolikes usuariolikes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion codcancion = usuariolikes.getCodcancion();
            if (codcancion != null) {
                codcancion = em.getReference(codcancion.getClass(), codcancion.getCodcancion());
                usuariolikes.setCodcancion(codcancion);
            }
            Usuarioc codusuario = usuariolikes.getCodusuario();
            if (codusuario != null) {
                codusuario = em.getReference(codusuario.getClass(), codusuario.getCodusuario());
                usuariolikes.setCodusuario(codusuario);
            }
            em.persist(usuariolikes);
            if (codcancion != null) {
                codcancion.getUsuariolikesList().add(usuariolikes);
                codcancion = em.merge(codcancion);
            }
            if (codusuario != null) {
                codusuario.getUsuariolikesList().add(usuariolikes);
                codusuario = em.merge(codusuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuariolikes(usuariolikes.getCodregistro()) != null) {
                throw new PreexistingEntityException("Usuariolikes " + usuariolikes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuariolikes usuariolikes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuariolikes persistentUsuariolikes = em.find(Usuariolikes.class, usuariolikes.getCodregistro());
            Cancion codcancionOld = persistentUsuariolikes.getCodcancion();
            Cancion codcancionNew = usuariolikes.getCodcancion();
            Usuarioc codusuarioOld = persistentUsuariolikes.getCodusuario();
            Usuarioc codusuarioNew = usuariolikes.getCodusuario();
            if (codcancionNew != null) {
                codcancionNew = em.getReference(codcancionNew.getClass(), codcancionNew.getCodcancion());
                usuariolikes.setCodcancion(codcancionNew);
            }
            if (codusuarioNew != null) {
                codusuarioNew = em.getReference(codusuarioNew.getClass(), codusuarioNew.getCodusuario());
                usuariolikes.setCodusuario(codusuarioNew);
            }
            usuariolikes = em.merge(usuariolikes);
            if (codcancionOld != null && !codcancionOld.equals(codcancionNew)) {
                codcancionOld.getUsuariolikesList().remove(usuariolikes);
                codcancionOld = em.merge(codcancionOld);
            }
            if (codcancionNew != null && !codcancionNew.equals(codcancionOld)) {
                codcancionNew.getUsuariolikesList().add(usuariolikes);
                codcancionNew = em.merge(codcancionNew);
            }
            if (codusuarioOld != null && !codusuarioOld.equals(codusuarioNew)) {
                codusuarioOld.getUsuariolikesList().remove(usuariolikes);
                codusuarioOld = em.merge(codusuarioOld);
            }
            if (codusuarioNew != null && !codusuarioNew.equals(codusuarioOld)) {
                codusuarioNew.getUsuariolikesList().add(usuariolikes);
                codusuarioNew = em.merge(codusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuariolikes.getCodregistro();
                if (findUsuariolikes(id) == null) {
                    throw new NonexistentEntityException("The usuariolikes with id " + id + " no longer exists.");
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
            Usuariolikes usuariolikes;
            try {
                usuariolikes = em.getReference(Usuariolikes.class, id);
                usuariolikes.getCodregistro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariolikes with id " + id + " no longer exists.", enfe);
            }
            Cancion codcancion = usuariolikes.getCodcancion();
            if (codcancion != null) {
                codcancion.getUsuariolikesList().remove(usuariolikes);
                codcancion = em.merge(codcancion);
            }
            Usuarioc codusuario = usuariolikes.getCodusuario();
            if (codusuario != null) {
                codusuario.getUsuariolikesList().remove(usuariolikes);
                codusuario = em.merge(codusuario);
            }
            em.remove(usuariolikes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuariolikes> findUsuariolikesEntities() {
        return findUsuariolikesEntities(true, -1, -1);
    }

    public List<Usuariolikes> findUsuariolikesEntities(int maxResults, int firstResult) {
        return findUsuariolikesEntities(false, maxResults, firstResult);
    }

    private List<Usuariolikes> findUsuariolikesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuariolikes.class));
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

    public Usuariolikes findUsuariolikes(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuariolikes.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariolikesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuariolikes> rt = cq.from(Usuariolikes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public BigDecimal getUltimoLike(){
        
        BigDecimal ultimoCodigo = new BigDecimal(0);
        BigDecimal mayor = new BigDecimal(0);
        
        List<Usuariolikes> likes = findUsuariolikesEntities();
        if (likes.size() != 0){
            for(Usuariolikes actual: likes){
                if(actual.getCodregistro().compareTo(mayor) == 1){
                    mayor = actual.getCodregistro();
                    ultimoCodigo = actual.getCodregistro();
                }
            }
        }else{
            ultimoCodigo = new BigDecimal(4999);
        }
        return ultimoCodigo.add(new BigDecimal(1));
        
    }
    
    public void crearLike(Cancion pCancion, Usuarioc pUsuario){
        
        Usuariolikes like = new Usuariolikes();
        like.setCodcancion(pCancion);
        like.setCodusuario(pUsuario);
        like.setCodregistro(getUltimoLike());
        like.setFechalike(new Date());
        
        try {
            create(like);
            JOptionPane.showMessageDialog(null, "El usuario dio like a la canción", "Like", 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al dar like la canción", "Error", 0);
        }
        
    }
    
}
