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
public class UpdateCloseDateCommandHandler extends CommandHandler<CloseDateCommand> {

	@Inject
	private CloseDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CloseDateCommand> context) {
		CloseDateCommand updateCommand = context.getCommand();
		repository.update(new CloseDate(updateCommand.getProcessCateNo(), updateCommand.getCid(),
				updateCommand.getTimeCloseDate(), updateCommand.getBaseMonth(), updateCommand.getBaseYear(),
				updateCommand.getRefeDate()));

	}
}
