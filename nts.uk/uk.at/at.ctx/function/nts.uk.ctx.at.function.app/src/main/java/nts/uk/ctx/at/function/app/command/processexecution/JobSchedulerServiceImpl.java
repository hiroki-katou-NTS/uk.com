package nts.uk.ctx.at.function.app.command.processexecution;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.task.schedule.UkJobScheduleOptions;
import nts.uk.shr.com.task.schedule.UkJobScheduler;
import nts.uk.shr.com.task.schedule.UkScheduledJob;

import java.util.Optional;

import javax.ejb.Stateless;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JobSchedulerServiceImpl implements JobSchedulerService {
	
	@Inject
	private UkJobScheduler scheduler;

	@Override
	public String schedule(UkJobScheduleOptions options) {
		return this.scheduler.scheduleOnCurrentCompany(options).getScheduleId();
	}

	@Override
	public void unschedule(Class<? extends UkScheduledJob> job, String scheduleId) {
		this.scheduler.unscheduleOnCurrentCompany(job, scheduleId);
	}

	@Override
	public Optional<GeneralDateTime> getNextFireTime(String scheduleId) {
		return this.scheduler.getNextFireTime(scheduleId);
	}

}
