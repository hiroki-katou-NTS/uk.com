package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.LineByLineSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItemCustom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 行別設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_LINE_BY_LINE_SET")
public class QpbmtLineByLineSet extends UkJpaEntity implements Serializable {

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtLineByLineSetPk lineByLineSetPk;
    /**
     * 開始日
     */
    @Basic(optional = false)
    @Column(name = "PRINT_SET")
    public int printSet;

    /**
     * 終了日
     */
    @Basic(optional = false)
    @Column(name = "ITEM_POSITION")
    public int itemPosition;

    // ????????????????????????????????????????????????????????????????
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="ITEM_ID", referencedColumnName="ITEM_NAME_CD")
    })
    public QpbmtStatementItemName statementItemName;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID"),
            @JoinColumn(name="ITEM_ID", referencedColumnName="SALARY_ITEM_ID")
    })
    public QpbmtPayItemDetailSet payItemDetailSet;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID"),
            @JoinColumn(name="ITEM_ID", referencedColumnName="SALARY_ITEM_ID")
    })
    public QpbmtDdtItemDetailSet ddtItemDetailSet;

    @Override
    protected Object getKey() {
        return lineByLineSetPk;
    }

    public Integer getLine() {
        return lineByLineSetPk.lineNumber;
    }

    public SettingByItemCustom toDomain() {
        return new SettingByItemCustom(this.itemPosition, this.lineByLineSetPk.itemID, this.statementItemName.shortName,
                this.ddtItemDetailSet == null ? null : this.ddtItemDetailSet.toDomain(),
                this.payItemDetailSet == null ? null : this.payItemDetailSet.toDomain());
    }

    public static QpbmtLineByLineSet toEntity(String histId, int categoryAtr, int printSet, int lineNumber, SettingByItem settingByItem) {
        QpbmtLineByLineSetPk lineByLineSetPk = new QpbmtLineByLineSetPk(histId, categoryAtr, lineNumber, settingByItem.getItemId());
        QpbmtPayItemDetailSet payItemDetailSet = null;
        QpbmtDdtItemDetailSet ddtItemDetailSet = null;

        if(settingByItem instanceof SettingByItemCustom) {
            SettingByItemCustom settingByItemCustom = (SettingByItemCustom) settingByItem;
            payItemDetailSet = settingByItemCustom.getPaymentItemDetailSet().map(i -> QpbmtPayItemDetailSet.toEntity(i)).orElse(null);
            ddtItemDetailSet = settingByItemCustom.getDeductionItemDetailSet().map(i -> QpbmtDdtItemDetailSet.toEntity(i)).orElse(null);
        }

        return new QpbmtLineByLineSet(lineByLineSetPk, printSet, settingByItem.getItemPosition(), null, payItemDetailSet, ddtItemDetailSet);
    }
}
