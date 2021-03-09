package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * スケジュール日次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_DAY_LINK")
public class KfndtScheCondDayLink extends JpaEntity implements Serializable {

    @EmbeddedId
    public KfndtScheCondDayLinkPk pk;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KfndtScheCondDayLink toDomain(){
        return new KfndtScheCondDayLink();
    }
}
