package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItemCustom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemName;

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
     * 項目ID
     */
    @Basic(optional = false)
    @Column(name="ITEM_ID")
    public String itemID;

    // ????????????????????????????????????????????????????????????????
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name="ITEM_ID", referencedColumnName="ITEM_NAME_CD")
//    })
//    public QpbmtStatementItemName statementItemName;
//
//    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumns({
//            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID"),
//            @JoinColumn(name="ITEM_ID", referencedColumnName="SALARY_ITEM_ID")
//    })
//    public QpbmtPayItemDetailSet payItemDetailSet;
//
//    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumns({
//            @JoinColumn(name="HIST_ID", referencedColumnName="HIST_ID"),
//            @JoinColumn(name="ITEM_ID", referencedColumnName="SALARY_ITEM_ID")
//    })
//    public QpbmtDdtItemDetailSet ddtItemDetailSet;

    public SettingByItemCustom toDomain() {
//        return new SettingByItemCustom(this.settingByItemPk.itemPosition, this.itemID, this.statementItemName.shortName,
//                this.ddtItemDetailSet == null ? null : this.ddtItemDetailSet.toDomain(),
//                this.payItemDetailSet == null ?
        return null;
    }

    public static QpbmtSettingByItem toEntity(String histId, int categoryAtr, int lineNumber, SettingByItem settingByItem) {
        QpbmtSettingByItemPk settingByItemPk = new QpbmtSettingByItemPk(histId, categoryAtr, lineNumber, settingByItem.getItemPosition());
        QpbmtPayItemDetailSet payItemDetailSet = null;
        QpbmtDdtItemDetailSet ddtItemDetailSet = null;

        if(settingByItem instanceof SettingByItemCustom) {
            SettingByItemCustom settingByItemCustom = (SettingByItemCustom) settingByItem;
            payItemDetailSet = settingByItemCustom.getPaymentItemDetailSet().map(i -> QpbmtPayItemDetailSet.toEntity(i)).orElse(null);
            ddtItemDetailSet = settingByItemCustom.getDeductionItemDetailSet().map(i -> QpbmtDdtItemDetailSet.toEntity(i)).orElse(null);
        }

        //return new QpbmtSettingByItem(settingByItemPk, settingByItem.getItemId(), null, payItemDetailSet, ddtItemDetailSet);
        return null;
    }
}
