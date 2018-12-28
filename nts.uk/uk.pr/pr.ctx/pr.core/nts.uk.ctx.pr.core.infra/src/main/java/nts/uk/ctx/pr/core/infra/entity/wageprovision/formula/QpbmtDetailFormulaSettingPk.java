package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 詳細計算式: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtDetailFormulaSettingPk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyID;
    
    /**
    * 構成順
    */
    @Basic(optional = false)
    @Column(name = "ELEMENT_ORDER")
    public int elementOrder;
    
}
