/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

/**
 * The Interface OutputItemDailyWorkScheduleRepository.
 */
public interface OutputItemDailyWorkScheduleRepository {
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	Optional<OutputItemDailyWorkSchedule> findByCode(int code);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(OutputItemDailyWorkSchedule domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(OutputItemDailyWorkSchedule domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(OutputItemDailyWorkSchedule domain);
}
