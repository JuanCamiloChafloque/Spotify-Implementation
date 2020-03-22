
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
@Table(name = "INTERPRETE")
@NamedQueries({
    @NamedQuery(name = "Interprete.findAll", query = "SELECT i FROM Interprete i")})
public class Interprete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODINTERPRETE")
    private BigDecimal codinterprete;
    @Column(name = "NOMBREARTISTICO")
    private String nombreartistico;
    @Basic(optional = false)
    @Column(name = "NOMBREREAL")
    private String nombrereal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interprete")
    private List<Cancionxinterprete> cancionxinterpreteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codprincipal")
    private List<Cancionxinterprete> cancionxinterpreteList1;
    @JoinColumn(name = "CODPAIS", referencedColumnName = "CODPAIS")
    @ManyToOne(optional = false)
    private Paisc codpais;

    public Interprete() {
    }

    public Interprete(BigDecimal codinterprete) {
        this.codinterprete = codinterprete;
    }

    public Interprete(BigDecimal codinterprete, String nombrereal) {
        this.codinterprete = codinterprete;
        this.nombrereal = nombrereal;
    }

    public BigDecimal getCodinterprete() {
        return codinterprete;
    }

    public void setCodinterprete(BigDecimal codinterprete) {
        this.codinterprete = codinterprete;
    }

    public String getNombreartistico() {
        return nombreartistico;
    }

    public void setNombreartistico(String nombreartistico) {
        this.nombreartistico = nombreartistico;
    }

    public String getNombrereal() {
        return nombrereal;
    }

    public void setNombrereal(String nombrereal) {
        this.nombrereal = nombrereal;
    }

    public List<Cancionxinterprete> getCancionxinterpreteList() {
        return cancionxinterpreteList;
    }

    public void setCancionxinterpreteList(List<Cancionxinterprete> cancionxinterpreteList) {
        this.cancionxinterpreteList = cancionxinterpreteList;
    }

    public List<Cancionxinterprete> getCancionxinterpreteList1() {
        return cancionxinterpreteList1;
    }

    public void setCancionxinterpreteList1(List<Cancionxinterprete> cancionxinterpreteList1) {
        this.cancionxinterpreteList1 = cancionxinterpreteList1;
    }

    public Paisc getCodpais() {
        return codpais;
    }

    public void setCodpais(Paisc codpais) {
        this.codpais = codpais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codinterprete != null ? codinterprete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interprete)) {
            return false;
        }
        Interprete other = (Interprete) object;
        if ((this.codinterprete == null && other.codinterprete != null) || (this.codinterprete != null && !this.codinterprete.equals(other.codinterprete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Interprete[ codinterprete=" + codinterprete + " ]";
    }
    
}
