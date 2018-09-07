package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.salary.ValPayDateSetRepository;

@Stateless
@Transactional
public class UpdateValPayDateSetCommandHandler extends CommandHandler<ValPayDateSetCommand> {

	@Inject
	private ValPayDateSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ValPayDateSetCommand> context) {
		ValPayDateSetCommand updateCommand = context.getCommand();
		repository.update(new ValPayDateSet(updateCommand.getCid(), updateCommand.getProcessCateNo()));

	}
}
