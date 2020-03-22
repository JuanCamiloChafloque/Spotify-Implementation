
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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


@Entity
@Table(name = "LISTAREPRODUCCION")
@NamedQueries({
    @NamedQuery(name = "Listareproduccion.findAll", query = "SELECT l FROM Listareproduccion l")})
public class Listareproduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODLISTA")
    private BigDecimal codlista;
    @Basic(optional = false)
    @Column(name = "NOMBRELISTA")
    private String nombrelista;
    @Basic(optional = false)
    @Column(name = "VISIBILIDAD")
    private String visibilidad;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private String orden;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codlista")
    private List<Cancionxlista> cancionxlistaList;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne(optional = false)
    private Usuarioc codusuario;

    public Listareproduccion() {
    }

    public Listareproduccion(BigDecimal codlista) {
        this.codlista = codlista;
    }

    public Listareproduccion(BigDecimal codlista, String nombrelista, String visibilidad, String orden) {
        this.codlista = codlista;
        this.nombrelista = nombrelista;
        this.visibilidad = visibilidad;
        this.orden = orden;
    }

    public BigDecimal getCodlista() {
        return codlista;
    }

    public void setCodlista(BigDecimal codlista) {
        this.codlista = codlista;
    }

    public String getNombrelista() {
        return nombrelista;
    }

    public void setNombrelista(String nombrelista) {
        this.nombrelista = nombrelista;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public List<Cancionxlista> getCancionxlistaList() {
        return cancionxlistaList;
    }

    public void setCancionxlistaList(List<Cancionxlista> cancionxlistaList) {
        this.cancionxlistaList = cancionxlistaList;
    }

    public Usuarioc getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuarioc codusuario) {
        this.codusuario = codusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codlista != null ? codlista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listareproduccion)) {
            return false;
        }
        Listareproduccion other = (Listareproduccion) object;
        if ((this.codlista == null && other.codlista != null) || (this.codlista != null && !this.codlista.equals(other.codlista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Listareproduccion[ codlista=" + codlista + " ]";
    }
    
}
