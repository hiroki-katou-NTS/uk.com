package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

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
    @Column(name = "TO_USE")
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


}
