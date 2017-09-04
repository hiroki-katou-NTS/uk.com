/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.personal;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;

/**
 * The Interface PersonalEstablishmentSetMemento.
 */
public interface PersonalEstablishmentSetMemento {
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(String employeeId);
	
	
	/**
	 * Sets the target year.
	 *
	 * @param targetYear the new target year
	 */
	void setTargetYear(Year targetYear);
	
	
	/**
	 * Sets the advanced setting.
	 *
	 * @param advancedSetting the new advanced setting
	 */
	void setAdvancedSetting(EstimateDetailSetting advancedSetting);

}
