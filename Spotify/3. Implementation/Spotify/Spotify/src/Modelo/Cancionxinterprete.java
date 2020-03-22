
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "CANCIONXINTERPRETE")
@NamedQueries({
    @NamedQuery(name = "Cancionxinterprete.findAll", query = "SELECT c FROM Cancionxinterprete c")})
public class Cancionxinterprete implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CancionxinterpretePK cancionxinterpretePK;
    @JoinColumn(name = "CODCANCION", referencedColumnName = "CODCANCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "CODINTERPRETE", referencedColumnName = "CODINTERPRETE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Interprete interprete;
    @JoinColumn(name = "CODPRINCIPAL", referencedColumnName = "CODINTERPRETE")
    @ManyToOne(optional = false)
    private Interprete codprincipal;

    public Cancionxinterprete() {
    }

    public Cancionxinterprete(CancionxinterpretePK cancionxinterpretePK) {
        this.cancionxinterpretePK = cancionxinterpretePK;
    }

    public Cancionxinterprete(BigDecimal codcancion, BigDecimal codinterprete) {
        this.cancionxinterpretePK = new CancionxinterpretePK(codcancion, codinterprete);
    }

    public CancionxinterpretePK getCancionxinterpretePK() {
        return cancionxinterpretePK;
    }

    public void setCancionxinterpretePK(CancionxinterpretePK cancionxinterpretePK) {
        this.cancionxinterpretePK = cancionxinterpretePK;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    public Interprete getCodprincipal() {
        return codprincipal;
    }

    public void setCodprincipal(Interprete codprincipal) {
        this.codprincipal = codprincipal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionxinterpretePK != null ? cancionxinterpretePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancionxinterprete)) {
            return false;
        }
        Cancionxinterprete other = (Cancionxinterprete) object;
        if ((this.cancionxinterpretePK == null && other.cancionxinterpretePK != null) || (this.cancionxinterpretePK != null && !this.cancionxinterpretePK.equals(other.cancionxinterpretePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Cancionxinterprete[ cancionxinterpretePK=" + cancionxinterpretePK + " ]";
    }
    
}
