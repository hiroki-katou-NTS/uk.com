package nts.uk.ctx.pr.core.infra.entity.wageprovision.individualwagecontract;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与個人別金額履歴: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtSalIndAmountHisPk implements Serializable
{
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
    public String empId;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyId;
    
    /**
    * 給与賞与区分
    */
    @Basic(optional = false)
    @Column(name = "SALARY_BONUS_ATR")
    public int salBonusCate;
    
    /**
    * カテゴリ区分
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ATR")
    public int cateIndicator;
    
    /**
    * 個人金額コード
    */
    @Basic(optional = false)
    @Column(name = "PER_VAL_CD")
    public String perValCode;
    
}
