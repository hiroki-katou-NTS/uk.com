package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.RepeatMonthDayRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.OneDayRepeatInterval;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.RepeatDetailSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.DailyDaySetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingDaily;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingMonthly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingWeekly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatWeekDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.WeeklyWeekSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.OneDayRepeatIntervalDetail;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveExecutionTaskSettingCommandHandler extends CommandHandlerWithResult<SaveExecutionTaskSettingCommand, String> {

	@Inject
	private ExecutionTaskSettingRepository execTaskSettingRepo;
	
	@Inject
	private RepeatMonthDayRepository repMonthDayRepo;

	@Override
	protected String handle(CommandHandlerContext<SaveExecutionTaskSettingCommand> context) {
		SaveExecutionTaskSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// 終了時刻 
		TaskEndTime endTime =
				new TaskEndTime(
						EnumAdaptor.valueOf(command.getEndTimeCls(), EndTimeClassification.class),
						command.getEndTime() == null ? null : new EndTime(command.getEndTime()));
				
		// 繰り返し間隔
		OneDayRepeatInterval oneDayRepInr =
				new OneDayRepeatInterval(
						command.getOneDayRepInterval() == null ? null : new OneDayRepeatIntervalDetail(command.getOneDayRepInterval()),
						EnumAdaptor.valueOf(command.getOneDayRepCls(), OneDayRepeatClassification.class));
		
		// 終了日日付指定
		TaskEndDate endDate = new TaskEndDate(EnumAdaptor.valueOf(command.getEndDateCls(), EndDateClassification.class), command.getEndDate());
		
		// 繰り返し詳細設定(毎日)
		RepeatDetailSettingDaily daily = new RepeatDetailSettingDaily(new DailyDaySetting(command.getRepIntervalDay()));
		
		// 繰り返し詳細設定(毎週)
		RepeatDetailSettingWeekly weekly =
				new RepeatDetailSettingWeekly(
						new RepeatWeekDaysSelect(command.isMonday(), command.isTuesday(), command.isWednesday(),
												 command.isThursday(), command.isFriday(), command.isSaturday(), command.isSunday()),
						new WeeklyWeekSetting(command.getRepIntervalWeek()));
		
		// 繰り返し詳細設定(毎月)
		List<RepeatMonthDaysSelect> days =
				command.getRepeatMonthDateList()
						.stream()
							.map(x -> EnumAdaptor.valueOf(x, RepeatMonthDaysSelect.class))
								.collect(Collectors.toList());
		RepeatMonthSelect months =
				new RepeatMonthSelect(command.isJanuary(), command.isFebruary(), command.isMarch(),
										command.isApril(), command.isMay(), command.isJune(),
										command.isJuly(), command.isAugust(), command.isSeptember(),
										command.isOctober(), command.isNovember(), command.isDecember());
		RepeatDetailSettingMonthly monthly = new RepeatDetailSettingMonthly(days, months);
		
		// 繰り返し詳細設定
		RepeatDetailSetting detailSetting = new RepeatDetailSetting(daily, weekly, monthly);
		
		ExecutionTaskSetting taskSetting = new ExecutionTaskSetting(oneDayRepInr,
									new ExecutionCode(command.getExecItemCd()),
									companyId,
									command.isEnabledSetting(),
									null, 
									endDate,
									endTime,
									command.isRepeatCls(),
									EnumAdaptor.valueOf(command.getRepeatContent(), RepeatContentItem.class),
									detailSetting,
									command.getStartDate(),
									new StartTime(command.getStartTime()));
		
		// Calculate next execution date time
		taskSetting.setNextExecDateTime();
		
		// 登録チェック処理
		taskSetting.validate();
		if (command.isNewMode()) {
			this.execTaskSettingRepo.insert(taskSetting);
		} else {
			this.repMonthDayRepo.removeAllByCidAndExecCd(companyId, command.getExecItemCd());
			this.execTaskSettingRepo.update(taskSetting);
			this.repMonthDayRepo.insert(companyId, command.getExecItemCd(), days);
		}
		return taskSetting.getExecItemCd().v();
	}
}
