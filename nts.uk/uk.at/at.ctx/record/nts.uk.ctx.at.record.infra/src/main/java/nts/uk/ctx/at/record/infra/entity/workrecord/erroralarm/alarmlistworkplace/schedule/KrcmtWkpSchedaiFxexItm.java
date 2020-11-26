package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItems;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: アラームリスト（職場別）スケジュール／日次の固定抽出項目
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_SCHEDAI_FXEXITM")
public class KrcmtWkpSchedaiFxexItm extends ContractUkJpaEntity implements Serializable {

    /* No */
    @Id
    @Column(name = "NO")
    public int fixedCheckDayItemName;

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

    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    @Override
    protected Object getKey() {
        return fixedCheckDayItemName;
    }

    public static KrcmtWkpSchedaiFxexItm fromDomain(FixedExtractionScheduleItems domain) {
        KrcmtWkpSchedaiFxexItm entity = new KrcmtWkpSchedaiFxexItm();

        entity.fixedCheckDayItemName = domain.getFixedCheckDayItemName().value;
        entity.scheduleCheckName = domain.getScheduleCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.boldAtr = domain.isBoldAtr();
        entity.messageColor = domain.getMessageColor().map(i -> i.v()).orElse(null);
        entity.cid = AppContexts.user().companyId();

        return entity;
    }

    public FixedExtractionScheduleItems toDomain() {
        return FixedExtractionScheduleItems.create(
                this.fixedCheckDayItemName,
                this.alarmCheckCls,
                this.scheduleCheckName,
                this.boldAtr,
                this.firstMessageDisp,
                this.messageColor
        );
    }
}
