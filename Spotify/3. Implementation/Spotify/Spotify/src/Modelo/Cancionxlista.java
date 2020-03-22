
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "CANCIONXLISTA")
@NamedQueries({
    @NamedQuery(name = "Cancionxlista.findAll", query = "SELECT c FROM Cancionxlista c")})
public class Cancionxlista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODREGISTRO")
    private BigDecimal codregistro;
    @JoinColumn(name = "CODCANCION", referencedColumnName = "CODCANCION")
    @ManyToOne(optional = false)
    private Cancion codcancion;
    @JoinColumn(name = "CODLISTA", referencedColumnName = "CODLISTA")
    @ManyToOne(optional = false)
    private Listareproduccion codlista;

    public Cancionxlista() {
    }

    public Cancionxlista(BigDecimal codregistro) {
        this.codregistro = codregistro;
    }

    public BigDecimal getCodregistro() {
        return codregistro;
    }

    public void setCodregistro(BigDecimal codregistro) {
        this.codregistro = codregistro;
    }

    public Cancion getCodcancion() {
        return codcancion;
    }

    public void setCodcancion(Cancion codcancion) {
        this.codcancion = codcancion;
    }

    public Listareproduccion getCodlista() {
        return codlista;
    }

    public void setCodlista(Listareproduccion codlista) {
        this.codlista = codlista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codregistro != null ? codregistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancionxlista)) {
            return false;
        }
        Cancionxlista other = (Cancionxlista) object;
        if ((this.codregistro == null && other.codregistro != null) || (this.codregistro != null && !this.codregistro.equals(other.codregistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Cancionxlista[ codregistro=" + codregistro + " ]";
    }
    
}
