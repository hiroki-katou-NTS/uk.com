package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayCon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpFxexDayConPK {

    /* 職場のエラーアラームチェックID */
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItems;

    public static KrcmtWkpFxexDayConPK fromDomain(FixedExtractionDayCon domain) {
        return new KrcmtWkpFxexDayConPK(domain.getErrorAlarmWorkplaceId(), domain.getFixedCheckDayItems().value);
    }
}
