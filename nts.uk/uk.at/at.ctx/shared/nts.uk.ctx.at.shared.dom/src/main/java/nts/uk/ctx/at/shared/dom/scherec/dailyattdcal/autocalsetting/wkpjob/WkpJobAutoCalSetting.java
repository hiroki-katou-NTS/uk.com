/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkpjob;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BaseAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;

/**
 * The Class WkpJobAutoCalSetting.
 */
// 職場・職位別自動計算設定
@Getter
public class WkpJobAutoCalSetting extends BaseAutoCalSetting {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The wkp id. */
	// 職場ID
	private WorkplaceId wkpId;

	/** The job id. */
	// 職位ID
	private JobTitleId jobId;

	/**
	 * Instantiates a new wkp job auto cal setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpJobAutoCalSetting(WkpJobAutoCalSettingGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.wkpId = memento.getWkpId();
		this.jobId = memento.getJobId();
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
	public void saveToMemento(WkpJobAutoCalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWkpId(this.wkpId);
		memento.setJobId(this.jobId);
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
		result = prime * result + ((wkpId == null) ? 0 : wkpId.hashCode());
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
		WkpJobAutoCalSetting other = (WkpJobAutoCalSetting) obj;
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
		if (wkpId == null) {
			if (other.wkpId != null)
				return false;
		} else if (!wkpId.equals(other.wkpId))
			return false;
		return true;
	}
}
