package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.schedule.ScheduledJobUserData;
import nts.arc.task.schedule.cron.CronSchedule;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionTaskSettingDto;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;
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

	@Override
	protected ExecutionTaskSettingDto handle(CommandHandlerContext<ChangeExecutionTaskSettingCommand> context) {
		ChangeExecutionTaskSettingCommand command = context.getCommand();
		SaveExecutionTaskSettingCommand saveCommand = command.convertToSaveCommand();
		ExecutionTaskSetting[] executionTaskSetting = new ExecutionTaskSetting[1];
		// ドメインモデル「更新処理自動実行管理」を取得し、「現在の実行状態」を確認する
		Optional<ProcessExecutionLogManage> optLogManage = this.processExecutionLogManageRepository
				.getLogByCIdAndExecCd(AppContexts.user().companyId(), command.getExecItemCd());
		optLogManage.ifPresent(logManage -> {
			logManage.getCurrentStatus().ifPresent(status -> {
				// 「実行中」の場合
				if (status.equals(CurrentExecutionStatus.RUNNING)) {
					// エラーメッセージ「#Msg_1318」を表示する
					throw new BusinessException("Msg_1318");
				}
				// 「実行中」以外の場合
				executionTaskSetting[0] = command.toDomain();
				// バッチのスケジュールを削除する
				this.scheduler.unscheduleOnCurrentCompany(SortingProcessScheduleJob.class, command.getScheduleId());
				if (command.getEndScheduleId() != null) {
					this.scheduler.unscheduleOnCurrentCompany(SortingProcessEndScheduleJob.class, command.getEndScheduleId());
				}
				// 次回実行日時作成処理（実行タスク設定）
				List<String> cronList = saveExecutionTaskSettingCommandHandler.getCron(saveCommand);
				CronSchedule cron = new CronSchedule(Arrays.asList(cronList.get(0)));
				ScheduledJobUserData scheduletimeData = new ScheduledJobUserData();
				scheduletimeData.put("companyId", AppContexts.user().companyId());
				scheduletimeData.put("execItemCd", command.getExecItemCd());
				String scheduleIdDef = "KBT002_" + command.getExecItemCd();
				String scheduleIdEnd = scheduleIdDef + "_end";
				
				// sheet 補足資料⑤
				// compare system date and system time
				GeneralDate startDate = command.getStartDate();
				GeneralDate endDate = command.getEndDate();
				Integer timeSystem = GeneralDateTime.now().minutes() + GeneralDateTime.now().hours() * 60;
				if (startDate.before(GeneralDate.today())) {
					if (command.getStartTime() < timeSystem) {
						startDate = GeneralDate.today().addDays(1);
					} else {
						startDate = GeneralDate.today();
					}
				}
				UkJobScheduleOptions options = UkJobScheduleOptions.builder(SortingProcessScheduleJob.class, scheduleIdDef, cron)
						.userData(scheduletimeData).startDate(startDate).endDate(endDate)
						.startClock(new StartTime(command.getStartTime())).endClock(new EndTime(command.getEndTime()))
						.build();
				UkJobScheduleOptions optionsEnd = UkJobScheduleOptions.builder(SortingProcessEndScheduleJob.class, scheduleIdEnd, cron)
						.userData(scheduletimeData).startDate(startDate).endDate(endDate)
						.startClock(new StartTime(command.getStartTime())).endClock(new EndTime(command.getEndTime()))
						.build();
				String scheduleId = this.schedule(options);
				String endScheduleId = this.schedule(optionsEnd);
				// INPUT「実行タスク設定．更新処理有効設定」を確認する
				if (executionTaskSetting[0].isEnabledSetting()) {
					// アルゴリズム「次回実行日時作成処理」を実行する
					Optional<GeneralDateTime> nextExecDateTime = this.getNextExecDateTime(executionTaskSetting[0], scheduleId);
					executionTaskSetting[0].setNextExecDateTime(nextExecDateTime);
				} else {
					// 次回実行日時をNULLとする
					executionTaskSetting[0].setNextExecDateTime(Optional.empty());
				}
				// 取得した内容をINPUT「実行タスク設定」にセットする
				executionTaskSetting[0].setScheduleId(scheduleId);
				executionTaskSetting[0].setEndScheduleId(Optional.ofNullable(endScheduleId));
				
				try {
					// 登録処理
					this.performRegister(executionTaskSetting[0], saveCommand);
				} catch (Exception e) {
					throw new BusinessException("Msg_1110");
				}
			});
		});
		if (executionTaskSetting[0] != null) {
			// 更新後の「実行タスク設定」を返す
			return ExecutionTaskSettingDto.fromDomain(executionTaskSetting[0]);
		}
		return null;
	}
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	private String schedule(UkJobScheduleOptions options) {
		return this.scheduler.scheduleOnCurrentCompany(options).getScheduleId();
	}
	
	private Optional<GeneralDateTime> getNextExecDateTime(ExecutionTaskSetting domain, String scheduleId) {
		// アルゴリズム「スケジュールされたバッチ処理の次回実行日時を取得する」を実行する
		Optional<GeneralDateTime> nextExecDateTime = this.scheduler.getNextFireTime(scheduleId);
		// 「次回実行日時（スケジュールID）」をチェックする
		// それ以外（取得失敗、エラー等）
		if (!nextExecDateTime.isPresent()) {
			// 次回実行日時をNULLとする
			return Optional.empty();
		}
		// 取得成功
		// 「次回実行日時（暫定）」が「終了日＋終了時刻」を過ぎているか判定する
		GeneralDateTime endDateTime = null;
		EndTime endTime = domain.getEndTime().getEndTime();
		GeneralDate endDate = domain.getEndDate().getEndDate();
		if (domain.getEndDate().getEndDateCls().equals(EndDateClassification.DATE)) {
			if (domain.getEndTime().getEndTimeCls().equals(EndTimeClassification.YES)) {
				endDateTime = GeneralDateTime.ymdhms(
						endDate.year(),
						endDate.month(),
						endDate.day(),
						endTime.hour(),
						endTime.minute(),
						0);	
			} else {
				endDateTime = GeneralDateTime.ymdhms(
						endDate.year(),
						endDate.month(),
						endDate.day(),
						0,
						0,
						0);	
			}
		}
		// 終了日時を過ぎていない、もしくは終了日時なし
		if (endDateTime == null || nextExecDateTime.get().before(endDateTime)) {
			// 次回実行日時（暫定）を「次回実行日時」として確定する
			return nextExecDateTime;
		}
		// 終了日時を過ぎている
		else {
			// 次回実行日時をNULLとする
			return Optional.empty();
		}
	}
	
	private void performRegister(ExecutionTaskSetting domain, SaveExecutionTaskSettingCommand command) throws Exception {
		domain.setEnabledSetting(!domain.isEnabledSetting());
		this.saveExecutionTaskSettingCommandHandler.saveTaskSetting(
				command, 
				domain, 
				AppContexts.user().companyId(),
				command.getRepeatMonthDateList().stream()
					.map(x -> EnumAdaptor.valueOf(x, RepeatMonthDaysSelect.class)).collect(Collectors.toList()),
				domain.getScheduleId(),
				domain.getEndScheduleId().orElse(null));
	}
}
