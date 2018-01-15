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
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;

@Stateless
@Transactional
public class AddEmpCalSumAndTargetCommandHandler extends CommandHandlerWithResult<AddEmpCalSumAndTargetCommand, AddEmpCalSumAndTargetCommandResult> {

	@Inject
	private AddEmpCalSumAndTargetCommandAssembler empCalAndAggregationAssembler;

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private TargetPersonRepository targetPersonRepository;

	@Override
	protected AddEmpCalSumAndTargetCommandResult handle(CommandHandlerContext<AddEmpCalSumAndTargetCommand> context) {
		val command = context.getCommand();

		// Insert EmpCalAndSumExeLog
		EmpCalAndSumExeLog empCalAndSumExeLog = empCalAndAggregationAssembler.fromDTO(command);
		empCalAndSumExeLogRepository.add(empCalAndSumExeLog);

		// Insert all TargetPersons
		List<TargetPerson> lstTargetPerson = new ArrayList<TargetPerson>();
		for (String employeeID : command.getLstEmployeeID()) {
			List<ComplStateOfExeContents> lstState = new ArrayList<ComplStateOfExeContents>();
			for (ExecutionLog executionLog : empCalAndSumExeLog.getExecutionLogs()) {
				ComplStateOfExeContents state = new ComplStateOfExeContents(executionLog.getExecutionContent(), EmployeeExecutionStatus.INCOMPLETE);
				lstState.add(state);
			}
			TargetPerson targetPerson = new TargetPerson(employeeID, empCalAndSumExeLog.getEmpCalAndSumExecLogID(), lstState);
			lstTargetPerson.add(targetPerson);
		}
		targetPersonRepository.addAll(lstTargetPerson);

		// Build result
		AddEmpCalSumAndTargetCommandResult result = new AddEmpCalSumAndTargetCommandResult(empCalAndSumExeLog.getEmpCalAndSumExecLogID(), command.getPeriodStartDate(), command.getPeriodEndDate());
		return result;
	}

}
