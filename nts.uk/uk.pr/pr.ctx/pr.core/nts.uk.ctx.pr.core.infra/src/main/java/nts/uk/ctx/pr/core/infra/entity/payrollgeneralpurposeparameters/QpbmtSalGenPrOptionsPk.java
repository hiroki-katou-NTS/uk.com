package nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与汎用パラメータ選択肢: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtSalGenPrOptionsPk implements Serializable
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
    * 選択肢No
    */
    @Basic(optional = false)
    @Column(name = "OPTION_NO")
    public int optionNo;
    
}
