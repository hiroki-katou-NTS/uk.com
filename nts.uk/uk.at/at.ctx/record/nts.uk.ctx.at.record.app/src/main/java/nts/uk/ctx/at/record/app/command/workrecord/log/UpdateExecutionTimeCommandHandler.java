package nts.uk.ctx.at.record.app.command.workrecord.log;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;

@Stateless
public class UpdateExecutionTimeCommandHandler extends CommandHandler<UpdateExecutionTimeCommand>{
	
	@Inject
	ExecutionLogRepository executionLogRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateExecutionTimeCommand> context) {
		UpdateExecutionTimeCommand command = context.getCommand();
		
		this.executionLogRepository.updateExecutionDate(command.getEmpCalAndSumExecLogID(), command.getExecutionStartDate(), command.getExecutionEndDate());
	}

}
