package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* かんたん計算式: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtBasicCalculationStandardAmountPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 計算式コード
     */
    @Basic(optional = false)
    @Column(name = "FORMULA_CD")
    public String formulaCode;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyID;

    /**
     * 使用マスタコード
     */
    @Basic(optional = false)
    @Column(name = "MASTER_USE_CD")
    public String masterUseCode;

    /**
    * 対象項目コードリスト
    */
    @Basic(optional = false)
    @Column(name = "TARGET_ITEM_CD")
    public String targetItemCode;
    
}
