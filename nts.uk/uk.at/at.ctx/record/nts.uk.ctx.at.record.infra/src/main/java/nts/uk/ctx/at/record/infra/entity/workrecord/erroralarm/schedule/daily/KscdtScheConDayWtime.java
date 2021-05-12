package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
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
    
    @OneToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", insertable = false, updatable = false),
    	@JoinColumn(name="COND_NO", referencedColumnName="SORT_BY", insertable = false, updatable = false),
    })
    public KscdtScheAnyCondDay conditionDay;
    
    @Override
    protected Object getKey() {
        return this.pk;
    }

	public KscdtScheConDayWtime(KscdtScheCondDayWtimePk pk) {
		super();
		this.pk = pk;
	}
}
