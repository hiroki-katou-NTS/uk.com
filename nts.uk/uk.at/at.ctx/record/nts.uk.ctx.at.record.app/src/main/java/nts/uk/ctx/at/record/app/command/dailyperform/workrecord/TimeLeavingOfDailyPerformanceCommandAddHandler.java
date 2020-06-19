package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.dom.worktime.TimeLeavingOfDailyPerformance;

@Stateless
public class TimeLeavingOfDailyPerformanceCommandAddHandler extends CommandFacade<TimeLeavingOfDailyPerformanceCommand> {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<TimeLeavingOfDailyPerformanceCommand> context) {
		TimeLeavingOfDailyPerformanceCommand command = context.getCommand();
		if(command.getData().isPresent()){
			TimeLeavingOfDailyPerformance domain = command.toDomain().get();
			repo.add(domain);
			if(command.isTriggerEvent()){
				domain.timeLeavesChanged();
			}
		}
	}
}
