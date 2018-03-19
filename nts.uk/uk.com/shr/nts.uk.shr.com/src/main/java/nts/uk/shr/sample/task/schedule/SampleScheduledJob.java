package nts.uk.shr.sample.task.schedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.task.schedule.ExecutionContext;
import nts.uk.shr.com.task.schedule.UkScheduledJob;

@Stateless
public class SampleScheduledJob extends UkScheduledJob {
	
	@Inject
	private SampleJobService service;

	@Override
	protected void execute(ExecutionContext context) {
		
		this.service.doSomething(
				context.runtimeData(),
				context.scheduletimeData().getString("TEST"));
	}

}
