/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.personal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;

/**
 * The Class PersonEstablishment.
 */
// 個人目安設定
@Getter
public class PersonalEstablishment extends AggregateRoot{
	
	/** The employee id. */
	//社員ID
	private String employeeId;

	/** The target year. */
	// 対象年
	private Year targetYear;
	
	/** The advanced setting. */
	//詳細設定
	private EstimateDetailSetting advancedSetting;
	
	
	/**
	 * Instantiates a new personal establishment.
	 *
	 * @param memento the memento
	 */
	public PersonalEstablishment(PersonalEstablishmentGetMemento memento){
		this.employeeId = memento.getEmployeeId();
		this.targetYear = memento.getTargetYear();
		this.advancedSetting = memento.getAdvancedSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PersonalEstablishmentSetMemento memento){
		memento.setEmployeeId(this.employeeId);
		memento.setTargetYear(this.targetYear);
		memento.setAdvancedSetting(this.advancedSetting);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((targetYear == null) ? 0 : targetYear.hashCode());
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
		PersonalEstablishment other = (PersonalEstablishment) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (targetYear == null) {
			if (other.targetYear != null)
				return false;
		} else if (!targetYear.equals(other.targetYear))
			return false;
		return true;
	}
	
	
}
