package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.weekly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  週次のアラームチェック条件 Entity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_WEEK_COND_ALARM_LINK")
public class KfndtWeekCondAlarmLink extends JpaEntity implements Serializable {

    @EmbeddedId
    @Column(name = "ERAL_CHECK_ID")
    public KfndtWeekCondAlarmLinkPk pk;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KfndtWeekCondAlarmLink toDomain(){
        return new KfndtWeekCondAlarmLink();
    }
}
