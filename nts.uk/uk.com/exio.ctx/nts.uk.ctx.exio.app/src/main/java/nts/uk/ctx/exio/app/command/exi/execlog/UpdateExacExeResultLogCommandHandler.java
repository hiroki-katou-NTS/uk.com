package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;

@Stateless
@Transactional
public class UpdateExacExeResultLogCommandHandler extends CommandHandler<ExacExeResultLogCommand> {

	@Inject
	private ExacExeResultLogRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExacExeResultLogCommand> context) {
		ExacExeResultLogCommand updateCommand = context.getCommand();
		repository.update(ExacExeResultLog.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(),
				updateCommand.getConditionSetCd(), updateCommand.getExternalProcessId(), updateCommand.getExecutorId(),
				updateCommand.getUserId(), updateCommand.getProcessStartDatetime(), updateCommand.getStandardAtr(),
				updateCommand.getExecuteForm(), updateCommand.getTargetCount(), updateCommand.getErrorCount(),
				updateCommand.getFileName(), updateCommand.getSystemType(), updateCommand.getResultStatus(),
				updateCommand.getProcessEndDatetime(), updateCommand.getProcessAtr()));

	}
}
