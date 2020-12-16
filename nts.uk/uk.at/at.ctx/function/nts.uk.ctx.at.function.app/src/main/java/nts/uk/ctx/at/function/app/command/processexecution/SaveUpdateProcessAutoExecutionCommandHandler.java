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
		UpdateProcessAutoExecution domain = UpdateProcessAutoExecution.createFromMemento(AppContexts.user().companyId(),
				command);
		// アルゴリズム「登録チェック処理」を実行する
		domain.validate();
		// 画面モードチェック
		if (command.isNewMode()) {
			// 新規モード
			// 更新処理自動実行項目コードは重複してはならない
			// #Msg_3
			if (this.processExecutionRepository
					.getProcessExecutionByCidAndExecCd(AppContexts.user().companyId(), domain.getExecItemCode().v())
					.isPresent()) {
				throw new BusinessException("Msg_3");
			}
			// アルゴリズム「新規登録処理」を実行する
			this.processRegister(domain);
		} else {
			// 更新モード
			this.processUpdate(domain, command.isEnabledSetting());
		}
	}

	private void processRegister(UpdateProcessAutoExecution domain) {
		String cid = AppContexts.user().companyId();
		// ドメインモデル「更新処理自動実行」に新規登録する
		this.processExecutionRepository.insert(domain);
		// ドメインモデル「更新処理自動実行管理」に新規登録する
		this.processExecutionLogManageRepository.insert(ProcessExecutionLogManage.builder().companyId(cid)
				.currentStatus(Optional.of(CurrentExecutionStatus.INVALID)).execItemCd(domain.getExecItemCode())
				.overallStatus(Optional.of(EndStatus.NOT_IMPLEMENT)).build());
		// ドメインモデル「更新処理前回実行日時」に新規登録する
		this.lastExecDateTimeRepository.insert(new LastExecDateTime(cid, domain.getExecItemCode(), null));
	}

	private void processUpdate(UpdateProcessAutoExecution domain, boolean enabledSetting) {
		String cid = AppContexts.user().companyId();
		String execItemCd = domain.getExecItemCode().v();
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
			// Update 実行タスク設定
			this.updateExecutionTaskSetting(cid, execItemCd, enabledSetting);
			// ドメインモデル「更新処理自動実行」に更新登録する
			this.processExecutionRepository.update(domain);
		});
	}

	private void updateExecutionTaskSetting(String cid, String execItemCd, boolean enabledSetting) {
		// ドメインモデル「実行タスク設定」があるかチェックする
		Optional<ExecutionTaskSetting> optExecTaskSetting = this.executionTaskSettingRepository.getByCidAndExecCd(cid,
				execItemCd);
		optExecTaskSetting.ifPresent(domain -> {
			ChangeExecutionTaskSettingCommand command = ChangeExecutionTaskSettingCommand.builder()
					.april(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getApril().equals(NotUseAtr.USE)).orElse(false))
					.august(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getAugust().equals(NotUseAtr.USE)).orElse(false))
					.companyId(cid)
					.december(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getDecember().equals(NotUseAtr.USE)).orElse(false))
					.enabledSetting(enabledSetting).endDate(domain.getEndDate().getEndDate().orElse(null))
					.endDateCls(domain.getEndDate().getEndDateCls().value)
					.endScheduleId(domain.getEndScheduleId().orElse(null))
					.endTime(domain.getEndTime().getEndTime().map(EndTime::v).orElse(null))
					.endTimeCls(domain.getEndTime().getEndTimeCls().value).execItemCd(execItemCd)
					.february(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getFebruary().equals(NotUseAtr.USE)).orElse(false))
					.friday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getFriday().equals(NotUseAtr.USE)).orElse(false))
					.january(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getJanuary().equals(NotUseAtr.USE)).orElse(false))
					.july(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getJuly().equals(NotUseAtr.USE)).orElse(false))
					.june(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getJune().equals(NotUseAtr.USE)).orElse(false))
					.march(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getMarch().equals(NotUseAtr.USE)).orElse(false))
					.may(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getMay().equals(NotUseAtr.USE)).orElse(false))
					.monday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getMonday().equals(NotUseAtr.USE)).orElse(false))
					.nextExecDateTime(domain.getNextExecDateTime().orElse(null))
					.november(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getNovember().equals(NotUseAtr.USE)).orElse(false))
					.october(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getOctober().equals(NotUseAtr.USE)).orElse(false))
					.oneDayRepClassification(domain.getOneDayRepInr().getOneDayRepCls().value)
					.oneDayRepInterval(domain.getOneDayRepInr().getDetail().map(data -> data.value).orElse(null))
					.repeatContent(domain.getContent().value)
					.repeatMonthDateList(domain.getDetailSetting().getMonthly()
							.map(data -> data.getDays().stream().map(date -> date.value).collect(Collectors.toList()))
							.orElse(Collections.emptyList()))
					.saturday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getSaturday().equals(NotUseAtr.USE)).orElse(false))
					.scheduleId(domain.getScheduleId())
					.september(domain.getDetailSetting().getMonthly()
							.map(data -> data.getMonth().getSeptember().equals(NotUseAtr.USE)).orElse(false))
					.startDate(domain.getStartDate()).startTime(domain.getStartTime().v())
					.sunday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getSunday().equals(NotUseAtr.USE)).orElse(false))
					.thursday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getThursday().equals(NotUseAtr.USE)).orElse(false))
					.tuesday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getTuesday().equals(NotUseAtr.USE)).orElse(false))
					.wednesday(domain.getDetailSetting().getWeekly()
							.map(data -> data.getWeekdaySetting().getWednesday().equals(NotUseAtr.USE)).orElse(false))
					.build();
			this.changeExecutionTaskSettingCommandHandler.handle(command);
		});
	}

}
