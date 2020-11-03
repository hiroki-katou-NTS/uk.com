package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: アラームリスト（職場）月次の固定抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_MON_FXEX_CON")
public class KrcmtWkpMonFxexCon extends AggregateTableEntity {
    @EmbeddedId
    public KrcmtWkpMonFxexConPK pk;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    private boolean useAtr;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpMonFxexCon fromDomain(FixedExtractionMonthlyCon domain) {
        KrcmtWkpMonFxexCon entity = new KrcmtWkpMonFxexCon();

        entity.pk = KrcmtWkpMonFxexConPK.fromDomain(domain);
        entity.contractCd = AppContexts.user().contractCode();
        entity.useAtr = domain.isUseAtr();
        entity.messageDisp = domain.getMessageDisp().v();

        return entity;
    }

    public FixedExtractionMonthlyCon toDomain() {
        FixedExtractionMonthlyCon domain = FixedExtractionMonthlyCon.create(
                this.pk.errorAlarmWorkplaceId,
                EnumAdaptor.valueOf(this.pk.fixedCheckMonthlyItemName, FixedCheckMonthlyItemName.class),
                this.useAtr,
                new DisplayMessage(this.messageDisp)
        );

        return domain;
    }
}
