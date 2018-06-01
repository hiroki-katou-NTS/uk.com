package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveBreakTimeByWorkTypeChangeHandler extends CommandHandler<RemoveBreakTimeByWorkTypeChangeCommand> {

	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private BreakTimeOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RemoveBreakTimeByWorkTypeChangeCommand> context) {
		//TODO: not use
		RemoveBreakTimeByWorkTypeChangeCommand command = context.getCommand();
		workTypeRepo.findByPK(AppContexts.user().companyId(), command.getWorkTypeCode()).ifPresent(wt -> {
			if (shouldRemove(wt.getDailyWork())) {
				this.repo.delete(command.getEmployeeId(), command.getWorkingDate());
			}
		});
	}

	private boolean shouldRemove(DailyWork wt) {
		return wt.getOneDay() == WorkTypeClassification.Holiday
				|| wt.getOneDay() == WorkTypeClassification.Pause
				|| wt.getOneDay() == WorkTypeClassification.ContinuousWork
				|| wt.getOneDay() == WorkTypeClassification.LeaveOfAbsence
				|| wt.getOneDay() == WorkTypeClassification.Closure;
	}

}
