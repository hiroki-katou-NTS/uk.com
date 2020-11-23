package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.AsyncTaskService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.command.processexecution.appupdatesuspension.AppUpdateSuspension;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EachProcessPeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionStatus;
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

    @Inject
    private ProcessExecutionLogHistRepository processExecutionLogHistRepo;

    @Inject
    private AppUpdateSuspension appUpdateSuspension;

    @Inject
    private AggrPeriodExcutionRepository aggrPeriodExcutionRepo;

    //終了ボタン押下時処理
    @Override
    public void handle(CommandHandlerContext<TerminateProcessExecutionCommand> context) {
//		val asyncContext = context.asAsync();
//		val dataSetter = asyncContext.getDataSetter();
        TerminateProcessExecutionCommand command = context.getCommand();
        String execItemCd = command.getExecItemCd();
        String companyId = command.getCompanyId();
        String taskTerminate = command.getTaskTerminate();
        //ドメインモデル「更新処理自動実行管理」を取得する
        Optional<ProcessExecutionLogManage> processExecLogManOpt = this.processExecLogManRepo.getLogByCIdAndExecCd(companyId, execItemCd);
        if (!processExecLogManOpt.isPresent()) {
           return;
        }
        // ドメインモデル「更新処理自動実行管理」．全体の終了状態 をチェックする
        if (processExecLogManOpt.get().getOverallStatus().isPresent()) {
            if (processExecLogManOpt.get().getOverallStatus().get() == EndStatus.SUCCESS
                    || processExecLogManOpt.get().getOverallStatus().get() == EndStatus.FORCE_END) {
                return;
            }
        }

        ProcessExecutionLogManage processExecLogMan = processExecLogManOpt.get();

        //「待機中」 or 「無効」の場合
        if (processExecLogMan.getCurrentStatus().isPresent()
        		&& !processExecLogMan.getCurrentStatus().get().equals(CurrentExecutionStatus.RUNNING)) {
//			dataSetter.setData("currentStatusIsOneOrTwo", "Msg_1102");
            throw new BusinessException("Msg_1102");
        }

        // ドメインモデル「更新処理自動実行ログ」を取得する
        ProcessExecutionLog procExecLog = null;
        Optional<ProcessExecutionLog> procExecLogOpt =
                this.procExecLogRepo.getLog(companyId, execItemCd);
        if (!procExecLogOpt.isPresent()) {
            return;
        }

        procExecLog = procExecLogOpt.get();

        //アルゴリズム[全体エラー状況確認処理]を実行する
        //ErrorConditionOutput errorCondition = overallErrorProcess.overallErrorProcess(procExecLog);
        /*
         * ドメインモデル「更新処理自動実行管理」を更新する
         *
         *	【終了タイプ　＝　1（F画面終了ボタン）の場合】
         *	全体の終了状態　＝　終了中
         *	強制終了の原因　＝　画面から強制終了しました。
         *	【終了タイプ　＝　0（終了時刻）の場合】
         *	強制終了の原因　＝　更新処理の途中で終了時刻を超過したため中断しました。
         *	全体の終了状態　＝　終了中
         */
        int execType = command.getExecType();
        if (execType == 1) {
            processExecLogMan.setOverallStatus(EndStatus.CLOSING);
            processExecLogMan.setOverallError(OverallErrorDetail.TERMINATED);
        } else if (execType == 0) {
            processExecLogMan.setOverallStatus(EndStatus.CLOSING);
            processExecLogMan.setOverallError(OverallErrorDetail.EXCEED_TIME);
        }
        processExecLogMan.setCurrentStatus(CurrentExecutionStatus.WAITING);
        this.processExecLogManRepo.update(processExecLogMan);
		
		/*【登録内容】
		・会社ID　＝　取得した会社ID
		・実行ID　＝　「更新処理自動実行ログ」.実行ID
		・コード　＝　取得した更新処理自動実行項目コード
		・前回実行日時　＝　「更新処理自動実行管理」.前回実行日時
		・各処理の終了状態(List)　＝　「更新処理自動実行ログ」.各処理の終了状態（List）
		・全体の終了状態　＝　「更新処理自動実行管理」.全体の終了状態
		・全体のエラー詳細　＝　「更新処理自動実行管理」.全体のエラー詳細
		・各処理の期間　＝　「更新処理自動実行ログ」.各処理の期間
		*/
        procExecLog.getTaskLogList().stream().sorted((x, y) -> x.getProcExecTask().value - y.getProcExecTask().value).collect(Collectors.toList());
//        List<ProcessExecutionTaskLogCommand> taskLogListCommand = procExecLog.getTaskLogList().stream()
//                .map(item -> ProcessExecutionTaskLogCommand.builder()
//                        .taskId(item.getProcExecTask().value)
//                        .status(item.getStatus().map(e -> e.value).orElse(null))
//                        .lastExecDateTime(item.getLastExecDateTime().orElse(null))
//                        .lastEndExecDateTime(item.getLastEndExecDateTime().orElse(null))
//                        .errorSystem(item.getErrorSystem().orElse(null))
//                        .errorBusiness(item.getErrorBusiness().orElse(null))
//                        .systemErrorDetails(item.getSystemErrorDetails().orElse(null))
//                        .build())
//                .collect(Collectors.toList());
        Optional<DatePeriod> scheduleCreationPeriod = procExecLog.getEachProcPeriod().map(EachProcessPeriod::getScheduleCreationPeriod).orElse(null);
        Optional<DatePeriod> dailyCreationPeriod = procExecLog.getEachProcPeriod().map(EachProcessPeriod::getDailyCreationPeriod).orElse(null);
        Optional<DatePeriod> dailyCalcPeriod = procExecLog.getEachProcPeriod().map(EachProcessPeriod::getDailyCalcPeriod).orElse(null);
        Optional<DatePeriod> reflectApprovalResult = procExecLog.getEachProcPeriod().map(EachProcessPeriod::getReflectApprovalResult).orElse(null);
        ProcessExecutionLogHistoryCommand processExecutionLogHistoryCommand = ProcessExecutionLogHistoryCommand.builder()
                .execItemCd(execItemCd)
                .companyId(companyId)
                .execId(procExecLog.getExecId())
                .overallError(processExecLogMan.getOverallError().map(item -> item.value).orElse(null))
                .overallStatus(processExecLogMan.getOverallStatus().map(item -> item.value).orElse(null))
                .lastExecDateTime(processExecLogMan.getLastExecDateTime().orElse(null))
                .lastEndExecDateTime(processExecLogMan.getLastEndExecDateTime().orElse(null))
                .errorSystem(processExecLogMan.getErrorSystem().orElse(null))
                .errorBusiness(processExecLogMan.getErrorBusiness().orElse(null))
                .taskLogList(Collections.emptyList())
                .schCreateStart(scheduleCreationPeriod.map(DatePeriod::start).orElse(null))
                .schCreateEnd(scheduleCreationPeriod.map(DatePeriod::end).orElse(null))
                .dailyCreateStart(dailyCreationPeriod.map(DatePeriod::start).orElse(null))
                .dailyCreateEnd(dailyCreationPeriod.map(DatePeriod::end).orElse(null))
                .dailyCalcStart(dailyCalcPeriod.map(DatePeriod::start).orElse(null))
                .dailyCalcEnd(dailyCalcPeriod.map(DatePeriod::end).orElse(null))
                .reflectApprovalResultStart(reflectApprovalResult.map(DatePeriod::start).orElse(null))
                .reflectApprovalResultEnd(reflectApprovalResult.map(DatePeriod::end).orElse(null))
                .build();

        ProcessExecutionLogHistory processExecutionLogHistory = ProcessExecutionLogHistory.createFromMemento(processExecutionLogHistoryCommand);
        //ドメインモデル「更新処理自動実行ログ履歴」を追加する
        processExecutionLogHistRepo.insert(processExecutionLogHistory);
        String execId = procExecLog.getExecId();
        //スケジュールの作成の処理が完了しているか確認する
        //TODO NO3  fixed da tao schedule
		/*
		if(execType == 1){ // dung cho NO3 o tren
			dataSetter.setData("interupt", "true");
		}
		*/
        ProcessExecutionTask statusStop = null;
        for (ExecutionTaskLog task : procExecLog.getTaskLogList()) {
            //procExecLog.getTaskLogList().forEach(task ->{
            if (task.getProcExecTask().value == ProcessExecutionTask.SCH_CREATION.value) {

                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    System.out.println("update trang thai dung xu ly tao schdule cua anh Son");
                    this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
                    statusStop = task.getProcExecTask();
                    //break;
                }
            }
            //日別作成の処理が完了しているか確認する
            else if (task.getProcExecTask().value == ProcessExecutionTask.DAILY_CREATION.value) {
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    //TU_DEBUG
                    System.out.println("update trang thai dung xu ly tao data cua anh Nam");
                    this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
                    statusStop = task.getProcExecTask();
//					break;
                }
                //日別計算の処理が完了しているか確認する
            } else if (task.getProcExecTask().value == ProcessExecutionTask.DAILY_CALCULATION.value) {
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    //TU_DEBUG
                    System.out.println("update trang thai dung xu ly tinh toan data");
                    this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
                    statusStop = task.getProcExecTask();
//					break;
                }
                //承認結果反映の処理が完了しているか確認する
            } else if (task.getProcExecTask().value == ProcessExecutionTask.RFL_APR_RESULT.value) {
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
                    statusStop = task.getProcExecTask();
//					break;
                }
                //月別集計の処理が完了しているか確認する
            } else if (task.getProcExecTask().value == ProcessExecutionTask.MONTHLY_AGGR.value) {
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
                    statusStop = task.getProcExecTask();
//					break;
                }
                //アラーム抽出を中断する
            } else if (task.getProcExecTask().value == ProcessExecutionTask.AL_EXTRACTION.value) {
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    this.interupt(execId, ExeStateOfCalAndSum.START_INTERRUPTION.value);
                    statusStop = task.getProcExecTask();
//					break;
                }
            } else if (task.getProcExecTask().value == ProcessExecutionTask.APP_ROUTE_U_DAI.value) {
                if (statusStop != null) {
                    appUpdateSuspension.updateSuspension(execId, true);
                    statusStop = task.getProcExecTask();
                }
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    appUpdateSuspension.updateSuspension(execId, true);
                    statusStop = task.getProcExecTask();
                }
            } else if (task.getProcExecTask().value == ProcessExecutionTask.APP_ROUTE_U_MON.value) {
                if (statusStop != null && statusStop != ProcessExecutionTask.APP_ROUTE_U_DAI) {
                    appUpdateSuspension.updateSuspension(execId, false); //if monthly
                    statusStop = task.getProcExecTask();
                }
                if (task.getStatus() == null || !task.getStatus().isPresent()) {
                    if (taskTerminate != null && !"".equals(taskTerminate)) {
                        service.requestToCancel(taskTerminate);
                    }
                    appUpdateSuspension.updateSuspension(execId, false); //if monthly
                    statusStop = task.getProcExecTask();
                }
            } else if (task.getProcExecTask().equals(ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD)) {
                // 任意期間の集計の処理が完了しているか確認する
                if (statusStop == null) {
                    // NULLの場合
                    // 任意期間の集計中断処理
                    this.aggregationSuspensionProcessing(companyId, execId);
                    statusStop = task.getProcExecTask();
                }
            }
        }
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
//		this.interupt(execId, ExeStateOfCalAndSum.END_INTERRUPTION.value);
        //ドメインモデル「更新処理自動実行ログ履歴」を更新する
        for (ExecutionTaskLog task : processExecutionLogHistory.getTaskLogList()) {
            if (task.getStatus() == null || !task.getStatus().isPresent()) {
                if (task.getProcExecTask() == statusStop) {
                    task.setStatus(EndStatus.FORCE_END);
                    for (ExecutionTaskLog taskAfter : processExecutionLogHistory.getTaskLogList()) {
                        if (taskAfter.getProcExecTask().value > task.getProcExecTask().value) {
                            taskAfter.setStatus(EndStatus.NOT_IMPLEMENT);
                        }
                    }

                    break;
                }
            }
        }

        this.processExecutionLogHistRepo.update(processExecutionLogHistory);
	}

    /**
     * 任意期間の集計中断処理
     *
     * @param companyId
     * @param execId
     */
    private void aggregationSuspensionProcessing(String companyId, String execId) {
        // ドメインモデル「任意期間集計実行ログ」を更新する
        List<AggrPeriodExcution> listDomain = this.aggrPeriodExcutionRepo.findAggrCode(companyId, execId);
        listDomain = listDomain.stream()
                // 任意期間集計実行ログ．実行状況 = 中断開始
                .map(domain -> AggrPeriodExcution.createFromJavaType(
                        companyId,
                        execId,
                        domain.getAggrFrameCode().v(),
                        domain.getAggrId(),
                        domain.getStartDateTime(),
                        domain.getEndDateTime(),
                        domain.getExecutionAtr().value,
                        ExecutionStatus.START_OF_INTERRUPTION.value,
                        domain.getPresenceOfError().value))
                .collect(Collectors.toList());
        this.aggrPeriodExcutionRepo.updateAll(listDomain);
    }

    /**
     * ドメインモデル「就業計算と集計実行ログ」を更新する
     *
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
