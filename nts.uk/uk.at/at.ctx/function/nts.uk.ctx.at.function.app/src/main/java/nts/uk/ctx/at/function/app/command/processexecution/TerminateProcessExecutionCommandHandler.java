package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.AsyncTaskService;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;

@Stateless
public class TerminateProcessExecutionCommandHandler extends AsyncCommandHandler<TerminateProcessExecutionCommand> {

	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalSumRepo;
	
	@Inject
	private ProcessExecutionLogManageRepository processExecLogManRepo;
	@Inject
	private AsyncTaskService service;

	//終了ボタン押下時処理
	@Override
	public void handle(CommandHandlerContext<TerminateProcessExecutionCommand> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		TerminateProcessExecutionCommand command = context.getCommand();
		String execItemCd = command.getExecItemCd();
		String companyId = command.getCompanyId();
		String taskTerminate = command.getTaskTerminate();
		//ドメインモデル「更新処理自動実行管理」を取得する
		Optional<ProcessExecutionLogManage> processExecLogManOpt = this.processExecLogManRepo.getLogByCIdAndExecCd(companyId, execItemCd);
		if(!processExecLogManOpt.isPresent()){
			return;
		}
		ProcessExecutionLogManage processExecLogMan =processExecLogManOpt.get();
		
		//「待機中」 or 「無効」の場合
		if(processExecLogMan.getCurrentStatus().value==1 || processExecLogMan.getCurrentStatus().value==2){
			dataSetter.setData("currentStatusIsOneOrTwo", "Msg_1102");
			return;
		}
		
		
		/*
		 * ドメインモデル「更新処理自動実行管理」を更新する
		 * 
		 * 【終了タイプ　＝　1（F画面終了ボタン）の場合】
		 * 全体の終了状態　＝　強制終了
		 * 
		 * 【終了タイプ　＝　0（終了時刻）の場合】
		 * 全体の終了状態　＝　異常終了
		 * 全体のエラー詳細　＝　更新処理の途中で終了時刻を超過したため中断しました
		 */	
		int execType = command.getExecType();
		if (execType == 1) {
			processExecLogMan.setOverallStatus(EndStatus.FORCE_END);
		} else if (execType == 0) {
			processExecLogMan.setOverallStatus(EndStatus.ABNORMAL_END);
			processExecLogMan.setOverallError(OverallErrorDetail.EXCEED_TIME);
		}
		this.processExecLogManRepo.update(processExecLogMan);
		
		// ドメインモデル「更新処理自動実行ログ」を取得する
				ProcessExecutionLog procExecLog = null;
				Optional<ProcessExecutionLog> procExecLogOpt =
									this.procExecLogRepo.getLog(companyId, execItemCd);
				if (!procExecLogOpt.isPresent()) {
					return;
				}
				procExecLog = procExecLogOpt.get();
				String execId = procExecLog.getExecId();
		
		
		//スケジュールの作成の処理が完了しているか確認する
		//TODO NO3  fixed da tao schedule
		/*
		if(execType == 1){ // dung cho NO3 o tren
			dataSetter.setData("interupt", "true");
		}
		*/
		procExecLog.getTaskLogList().forEach(task ->{
			if (task.getProcExecTask().value == ProcessExecutionTask.SCH_CREATION.value) {
				if (task.getStatus() == null || !task.getStatus().isPresent()) {
					if(taskTerminate!=null&& !"".equals(taskTerminate)){
						service.requestToCancel(taskTerminate);	
					}
					this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
					return;
				}
			}
			 //日別作成の処理が完了しているか確認する
				else if (task.getProcExecTask().value == ProcessExecutionTask.DAILY_CREATION.value) {
				if (task.getStatus() == null || !task.getStatus().isPresent()) {
					if(taskTerminate!=null&& !"".equals(taskTerminate)){
						service.requestToCancel(taskTerminate);	
					}
					this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
					return;
				}
			//日別計算の処理が完了しているか確認する	
			} else if (task.getProcExecTask().value == ProcessExecutionTask.DAILY_CALCULATION.value) {
				if (task.getStatus() == null || !task.getStatus().isPresent()) {
					if(taskTerminate!=null&& !"".equals(taskTerminate)){
						service.requestToCancel(taskTerminate);
						}
					this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
					return;
				}
			//承認結果反映の処理が完了しているか確認する	
			} else if (task.getProcExecTask().value == ProcessExecutionTask.RFL_APR_RESULT.value) {
				if (task.getStatus() == null || !task.getStatus().isPresent()) {
					if(taskTerminate!=null&& !"".equals(taskTerminate)){
						service.requestToCancel(taskTerminate);
						}
					this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
					return;
				}
			//月別集計の処理が完了しているか確認する	
			} else if (task.getProcExecTask().value == ProcessExecutionTask.MONTHLY_AGGR.value) {
				if (task.getStatus() == null || !task.getStatus().isPresent()) {
					if(taskTerminate!=null && !"".equals(taskTerminate)){
						service.requestToCancel(taskTerminate);
					}
					this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
					return;
				}
			//アラーム抽出を中断する
			} else if (task.getProcExecTask().value == ProcessExecutionTask.AL_EXTRACTION.value) {
				if (task.getStatus() == null || !task.getStatus().isPresent()) {
					if(taskTerminate!=null && !"".equals(taskTerminate)){
						service.requestToCancel(taskTerminate);
					}
					this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
					return;
				}
			} else{
				if(execType == 1){ 
					dataSetter.setData("interupt", "true");
				}
			} 
		});
		/*
		 * ドメインモデル「就業計算と集計実行ログ」を更新する
		 * 
		 * 【条件】
		 * ID　＝　取得した実行ID
		 * 会社ID　＝　取得した会社ID
		 * 
		 * 【更新内容】
		 * 就業計算と集計実行ログ．実行状況 ← 中断終了
		 */
		this.interupt(execId, ExeStateOfCalAndSum.END_INTERRUPTION.value);
	}
	
	/**
	 * ドメインモデル「就業計算と集計実行ログ」を更新する
	 * @param execId
	 * @param status
	 */
	//日別作成を中断する
	private void interupt(String execId, int status) {
		/* ドメインモデル「就業計算と集計実行ログ」を更新する
		 * 
		 * 【条件】
		 * ID　＝　実行ID
		 * 会社ID　＝　取得した会社ID
		 * 
		 * 【更新内容】
		 * 就業計算と集計実行ログ．実行状況 ← 中断開始
		 */
		this.empCalSumRepo.updateStatus(execId, status);
	}
}
