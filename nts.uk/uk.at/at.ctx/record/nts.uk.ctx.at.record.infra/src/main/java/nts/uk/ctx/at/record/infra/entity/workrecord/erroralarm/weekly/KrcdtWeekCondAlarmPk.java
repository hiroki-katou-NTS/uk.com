package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 週別実績の任意抽出条件 Entity
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcdtWeekCondAlarmPk implements Serializable {
    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    /* ID */
    @Column(name = "ERROR_ALARM_CD")
    public String code;

}
