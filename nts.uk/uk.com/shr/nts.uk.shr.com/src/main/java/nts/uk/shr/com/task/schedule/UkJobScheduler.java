package nts.uk.shr.com.task.schedule;

import java.util.Optional;

import nts.arc.task.schedule.ScheduledJob;
import nts.arc.time.GeneralDateTime;

public interface UkJobScheduler {

	ScheduleInfo scheduleOnCurrentCompany(UkJobScheduleOptions options);
	
	void unscheduleOnCurrentCompany(Class<? extends ScheduledJob> jobClass, String scheduleId);
	
	Optional<GeneralDateTime> getNextFireTime(Class<? extends ScheduledJob> jobClass, String scheduleId);
}
