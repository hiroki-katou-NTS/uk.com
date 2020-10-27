package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyLaborTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 月単位労働時間の共通
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshmtLegalMon extends ContractUkJpaEntity {

	/** 法定労働時間 */
	@Column(name = "LEGAL_TIME")
	public int legalTime;

	/** 所定労働時間 */
	@Column(name = "WITHIN_TIME")
	public Integer withinTime;

	/** 週平均時間 */
	@Column(name = "WEEK_AVG_TIME")
	public Integer weekAvgTime;
	
	public MonthlyLaborTime domain() {
		return MonthlyLaborTime.of(new MonthlyEstimateTime(legalTime), 
				withinTime == null ? Optional.empty() : Optional.of(new MonthlyEstimateTime(withinTime)),
				weekAvgTime == null ? Optional.empty() : Optional.of(new MonthlyEstimateTime(weekAvgTime)));
	}
	
	public void transfer(MonthlyLaborTime domain) {
		this.legalTime = domain.getLegalLaborTime().valueAsMinutes();
		
		domain.getWithinLaborTime().ifPresent(v -> {
			this.withinTime = v.valueAsMinutes();
		});
		
		domain.getWeekAvgTime().ifPresent(v -> {
			this.weekAvgTime = v.valueAsMinutes();
		});
	}
}
