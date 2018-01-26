package nts.uk.ctx.at.function.app.command.processexecution;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveProcessExecutionCommandHandler extends CommandHandler<RemoveProcessExecutionCommand> {

	@Inject
	private ProcessExecutionRepository procExecRepo;
	
	@Inject
	private ExecutionTaskSettingRepository execSetRepo;
	
	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;

	@Override
	protected void handle(CommandHandlerContext<RemoveProcessExecutionCommand> context) {
		String companyId = AppContexts.user().companyId();

		RemoveProcessExecutionCommand command = context.getCommand();

		this.procExecRepo.remove(companyId, command.getExecItemCd());
		this.execSetRepo.remove(companyId, command.getExecItemCd());
		this.procExecLogRepo.removeList(companyId, command.getExecItemCd());
	}
}
