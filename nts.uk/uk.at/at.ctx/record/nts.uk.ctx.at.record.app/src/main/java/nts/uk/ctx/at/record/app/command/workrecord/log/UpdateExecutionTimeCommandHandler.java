package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;

@Stateless
public class UpdateExecutionTimeCommandHandler extends CommandHandler<UpdateExecutionTimeCommand>{
	
	@Inject
	ExecutionLogRepository executionLogRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateExecutionTimeCommand> context) {
		UpdateExecutionTimeCommand command = context.getCommand();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		GeneralDateTime startDate = GeneralDateTime.localDateTime(LocalDateTime.parse(command.getExecutionStartDate(),formatter));
		GeneralDateTime endDate = GeneralDateTime.localDateTime(LocalDateTime.parse(command.getExecutionEndDate(),formatter));
		
		this.executionLogRepository.updateExecutionDate(command.getEmpCalAndSumExecLogID(), startDate, endDate);
	}

}
