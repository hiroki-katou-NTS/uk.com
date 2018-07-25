package nts.uk.ctx.at.function.app.command.processexecution;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.shr.com.task.schedule.ExecutionContext;
import nts.uk.shr.com.task.schedule.UkScheduledJob;
@Stateless
public class SortingProcessScheduleJob extends UkScheduledJob{
	@Inject
	private SortingProcessCommandHandler sortingProcessCommandHandler;
	@Override
	protected void execute(ExecutionContext context) {
		
		String companyId = context.scheduletimeData().getString("companyId");
		String execItemCd = context.scheduletimeData().getString("execItemCd");
		ScheduleExecuteCommand s = new ScheduleExecuteCommand();
		s.setCompanyId(companyId);
		s.setExecItemCd(execItemCd);
		s.setNextDate(context.nextFireTime());
		this.sortingProcessCommandHandler.handle(s);
	}

}
