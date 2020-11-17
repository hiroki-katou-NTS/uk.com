package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;

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
    public int checkItemAppapv;

    public static KrcmtWkpfxexAppapvConPK fromDomain(FixedExtractionAppapvCon domain) {
        return new KrcmtWkpfxexAppapvConPK(domain.getErrorAlarmWorkplaceId(), domain.getCheckItemAppapv().value);
    }
}
