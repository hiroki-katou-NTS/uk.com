package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.FixedExtractionMonthlyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * Entity: アラームリスト（職場）月次の固定抽出項目
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_MON_FXEX_ITM")
public class KrcmtWkpMonFxexItm extends AggregateTableEntity {
    @EmbeddedId
    public KrcmtWkpMonFxexItmPK pk;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* 月次チェック名称 */
    @Column(name = "MON_CHKNAME")
    public String monthlyCheckName;

    /* アラームチェック区分 */
    @Column(name = "ALARM_CHK_ATR")
    public int alarmCheckCls;

    /* 最初表示するメッセージ */
    @Column(name = "FIRST_MESSAGE_DIS")
    public String firstMessageDisp;

    /* メッセージの色 */
    @Column(name = "MESSAGE_COLOR")
    public String messageColor;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpMonFxexItm fromDomain(FixedExtractionMonthlyItems domain) {
        KrcmtWkpMonFxexItm entity = new KrcmtWkpMonFxexItm();

        entity.pk = KrcmtWkpMonFxexItmPK.fromDomain(domain);
        entity.contractCd = AppContexts.user().contractCode();
        entity.monthlyCheckName = domain.getMonthlyCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();

        if (domain.getMessageColor().isPresent()) {
            entity.messageColor = domain.getMessageColor().get().v();
        }

        return entity;
    }

    public FixedExtractionMonthlyItems toDomain() {
        FixedExtractionMonthlyItems domain = FixedExtractionMonthlyItems.create(
                EnumAdaptor.valueOf(this.pk.fixedCheckMonthlyItemName, FixedCheckMonthlyItemName.class),
                EnumAdaptor.valueOf(this.alarmCheckCls, AlarmCheckClassification.class),
                this.monthlyCheckName,
                new DisplayMessage(this.firstMessageDisp),
                Optional.of(new ColorCode(this.messageColor))
        );

        return domain;
    }
}
