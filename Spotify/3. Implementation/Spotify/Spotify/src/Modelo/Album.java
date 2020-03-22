
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "ALBUM")
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODALBUM")
    private BigDecimal codalbum;
    @Basic(optional = false)
    @Column(name = "NOMBREALBUM")
    private String nombrealbum;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "FECHALANZAMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechalanzamiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codalbum")
    private List<Cancion> cancionList;
    @JoinColumn(name = "CODEMPRESA", referencedColumnName = "CODEMPRESA")
    @ManyToOne(optional = false)
    private Empresar codempresa;

    public Album() {
    }

    public Album(BigDecimal codalbum) {
        this.codalbum = codalbum;
    }

    public Album(BigDecimal codalbum, String nombrealbum, String tipo, Date fechalanzamiento) {
        this.codalbum = codalbum;
        this.nombrealbum = nombrealbum;
        this.tipo = tipo;
        this.fechalanzamiento = fechalanzamiento;
    }

    public BigDecimal getCodalbum() {
        return codalbum;
    }

    public void setCodalbum(BigDecimal codalbum) {
        this.codalbum = codalbum;
    }

    public String getNombrealbum() {
        return nombrealbum;
    }

    public void setNombrealbum(String nombrealbum) {
        this.nombrealbum = nombrealbum;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechalanzamiento() {
        return fechalanzamiento;
    }

    public void setFechalanzamiento(Date fechalanzamiento) {
        this.fechalanzamiento = fechalanzamiento;
    }

    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    public Empresar getCodempresa() {
        return codempresa;
    }

    public void setCodempresa(Empresar codempresa) {
        this.codempresa = codempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codalbum != null ? codalbum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.codalbum == null && other.codalbum != null) || (this.codalbum != null && !this.codalbum.equals(other.codalbum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Album[ codalbum=" + codalbum + " ]";
    }
    
}
