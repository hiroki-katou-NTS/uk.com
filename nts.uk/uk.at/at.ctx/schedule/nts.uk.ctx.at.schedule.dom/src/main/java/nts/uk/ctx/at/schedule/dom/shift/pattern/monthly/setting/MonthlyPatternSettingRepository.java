/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting;

import java.util.List;
import java.util.Optional;

/**
 * The Interface MonthlyPatternSettingRepository.
 */
public interface MonthlyPatternSettingRepository {
	
	/**
	 * Adds the.
	 */
	public void add(MonthlyPatternSetting monthlyPatternSetting);
	
	
	/**
	 * Update.
	 *
	 * @param monthlyPatternSetting the monthly pattern setting
	 */
	public void update(MonthlyPatternSetting monthlyPatternSetting);
	
	
	/**
	 * Removes the.
	 *
	 * @param employeeId the employee id
	 */
	public void remove(String employeeId);
		
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @return the optional
	 */
	public Optional<MonthlyPatternSetting> findById(String employeeId);
	
	
	/**
	 * Find all by employee ids.
	 *
	 * @param employeeIds the employee ids
	 * @return the list
	 */
	public List<MonthlyPatternSetting> findAllByEmployeeIds(List<String> employeeIds);
}
