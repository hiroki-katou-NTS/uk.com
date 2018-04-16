package nts.uk.ctx.exio.app.command.exi.opmanage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManageRepository;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManage;

@Stateless
@Transactional
public class AddExAcOpManageCommandHandler extends CommandHandler<ExAcOpManageCommand> {

	@Inject
	private ExAcOpManageRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExAcOpManageCommand> context) {
		ExAcOpManageCommand addCommand = context.getCommand();
		repository.add(ExAcOpManage.createFromJavaType(0L, addCommand.getCid(), addCommand.getProcessId(),
				addCommand.getErrorCount(), addCommand.getInterruption(), addCommand.getProcessCount(),
				addCommand.getProcessTotalCount(), addCommand.getStateBehavior()));

	}
}
