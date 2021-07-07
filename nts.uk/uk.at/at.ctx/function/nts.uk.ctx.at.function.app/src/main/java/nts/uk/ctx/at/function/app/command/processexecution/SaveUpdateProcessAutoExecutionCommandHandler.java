package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
/**
 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.B:実行設定.アルゴリズム.登録ボタン押下時処理.登録ボタン押下時処理
 */
public class SaveUpdateProcessAutoExecutionCommandHandler
		extends CommandHandler<SaveUpdateProcessAutoExecutionCommand> {

	@Inject
	private ProcessExecutionRepository processExecutionRepository;

	@Inject
	private ProcessExecutionLogManageRepository processExecutionLogManageRepository;

	@Inject
	private LastExecDateTimeRepository lastExecDateTimeRepository;

	@Inject
	private ExecutionTaskSettingRepository executionTaskSettingRepository;

	@Inject
	private ChangeExecutionTaskSettingCommandHandler changeExecutionTaskSettingCommandHandler;

	@Override
	protected void handle(CommandHandlerContext<SaveUpdateProcessAutoExecutionCommand> context) {
		SaveUpdateProcessAutoExecutionCommand command = context.getCommand();
		UpdateProcessAutoExecution execTaskSetting = UpdateProcessAutoExecution
				.createFromMemento(AppContexts.user().companyId(), command);
		// アルゴリズム「登録チェック処理」を実行する
		execTaskSetting.validate();
		// 画面モードチェック
		if (command.isNewMode()) {
			// 新規モード
			// 更新処理自動実行項目コードは重複してはならない
			// #Msg_3
			if (this.processExecutionRepository.getProcessExecutionByCidAndExecCd(AppContexts.user().companyId(),
					execTaskSetting.getExecItemCode().v()).isPresent()) {
				throw new BusinessException("Msg_3");
			}
			// アルゴリズム「新規登録処理」を実行する
			this.processRegister(execTaskSetting);
		} else {
			// 更新モード
			this.processUpdate(execTaskSetting, command.isEnabledSetting());
		}
	}

	private void processRegister(UpdateProcessAutoExecution execTaskSetting) {
		String cid = AppContexts.user().companyId();
		// ドメインモデル「更新処理自動実行」に新規登録する
		this.processExecutionRepository.insert(execTaskSetting);
		// ドメインモデル「更新処理自動実行管理」に新規登録する
		this.processExecutionLogManageRepository.insert(ProcessExecutionLogManage.builder().companyId(cid)
				.currentStatus(Optional.of(CurrentExecutionStatus.WAITING))
				.execItemCd(execTaskSetting.getExecItemCode()).overallStatus(Optional.of(EndStatus.NOT_IMPLEMENT))
				.build());
		// ドメインモデル「更新処理前回実行日時」に新規登録する
		this.lastExecDateTimeRepository.insert(new LastExecDateTime(cid, execTaskSetting.getExecItemCode(), null));
	}

	private void processUpdate(UpdateProcessAutoExecution procExec, boolean enabledSetting) {
		String cid = AppContexts.user().companyId();
		String execItemCd = procExec.getExecItemCode().v();
		// ドメインモデル「更新処理自動実行管理」を取得し、現在の実行状態を判断する
		Optional<ProcessExecutionLogManage> optLogMng = this.processExecutionLogManageRepository
				.getLogByCIdAndExecCd(cid, execItemCd);
		optLogMng.ifPresent(logManage -> {
			// 更新処理自動実行管理.現在の実行状態 ＝ 実行中
			if (logManage.getCurrentStatus().isPresent()
					&& logManage.getCurrentStatus().get().equals(CurrentExecutionStatus.RUNNING)) {
				// エラーメッセージ「#Msg_1318」を表示する
				throw new BusinessException("Msg_1318");
			}
			// ドメインモデル「実行タスク設定」があるかチェックする
			Optional<ExecutionTaskSetting> optExecTaskSetting = this.executionTaskSettingRepository
					.getByCidAndExecCd(cid, execItemCd);
			optExecTaskSetting.ifPresent(execTaskSetting -> {
				// Update 実行タスク設定
				this.updateExecutionTaskSetting(cid, execItemCd, enabledSetting, execTaskSetting);
				// ドメインモデル「更新処理自動実行管理」を更新する
				logManage.setCurrentStatus(enabledSetting ? CurrentExecutionStatus.WAITING
						: CurrentExecutionStatus.INVALID);
				this.processExecutionLogManageRepository.update(logManage);
			});
			// ドメインモデル「更新処理自動実行」に更新登録する
			this.processExecutionRepository.update(procExec);
		});
	}

	private void updateExecutionTaskSetting(String cid, String execItemCd, boolean enabledSetting,
			ExecutionTaskSetting execTaskSetting) {
		ChangeExecutionTaskSettingCommand command = ChangeExecutionTaskSettingCommand.builder()
				.april(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getApril().equals(NotUseAtr.USE)).orElse(false))
				.august(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getAugust().equals(NotUseAtr.USE)).orElse(false))
				.companyId(cid)
				.december(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getDecember().equals(NotUseAtr.USE)).orElse(false))
				.enabledSetting(enabledSetting).endDate(execTaskSetting.getEndDate().getEndDate().orElse(null))
				.endDateCls(execTaskSetting.getEndDate().getEndDateCls().value)
				.endScheduleId(execTaskSetting.getEndScheduleId().orElse(null))
				.endTime(execTaskSetting.getEndTime().getEndTime().map(EndTime::v).orElse(null))
				.endTimeCls(execTaskSetting.getEndTime().getEndTimeCls().value).execItemCd(execItemCd)
				.february(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getFebruary().equals(NotUseAtr.USE)).orElse(false))
				.friday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getFriday().equals(NotUseAtr.USE)).orElse(false))
				.january(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getJanuary().equals(NotUseAtr.USE)).orElse(false))
				.july(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getJuly().equals(NotUseAtr.USE)).orElse(false))
				.june(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getJune().equals(NotUseAtr.USE)).orElse(false))
				.march(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getMarch().equals(NotUseAtr.USE)).orElse(false))
				.may(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getMay().equals(NotUseAtr.USE)).orElse(false))
				.monday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getMonday().equals(NotUseAtr.USE)).orElse(false))
				.nextExecDateTime(execTaskSetting.getNextExecDateTime().orElse(null))
				.november(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getNovember().equals(NotUseAtr.USE)).orElse(false))
				.october(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getOctober().equals(NotUseAtr.USE)).orElse(false))
				.oneDayRepClassification(execTaskSetting.getOneDayRepInr().getOneDayRepCls().value)
				.oneDayRepInterval(execTaskSetting.getOneDayRepInr().getDetail().map(data -> data.value).orElse(null))
				.repeatContent(execTaskSetting.getContent().value)
				.repeatMonthDateList(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getDays().stream().map(date -> date.value).collect(Collectors.toList()))
						.orElse(Collections.emptyList()))
				.repeatScheduleId(execTaskSetting.getRepeatScheduleId().orElse(null))
				.saturday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getSaturday().equals(NotUseAtr.USE)).orElse(false))
				.scheduleId(execTaskSetting.getScheduleId())
				.september(execTaskSetting.getDetailSetting().getMonthly()
						.map(data -> data.getMonth().getSeptember().equals(NotUseAtr.USE)).orElse(false))
				.startDate(execTaskSetting.getStartDate()).startTime(execTaskSetting.getStartTime().v())
				.sunday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getSunday().equals(NotUseAtr.USE)).orElse(false))
				.thursday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getThursday().equals(NotUseAtr.USE)).orElse(false))
				.tuesday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getTuesday().equals(NotUseAtr.USE)).orElse(false))
				.wednesday(execTaskSetting.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getWednesday().equals(NotUseAtr.USE)).orElse(false))
				.build();
		// 変更後のドメインモデル「実行タスク設定」を更新する
		this.changeExecutionTaskSettingCommandHandler.handle(command);
	}

}
