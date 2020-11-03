package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.FixedExtractionMonthlyItems;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpMonFxexItmPK {
    /* No */
    @Column(name = "NO")
    public int fixedCheckMonthlyItemName;

    public static KrcmtWkpMonFxexItmPK fromDomain(FixedExtractionMonthlyItems domain) {
        return new KrcmtWkpMonFxexItmPK(domain.getFixedCheckMonthlyItemName().value);
    }
}
