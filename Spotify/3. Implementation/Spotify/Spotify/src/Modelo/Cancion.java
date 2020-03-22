
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
@Table(name = "CANCION")
@NamedQueries({
    @NamedQuery(name = "Cancion.findAll", query = "SELECT c FROM Cancion c")})
public class Cancion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codcancion")
    private List<Usuariolikes> usuariolikesList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODCANCION")
    private BigDecimal codcancion;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DURACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date duracion;
    @JoinColumn(name = "CODALBUM", referencedColumnName = "CODALBUM")
    @ManyToOne(optional = false)
    private Album codalbum;
    @JoinColumn(name = "CODGENERO", referencedColumnName = "CODGENERO")
    @ManyToOne(optional = false)
    private Genero codgenero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Cancionxinterprete> cancionxinterpreteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codcancion")
    private List<Cancionxlista> cancionxlistaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codcancion")
    private List<Usuarioxreproduccion> usuarioxreproduccionList;

    public Cancion() {
    }

    public Cancion(BigDecimal codcancion) {
        this.codcancion = codcancion;
    }

    public Cancion(BigDecimal codcancion, String titulo) {
        this.codcancion = codcancion;
        this.titulo = titulo;
    }

    public BigDecimal getCodcancion() {
        return codcancion;
    }

    public void setCodcancion(BigDecimal codcancion) {
        this.codcancion = codcancion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    public Album getCodalbum() {
        return codalbum;
    }

    public void setCodalbum(Album codalbum) {
        this.codalbum = codalbum;
    }

    public Genero getCodgenero() {
        return codgenero;
    }

    public void setCodgenero(Genero codgenero) {
        this.codgenero = codgenero;
    }

    public List<Cancionxinterprete> getCancionxinterpreteList() {
        return cancionxinterpreteList;
    }

    public void setCancionxinterpreteList(List<Cancionxinterprete> cancionxinterpreteList) {
        this.cancionxinterpreteList = cancionxinterpreteList;
    }

    public List<Cancionxlista> getCancionxlistaList() {
        return cancionxlistaList;
    }

    public void setCancionxlistaList(List<Cancionxlista> cancionxlistaList) {
        this.cancionxlistaList = cancionxlistaList;
    }

    public List<Usuarioxreproduccion> getUsuarioxreproduccionList() {
        return usuarioxreproduccionList;
    }

    public void setUsuarioxreproduccionList(List<Usuarioxreproduccion> usuarioxreproduccionList) {
        this.usuarioxreproduccionList = usuarioxreproduccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codcancion != null ? codcancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancion)) {
            return false;
        }
        Cancion other = (Cancion) object;
        if ((this.codcancion == null && other.codcancion != null) || (this.codcancion != null && !this.codcancion.equals(other.codcancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Cancion[ codcancion=" + codcancion + " ]";
    }

    public List<Usuariolikes> getUsuariolikesList() {
        return usuariolikesList;
    }

    public void setUsuariolikesList(List<Usuariolikes> usuariolikesList) {
        this.usuariolikesList = usuariolikesList;
    }
    
}
