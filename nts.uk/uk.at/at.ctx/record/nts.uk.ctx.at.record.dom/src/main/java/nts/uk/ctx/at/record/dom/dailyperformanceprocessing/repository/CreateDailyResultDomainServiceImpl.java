package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CreateDailyResultDomainServiceImpl implements CreateDailyResultDomainService {

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private CreateDailyResultEmployeeDomainService createDailyResultEmployeeDomainService;
	
	@Inject
	private TargetPersonRepository targetPersonRepository;

	@Override
	public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
			DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog) {

		val dataSetter = asyncContext.getDataSetter();

		ProcessState status = ProcessState.SUCCESS;

		// AsyncCommandHandlerContext<SampleCancellableAsyncCommand> ABC;

		// ③日別実績の作成処理
		if (executionLog.isPresent()) {

			// 日別作成を実行するかチェックする
			ExecutionType reCreateAttr = executionLog.get().getDailyCreationSetInfo().get().getExecutionType();
			
			// ④ログ情報（実行ログ）を更新する
			empCalAndSumExeLogRepository.updateLogInfo(empCalAndSumExecLogID, 0, ExecutionStatus.PROCESSING.value);

			int dailyCreateCount = 0;
			// 社員1人分の処理
			for (String employee : emloyeeIds) {
				
				// 状態を確認する
				// status from activity ⑤社員の日別実績を作成する
				status = createDailyResultEmployeeDomainService.createDailyResultEmployee(asyncContext, employee,
						periodTime, companyId, empCalAndSumExecLogID, reCreateAttr);
				if (status == ProcessState.SUCCESS) {
					dailyCreateCount++;
					// ログ情報（実行内容の完了状態）を更新する
					updateExecutionStatusOfDailyCreation(employee, executionAttr.value, empCalAndSumExecLogID);
					status = ProcessState.SUCCESS;
					dataSetter.updateData("dailyCreateCount", dailyCreateCount);
				} else if (status == ProcessState.INTERRUPTION) {
					status = ProcessState.INTERRUPTION;
					break;
				}
			};
			if (status == ProcessState.SUCCESS) {
				if (executionAttr.value == 0) {
					empCalAndSumExeLogRepository.updateLogInfo(empCalAndSumExecLogID, 0, ExecutionStatus.DONE.value);
				}
			}
		} else {
			status = ProcessState.INTERRUPTION;
		}

		return status;
	}

	private void updateExecutionStatusOfDailyCreation(String employeeID, int executionAttr, String empCalAndSumExecLogID) {

		if (executionAttr == 0) {
			targetPersonRepository.update(employeeID, empCalAndSumExecLogID, ExecutionStatus.DONE.value);
		}

	}

	@AllArgsConstructor
	/**
	 * 正常終了 : 0 中断 : 1
	 */
	public enum ProcessState {
		/* 中断 */
		INTERRUPTION(0),

		/* 正常終了 */
		SUCCESS(1);

		public final int value;

	}

}
