package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.quartz.CronExpression;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.schedule.cron.CronSchedule;
import nts.arc.task.schedule.job.jobdata.ScheduledJobUserData;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionTaskSettingDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionService;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.CronType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.task.schedule.UkJobScheduleOptions;
import nts.uk.shr.com.task.schedule.UkJobScheduler;

/**
 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.F:実行選択.アルゴリズム.実行タスクの有効設定を変更する.実行タスクの有効設定を変更する
 */
@Stateless
//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ChangeExecutionTaskSettingCommandHandler
		extends CommandHandlerWithResult<ChangeExecutionTaskSettingCommand, ExecutionTaskSettingDto> {

	@Inject
	private ProcessExecutionLogManageRepository processExecutionLogManageRepository;

	@Inject
	private UkJobScheduler scheduler;

	@Inject
	private SaveExecutionTaskSettingCommandHandler saveExecutionTaskSettingCommandHandler;

	@Inject
	private ProcessExecutionService processExecutionService;

	@Override
	protected ExecutionTaskSettingDto handle(CommandHandlerContext<ChangeExecutionTaskSettingCommand> context) {
		ChangeExecutionTaskSettingCommand command = context.getCommand();
		SaveExecutionTaskSettingCommand saveCommand = command.convertToSaveCommand();
		// ドメインモデル「更新処理自動実行管理」を取得し、「現在の実行状態」を確認する
		Optional<ProcessExecutionLogManage> optLogManage = this.processExecutionLogManageRepository
				.getLogByCIdAndExecCd(AppContexts.user().companyId(), command.getExecItemCd());
		if (optLogManage.isPresent()) {
			ProcessExecutionLogManage logManage = optLogManage.get();
			if (logManage.getCurrentStatus().isPresent()) {
				CurrentExecutionStatus status = logManage.getCurrentStatus().get();
				// 「実行中」の場合
				if (status.equals(CurrentExecutionStatus.RUNNING)) {
					// エラーメッセージ「#Msg_1318」を表示する
					throw new BusinessException("Msg_1318");
				}
				// 「実行中」以外の場合
				ExecutionTaskSetting executionTaskSetting = command.toDomain();
				// バッチのスケジュールを削除する
				this.unschedule(executionTaskSetting.getScheduleId());
				executionTaskSetting.getRepeatScheduleId().ifPresent(this::unschedule);
				executionTaskSetting.getEndScheduleId().ifPresent(this::unschedule);

				// 次回実行日時作成処理（実行タスク設定）
				Map<CronType, CronSchedule> cronList = saveExecutionTaskSettingCommandHandler.getCron(saveCommand);
				ScheduledJobUserData scheduletimeData = new ScheduledJobUserData();
				scheduletimeData.put("companyId", AppContexts.user().companyId());
				scheduletimeData.put("execItemCd", command.getExecItemCd());
				scheduletimeData.put("companyCd", AppContexts.user().companyCode());

				Map<CronType, UkJobScheduleOptions> optionsList = cronList.entrySet().stream()
						.filter(e -> CronExpression.isValidExpression(e.getValue().getCronExpressions().get(0)))
						.collect(Collectors.toMap(Entry::getKey,
								e -> this.processExecutionService.buildScheduleOptions(SortingProcessScheduleJob.class,
										e.getKey(), e.getValue(), scheduletimeData, executionTaskSetting)));
				String scheduleId = Optional.ofNullable(optionsList.get(CronType.START)).map(this::schedule)
						.orElse(null);
				Optional<String> endScheduleId = Optional.ofNullable(optionsList.get(CronType.END)).map(this::schedule);
				Optional<String> repScheduleId = Optional.ofNullable(optionsList.get(CronType.REPEAT))
						.map(this::schedule);
				executionTaskSetting.setScheduleId(scheduleId);
				executionTaskSetting.setRepeatScheduleId(repScheduleId);
				executionTaskSetting.setEndScheduleId(endScheduleId);
				try {
					// INPUT「実行タスク設定．更新処理有効設定」を確認する
					if (executionTaskSetting.isEnabledSetting()) {
						// アルゴリズム「次回実行日時作成処理」を実行する
						Optional<GeneralDateTime> nextExecDateTime = Optional.ofNullable(
								this.processExecutionService.processNextExecDateTimeCreation(executionTaskSetting));
						executionTaskSetting.setNextExecDateTime(nextExecDateTime);
					} else {
						// 取得した内容をINPUT「実行タスク設定」にセットする
						// 次回実行日時をNULLとする
						executionTaskSetting.setNextExecDateTime(Optional.empty());
					}
					// 登録処理
					this.performRegister(saveCommand);
				} catch (Exception e) {
					throw new BusinessException("Msg_1110");
				}
				// 更新後の「実行タスク設定」を返す
				return ExecutionTaskSettingDto.fromDomain(executionTaskSetting);
			}
		}
		return null;
	}

	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	private String schedule(UkJobScheduleOptions options) {
		return this.scheduler.scheduleOnCurrentCompany(options).getScheduleId();
	}

	private void unschedule(String scheduleId) {
		this.scheduler.unscheduleOnCurrentCompany(scheduleId);
	}

	private void performRegister(SaveExecutionTaskSettingCommand command)
			throws Exception {
		this.saveExecutionTaskSettingCommandHandler.handle(command);
	}
}
