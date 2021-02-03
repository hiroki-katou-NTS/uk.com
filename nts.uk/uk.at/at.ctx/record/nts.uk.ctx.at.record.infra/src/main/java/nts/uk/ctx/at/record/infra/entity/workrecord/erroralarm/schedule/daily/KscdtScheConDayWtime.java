package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * スケジュール日次のチェック条件（就業時間帯）
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KSCDT_SCHE_COND_DAY_WTIME")
public class KscdtScheConDayWtime extends ContractUkJpaEntity {
    @EmbeddedId
    public KscdtScheCondDayWtimePk pk;
    @Override
    protected Object getKey() {
        return this.pk;
    }
}
