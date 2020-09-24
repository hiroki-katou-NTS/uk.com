/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ScheduleCreatorRepository.
 */
public interface ScheduleCreatorRepository {
	
	/**
	 * Find all.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	public List<ScheduleCreator> findAll(String executionId);
	
	/**
	 * Countd all.
	 *
	 * @param executionId the execution id
	 * @return the int
	 */
	public int countdAll(String executionId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	public void add(ScheduleCreator domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(ScheduleCreator domain);
	
	/**
	 * Save all.
	 *
	 * @param domains the domains
	 */
	public void saveAll(List<ScheduleCreator> domains);

	public void saveAllNew(List<ScheduleCreator> domains );

	
	/**
	 * Count by status.
	 *
	 * @param executionId the execution id
	 * @param executionStatus the execution status
	 * @return the int
	 */
	public int countByStatus(String executionId, int executionStatus);

	/**
	 * Find by executionId and sid.
	 *
	 * @param executionId the execution id
	 * @return the Optional<ScheduleCreator>
	 */
	public Optional<ScheduleCreator> findByExecutionIdAndSId(String executionId, String sId);

}
