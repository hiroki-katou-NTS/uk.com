/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;

/**
 * The Class MonthlyPatternSetting.
 */
// 個人月間パターン設定

@Getter
public class MonthlyPatternSetting extends AggregateRoot{
	
	/** The monthly pattern code. */
	// 月間パターンコード
	private MonthlyPatternCode monthlyPatternCode;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;

	
	/**
	 * Instantiates a new monthly pattern setting.
	 *
	 * @param memento the memento
	 */
	public MonthlyPatternSetting(MonthlyPatternSettingGetMemento memento){
		this.monthlyPatternCode = memento.getMonthlyPatternCode();
		this.employeeId = memento.getEmployeeId();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MonthlyPatternSettingSetMemento memento){
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
		memento.setEmployeeId(this.employeeId);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result
				+ ((monthlyPatternCode == null) ? 0 : monthlyPatternCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonthlyPatternSetting other = (MonthlyPatternSetting) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (monthlyPatternCode == null) {
			if (other.monthlyPatternCode != null)
				return false;
		} else if (!monthlyPatternCode.equals(other.monthlyPatternCode))
			return false;
		return true;
	}
	
	
}
