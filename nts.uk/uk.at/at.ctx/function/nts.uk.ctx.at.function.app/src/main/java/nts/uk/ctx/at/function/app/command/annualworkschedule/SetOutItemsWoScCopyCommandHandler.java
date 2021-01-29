package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutputItemOfAnnualWorkSchRepository;

@Stateless
@Transactional
public class SetOutItemsWoScCopyCommandHandler extends CommandHandler<SetOutItemsWoScCopyCommand> {

	@Inject
	private SetOutputItemOfAnnualWorkSchRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SetOutItemsWoScCopyCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
