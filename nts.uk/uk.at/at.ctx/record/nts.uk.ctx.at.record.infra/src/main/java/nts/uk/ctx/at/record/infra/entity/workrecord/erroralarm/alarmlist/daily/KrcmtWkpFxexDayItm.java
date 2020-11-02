package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedCheckDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedExtractionDayItems;
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
 * Entity: アラームリスト（職場）日別の固定抽出項目
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_FXEX_DAY_ITM")
public class KrcmtWkpFxexDayItm extends AggregateTableEntity {
    @EmbeddedId
    public KrcmtWkpFxexDayItmPK pk;

    /* 契約コード */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /* 日別チェック名称 */
    @Column(name = "DAILY_CHECK_NAME")
    public String dailyCheckName;

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

    public static KrcmtWkpFxexDayItm fromDomain(FixedExtractionDayItems domain) {
        KrcmtWkpFxexDayItm entity = new KrcmtWkpFxexDayItm();

        entity.pk = KrcmtWkpFxexDayItmPK.fromDomain(domain);
        entity.contractCd = AppContexts.user().contractCode();
        entity.dailyCheckName = domain.getDailyCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.boldAtr = domain.isBoldAtr();

        if (domain.getMessageColor().isPresent()) {
            entity.messageColor = domain.getMessageColor().get().v();
        }

        return entity;
    }

    public FixedExtractionDayItems toDomain() {
        FixedExtractionDayItems domain = FixedExtractionDayItems.create(
                EnumAdaptor.valueOf(this.pk.fixedCheckDayItems, FixedCheckDayItems.class),
                EnumAdaptor.valueOf(this.alarmCheckCls, AlarmCheckClassification.class),
                this.boldAtr,
                this.dailyCheckName,
                new DisplayMessage(this.firstMessageDisp),
                toColorCode()
        );

        return domain;
    }

    private Optional<ColorCode> toColorCode() {
        if (this.messageColor != null && !this.messageColor.trim().isEmpty()) {
            return Optional.of(new ColorCode(messageColor));
        }

        return Optional.empty();
    }
}
