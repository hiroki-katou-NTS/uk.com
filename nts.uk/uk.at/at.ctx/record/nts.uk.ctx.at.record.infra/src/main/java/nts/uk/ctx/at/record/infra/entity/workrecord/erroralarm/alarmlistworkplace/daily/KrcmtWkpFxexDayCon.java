package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayCon;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: アラームリスト（職場）日別の固定抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_FXEX_DAY_CON")
public class KrcmtWkpFxexDayCon extends UkJpaEntity {

    /* 職場のエラーアラームチェックID */
    @Id
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItems;

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

    public static KrcmtWkpFxexDayCon fromDomain(FixedExtractionDayCon domain) {
        KrcmtWkpFxexDayCon entity = new KrcmtWkpFxexDayCon();

        entity.errorAlarmWorkplaceId = domain.getErrorAlarmWorkplaceId();
        entity.fixedCheckDayItems = domain.getFixedCheckDayItems().value;
        entity.messageDisp = domain.getMessageDisp().v();
        entity.cid = AppContexts.user().companyId();

        return entity;
    }

    public FixedExtractionDayCon toDomain() {
        return FixedExtractionDayCon.create(
                this.errorAlarmWorkplaceId,
                this.fixedCheckDayItems,
                this.messageDisp
        );
    }
}
