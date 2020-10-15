/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.Optional;

/**
 * The Interface ScheduleCreateContentRepository.
 */
public interface ScheduleCreateContentRepository {

	/**
	 * Find by execution id.
	 *
	 * @param executionId the execution id
	 * @return the optional
	 */
	public Optional<ScheduleCreateContent> findByExecutionId(String executionId);
	
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	public void add(ScheduleCreateContent domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(ScheduleCreateContent domain);

	/**
	 * Update KSC001-26/8/2020
	 * @param domain
	 */
	public void addNew(ScheduleCreateContent domain);
}
