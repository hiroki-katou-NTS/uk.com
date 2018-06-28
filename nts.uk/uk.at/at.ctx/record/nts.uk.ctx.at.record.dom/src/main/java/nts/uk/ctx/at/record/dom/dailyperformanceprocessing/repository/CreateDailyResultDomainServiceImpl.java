package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.diagnose.stopwatch.Stopwatches;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.AsyncTask;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class CreateDailyResultDomainServiceImpl implements CreateDailyResultDomainService {

//	@Inject
//	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private CreateDailyResultEmployeeDomainService createDailyResultEmployeeDomainService;

	@Inject
	private TargetPersonRepository targetPersonRepository;
	
	@Inject
	private EmployeeGeneralInfoService employeeGeneralInfoService;
	
	@Inject
	private UpdateLogInfoWithNewTransaction updateLogInfoWithNewTransaction; 

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
			DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
			Optional<ExecutionLog> executionLog) {

		val dataSetter = asyncContext.getDataSetter();

		ProcessState status = ProcessState.SUCCESS;

		// AsyncCommandHandlerContext<SampleCancellableAsyncCommand> ABC;

		// ③日別実績の作成処理
		if (executionLog.isPresent()) {

			ExecutionContent executionContent = executionLog.get().getExecutionContent();

			if (executionContent == ExecutionContent.DAILY_CREATION) {

				// ④ログ情報（実行ログ）を更新する
				updateLogInfoWithNewTransaction.updateLogInfo(empCalAndSumExecLogID, 0, ExecutionStatus.PROCESSING.value);
				
				EmployeeGeneralInfoImport employeeGeneralInfoImport = this.employeeGeneralInfoService.getEmployeeGeneralInfo(emloyeeIds, periodTime);

				Stopwatches.start("start create");
				
				StateHolder stateHolder = new StateHolder(emloyeeIds.size());
				
				/** 並列処理、AsyncTask */
				// Create thread pool.
				ExecutorService executorService = Executors.newFixedThreadPool(20);
				CountDownLatch countDownLatch = new CountDownLatch(emloyeeIds.size());
				
				emloyeeIds.forEach(employeeId -> {
					AsyncTask task = AsyncTask.builder()
							.withContexts()
							.keepsTrack(false)
							.setDataSetter(dataSetter)
							.threadName(this.getClass().getName())
							.build(() -> {
								// 社員の日別実績を計算
								if(stateHolder.isInterrupt()){
									return;
								}
								ProcessState cStatus = createData(asyncContext, periodTime, executionAttr, companyId, empCalAndSumExecLogID,
										executionLog, dataSetter, employeeGeneralInfoImport, stateHolder, employeeId);
								
								stateHolder.add(cStatus);
								// Count down latch.
								countDownLatch.countDown();
							});
					executorService.submit(task);
				});
				// Wait for latch until finish.
				try {
					countDownLatch.await();
				} catch (InterruptedException ie) {
					throw new RuntimeException(ie);
				} finally {
					// Force shut down executor services.
					executorService.shutdown();
				}
				Stopwatches.stop("start create");
				status = stateHolder.status.stream().filter(c -> c == ProcessState.INTERRUPTION)
						.findFirst().orElse(ProcessState.SUCCESS);
				if (status == ProcessState.SUCCESS) {
					if (executionAttr.value == 0) {
						updateLogInfoWithNewTransaction.updateLogInfo(empCalAndSumExecLogID, 0,
								ExecutionStatus.DONE.value);
					}
				}
			} else {
				status = ProcessState.INTERRUPTION;
			}
		}

		return status;
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	private ProcessState createData(AsyncCommandHandlerContext asyncContext, DatePeriod periodTime, ExecutionAttr executionAttr,
			String companyId, String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog,
			TaskDataSetter dataSetter, EmployeeGeneralInfoImport employeeGeneralInfoImport,
			StateHolder stateHolder, String employeeId) {
		ProcessState cStatus = createDailyResultEmployeeDomainService.createDailyResultEmployee(asyncContext, employeeId,
				periodTime, companyId, empCalAndSumExecLogID, executionLog, false, employeeGeneralInfoImport);
		// 状態確認
		if (cStatus == ProcessState.SUCCESS){
			updateExecutionStatusOfDailyCreation(employeeId, executionAttr.value, empCalAndSumExecLogID);
			dataSetter.updateData("dailyCreateCount", stateHolder.count() + 1);
		}
		return cStatus;
	}

	private void updateExecutionStatusOfDailyCreation(String employeeID, int executionAttr,
			String empCalAndSumExecLogID) {

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
	
	class StateHolder {
		private BlockingQueue<ProcessState> status;
		
		StateHolder(int max){
			status = new ArrayBlockingQueue<ProcessState>(max);
		}
		
		void add(ProcessState status){
			this.status.add(status);
		}
		
		int count(){
			return this.status.size();
		}
		
		boolean isInterrupt(){
			return this.status.stream().filter(s -> s == ProcessState.INTERRUPTION).findFirst().isPresent();
		}
	}

}
