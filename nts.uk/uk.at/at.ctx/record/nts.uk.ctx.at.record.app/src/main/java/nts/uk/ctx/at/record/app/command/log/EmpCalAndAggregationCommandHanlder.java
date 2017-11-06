package nts.uk.ctx.at.record.app.command.log;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult processingMonth
 *
 */
@Stateless
@Transactional
public class EmpCalAndAggregationCommandHanlder extends CommandHandler<EmpCalAndAggregationCommand> {

	@Inject EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject TargetPersonRepository targetPersonRepository;
	
	@Override
	protected void handle(CommandHandlerContext<EmpCalAndAggregationCommand> context) {
		val command = context.getCommand();
		EmpCalAndAggregationAssembler empCalAndAggregationAssembler = new EmpCalAndAggregationAssembler();
		EmpCalAndSumExeLog empCalAndSumExeLog = empCalAndAggregationAssembler.fromDTO(command);
		TargetPerson targetPerson = TargetPerson.createJavaType(
				/** employeeId */
				command.getEmployeeID(),
				/** empCalAndSumExecLogId */
				empCalAndSumExeLog.getEmpCalAndSumExecLogID(),
				/** state */
				new ComplStateOfExeContents(ExecutionContent.DAILY_CALCULATION, EmployeeExecutionStatus.INCOMPLETE));
		
		
	}

}
