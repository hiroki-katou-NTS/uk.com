package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.FixedExtractionScheduleItems;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpSchedaiFxexItmPK {
    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItemName;

    public static KrcmtWkpSchedaiFxexItmPK fromDomain(FixedExtractionScheduleItems domain) {
        return new KrcmtWkpSchedaiFxexItmPK(domain.getFixedCheckDayItemName().value);
    }
}
