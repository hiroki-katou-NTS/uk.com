package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.FixedExtractionAppapvCon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpfxexAppapvConPK {

    /* 職場のエラーアラームチェックID */
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItems;

    public static KrcmtWkpfxexAppapvConPK fromDomain(FixedExtractionAppapvCon domain) {
        return new KrcmtWkpfxexAppapvConPK(domain.getErrorAlarmWorkplaceId(), domain.getCheckItemAppapv().value);
    }
}
