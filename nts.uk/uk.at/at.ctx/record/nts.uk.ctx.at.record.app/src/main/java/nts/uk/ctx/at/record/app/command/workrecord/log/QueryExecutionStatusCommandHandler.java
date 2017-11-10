package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;

@Stateless
@Transactional
public class QueryExecutionStatusCommandHandler extends AsyncCommandHandler<ExecutionProcessingCommand> {

	@Inject EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject TargetPersonRepository targetPersonRepository;

	@Override
	protected void handle(CommandHandlerContext<ExecutionProcessingCommand> context) {
		val command = context.getCommand();
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		
		// Insert EmpCalAndSumExeLog
		DefaultExecutionProcessingCommandAssembler empCalAndAggregationAssembler = new DefaultExecutionProcessingCommandAssembler();
		EmpCalAndSumExeLog empCalAndSumExeLog = empCalAndAggregationAssembler.fromDTO(command);
		empCalAndSumExeLogRepository.add(empCalAndSumExeLog);
		
		ExecutionCommandResult metadata = new ExecutionCommandResult(
				empCalAndSumExeLog.getEmpCalAndSumExecLogID(), 
				command.getPeriodStartDate(),
				command.getPeriodEndDate(),
				command.getTargetEndDate());		
		dataSetter.setData("processingData", metadata);
		
		// Insert TargetPerson
		List<TargetPerson> listTarget = new ArrayList<TargetPerson>();
		dataSetter.setData("targetPersons", listTarget);
		for (String employeeID : command.getLstEmployeeID()) {
			if (asyncContext.hasBeenRequestedToCancel()) {
				asyncContext.finishedAsCancelled();
				metadata.setContinue(false);
				dataSetter.updateData("processingData", metadata);
				break;
			}
			
			TargetPerson targetPerson = TargetPerson.createJavaType(
					/** employeeId */
					employeeID,
					/** empCalAndSumExecLogId */
					empCalAndSumExeLog.getEmpCalAndSumExecLogID(),
					/** state */
					new ComplStateOfExeContents(ExecutionContent.DAILY_CALCULATION, EmployeeExecutionStatus.INCOMPLETE));
			targetPersonRepository.add(targetPerson);
			listTarget.add(targetPerson);
			dataSetter.updateData("targetPersons", listTarget);
		}
	}
	
}
