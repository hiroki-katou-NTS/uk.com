package nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与汎用パラメータ年月履歴: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtSalGenParaYmHisPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * パラメータNo
    */
    @Basic(optional = false)
    @Column(name = "PARA_NO")
    public String paraNo;
    
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
    @Column(name = "HIS_ID")
    public String hisId;
    
}
