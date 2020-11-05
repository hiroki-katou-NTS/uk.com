package nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 期限情報
 * @author masaaki_jinno
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT_DEADLINE")
public class KshmtHdspGrantDeadline extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KshmtHdspGrantDeadlinePK pk;

	/* 期限指定方法 */
	@Column(name = "TIME_CSL_METHOD")
	public int timeMethod;

	/* 特別休暇の有効期限.年数 */
	@Column(name = "DEADLINE_YEARS")
	public Integer deadlineYears;

	/* 特別休暇の有効期限.月数 */
	@Column(name = "DEADLINE_MONTHS")
	public Integer deadlineMonths;

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
	public static KshmtHdspGrantDeadline toEntity(GrantDeadline domain) {
		return new KshmtHdspGrantDeadline(
				new KshmtHdspGrantDeadlinePK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()),
				domain.getTimeSpecifyMethod().value,
				domain.getExpirationDate().isPresent() ? domain.getExpirationDate().get().getYears().v() : null,
				domain.getExpirationDate().isPresent() ? domain.getExpirationDate().get().getMonths().v() : null,
				domain.getLimitAccumulationDays().isPresent() ?
						domain.getLimitAccumulationDays().get().getLimitAccumulationDays().get().v() : null);
	}

	public KshmtHdspGrantDeadline(
			KshmtHdspGrantDeadlinePK pk,
			int timeMethod,
			Integer deadlineYears,
			Integer deadlineMonths,
			Integer limitCarryoverDays) {

		this.pk = pk;
		this.timeMethod = timeMethod;
		this.deadlineYears = deadlineYears;
		this.deadlineMonths = deadlineMonths;
		this.limitCarryoverDays = limitCarryoverDays;
	}
}