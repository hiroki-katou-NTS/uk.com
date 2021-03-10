package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  スケジュール月次のアラームチェック条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNDT_SCHE_COND_MONTH_LINK")
public class KfndtScheCondMonthLink extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KfndtScheCondMonthLinkPk pk;

    @Column(name = "ERAL_CHECK_ID")
    public String eralCheckId;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KfndtScheCondMonthLink toDomain(){
        return new KfndtScheCondMonthLink();
    }
}
