package nts.uk.ctx.at.schedule.dom.executionlog;

import java.util.List;

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
	List<ScheduleErrorLog> findByExecutionId(String executionId);
	
	/**
	 * Distinct error by execution id.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	Integer distinctErrorByExecutionId(String executionId);
}
