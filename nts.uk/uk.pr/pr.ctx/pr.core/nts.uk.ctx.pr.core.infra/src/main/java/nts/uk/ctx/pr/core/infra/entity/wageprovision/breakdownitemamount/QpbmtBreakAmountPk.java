package nts.uk.ctx.pr.core.infra.entity.wageprovision.breakdownitemamount;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与内訳個人金額履歴: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtBreakAmountPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;
    
    /**
    * 内訳項目コード
    */
    @Basic(optional = false)
    @Column(name = "BREAKDOWN_ITEM_CODE ")
    public String breakdownItemCode ;
    
}
