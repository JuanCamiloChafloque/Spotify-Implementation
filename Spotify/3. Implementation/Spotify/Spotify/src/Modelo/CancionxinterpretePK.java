
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class CancionxinterpretePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODCANCION")
    private BigDecimal codcancion;
    @Basic(optional = false)
    @Column(name = "CODINTERPRETE")
    private BigDecimal codinterprete;

    public CancionxinterpretePK() {
    }

    public CancionxinterpretePK(BigDecimal codcancion, BigDecimal codinterprete) {
        this.codcancion = codcancion;
        this.codinterprete = codinterprete;
    }

    public BigDecimal getCodcancion() {
        return codcancion;
    }

    public void setCodcancion(BigDecimal codcancion) {
        this.codcancion = codcancion;
    }

    public BigDecimal getCodinterprete() {
        return codinterprete;
    }

    public void setCodinterprete(BigDecimal codinterprete) {
        this.codinterprete = codinterprete;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancionxinterpretePK)) {
            return false;
        }
        CancionxinterpretePK other = (CancionxinterpretePK) object;
        if (this.codcancion != other.codcancion) {
            return false;
        }
        if (this.codinterprete != other.codinterprete) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.CancionxinterpretePK[ codcancion=" + codcancion + ", codinterprete=" + codinterprete + " ]";
    }
    
}
