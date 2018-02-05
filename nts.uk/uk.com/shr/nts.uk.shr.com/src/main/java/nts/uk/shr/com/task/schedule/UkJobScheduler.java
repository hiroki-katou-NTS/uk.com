package nts.uk.shr.com.task.schedule;

import nts.arc.task.schedule.ScheduledJob;
import nts.arc.task.schedule.ScheduledJobUserData;
import nts.arc.task.schedule.cron.CronSchedule;

public interface UkJobScheduler {

	default void scheduleOnCurrentCompany(Class<? extends ScheduledJob> jobClass, CronSchedule cronSchedule) {
		this.scheduleOnCurrentCompany(jobClass, cronSchedule, new ScheduledJobUserData());
	}

	void scheduleOnCurrentCompany(Class<? extends ScheduledJob> jobClass, CronSchedule cronSchedule, ScheduledJobUserData userData);
	
	void unscheduleOnCurrentCompany(Class<? extends ScheduledJob> jobClass);
}
