package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBus;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
* 労災保険事業
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_OCC_ACC_INSUR_BUS")
public class QpbmtOccAccInsurBus extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtOccAccInsurBusPk occAccInsurBusPk;
    
    /**
    * 利用する
    */
    @Basic(optional = false)
    @Column(name = "USE_ATR")
    public int toUse;
    
    /**
    * 名称
    */
    @Basic(optional = true)
    @Column(name = "NAME")
    public String name;
    
    @Override
    protected Object getKey()
    {
        return occAccInsurBusPk;
    }

    public static List<QpbmtOccAccInsurBus> toEntity(OccAccInsurBus domain){
        List<QpbmtOccAccInsurBus> qpbmtOccAccInsurBus = domain.getEachBusiness().stream().map(item ->{
            return new QpbmtOccAccInsurBus( new QpbmtOccAccInsurBusPk(domain.getCid(),item.getOccAccInsurBusNo()),item.getToUse(),item.getName().get().v());
        }).collect(Collectors.toList());

        return qpbmtOccAccInsurBus;
    }

}
