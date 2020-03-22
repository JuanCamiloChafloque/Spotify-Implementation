
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "AUDITORIA")
@NamedQueries({
    @NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a")})
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODREGISTRO")
    private BigDecimal codregistro;
    @Basic(optional = false)
    @Column(name = "ENTIDAD")
    private String entidad;
    @Basic(optional = false)
    @Column(name = "OPERACION")
    private Character operacion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Auditoria() {
    }

    public Auditoria(BigDecimal codregistro) {
        this.codregistro = codregistro;
    }

    public Auditoria(BigDecimal codregistro, String entidad, Character operacion) {
        this.codregistro = codregistro;
        this.entidad = entidad;
        this.operacion = operacion;
    }

    public BigDecimal getCodregistro() {
        return codregistro;
    }

    public void setCodregistro(BigDecimal codregistro) {
        this.codregistro = codregistro;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Character getOperacion() {
        return operacion;
    }

    public void setOperacion(Character operacion) {
        this.operacion = operacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        if (!(object instanceof Auditoria)) {
            return false;
        }
        Auditoria other = (Auditoria) object;
        if ((this.codregistro == null && other.codregistro != null) || (this.codregistro != null && !this.codregistro.equals(other.codregistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Auditoria[ codregistro=" + codregistro + " ]";
    }
    
}
