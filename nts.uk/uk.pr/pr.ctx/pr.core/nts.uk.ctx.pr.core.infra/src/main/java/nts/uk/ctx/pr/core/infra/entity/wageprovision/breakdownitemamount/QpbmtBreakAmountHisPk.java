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
public class QpbmtBreakAmountHisPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String employeeId;

    /**
     * カテゴリ区分
     */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ATR")
    public int categoryAtr;

    /**
     * 項目名コード
     */
    @Basic(optional = false)
    @Column(name = "ITEM_NAME_CD")
    public String itemNameCd;

    /**
     * 給与賞与区分
     */
    @Basic(optional = false)
    @Column(name = "SALARY_BONUS_ATR")
    public int salaryBonusAtr;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyId;
}
