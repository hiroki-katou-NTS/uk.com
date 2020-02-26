package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 現在処理年月: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtCurrProcessDatePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * CID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * PROCESS_CATE_NO
    */
    @Basic(optional = false)
    @Column(name = "PROCESS_CATE_NO")
    public int processCateNo;
    
}
