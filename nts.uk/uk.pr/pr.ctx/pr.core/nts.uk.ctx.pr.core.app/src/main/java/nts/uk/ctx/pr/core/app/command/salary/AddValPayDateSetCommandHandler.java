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
public class AddValPayDateSetCommandHandler extends CommandHandler<ValPayDateSetCommand> {

	@Inject
	private ValPayDateSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ValPayDateSetCommand> context) {
		ValPayDateSetCommand addCommand = context.getCommand();
		repository.add(new ValPayDateSet(addCommand.getCid(), addCommand.getProcessCateNo()));

	}
}
