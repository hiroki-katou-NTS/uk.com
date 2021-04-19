package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class KrcmtWkpBasicFxexConPk {

    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String id;


}
