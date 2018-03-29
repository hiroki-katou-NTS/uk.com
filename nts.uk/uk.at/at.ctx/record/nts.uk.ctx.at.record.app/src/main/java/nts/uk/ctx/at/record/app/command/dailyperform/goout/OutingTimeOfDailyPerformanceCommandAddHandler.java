package nts.uk.ctx.at.record.app.command.dailyperform.goout;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class OutingTimeOfDailyPerformanceCommandAddHandler extends CommandFacade<OutingTimeOfDailyPerformanceCommand> {

	@Inject
	private OutingTimeOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<OutingTimeOfDailyPerformanceCommand> context) {
		OutingTimeOfDailyPerformanceCommand command = context.getCommand();
		if(command.getData().isPresent()){
			repo.add(command.toDomain().get());
		}
	}
}
