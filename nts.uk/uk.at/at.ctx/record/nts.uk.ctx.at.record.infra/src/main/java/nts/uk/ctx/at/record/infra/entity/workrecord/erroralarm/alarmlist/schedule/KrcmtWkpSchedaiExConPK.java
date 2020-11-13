package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.ExtractionScheduleCon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtWkpSchedaiExConPK {

    /* スケジュール／日次抽出条件ID */
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    public static KrcmtWkpSchedaiExConPK fromDomain(ExtractionScheduleCon domain) {
        return new KrcmtWkpSchedaiExConPK(domain.getErrorAlarmWorkplaceId());
    }
}
