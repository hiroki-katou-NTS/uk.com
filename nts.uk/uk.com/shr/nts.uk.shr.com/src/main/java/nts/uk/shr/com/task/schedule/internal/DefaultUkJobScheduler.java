package nts.uk.shr.com.task.schedule.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.schedule.JobScheduler;
import nts.arc.task.schedule.ScheduledJob;
import nts.arc.task.schedule.ScheduledJobUserData;
import nts.arc.task.schedule.cron.CronSchedule;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.task.schedule.UkJobScheduler;

@Stateless
public class DefaultUkJobScheduler implements UkJobScheduler {

	@Inject
	private JobScheduler scheduler;
	
	@Override
	public void scheduleOnCurrentCompany(
			Class<? extends ScheduledJob> jobClass,
			CronSchedule cronSchedule,
			ScheduledJobUserData userData) {
		this.scheduler.schedule(jobClass, createJobContextKey(), cronSchedule, userData);
	}

	@Override
	public void unscheduleOnCurrentCompany(Class<? extends ScheduledJob> jobClass) {
		this.scheduler.unschedule(jobClass, createJobContextKey());
	}

	private static String createJobContextKey() {
		return AppContexts.user().companyId();
	}
}
