package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleCon;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: アラームリスト（職場別）スケジュール／日次の固定抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_SCHEDAI_FXEXCON")
public class KrcmtWkpSchedaiFxexCon extends UkJpaEntity {

    /* 職場のエラーアラームチェックID */
    @Id
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItemName;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    private boolean useAtr;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    @Override
    protected Object getKey() {
        return errorAlarmWorkplaceId;
    }

    public static KrcmtWkpSchedaiFxexCon fromDomain(FixedExtractionScheduleCon domain) {
        KrcmtWkpSchedaiFxexCon entity = new KrcmtWkpSchedaiFxexCon();

        entity.errorAlarmWorkplaceId = domain.getErrorAlarmWorkplaceId();
        entity.fixedCheckDayItemName = domain.getFixedCheckDayItemName().value;
        entity.useAtr = domain.isUseAtr();
        entity.messageDisp = domain.getMessageDisp().v();
        entity.cid = AppContexts.user().companyId();

        return entity;
    }

    public FixedExtractionScheduleCon toDomain() {
        return FixedExtractionScheduleCon.create(
                this.errorAlarmWorkplaceId,
                this.fixedCheckDayItemName,
                this.useAtr,
                this.messageDisp
        );
    }
}
