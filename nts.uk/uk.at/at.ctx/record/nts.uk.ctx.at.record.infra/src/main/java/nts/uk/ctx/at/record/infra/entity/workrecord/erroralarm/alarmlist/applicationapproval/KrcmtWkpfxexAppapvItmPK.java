package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.FixedExtractionAppapvItems;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpfxexAppapvItmPK {

    /* No */
    @Column(name = "NO")
    public int fixedCheckDayItems;

    public static KrcmtWkpfxexAppapvItmPK fromDomain(FixedExtractionAppapvItems domain) {
        return new KrcmtWkpfxexAppapvItmPK(domain.getCheckItemAppapv().value);
    }
}
