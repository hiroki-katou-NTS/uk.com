package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class KrcmtWkpFxexConPk {

    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String id;


}
