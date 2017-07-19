/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

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
	 * Find by id.
	 *
	 * @param emmployeeId the emmployee id
	 * @return the optional
	 */
	public Optional<MonthlyPatternSetting> findById(String emmployeeId);
	
	
	
}
