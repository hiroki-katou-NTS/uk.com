package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurPreRate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 雇用保険料率
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_INSUR_PRE_RATE")
public class QpbmtEmpInsurPreRate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpInsurPreRatePk empInsurPreRatePk;

    /**
     * 個人負担率
     */
    @Basic(optional = false)
    @Column(name = "IND_BD_RATIO")
    public String indBdRatio;

    /**
     * 事業主負担率
     */
    @Basic(optional = false)
    @Column(name = "EMP_CONTR_RATIO")
    public String empContrRatio;

    /**
     * 個人端数区分
     */
    @Basic(optional = false)
    @Column(name = "PER_FRAC_CLASS")
    public int perFracClass;

    /**
     * 事業主端数区分
     */
    @Basic(optional = false)
    @Column(name = "BUSI_OW_FRAC_CLASS")
    public int busiOwFracClass;

    @Override
    protected Object getKey()
    {
        return empInsurPreRatePk;
    }

    public EmpInsurPreRate toDomain() {
        return new EmpInsurPreRate(this.empInsurPreRatePk.hisId, this.empInsurPreRatePk.empPreRateId, this.indBdRatio, this.empContrRatio, this.perFracClass, this.busiOwFracClass);
    }
    public static QpbmtEmpInsurPreRate toEntity(EmpInsurPreRate domain) {
        return new QpbmtEmpInsurPreRate(new QpbmtEmpInsurPreRatePk(domain.getHisId(), domain.getEmpPreRateId()), domain.getIndBdRatio(), domain.getEmpContrRatio(), domain.getPerFracClass().value, domain.getBusiOwFracClass().value);
    }

}
