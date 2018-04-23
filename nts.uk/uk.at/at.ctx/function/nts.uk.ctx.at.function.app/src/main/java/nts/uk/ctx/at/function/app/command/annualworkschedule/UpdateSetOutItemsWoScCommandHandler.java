package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoScRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;

@Stateless
@Transactional
public class UpdateSetOutItemsWoScCommandHandler extends CommandHandler<SetOutItemsWoScCommand> {
	@Inject
	private SetOutItemsWoScRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
		SetOutItemsWoScCommand updateCommand = context.getCommand();
		repository.update(new SetOutItemsWoSc(updateCommand.getCid(), updateCommand.getCd(), updateCommand.getName(), updateCommand.getOutNumExceedTime36Agr(), updateCommand.getDisplayFormat()));
	}
}
