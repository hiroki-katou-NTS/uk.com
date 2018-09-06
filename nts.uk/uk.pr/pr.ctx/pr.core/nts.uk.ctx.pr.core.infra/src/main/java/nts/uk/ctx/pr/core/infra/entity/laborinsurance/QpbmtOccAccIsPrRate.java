package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatio;
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



}
