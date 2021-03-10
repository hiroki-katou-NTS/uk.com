package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * スケジュール年間のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_YEAR_LINK")
public class KfndtScheCondYearLink extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KfndtScheCondMonthYearPk pk;


    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KfndtScheCondYearLink toDomain(){
        return new KfndtScheCondYearLink();
    }
}
