/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BaseAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;

/**
 * The Class JobAutoCalSetting.
 */
// 職位別自動計算設定
@Getter
public class JobAutoCalSetting extends BaseAutoCalSetting {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The job id. */
	// 職位ID
	private JobTitleId jobId;

	/**
	 * Instantiates a new job auto cal setting.
	 *
	 * @param companyId
	 *            the company id
	 * @param jobId
	 *            the job id
	 * @param normalOTTime
	 *            the normal OT time
	 * @param flexOTTime
	 *            the flex OT time
	 * @param restTime
	 *            the rest time
	 */
	public JobAutoCalSetting(JobAutoCalSettingGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.jobId = memento.getPositionId();
		this.normalOTTime = memento.getNormalOTTime();
		this.flexOTTime = memento.getFlexOTTime();
		this.restTime = memento.getRestTime();
		this.leaveEarly = memento.getLeaveEarly();
		this.raisingSalary = memento.getRaisingSalary();
		this.divergenceTime = memento.getDivergenceTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(JobAutoCalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setPositionId(this.jobId);
		memento.setFlexOTTime(this.flexOTTime);
		memento.setNormalOTTime(this.normalOTTime);
		memento.setRestTime(this.restTime);
		memento.setLeaveEarly(this.leaveEarly);
		memento.setRaisingSalary(this.raisingSalary);
		memento.setDivergenceTime(this.divergenceTime);
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
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
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
		JobAutoCalSetting other = (JobAutoCalSetting) obj;
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
