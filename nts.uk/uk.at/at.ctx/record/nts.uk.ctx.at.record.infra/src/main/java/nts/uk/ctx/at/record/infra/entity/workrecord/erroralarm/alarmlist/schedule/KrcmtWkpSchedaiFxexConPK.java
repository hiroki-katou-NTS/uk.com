package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.FixedExtractionScheduleCon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpSchedaiFxexConPK {

    /* 職場のエラーアラームチェックID */
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItemName;

    public static KrcmtWkpSchedaiFxexConPK fromDomain(FixedExtractionScheduleCon domain) {
        return new KrcmtWkpSchedaiFxexConPK(domain.getErrorAlarmWorkplaceId(), domain.getFixedCheckDayItemName().value);
    }
}
