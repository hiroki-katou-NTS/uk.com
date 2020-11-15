package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedExtractionDayItems;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    /* No */
    @Id
    @Column(name = "NO")
    public int fixedCheckDayItems;

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
        return fixedCheckDayItems;
    }

    public static KrcmtWkpFxexDayItm fromDomain(FixedExtractionDayItems domain) {
        KrcmtWkpFxexDayItm entity = new KrcmtWkpFxexDayItm();

        entity.fixedCheckDayItems = domain.getFixedCheckDayItems().value;
        entity.contractCd = AppContexts.user().contractCode();
        entity.dailyCheckName = domain.getDailyCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.boldAtr = domain.isBoldAtr();

        entity.messageColor = domain.getMessageColor().isPresent() ? domain.getMessageColor().get().v() : null;

        return entity;
    }

    public FixedExtractionDayItems toDomain() {
        return FixedExtractionDayItems.create(
                this.fixedCheckDayItems,
                this.alarmCheckCls,
                this.boldAtr,
                this.dailyCheckName,
                this.firstMessageDisp,
                this.messageColor
        );
    }
}
