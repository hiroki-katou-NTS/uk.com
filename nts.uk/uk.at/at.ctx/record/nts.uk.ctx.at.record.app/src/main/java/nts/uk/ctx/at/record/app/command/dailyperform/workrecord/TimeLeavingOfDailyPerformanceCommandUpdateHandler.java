package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class TimeLeavingOfDailyPerformanceCommandUpdateHandler extends CommandFacade<TimeLeavingOfDailyPerformanceCommand> {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<TimeLeavingOfDailyPerformanceCommand> context) {
		TimeLeavingOfDailyPerformanceCommand command = context.getCommand();
		if(command.shouldDelete() || !command.getData().isPresent() || command.getData().get().getTimeLeavingWorks().isEmpty()){
			repo.delete(command.getEmployeeId(), command.getWorkDate());
			return;
		}
		
		if(command.getData().isPresent()){
			TimeLeavingOfDailyPerformance domain = command.toDomain().get();
			repo.update(domain);

			if(command.isTriggerEvent()){
				domain.timeLeavesChanged();
			}
		}
		
	}
}
