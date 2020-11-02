package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedExtractionDayItems;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpFxexDayItmPK {

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItems;

    public static KrcmtWkpFxexDayItmPK fromDomain(FixedExtractionDayItems domain) {
        return new KrcmtWkpFxexDayItmPK(domain.getFixedCheckDayItems().value);
    }
}
