/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.automaticcalculation;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * Gets the rest time.
 *
 * @return the rest time
 */
@Getter
public class AutoCalCompanySetting extends AggregateRoot {

	/** The company id. */
	private CompanyId companyId;

	/** The normal OT time. */
	// 残業時間
	private AutoCalOvertimeSetting normalOTTime;

	/** The flex OT time. */
	// フレックス超過時間
	private AutoCalFlexOvertimeSetting flexOTTime;

	/** The rest time. */
	// 休出時間
	private AutoCalRestTimeSetting restTime;

	/**
	 * Instantiates a new auto cal company setting.
	 *
	 * @param companyId
	 *            the company id
	 * @param normalOTTime
	 *            the normal OT time
	 * @param flexOTTime
	 *            the flex OT time
	 * @param restTime
	 *            the rest time
	 */
	public AutoCalCompanySetting(CompanyId companyId, AutoCalOvertimeSetting normalOTTime,
			AutoCalFlexOvertimeSetting flexOTTime, AutoCalRestTimeSetting restTime) {
		super();
		this.companyId = companyId;
		this.normalOTTime = normalOTTime;
		this.flexOTTime = flexOTTime;
		this.restTime = restTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutoCalCompanySetting other = (AutoCalCompanySetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
}
