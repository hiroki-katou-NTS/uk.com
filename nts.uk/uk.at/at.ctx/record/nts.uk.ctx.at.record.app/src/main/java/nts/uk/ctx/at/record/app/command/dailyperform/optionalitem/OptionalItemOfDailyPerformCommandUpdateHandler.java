package nts.uk.ctx.at.record.app.command.dailyperform.optionalitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class OptionalItemOfDailyPerformCommandUpdateHandler extends CommandFacade<OptionalItemOfDailyPerformCommand> {

	// TODO: create table and write repo
	@Inject
	private AnyItemValueOfDailyRepo repo;

	@Override
	protected void handle(CommandHandlerContext<OptionalItemOfDailyPerformCommand> context) {
		OptionalItemOfDailyPerformCommand command = context.getCommand();
		if(command.getData().isPresent()){
			repo.update(command.toDomain().get());
		}
	}

}
