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
public class UpdateCloseDateCommandHandler extends CommandHandler<CloseDateCommand> {

	@Inject
	private CloseDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CloseDateCommand> context) {
		CloseDateCommand updateCommand = context.getCommand();
		repository.update(new CloseDate(updateCommand.getTimeCloseDate(), updateCommand.getBaseMonth(),
				updateCommand.getBaseYear(), updateCommand.getRefeDate()));

	}
}
