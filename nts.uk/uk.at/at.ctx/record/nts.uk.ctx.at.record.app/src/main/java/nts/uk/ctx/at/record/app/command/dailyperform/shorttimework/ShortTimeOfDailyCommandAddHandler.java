package nts.uk.ctx.at.record.app.command.dailyperform.shorttimework;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class ShortTimeOfDailyCommandAddHandler extends CommandFacade<ShortTimeOfDailyCommand> {

	@Inject
	private ShortTimeOfDailyPerformanceRepository repo;

	@Override
	protected void handle(CommandHandlerContext<ShortTimeOfDailyCommand> context) {
		ShortTimeOfDailyCommand command = context.getCommand();
		if(command.getData().isPresent()){
			repo.insert(command.toDomain().get());
		}
	}

}
