/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

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
}
