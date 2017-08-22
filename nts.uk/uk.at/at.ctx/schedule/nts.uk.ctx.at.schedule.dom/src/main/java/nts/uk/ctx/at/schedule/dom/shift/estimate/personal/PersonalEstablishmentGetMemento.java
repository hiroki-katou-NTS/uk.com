/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.personal;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;

/**
 * The Interface PersonalEstablishmentGetMemento.
 */
public interface PersonalEstablishmentGetMemento {
	
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();
	
	
	/**
	 * Gets the target year.
	 *
	 * @return the target year
	 */
	Year getTargetYear();
	
	
	
	/**
	 * Gets the advanced setting.
	 *
	 * @return the advanced setting
	 */
	EstimateDetailSetting getAdvancedSetting();

}
