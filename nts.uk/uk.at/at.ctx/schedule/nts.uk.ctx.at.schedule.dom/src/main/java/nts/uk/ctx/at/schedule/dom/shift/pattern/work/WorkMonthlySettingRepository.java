/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkMonthlySettingRepository.
 */
public interface WorkMonthlySettingRepository  {
	
	/**
	 * Adds the.
	 *
	 * @param workMonthlySetting the work monthly setting
	 */
	public void add(WorkMonthlySetting workMonthlySetting);
	
	
	/**
	 * Update.
	 *
	 * @param workMonthlySetting the work monthly setting
	 */
	public void update(WorkMonthlySetting workMonthlySetting);
	
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<WorkMonthlySetting> findById(String companyId, String monthlyPatternCode, GeneralDate baseDate);

}
