package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItemCustom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemName;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.itemrangeset.QpbmtStateItemRangeSet;
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
            @JoinColumn(name="CATEGORY_ATR", referencedColumnName="CATEGORY_ATR", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_NAME_CD", referencedColumnName="ITEM_NAME_CD", insertable = false, updatable = false)
    })
    public QpbmtStatementItemName statementItemName;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
            @JoinColumn(name="STATEMENT_CD", referencedColumnName="STATEMENT_CD", insertable = false, updatable = false),
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_NAME_CD", referencedColumnName="ITEM_NAME_CD", insertable = false, updatable = false)
    })
    public QpbmtPayItemDetailSet payItemDetailSet;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
            @JoinColumn(name="STATEMENT_CD", referencedColumnName="STATEMENT_CD", insertable = false, updatable = false),
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_NAME_CD", referencedColumnName="ITEM_NAME_CD", insertable = false, updatable = false)
    })
    public QpbmtDdtItemDetailSet ddtItemDetailSet;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
            @JoinColumn(name="STATEMENT_CD", referencedColumnName="STATEMENT_CD", insertable = false, updatable = false),
            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID", insertable = false, updatable = false),
            @JoinColumn(name="ITEM_NAME_CD", referencedColumnName="ITEM_NAME_CD", insertable = false, updatable = false),
            @JoinColumn(name="CATEGORY_ATR", referencedColumnName="CATEGORY_ATR", insertable = false, updatable = false)
    })
    public QpbmtStateItemRangeSet itemRangeSet;

    public SettingByItemCustom toDomain() {
        return new SettingByItemCustom(this.settingByItemPk.itemPosition, this.itemNameCd,
                this.statementItemName == null ? null : this.statementItemName.shortName,
                this.ddtItemDetailSet == null ? null : this.ddtItemDetailSet.toDomain(),
                this.payItemDetailSet == null ? null : this.payItemDetailSet.toDomain(),
                this.itemRangeSet == null ? null : this.itemRangeSet.toDomain());
    }

    public static QpbmtSettingByItem toEntity(String cid, String statementCd, String histId, int categoryAtr, int lineNumber, SettingByItem settingByItem) {
        QpbmtSettingByItemPk settingByItemPk = new QpbmtSettingByItemPk(cid, statementCd, histId, categoryAtr, lineNumber, settingByItem.getItemPosition());
        QpbmtPayItemDetailSet payItemDetailSet = null;
        QpbmtDdtItemDetailSet ddtItemDetailSet = null;
        QpbmtStateItemRangeSet itemRangeSet = null;

        if(settingByItem instanceof SettingByItemCustom) {
            SettingByItemCustom settingByItemCustom = (SettingByItemCustom) settingByItem;
            if (CategoryAtr.PAYMENT_ITEM.value == categoryAtr){
                payItemDetailSet = settingByItemCustom.getPaymentItemDetailSet().map(i -> QpbmtPayItemDetailSet.toEntity(i)).orElse(null);
            }
            if (CategoryAtr.DEDUCTION_ITEM.value == categoryAtr){
                ddtItemDetailSet = settingByItemCustom.getDeductionItemDetailSet().map(i -> QpbmtDdtItemDetailSet.toEntity(i)).orElse(null);
            }
            itemRangeSet = settingByItemCustom.getItemRangeSetting().map(i -> QpbmtStateItemRangeSet.toEntity(i)).orElse(null);
        }

        return new QpbmtSettingByItem(settingByItemPk, settingByItem.getItemId(), null, payItemDetailSet, ddtItemDetailSet, itemRangeSet);
    }
}
