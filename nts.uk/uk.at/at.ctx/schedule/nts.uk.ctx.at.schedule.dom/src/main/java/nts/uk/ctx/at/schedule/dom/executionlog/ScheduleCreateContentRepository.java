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
	Optional<ScheduleCreateContent> findByExecutionId(String executionId);
}
