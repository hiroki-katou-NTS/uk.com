package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.app.command.workrecord.log.CheckingProcessingResult.CheckingExecutionLogResult;
import nts.uk.ctx.at.record.app.find.log.dto.ErrMessageInfoDto;
import nts.uk.ctx.at.record.dom.workrecord.log.ComplStateOfExeContents;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;

@Stateless
@Transactional
public class QueryExecutionStatusCommandHandler extends CommandHandlerWithResult<ExecutionCommandResult, CheckingProcessingResult> {
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private TargetPersonRepository targetPersonRepository;
	
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	@Override
	protected CheckingProcessingResult handle(CommandHandlerContext<ExecutionCommandResult> context) {
		val command = context.getCommand();
		
		CheckingProcessingResult result = new CheckingProcessingResult();

		// EmpCalAndSumExeLog
		val empCalAndSumExeLog = empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(command.getEmpCalAndSumExecLogID()).get();
		
		// Get list TargetPerson
		List<TargetPerson> lstTargetPerson = targetPersonRepository.getByempCalAndSumExecLogID(command.getEmpCalAndSumExecLogID());
		
		// Set other data
		for (ExecutionLog executionLog: empCalAndSumExeLog.getExecutionLogs()) {
			CheckingExecutionLogResult checkingExecutionLogResult = result.new CheckingExecutionLogResult();
			checkingExecutionLogResult.setTotal(lstTargetPerson.size());
			checkingExecutionLogResult.updateStatusFromLog(executionLog);
			result.addLogResult(checkingExecutionLogResult);
			if (!executionLog.isComplete())
				result.notComplete();
		}
		
		// Increase count
		for (TargetPerson targetPerson : lstTargetPerson) {
			for (ComplStateOfExeContents state: targetPerson.getState()) {
				if (state.isComplete()) {
					result.increaseCount(state.getExecutionContent());
				}
			}
		}
		
		// Check complete
		if (result.isComplete()) {
			val errMessageInfos = errMessageInfoRepository.getAllErrMessageInfoByEmpID(command.getEmpCalAndSumExecLogID());
			result.setErrorMessageInfos(errMessageInfos.stream().map(c -> ErrMessageInfoDto.fromDomain(c)).collect(Collectors.toList()));
			
		}
		
		return result;
	}
}