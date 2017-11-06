package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;

@Stateless
@Transactional
public class QueryExecutionStatusCommandHandler extends CommandHandlerWithResult<ExecutionProcessingCommand, ExecutionCommandResult> {

	@Inject EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject TargetPersonRepository targetPersonRepository;

	@Override
	protected ExecutionCommandResult handle(CommandHandlerContext<ExecutionProcessingCommand> context) {		
		val command = context.getCommand();
		
		ExecutionProcessingCommandAssembler empCalAndAggregationAssembler = new ExecutionProcessingCommandAssembler();
		EmpCalAndSumExeLog empCalAndSumExeLog = empCalAndAggregationAssembler.fromDTO(command);
		
		List<TargetPerson> lstTargetPerson = new ArrayList<TargetPerson>();
		for (String employeeID : command.getLstEmployeeID()) {
			TargetPerson targetPerson = TargetPerson.createJavaType(
					/** employeeId */
					employeeID,
					/** empCalAndSumExecLogId */
					empCalAndSumExeLog.getEmpCalAndSumExecLogID(),
					/** state */
					new ComplStateOfExeContents(ExecutionContent.DAILY_CALCULATION, EmployeeExecutionStatus.INCOMPLETE));
			lstTargetPerson.add(targetPerson);
		}
		targetPersonRepository.addAll(lstTargetPerson);
		
		return new ExecutionCommandResult(
				empCalAndSumExeLog.getEmpCalAndSumExecLogID(),
				command.getPeriodStartDate(),
				command.getPeriodEndDate(),
				command.getTargetEndDate());
	}

}
