
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
@Table(name = "PAISC")
@NamedQueries({
    @NamedQuery(name = "Paisc.findAll", query = "SELECT p FROM Paisc p")})
public class Paisc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODPAIS")
    private BigDecimal codpais;
    @Basic(optional = false)
    @Column(name = "NOMBREPAIS")
    private String nombrepais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codpais")
    private List<Interprete> interpreteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codpais")
    private List<Usuarioc> usuariocList;

    public Paisc() {
    }

    public Paisc(BigDecimal codpais) {
        this.codpais = codpais;
    }

    public Paisc(BigDecimal codpais, String nombrepais) {
        this.codpais = codpais;
        this.nombrepais = nombrepais;
    }

    public BigDecimal getCodpais() {
        return codpais;
    }

    public void setCodpais(BigDecimal codpais) {
        this.codpais = codpais;
    }

    public String getNombrepais() {
        return nombrepais;
    }

    public void setNombrepais(String nombrepais) {
        this.nombrepais = nombrepais;
    }

    public List<Interprete> getInterpreteList() {
        return interpreteList;
    }

    public void setInterpreteList(List<Interprete> interpreteList) {
        this.interpreteList = interpreteList;
    }

    public List<Usuarioc> getUsuariocList() {
        return usuariocList;
    }

    public void setUsuariocList(List<Usuarioc> usuariocList) {
        this.usuariocList = usuariocList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codpais != null ? codpais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paisc)) {
            return false;
        }
        Paisc other = (Paisc) object;
        if ((this.codpais == null && other.codpais != null) || (this.codpais != null && !this.codpais.equals(other.codpais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Paisc[ codpais=" + codpais + " ]";
    }
    
}
