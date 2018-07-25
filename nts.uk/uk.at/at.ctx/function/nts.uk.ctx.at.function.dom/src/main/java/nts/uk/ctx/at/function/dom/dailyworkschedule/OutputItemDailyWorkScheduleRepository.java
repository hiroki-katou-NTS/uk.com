/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;
import java.util.Optional;

/**
 * The Interface OutputItemDailyWorkScheduleRepository.
 * @author HoangDD
 */
public interface OutputItemDailyWorkScheduleRepository {
	
	/**
	 * Find by cid and code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	public Optional<OutputItemDailyWorkSchedule> findByCidAndCode(String companyId, String code);
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public List<OutputItemDailyWorkSchedule> findByCid(String companyId);
	
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
	
	/**
	 * Delete by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 */
	void deleteByCidAndCode(String companyId, String code);
}
