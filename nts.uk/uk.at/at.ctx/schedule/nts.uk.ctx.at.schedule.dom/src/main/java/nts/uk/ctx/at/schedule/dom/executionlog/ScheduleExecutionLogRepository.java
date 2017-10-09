/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.closure.Period;

/**
 * The Interface ScheduleExecutionLogRepository.
 */
public interface ScheduleExecutionLogRepository {

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param period the period
	 * @return the optional
	 */
	public List<ScheduleExecutionLog> find(String companyId ,Period period);
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param executionId the execution id
	 * @return the optional
	 */
	public Optional<ScheduleExecutionLog> findById(String companyId, String executionId);
	
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void add(ScheduleExecutionLog domain);
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void update(ScheduleExecutionLog domain);
}
