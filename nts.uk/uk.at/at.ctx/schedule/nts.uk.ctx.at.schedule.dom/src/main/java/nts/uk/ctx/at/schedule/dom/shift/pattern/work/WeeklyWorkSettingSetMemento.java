/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface WeeklyWorkSettingSetMemento.
 */
public interface WeeklyWorkSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the day of week.
	 *
	 * @param dayOfWeek the new day of week
	 */
	public void setDayOfWeek(DayOfWeek dayOfWeek);
	
	
	/**
	 * Sets the work day division.
	 *
	 * @param workdayDivision the new work day division
	 */
	public void setWorkdayDivision(WorkdayDivision workdayDivision);

	/**
	 * Sets the contrac code.
	 *
	 * @param constractCode
	 */
	public void setContractCode(String constractCode);

}
