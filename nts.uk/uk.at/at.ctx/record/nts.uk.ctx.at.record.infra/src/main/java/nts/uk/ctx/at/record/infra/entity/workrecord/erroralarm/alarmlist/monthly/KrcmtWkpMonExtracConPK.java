package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.ExtractionMonthlyCon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpMonExtracConPK {
    /* 職場のエラーアラームチェックID */
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    public static KrcmtWkpMonExtracConPK fromDomain(ExtractionMonthlyCon domain) {
        return new KrcmtWkpMonExtracConPK(domain.getErrorAlarmWorkplaceId());
    }
}
