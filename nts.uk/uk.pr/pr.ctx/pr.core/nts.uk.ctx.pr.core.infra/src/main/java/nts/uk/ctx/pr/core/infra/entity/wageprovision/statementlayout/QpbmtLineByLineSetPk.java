package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 行別設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtLineByLineSetPk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String histId;

    /**
     * カテゴリ区分
     */
    @Basic(optional = false)
    @Column(name="CTG_ATR")
    public int categoryAtr;

    /**
     * 行番号
     */
    @Basic(optional = false)
    @Column(name="LINE_NUM")
    public int lineNumber;

    /**
     * 項目ID
     */
    @Basic(optional = false)
    @Column(name="ITEM_ID")
    public String itemID;
}
