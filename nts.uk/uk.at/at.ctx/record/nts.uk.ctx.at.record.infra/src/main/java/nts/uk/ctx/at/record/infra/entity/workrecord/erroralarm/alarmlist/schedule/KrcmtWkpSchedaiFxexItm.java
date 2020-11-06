package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.FixedCheckDayItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.FixedExtractionScheduleItems;
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
 * Entity: アラームリスト（職場別）スケジュール／日次の固定抽出項目
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_SCHEDAI_FXEXITM")
public class KrcmtWkpSchedaiFxexItm extends AggregateTableEntity {
    @EmbeddedId
    public KrcmtWkpSchedaiFxexItmPK pk;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* スケジュール／日次チェック名称 */
    @Column(name = "SCHEDAI_CHKNAME")
    public String scheduleCheckName;

    /* アラームチェック区分 */
    @Column(name = "ALARM_CHK_ATR")
    public int alarmCheckCls;

    /* 最初表示するメッセージ */
    @Column(name = "FIRST_MESSAGE_DIS")
    public String firstMessageDisp;

    /* メッセージを太字にする */
    @Column(name = "MESSAGE_BOLD")
    public boolean boldAtr;

    /* メッセージの色 */
    @Column(name = "MESSAGE_COLOR")
    public String messageColor;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpSchedaiFxexItm fromDomain(FixedExtractionScheduleItems domain) {
        KrcmtWkpSchedaiFxexItm entity = new KrcmtWkpSchedaiFxexItm();

        entity.pk = KrcmtWkpSchedaiFxexItmPK.fromDomain(domain);
        entity.contractCd = AppContexts.user().contractCode();
        entity.scheduleCheckName = domain.getScheduleCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.boldAtr = domain.isBoldAtr();

        if (domain.getMessageColor().isPresent()) {
            entity.messageColor = domain.getMessageColor().get().v();
        }

        return entity;
    }

    public FixedExtractionScheduleItems toDomain() {
        FixedExtractionScheduleItems domain = FixedExtractionScheduleItems.create(
                EnumAdaptor.valueOf(this.pk.fixedCheckDayItemName, FixedCheckDayItemName.class),
                EnumAdaptor.valueOf(this.alarmCheckCls, AlarmCheckClassification.class),
                this.boldAtr,
                this.scheduleCheckName,
                new DisplayMessage(this.firstMessageDisp),
                Optional.of(new ColorCode(this.messageColor))
        );

        return domain;
    }
}
