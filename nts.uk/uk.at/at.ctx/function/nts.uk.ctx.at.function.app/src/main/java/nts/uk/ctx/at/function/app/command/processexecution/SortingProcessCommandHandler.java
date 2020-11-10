package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.extern.slf4j.Slf4j;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.adapter.stopbycompany.StopByCompanyAdapter;
import nts.uk.ctx.at.function.dom.adapter.stopbycompany.UsageStopOutputImport;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.*;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.Abolition;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import nts.uk.shr.com.task.schedule.UkJobScheduler;
@Stateless
@Slf4j
public class SortingProcessCommandHandler extends CommandHandler<ScheduleExecuteCommand> {
    @Inject
    private ExecuteProcessExecutionAutoCommandHandler execHandler;
    @Inject
    private ProcessExecutionLogManageRepository processExecLogManaRepo;
    @Inject
    private ProcessExecutionLogHistRepository processExecLogHistRepo;
    @Inject
    private ExecutionTaskSettingRepository execSettingRepo;

    @Inject
    private CompanyAdapter companyAdapter;

    @Inject
    private StopByCompanyAdapter stopBycompanyAdapter;

//	@Inject
//	private UkJobScheduler ukJobScheduler;

    /**
     * Handle.
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.アルゴリズム.振り分け処理.振り分け処理
     *
     * @param context the context
     */
    @Override //振り分け処理
    protected void handle(CommandHandlerContext<ScheduleExecuteCommand> context) {
        ScheduleExecuteCommand command = context.getCommand();
        String companyId = command.getCompanyId();
        String execItemCd = command.getExecItemCd();
        GeneralDateTime nextDate = command.getNextDate();
        // Step 1: RQ580「会社の廃止をチェックする」を実行する
        boolean isAbolished = this.isCheckCompanyAbolished(companyId);
        //	取得した「廃止区分」をチェックする
        if (isAbolished) {
            return;
        }
        // Step 2: ドメインモデル「実行タスク設定」を取得する
        Optional<ExecutionTaskSetting> executionTaskSettingOpt = execSettingRepo.getByCidAndExecCd(companyId, command.getExecItemCd());
        if (!executionTaskSettingOpt.isPresent()) {
            return;
        }
        // Step 3: ドメインモデル「実行タスク設定.更新処理有効設定」をチェックする
        if (!executionTaskSettingOpt.get().isEnabledSetting()) {
            //無効の場合
            return;//フロー終了
        }
        log.info(":更新処理自動実行_START_" + command.getExecItemCd() + "_" + GeneralDateTime.now());
        //実行IDを新規採番する
        String execItemId = IdentifierUtil.randomUniqueId();
        // Step 4: 利用停止をチェックする
        String contractCode = AppContexts.user().contractCode();
        UsageStopOutputImport isSuspension = this.stopBycompanyAdapter.checkUsageStop(contractCode, companyId);
        if (isSuspension.isUsageStop()) {
            // case 利用停止する
            // Step 前回の更新処理が実行中の登録処理
            this.DistributionRegistProcess(companyId, execItemCd, execItemId, nextDate, true);
            return;
        } else {
            // case 利用停止しない
            // Step ドメインモデル「更新処理自動実行管理」取得する
            Optional<ProcessExecutionLogManage> logManageOpt = this.processExecLogManaRepo.getLogByCIdAndExecCd(companyId, execItemCd);
            if (!logManageOpt.isPresent()) {
                return;
            }
            ProcessExecutionLogManage processExecutionLogManage = logManageOpt.get();
            // Step ドメインモデル「更新処理自動実行管理.現在の実行状態」をチェックする
            // 「実行中」
            if (processExecutionLogManage.getCurrentStatus().value == 0) {
                // ドメインモデル「更新処理自動実行管理．前回実行日時」から5時間を経っているかチェックする
                boolean checkLastTime = checkLastDateTimeLessthanNow5h(processExecutionLogManage.getLastExecDateTime());
                if (checkLastTime) {
                    // Step 実行中の場合の登録処理 - Registration process when running
                    this.DistributionRegistProcess(companyId, execItemCd, execItemId, nextDate, false);
                } else {
                    // Step 実行処理
                    this.executeHandler(companyId, execItemCd, execItemId, nextDate);
                }
            }
            // 「待機中」
            else if (processExecutionLogManage.getCurrentStatus().value == 1) {
                // Step 実行処理
                this.executeHandler(companyId, execItemCd, execItemId, nextDate);
            }
        }

    }

    private void executeHandler(String companyId, String execItemCd, String execItemId, GeneralDateTime nextDate) {
        ExecuteProcessExecutionCommand executeProcessExecutionCommand = new ExecuteProcessExecutionCommand();
        executeProcessExecutionCommand.setCompanyId(companyId);
        executeProcessExecutionCommand.setExecItemCd(execItemCd);
        executeProcessExecutionCommand.setExecId(execItemId);
        executeProcessExecutionCommand.setExecType(0);
        executeProcessExecutionCommand.setNextFireTime(Optional.ofNullable(nextDate));
        //AsyncCommandHandlerContext<ExecuteProcessExecutionCommand> ctxNew = new AsyncCommandHandlerContext<ExecuteProcessExecutionCommand>(executeProcessExecutionCommand);
        this.execHandler.handle(executeProcessExecutionCommand);
    }

    /**
     * Distribution regist process.
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.アルゴリズム.振り分け処理.前回の更新処理が実行中の登録処理.前回の更新処理が実行中の登録処理
     *
     * @param companyId  the company id
     * @param execItemCd the exec item cd
     * @param execItemId the exec item id
     * @param nextDate   the next date
     */
    //振り分け登録処理 -> 前回の更新処理が実行中の登録処理
    private void DistributionRegistProcess(String companyId, String execItemCd, String execItemId, GeneralDateTime nextDate, boolean isSystemSuspended) {
        // Step ドメインモデル「更新処理自動実行管理」を更新する
        ProcessExecutionLogManage processExecutionLogManage = this.processExecLogManaRepo.getLogByCIdAndExecCd(companyId, execItemCd).get();
        processExecutionLogManage.setLastExecDateTimeEx(GeneralDateTime.now());
        processExecLogManaRepo.update(processExecutionLogManage);
        // Step ドメインモデル「更新処理自動実行ログ履歴」を新規登録する
        List<ExecutionTaskLog> taskLogList = ProcessExecutionLog.processInitTaskLog();

        List<ProcessExecutionTaskLogCommand> taskLogListCommand = taskLogList.stream()
                .map(item -> ProcessExecutionTaskLogCommand.builder()
                        .taskId(item.getProcExecTask().value)
                        .status(item.getStatus().map(e -> e.value).orElse(null))
                        .lastExecDateTime(item.getLastExecDateTime().orElse(null))
                        .lastEndExecDateTime(item.getLastEndExecDateTime().orElse(null))
                        .errorSystem(item.getErrorSystem().orElse(null))
                        .errorBusiness(item.getErrorBusiness().orElse(null))
                        .systemErrorDetails(item.getSystemErrorDetails().orElse(null))
                        .build())
                .collect(Collectors.toList());
        ProcessExecutionLogHistoryCommand processExecutionLogHistoryCommand = ProcessExecutionLogHistoryCommand.builder()
                .execItemCd(execItemCd)
                .companyId(companyId)
                .execId(execItemId)
                .overallError(isSystemSuspended ? OverallErrorDetail.NOT_EXECUTE.value : OverallErrorDetail.NOT_FINISHED.value)
                .overallStatus(EndStatus.FORCE_END.value)
                .lastExecDateTime(GeneralDateTime.now())
                .lastEndExecDateTime(GeneralDateTime.now())
                .taskLogList(taskLogListCommand)
                .build();
        ProcessExecutionLogHistory processExecutionLogHistory = ProcessExecutionLogHistory.createFromMemento(processExecutionLogHistoryCommand);
        processExecLogHistRepo.insert(processExecutionLogHistory);

        //ドメインモデル「実行タスク設定」を更新する
        Optional<ExecutionTaskSetting> executionTaskSetOpt = this.execSettingRepo.getByCidAndExecCd(companyId, execItemCd);
        if (executionTaskSetOpt.isPresent() && nextDate != null) {
            ExecutionTaskSetting executionTaskSetting = executionTaskSetOpt.get();
            executionTaskSetting.setNextExecDateTime(Optional.ofNullable(nextDate));
            this.execSettingRepo.update(executionTaskSetting);
        }
    }

    //No.3604
    private boolean checkLastDateTimeLessthanNow5h(GeneralDateTime dateTime) {
        GeneralDateTime today = GeneralDateTime.now();
        GeneralDateTime newDateTime = dateTime.addHours(5);
		//システム日時 - 前回実行日時 <= 5時間
		return today.beforeOrEquals(newDateTime);
        //システム日時 - 前回実行日時 > 5時間
	}

    /**
     * RQ580「会社の廃止をチェックする」を実行する.
     *
     * @param companyId the company id
     * @return true, if successful
     */
    private boolean isCheckCompanyAbolished(String companyId) {
        // Step 1 ドメインモデル「会社情報」を取得する - Get domain model "company information"
        CompanyInfo companyInfo = this.companyAdapter.getCompanyInfoById(companyId);
        // Step 2 廃止区分をチェックする - Check the abolition category
        return companyInfo.getIsAbolition() == Abolition.ABOLISH.value;
    }

}
