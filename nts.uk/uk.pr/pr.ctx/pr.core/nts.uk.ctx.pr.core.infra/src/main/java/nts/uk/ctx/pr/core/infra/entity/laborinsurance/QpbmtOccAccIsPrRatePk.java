package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 労災保険料率: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
	public class QpbmtOccAccIsPrRatePk implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;
    
    /**
    * 労災保険事業No
    */
    @Basic(optional = false)
    @Column(name = "OCC_ACC_IS_BUS_NO")
    public int occAccIsBusNo;
    
}
