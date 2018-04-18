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
public class UpdateExAcOpManageCommandHandler extends CommandHandler<ExAcOpManageCommand> {

	@Inject
	private ExAcOpManageRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExAcOpManageCommand> context) {
		ExAcOpManageCommand updateCommand = context.getCommand();
		repository.update(ExAcOpManage.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(),
				updateCommand.getProcessId(), updateCommand.getErrorCount(), updateCommand.getInterruption(),
				updateCommand.getProcessCount(), updateCommand.getProcessTotalCount(),
				updateCommand.getStateBehavior()));

	}
}
