
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "USUARIOLIKES")
@NamedQueries({
    @NamedQuery(name = "Usuariolikes.findAll", query = "SELECT u FROM Usuariolikes u")})
public class Usuariolikes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODREGISTRO")
    private BigDecimal codregistro;
    @Column(name = "FECHALIKE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechalike;
    @JoinColumn(name = "CODCANCION", referencedColumnName = "CODCANCION")
    @ManyToOne(optional = false)
    private Cancion codcancion;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne(optional = false)
    private Usuarioc codusuario;

    public Usuariolikes() {
    }

    public Usuariolikes(BigDecimal codregistro) {
        this.codregistro = codregistro;
    }

    public BigDecimal getCodregistro() {
        return codregistro;
    }

    public void setCodregistro(BigDecimal codregistro) {
        this.codregistro = codregistro;
    }

    public Date getFechalike() {
        return fechalike;
    }

    public void setFechalike(Date fechalike) {
        this.fechalike = fechalike;
    }

    public Cancion getCodcancion() {
        return codcancion;
    }

    public void setCodcancion(Cancion codcancion) {
        this.codcancion = codcancion;
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
        hash += (codregistro != null ? codregistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariolikes)) {
            return false;
        }
        Usuariolikes other = (Usuariolikes) object;
        if ((this.codregistro == null && other.codregistro != null) || (this.codregistro != null && !this.codregistro.equals(other.codregistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Usuariolikes[ codregistro=" + codregistro + " ]";
    }
    
}
