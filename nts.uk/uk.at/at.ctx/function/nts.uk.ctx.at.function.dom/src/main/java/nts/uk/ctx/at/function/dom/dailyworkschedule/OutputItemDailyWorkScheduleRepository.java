/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;
import java.util.Optional;

/**
 * The Interface OutputItemDailyWorkScheduleRepository.
 * @author HoangDD change by LienPTK 2020/08/29
 */
public interface OutputItemDailyWorkScheduleRepository {
	

	/**
	 * Find by layout id.
	 *
	 * @param layoutId the layout id
	 * @return the optional
	 */
	public Optional<OutputItemDailyWorkSchedule> findByLayoutId(String layoutId);
	
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
