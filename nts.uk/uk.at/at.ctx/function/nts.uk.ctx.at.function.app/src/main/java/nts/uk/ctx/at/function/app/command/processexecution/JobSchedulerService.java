package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.task.schedule.UkJobScheduleOptions;
import nts.uk.shr.com.task.schedule.UkScheduledJob;

public interface JobSchedulerService {
	
	String schedule(UkJobScheduleOptions options);
	
	void unschedule(Class<? extends UkScheduledJob> job, String scheduleId);
	
	Optional<GeneralDateTime> getNextFireTime(String scheduleId);
}
