/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface ScheduleExecutionLogRepository.
 */
public interface ScheduleExecutionLogRepository {

	/**
	 * Find by date time.
	 *
	 * @param companyId
	 *            the company id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param exeAtr
	 *            the execution Atr
	 * @return the list
	 */
	public List<ScheduleExecutionLog> findByDateTime(String companyId, GeneralDateTime startDate,
			GeneralDateTime endDate, int exeAtr);

	/**
	 * Find by id.
	 *
	 * @param companyId
	 *            the company id
	 * @param executionId
	 *            the execution id
	 * @return the optional
	 */
	public Optional<ScheduleExecutionLog> findById(String companyId, String executionId);

	/**
	 * Save.
	 *
	 * @param domain
	 *            the domain
	 */
	public void add(ScheduleExecutionLog domain);
	public void addNew(ScheduleExecutionLog domain);


	/**
	 * Save.
	 *
	 * @param domain
	 *            the domain
	 */
	public void update(ScheduleExecutionLog domain);
}
