
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "GENERO")
@NamedQueries({
    @NamedQuery(name = "Genero.findAll", query = "SELECT g FROM Genero g")})
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODGENERO")
    private BigDecimal codgenero;
    @Basic(optional = false)
    @Column(name = "NOMBREGENERO")
    private String nombregenero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codgenero")
    private List<Cancion> cancionList;

    public Genero() {
    }

    public Genero(BigDecimal codgenero) {
        this.codgenero = codgenero;
    }

    public Genero(BigDecimal codgenero, String nombregenero) {
        this.codgenero = codgenero;
        this.nombregenero = nombregenero;
    }

    public BigDecimal getCodgenero() {
        return codgenero;
    }

    public void setCodgenero(BigDecimal codgenero) {
        this.codgenero = codgenero;
    }

    public String getNombregenero() {
        return nombregenero;
    }

    public void setNombregenero(String nombregenero) {
        this.nombregenero = nombregenero;
    }

    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codgenero != null ? codgenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.codgenero == null && other.codgenero != null) || (this.codgenero != null && !this.codgenero.equals(other.codgenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Genero[ codgenero=" + codgenero + " ]";
    }
    
}
