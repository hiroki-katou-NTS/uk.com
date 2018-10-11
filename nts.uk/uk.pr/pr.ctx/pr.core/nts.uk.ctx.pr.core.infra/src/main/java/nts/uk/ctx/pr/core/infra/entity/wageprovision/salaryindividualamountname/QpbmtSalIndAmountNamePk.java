package nts.uk.ctx.pr.core.infra.entity.wageprovision.salaryindividualamountname;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与個人別金額名称: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtSalIndAmountNamePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * 個人金額コード
    */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_PRICE_CODE")
    public String individualPriceCode;
    
}
