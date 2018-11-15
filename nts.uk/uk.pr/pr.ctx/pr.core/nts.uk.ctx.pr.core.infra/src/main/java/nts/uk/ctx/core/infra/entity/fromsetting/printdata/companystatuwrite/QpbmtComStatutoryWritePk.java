package nts.uk.ctx.core.infra.entity.fromsetting.printdata.companystatuwrite;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 法定調書用会社: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtComStatutoryWritePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "CODE")
    public String code;
    
}
