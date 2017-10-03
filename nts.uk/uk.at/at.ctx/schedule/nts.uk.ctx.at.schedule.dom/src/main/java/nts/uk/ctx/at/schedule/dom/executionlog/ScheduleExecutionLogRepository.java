/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.List;

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
	public List<ScheduleExecutionLog> find(String companyId,Period period);
	
	
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(ScheduleExecutionLog domain);
}
