
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "SUSCRIPCION")
@NamedQueries({
    @NamedQuery(name = "Suscripcion.findAll", query = "SELECT s FROM Suscripcion s")})
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPOSUSCRIPCION")
    private String tiposuscripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposuscripcion")
    private List<Usuarioc> usuariocList;

    public Suscripcion() {
    }

    public Suscripcion(String tiposuscripcion) {
        this.tiposuscripcion = tiposuscripcion;
    }

    public String getTiposuscripcion() {
        return tiposuscripcion;
    }

    public void setTiposuscripcion(String tiposuscripcion) {
        this.tiposuscripcion = tiposuscripcion;
    }

    public List<Usuarioc> getUsuariocList() {
        return usuariocList;
    }

    public void setUsuariocList(List<Usuarioc> usuariocList) {
        this.usuariocList = usuariocList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tiposuscripcion != null ? tiposuscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suscripcion)) {
            return false;
        }
        Suscripcion other = (Suscripcion) object;
        if ((this.tiposuscripcion == null && other.tiposuscripcion != null) || (this.tiposuscripcion != null && !this.tiposuscripcion.equals(other.tiposuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Suscripcion[ tiposuscripcion=" + tiposuscripcion + " ]";
    }
    
}
