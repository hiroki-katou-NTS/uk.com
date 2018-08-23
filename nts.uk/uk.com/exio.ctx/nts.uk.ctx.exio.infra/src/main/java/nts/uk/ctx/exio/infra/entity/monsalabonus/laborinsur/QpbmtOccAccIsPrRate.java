package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 労災保険料率
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_OCC_ACC_IS_ PR_RATE")
public class QpbmtOccAccIsPrRate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtOccAccIsPrRatePk occAccIsPrRatePk;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "OCC_ACC_INSUR_BUS_NO")
    public int occAccInsurBusNo;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "FRAC_CLASS")
    public int fracClass;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "EMP_CON_RATIO")
    public int empConRatio;
    
    @Override
    protected Object getKey()
    {
        return occAccIsPrRatePk;
    }

    public OccAccIsPrRate toDomain() {
        return new OccAccIsPrRate(this.occAccIsPrRatePk.ocAcIsPrRtId, this.occAccIsPrRatePk.hisId, this.occAccInsurBusNo, this.fracClass, this.empConRatio);
    }
    public static QpbmtOccAccIsPrRate toEntity(OccAccIsPrRate domain) {
        return new QpbmtOccAccIsPrRate(new QpbmtOccAccIsPrRatePk(domain.getOcAcIsPrRtId(), domain.getHisId()), domain.getOccAccInsurBusNo(), domain.getFracClass(), domain.getEmpConRatio());
    }

}
