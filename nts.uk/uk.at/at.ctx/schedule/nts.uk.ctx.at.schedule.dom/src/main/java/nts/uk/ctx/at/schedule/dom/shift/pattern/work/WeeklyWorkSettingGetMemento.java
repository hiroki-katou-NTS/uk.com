/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class WeeklyWorkSettingGetMemento.
 */
public interface WeeklyWorkSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the day of week.
	 *
	 * @return the day of week
	 */
	public DayOfWeek getDayOfWeek();
	
	
	/**
	 * Gets the work day division.
	 *
	 * @return the work day division
	 */
	public WorkdayDivision getWorkdayDivision();

	/**
	 * Gets the contract code.
	 *
	 * @return the contract code
	 */
	public String getContractCode();

}
