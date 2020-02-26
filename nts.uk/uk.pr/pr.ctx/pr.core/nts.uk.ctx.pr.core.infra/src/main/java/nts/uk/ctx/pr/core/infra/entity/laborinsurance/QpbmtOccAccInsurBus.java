package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBus;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

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
    * 名称
    */
    @Basic(optional = true)
    @Column(name = "NAME")
    public String name;
    
    /**
    * 利用する
    */
    @Basic(optional = false)
    @Column(name = "USE_ATR")
    public int useAtr;
    
    @Override
    protected Object getKey(){
        return occAccInsurBusPk;
    }
    public static List<QpbmtOccAccInsurBus> toEntity(OccAccInsurBus domain){
        List<QpbmtOccAccInsurBus> qpbmtOccAccInsurBus = domain.getEachBusiness().stream().map(item ->{
            return new QpbmtOccAccInsurBus( new QpbmtOccAccInsurBusPk(domain.getCid(),item.getOccAccInsurBusNo()), item.getName().get().v(), item.getToUse());
        }).collect(Collectors.toList());
        return qpbmtOccAccInsurBus;
    }

}
