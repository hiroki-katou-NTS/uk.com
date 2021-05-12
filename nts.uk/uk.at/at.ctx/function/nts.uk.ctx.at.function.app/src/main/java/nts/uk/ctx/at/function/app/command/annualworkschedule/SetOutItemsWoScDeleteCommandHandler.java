package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;

@Stateless
@Transactional
public class SetOutItemsWoScDeleteCommandHandler extends CommandHandler<SetOutItemsWoScDeleteCommand> {

	@Inject
	private SetOutputItemOfAnnualWorkSchRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SetOutItemsWoScDeleteCommand> context) {
		SetOutItemsWoScDeleteCommand command = context.getCommand();
		this.repository.remove(command.getLayoutId());
	}

}
