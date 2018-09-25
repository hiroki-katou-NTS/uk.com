package nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 付与日数定期
 * 
 * @author tanlv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_PERIODIC")
public class KshstGrantPeriodic extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstGrantPeriodicPK pk;

	/* 期限指定方法 */
	@Column(name = "TIME_CSL_METHOD")
	public int timeMethod;
	
	/* 使用可能期間.開始日 */
	@Column(name = "START_DATE")
	public Integer startDate;

	/* 使用可能期間.終了日 */
	@Column(name = "END_DATE")
	public Integer endDate;

	/* 特別休暇の有効期限.月数 */
	@Column(name = "DEADLINE_MONTHS")
	public Integer deadlineMonths;
	
	/* 特別休暇の有効期限.年数 */
	@Column(name = "DEADLINE_YEARS")
	public Integer deadlineYears;

	/* 繰越上限日数 */
	@Column(name = "LIMIT_CARRYOVER_DAYS")
	public Integer limitCarryoverDays;
	
	@Override
	protected Object getKey() {
		return pk;
	}

	/**
	 * To Entity
	 * 
	 * @param domain
	 * @return
	 */
	public static KshstGrantPeriodic toEntity(GrantPeriodic domain) {
		return new KshstGrantPeriodic(
				new KshstGrantPeriodicPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()),
				domain.getTimeSpecifyMethod().value, 
				domain.getAvailabilityPeriod() != null ? domain.getAvailabilityPeriod().getStartDateValue() : null,
				domain.getAvailabilityPeriod() != null ? domain.getAvailabilityPeriod().getEndDateValue() : null,
				domain.getExpirationDate() != null ? domain.getExpirationDate().getMonths().v() : null,
				domain.getExpirationDate() != null ? domain.getExpirationDate().getYears().v() : null,
				domain.getLimitCarryoverDays().v());
	}

	public KshstGrantPeriodic(KshstGrantPeriodicPK pk, int timeMethod, Integer startDate,
			Integer endDate, Integer deadlineMonths, Integer deadlineYears, Integer limitCarryoverDays) {
		
		this.pk = pk;
		this.timeMethod = timeMethod;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deadlineMonths = deadlineMonths;
		this.deadlineYears = deadlineYears;
		this.limitCarryoverDays = limitCarryoverDays;
	}
}