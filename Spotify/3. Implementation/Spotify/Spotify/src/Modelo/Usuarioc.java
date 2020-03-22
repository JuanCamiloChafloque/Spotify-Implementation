
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
@Table(name = "USUARIOC")
@NamedQueries({
    @NamedQuery(name = "Usuarioc.findAll", query = "SELECT u FROM Usuarioc u")})
public class Usuarioc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODUSUARIO")
    private BigDecimal codusuario;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "NICKNAME")
    private String nickname;
    @Column(name = "NUMTARJETA")
    private Long numtarjeta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codusuario")
    private List<Usuariolikes> usuariolikesList;
    @JoinColumn(name = "CODPAIS", referencedColumnName = "CODPAIS")
    @ManyToOne(optional = false)
    private Paisc codpais;
    @JoinColumn(name = "TIPOSUSCRIPCION", referencedColumnName = "TIPOSUSCRIPCION")
    @ManyToOne(optional = false)
    private Suscripcion tiposuscripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codusuario")
    private List<Usuarioxreproduccion> usuarioxreproduccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codusuario")
    private List<Listareproduccion> listareproduccionList;

    public Usuarioc() {
    }

    public Usuarioc(BigDecimal codusuario) {
        this.codusuario = codusuario;
    }

    public Usuarioc(BigDecimal codusuario, String nombre, String apellido, String nickname) {
        this.codusuario = codusuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
    }

    public BigDecimal getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(BigDecimal codusuario) {
        this.codusuario = codusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getNumtarjeta() {
        return numtarjeta;
    }

    public void setNumtarjeta(Long numtarjeta) {
        this.numtarjeta = numtarjeta;
    }

    public List<Usuariolikes> getUsuariolikesList() {
        return usuariolikesList;
    }

    public void setUsuariolikesList(List<Usuariolikes> usuariolikesList) {
        this.usuariolikesList = usuariolikesList;
    }

    public Paisc getCodpais() {
        return codpais;
    }

    public void setCodpais(Paisc codpais) {
        this.codpais = codpais;
    }

    public Suscripcion getTiposuscripcion() {
        return tiposuscripcion;
    }

    public void setTiposuscripcion(Suscripcion tiposuscripcion) {
        this.tiposuscripcion = tiposuscripcion;
    }

    public List<Usuarioxreproduccion> getUsuarioxreproduccionList() {
        return usuarioxreproduccionList;
    }

    public void setUsuarioxreproduccionList(List<Usuarioxreproduccion> usuarioxreproduccionList) {
        this.usuarioxreproduccionList = usuarioxreproduccionList;
    }

    public List<Listareproduccion> getListareproduccionList() {
        return listareproduccionList;
    }

    public void setListareproduccionList(List<Listareproduccion> listareproduccionList) {
        this.listareproduccionList = listareproduccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codusuario != null ? codusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarioc)) {
            return false;
        }
        Usuarioc other = (Usuarioc) object;
        if ((this.codusuario == null && other.codusuario != null) || (this.codusuario != null && !this.codusuario.equals(other.codusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Usuarioc[ codusuario=" + codusuario + " ]";
    }
    
}
