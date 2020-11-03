package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.FixedExtractionMonthlyCon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpMonFxexConPK {
    /* 職場のエラーアラームチェックID */
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int fixedCheckMonthlyItemName;

    public static KrcmtWkpMonFxexConPK fromDomain(FixedExtractionMonthlyCon domain) {
        return new KrcmtWkpMonFxexConPK(domain.getErrorAlarmWorkplaceId(), domain.getFixedCheckMonthlyItemName().value);
    }
}
