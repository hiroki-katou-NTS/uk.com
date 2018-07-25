package nts.uk.ctx.at.record.app.command.dailyperform.optionalitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;

@Stateless
public class OptionalItemOfDailyPerformCommandUpdateHandler extends CommandFacade<OptionalItemOfDailyPerformCommand> {

	@Inject
	private OptionalItemRepository optionalMasterRepo;

	@Inject
	private AnyItemValueOfDailyRepo repo;

	@Override
	protected void handle(CommandHandlerContext<OptionalItemOfDailyPerformCommand> context) {
		OptionalItemOfDailyPerformCommand command = context.getCommand();
//		if (command.getData().isPresent()) {
//			AnyItemValueOfDaily domain = command.getData().get();
//			domain.correctAnyType(optionalMasterRepo);
//			repo.update(domain);
		if(command.getData().isPresent()){
			repo.update(command.toDomain().get());
		}
	}

}
