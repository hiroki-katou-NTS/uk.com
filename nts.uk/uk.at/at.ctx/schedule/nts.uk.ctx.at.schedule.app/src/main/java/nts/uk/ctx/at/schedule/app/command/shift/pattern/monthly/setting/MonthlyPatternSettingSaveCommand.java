/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingGetMemento;

/**
 * The Class MonthlyPatternSettingSaveCommand.
 */

@Getter
@Setter
public class MonthlyPatternSettingSaveCommand {

	/** The employee id. */
	private String employeeId;

	/** The monthly pattern code. */
	private String monthlyPatternCode;
	
	/**
	 * To domain.
	 *
	 * @return the monthly pattern setting
	 */
	public MonthlyPatternSetting toDomain(){
		return new MonthlyPatternSetting(new MonthlyPatternSettingGetMementoImpl());
	}
	
	/**
	 * The Class MonthlyPatternSettingGetMementoImpl.
	 */
	class MonthlyPatternSettingGetMementoImpl implements MonthlyPatternSettingGetMemento{

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.
		 * MonthlyPatternSettingGetMemento#getMonthlyPatternCode()
		 */
		@Override
		public MonthlyPatternCode getMonthlyPatternCode() {
			return new MonthlyPatternCode(monthlyPatternCode);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.
		 * MonthlyPatternSettingGetMemento#getEmployeeId()
		 */
		@Override
		public String getEmployeeId() {
			return employeeId;
		}
		
	}
}
