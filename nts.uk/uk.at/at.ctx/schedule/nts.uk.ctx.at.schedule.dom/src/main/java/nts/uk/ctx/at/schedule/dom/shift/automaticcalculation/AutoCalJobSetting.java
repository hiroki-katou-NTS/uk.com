/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.automaticcalculation;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class AutoCalJobSetting.
 */
//職位別自動計算設定

/**
 * Gets the rest time.
 *
 * @return the rest time
 */

@Getter
public class AutoCalJobSetting extends AggregateRoot{

	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The job id. */
	//職位ID
	private PositionId jobId;
	
	/** The normal OT time. */
	//残業時間
	private AutoCalOvertimeSetting normalOTTime;
	
	/** The flex OT time. */
	//フレックス超過時間
	private AutoCalFlexOvertimeSetting flexOTTime;
	
	/** The rest time. */
	//休出時間
	private AutoCalRestTimeSetting restTime;

	/**
	 * Instantiates a new auto cal job setting.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @param normalOTTime the normal OT time
	 * @param flexOTTime the flex OT time
	 * @param restTime the rest time
	 */
	public AutoCalJobSetting(CompanyId companyId, PositionId jobId, AutoCalOvertimeSetting normalOTTime,
			AutoCalFlexOvertimeSetting flexOTTime, AutoCalRestTimeSetting restTime) {
		super();
		this.companyId = companyId;
		this.jobId = jobId;
		this.normalOTTime = normalOTTime;
		this.flexOTTime = flexOTTime;
		this.restTime = restTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		AutoCalJobSetting other = (AutoCalJobSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		return true;
	}
}
