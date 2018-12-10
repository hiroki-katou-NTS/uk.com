package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItemCustom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemName;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SETTING_BY_ITEM")
public class QpbmtSettingByItem {
    /**
     * ID
     */
    @EmbeddedId
    QpbmtSettingByItemPk settingByItemPk;

    /**
     * 項目名コード
     */
    @Basic(optional = false)
    @Column(name = "ITEM_NAME_CD")
    public String itemNameCd;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
            @JoinColumn(name="CTG_ATR", referencedColumnName="CATEGORY_ATR", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_NAME_CD", referencedColumnName="ITEM_NAME_CD", insertable = false, updatable = false)
    })
    public QpbmtStatementItemName statementItemName;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID", insertable = false, updatable = false),
            @JoinColumn(name="CTG_ATR", referencedColumnName="CTG_ATR", insertable = false, updatable = false),
            @JoinColumn(name="LINE_NUM", referencedColumnName="LINE_NUM", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_POSITION", referencedColumnName="ITEM_POSITION", insertable = false, updatable = false)
    })
    public QpbmtPayItemDetailSet payItemDetailSet;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID", insertable = false, updatable = false),
            @JoinColumn(name="CTG_ATR", referencedColumnName="CTG_ATR", insertable = false, updatable = false),
            @JoinColumn(name="LINE_NUM", referencedColumnName="LINE_NUM", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_POSITION", referencedColumnName="ITEM_POSITION", insertable = false, updatable = false)
    })
    public QpbmtDdtItemDetailSet ddtItemDetailSet;

    public SettingByItemCustom toDomain() {
        return new SettingByItemCustom(this.settingByItemPk.itemPosition, this.itemNameCd,
                this.statementItemName == null ? null : this.statementItemName.shortName,
                this.ddtItemDetailSet == null ? null : this.ddtItemDetailSet.toDomain(),
                this.payItemDetailSet == null ? null : this.payItemDetailSet.toDomain());
    }

    public static QpbmtSettingByItem toEntity(String cid, String statementCd, String histId, int categoryAtr, int lineNumber, SettingByItem settingByItem) {
        QpbmtSettingByItemPk settingByItemPk = new QpbmtSettingByItemPk(cid, statementCd, histId, categoryAtr, lineNumber, settingByItem.getItemPosition());
        QpbmtPayItemDetailSet payItemDetailSet = null;
        QpbmtDdtItemDetailSet ddtItemDetailSet = null;

        if(settingByItem instanceof SettingByItemCustom) {
            SettingByItemCustom settingByItemCustom = (SettingByItemCustom) settingByItem;
            payItemDetailSet = settingByItemCustom.getPaymentItemDetailSet().map(i -> QpbmtPayItemDetailSet.toEntity(i, categoryAtr, lineNumber, settingByItem.getItemPosition())).orElse(null);
            ddtItemDetailSet = settingByItemCustom.getDeductionItemDetailSet().map(i -> QpbmtDdtItemDetailSet.toEntity(i, categoryAtr, lineNumber, settingByItem.getItemPosition())).orElse(null);
        }

        return new QpbmtSettingByItem(settingByItemPk, settingByItem.getItemId(), null, payItemDetailSet, ddtItemDetailSet);
    }
}
