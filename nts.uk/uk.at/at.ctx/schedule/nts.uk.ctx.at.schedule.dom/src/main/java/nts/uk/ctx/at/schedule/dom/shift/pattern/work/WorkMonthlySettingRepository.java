/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

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
	public List<WorkMonthlySetting> findByYear(String companyId, String monthlyPatternCode, int year);
	
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
			GeneralDate baseDate);
	
	
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
			GeneralDate startDate, GeneralDate endDate);
	
	
	/**
	 * Find by YMD.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param baseDates the base dates
	 * @return the list
	 */
	public List<WorkMonthlySetting> findByYMD(String companyId, String monthlyPatternCode, List<GeneralDate> baseDates);
	
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public void remove(String companyId, String monthlyPatternCode);

	/**
	 * Find by start YMD - end YMD.
	 *
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param DatePeriod the base dates
	 * @return the list
	 */
	public List<WorkMonthlySetting> findByPeriod(String companyId, String monthlyPatternCode, DatePeriod datePeriod);

	/**
	 * Check exist KscmtWorkMonthSet.
	 * exists(会社ID, 月間パターンコード, 年月日)
	 * @param companyId the company id
	 * @param monthlyPatternCode the monthly pattern code
	 * @param date the ymd K
	 * @return the list
	 */
	public Boolean exists(String companyId, String monthlyPatternCode, GeneralDate date);

	/**
	 * insert KscmtWorkMonthSet.
	 * insert（月間パターンの勤務情報）
	 * @param workMonthlySetting
	 * @return
	 */
	public void add(WorkMonthlySetting workMonthlySetting);

	/**
	 * update KscmtWorkMonthSet.
	 * update（月間パターンの勤務情報）
	 * @param workMonthlySetting
	 * @return
	 */
	public void update(WorkMonthlySetting workMonthlySetting);

	/**
	 * update KscmtWorkMonthSet.
	 * update（月間パターンの勤務情報）
	 * @param companyId, mPatternCd, generalDate
	 * @return
	 */
	public void deleteWorkMonthlySettingById(String companyId,String mPatternCd,GeneralDate date);
}
