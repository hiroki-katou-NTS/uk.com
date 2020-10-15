/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface ScheduleErrorLogRepository.
 */
public interface ScheduleErrorLogRepository {

	/**
	 * Find by execution id.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	public List<ScheduleErrorLog> findByExecutionId(String executionId);
	
	/**
	 * Find by employee id.
	 *
	 * @param executionId the execution id
	 * @param employeeId the employee id
	 * @return the list
	 */
	public List<ScheduleErrorLog> findByEmployeeId(String executionId, String employeeId);
	
	/**
	 * Find by employee id.
	 *
	 * @param executionId the execution id
	 * @param employeeId the employee id
	 * @return the list
	 */
	public Optional<ScheduleErrorLog> findByKey(String executionId, String employeeId, GeneralDate baseDate); 
	/**
	 * Distinct error by execution id.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	public Integer distinctErrorByExecutionId(String executionId);
	
	/**
	 * Distinct error by execution id.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	public Integer distinctErrorByExecutionIdNew(String executionId);
	
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	public void add(ScheduleErrorLog domain);
	
	/**
	 * Add in Transaction
	 *
	 * @param domain the domain
	 */
	public void addByTransaction(ScheduleErrorLog domain);
	
	/**
	 * 
	 * @param executionId
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public Boolean checkExistErrorByKey(String executionId, String employeeId, GeneralDate baseDate); 
	
	/**
	 * 
	 * @param executionId
	 * @param employeeId
	 * @return
	 */
	public Boolean checkExistErrorByKey(String executionId, String employeeId);

	/**
	 * Update the.
	 *
	 * @param domain the domain
	 */
	public void update(ScheduleErrorLog domain);
}
