package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ProcessFlowOfDailyCreationDomainService;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
@Transactional
public class AddEmpCalSumAndTargetCommandHandler extends AsyncCommandHandler<ExecutionProcessingCommand> {

	@Inject
	private ExecutionProcessingCommandAssembler empCalAndAggregationAssembler;
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private TargetPersonRepository targetPersonRepository;
	
	@Inject
	private ProcessFlowOfDailyCreationDomainService processFlowOfDailyCreationDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<ExecutionProcessingCommand> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
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
		
		dataSetter.setData("EmpCalAndSumExecLogID", empCalAndSumExeLog.getEmpCalAndSumExecLogID());
		dataSetter.setData("dailyCreateCount", 0);
		dataSetter.setData("dailyCreateStatus", ExecutionStatus.PROCESSING.nameId);
		dataSetter.setData("dailyCreateHasError", "");

		DatePeriod periodTime = new DatePeriod(
				GeneralDate.fromString(command.getPeriodStartDate(), "yyyy/MM/dd"),
				GeneralDate.fromString(command.getPeriodEndDate(), "yyyy/MM/dd"));
		processFlowOfDailyCreationDomainService.processFlowOfDailyCreation(asyncContext, ExecutionAttr.MANUAL, periodTime, empCalAndSumExeLog.getEmpCalAndSumExecLogID());
	}

}
