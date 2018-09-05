package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.CloseDate;
import nts.uk.ctx.sys.assist.dom.salary.CloseDateRepository;

@Stateless
@Transactional
public class AddCloseDateCommandHandler extends CommandHandler<CloseDateCommand> {

	@Inject
	private CloseDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CloseDateCommand> context) {
		CloseDateCommand addCommand = context.getCommand();
		repository.add(new CloseDate(addCommand.getProcessCateNo(), addCommand.getCid(), addCommand.getTimeCloseDate(),
				addCommand.getBaseMonth(), addCommand.getBaseYear(), addCommand.getRefeDate()));

	}
}
