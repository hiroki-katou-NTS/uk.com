package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.InsuPremiumFractionClassification;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 労災保険料率
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_OCC_ACC_IS_PR_RATE")
public class QpbmtOccAccIsPrRate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtOccAccIsPrRatePk occAccIsPrRatePk;
    
    /**
    * 労災保険事業No
    */
    @Basic(optional = false)
    @Column(name = "OCC_ACC_INSUR_BUS_NO")
    public int occAccInsurBusNo;
    
    /**
    * 端数区分
    */
    @Basic(optional = false)
    @Column(name = "FRAC_CLASS")
    public int fracClass;
    
    /**
    * 事業主負担率
    */
    @Basic(optional = false)
    @Column(name = "EMP_CON_RATIO")
    public String empConRatio;
    
    @Override
    protected Object getKey()
    {
        return occAccIsPrRatePk;
    }

    public OccAccIsPrRate toDomain() {
        return new OccAccIsPrRate(this.occAccIsPrRatePk.ocAcIsPrRtId, this.occAccIsPrRatePk.hisId, this.occAccInsurBusNo,  EnumAdaptor.valueOf(this.fracClass, InsuPremiumFractionClassification.class), this.empConRatio);
    }
    public static QpbmtOccAccIsPrRate toEntity(OccAccIsPrRate domain) {
        return new QpbmtOccAccIsPrRate(new QpbmtOccAccIsPrRatePk(domain.getOcAcIsPrRtId(), domain.getHisId()), domain.getOccAccInsurBusNo(), domain.getFracClass().value, domain.getEmpConRatio());
    }

}
