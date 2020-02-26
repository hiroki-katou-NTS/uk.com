package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 労災保険事業: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtOccAccInsurBusPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * 労災保険事業No
    */
    @Basic(optional = false)
    @Column(name = "OCC_ACC_INSUR_BUS_NO")
    public int occAccInsurBusNo;
    
}
