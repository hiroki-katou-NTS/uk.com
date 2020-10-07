/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BaseAutoCalSetting;

/**
 * The Class ComAutoCalSetting.
 */
// 会社別自動計算設定
@Getter
public class ComAutoCalSetting extends BaseAutoCalSetting {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/**
	 * Instantiates a new com auto cal setting.
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
	public ComAutoCalSetting(ComAutoCalSettingGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
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
	public void saveToMemento(ComAutoCalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
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
		ComAutoCalSetting other = (ComAutoCalSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

}
