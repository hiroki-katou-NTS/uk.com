package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItems;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: アラームリスト（職場）日別の固定抽出項目
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_FXEX_DAY_ITM")
public class KrcmtWkpFxexDayItm extends ContractUkJpaEntity  implements Serializable {

    /* No */
    @Id
    @Column(name = "NO")
    public int fixedCheckDayItems;

    /* 日別チェック名称 */
    @Column(name = "DAILY_CHECK_NAME")
    public String dailyCheckName;

    /* アラームチェック区分 */
    @Column(name = "ALARM_CHK_ATR")
    public int alarmCheckCls;

    /* 最初表示するメッセージ */
    @Column(name = "FIRST_MESSAGE_DIS")
    public String firstMessageDisp;

    /* メッセージの色 */
    @Column(name = "MESSAGE_COLOR")
    public String messageColor;

    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    @Override
    protected Object getKey() {
        return fixedCheckDayItems;
    }

    public static KrcmtWkpFxexDayItm fromDomain(FixedExtractionDayItems domain) {
        KrcmtWkpFxexDayItm entity = new KrcmtWkpFxexDayItm();

        entity.fixedCheckDayItems = domain.getFixedCheckDayItems().value;
        entity.dailyCheckName = domain.getDailyCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.messageColor = domain.getMessageColor().map(i -> i.v()).orElse(null);
        entity.cid = AppContexts.user().companyId();

        return entity;
    }

    public FixedExtractionDayItems toDomain() {
        return FixedExtractionDayItems.create(
                this.fixedCheckDayItems,
                this.alarmCheckCls,
                this.dailyCheckName,
                this.firstMessageDisp,
                this.messageColor
        );
    }
}
