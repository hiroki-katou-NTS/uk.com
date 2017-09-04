/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkMonthlySettingRepository.
 */
public interface WorkMonthlySettingRepository  {
	/**
	 * Adds the all.
	 *
	 * @param workMonthlySettings the work monthly settings
	 */
	public void addAll(List<WorkMonthlySetting> workMonthlySettings);
	
	
	/**
	 * Update all.
	 *
	 * @param workMonthlySettings the work monthly settings
	 */
	public void updateAll(List<WorkMonthlySetting> workMonthlySettings);
	
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<WorkMonthlySetting> findById(String companyId, String monthlyPatternCode, 
			int baseDate);
	
	
	/**
	 * Find by start end date.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	public List<WorkMonthlySetting> findByStartEndDate(String companyId, String monthlyPatternCode,
			int startDate, int endDate);
	
	
	/**
	 * Find by YMD.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param baseDates the base dates
	 * @return the list
	 */
	public List<WorkMonthlySetting> findByYMD(String companyId, String monthlyPatternCode, List<BigDecimal> baseDates);
	
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public void remove(String companyId, String monthlyPatternCode);
}
