
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
@Table(name = "EMPRESAR")
@NamedQueries({
    @NamedQuery(name = "Empresar.findAll", query = "SELECT e FROM Empresar e")})
public class Empresar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODEMPRESA")
    private BigDecimal codempresa;
    @Basic(optional = false)
    @Column(name = "NOMBREEMPRESA")
    private String nombreempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codempresa")
    private List<Album> albumList;

    public Empresar() {
    }

    public Empresar(BigDecimal codempresa) {
        this.codempresa = codempresa;
    }

    public Empresar(BigDecimal codempresa, String nombreempresa) {
        this.codempresa = codempresa;
        this.nombreempresa = nombreempresa;
    }

    public BigDecimal getCodempresa() {
        return codempresa;
    }

    public void setCodempresa(BigDecimal codempresa) {
        this.codempresa = codempresa;
    }

    public String getNombreempresa() {
        return nombreempresa;
    }

    public void setNombreempresa(String nombreempresa) {
        this.nombreempresa = nombreempresa;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codempresa != null ? codempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresar)) {
            return false;
        }
        Empresar other = (Empresar) object;
        if ((this.codempresa == null && other.codempresa != null) || (this.codempresa != null && !this.codempresa.equals(other.codempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Empresar[ codempresa=" + codempresa + " ]";
    }
    
}
