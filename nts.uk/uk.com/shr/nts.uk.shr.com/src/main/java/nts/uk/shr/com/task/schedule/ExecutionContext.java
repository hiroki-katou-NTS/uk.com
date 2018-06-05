package nts.uk.shr.com.task.schedule;

import lombok.RequiredArgsConstructor;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.schedule.ScheduledJobExecutionContext;
import nts.arc.task.schedule.ScheduledJobUserData;
import nts.arc.time.GeneralDateTime;

@RequiredArgsConstructor
public class ExecutionContext {

	private final ScheduledJobExecutionContext source;
	
	/**
	 * Returns Job Execution ID
	 * @return Job Execution ID
	 */
	public String executionId() {
		return this.source.getExecutionId();
	}
	
	/**
	 * Returns data map that was made when scheduled job
	 * @return schedule-time data
	 */
	public ScheduledJobUserData scheduletimeData() {
		return this.source.getUserData();
	}
	
	/**
	 * Returns next fire time
	 * @return next fire time
	 */
	public GeneralDateTime nextFireTime() {
		return this.source.getNextFireTime();
	}

	/**
	 * Returns data setter runtime
	 * @return data setter runtime
	 */
	public TaskDataSetter runtimeData() {
		return this.source.getRuntimeUserData();
	}
}
