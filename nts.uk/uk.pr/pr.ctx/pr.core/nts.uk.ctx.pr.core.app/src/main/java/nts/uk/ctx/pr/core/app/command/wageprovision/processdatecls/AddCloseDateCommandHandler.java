package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CloseDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CloseDateRepository;

@Stateless
@Transactional
public class AddCloseDateCommandHandler extends CommandHandler<CloseDateCommand> {

	@Inject
	private CloseDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CloseDateCommand> context) {
		CloseDateCommand addCommand = context.getCommand();
		repository.add(new CloseDate(addCommand.getTimeCloseDate(), addCommand.getBaseMonth(), addCommand.getBaseYear(),
				addCommand.getRefeDate()));

	}
}
