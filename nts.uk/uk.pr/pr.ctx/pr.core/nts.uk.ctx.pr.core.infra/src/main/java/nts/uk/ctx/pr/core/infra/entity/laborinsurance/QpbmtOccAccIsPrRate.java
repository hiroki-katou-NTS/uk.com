package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusiBurdenRatio;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

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
    public BigDecimal empConRatio;
    
    @Override
    protected Object getKey()
    {
        return occAccIsPrRatePk;
    }


    public static List<QpbmtOccAccIsPrRate> toEntity(List<OccAccInsurBusiBurdenRatio> domain,String hisId){
        List<QpbmtOccAccIsPrRate> listEmpInsurBusBurRatio = domain.stream().map(item -> {return new QpbmtOccAccIsPrRate(
                new QpbmtOccAccIsPrRatePk(item.getOccAccInsurBusNo(),hisId),
                item.getFracClass().value,
                item.getEmpConRatio().v());}).collect(Collectors.toList());
        return listEmpInsurBusBurRatio;
    }



}
