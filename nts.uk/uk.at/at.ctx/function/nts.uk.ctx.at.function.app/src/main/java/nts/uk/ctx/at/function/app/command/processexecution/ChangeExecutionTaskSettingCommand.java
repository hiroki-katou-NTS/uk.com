package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.OneDayRepeatInterval;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.RepeatDetailSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.TaskEndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingMonthly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingWeekly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatWeekDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.OneDayRepeatIntervalDetail;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;

@Data
public class ChangeExecutionTaskSettingCommand {

	/* 会社ID */
	private String companyId;

	/* コード */
	private String execItemCd;

	/* 更新処理有効設定 */
	private boolean enabledSetting;

	/* 1日の繰り返し間隔.繰り返し間隔 */
	private Integer oneDayRepInterval;

	/* 1日の繰り返し間隔.指定区分 */
	private Integer oneDayRepClassification;

	/* 次回実行日時 */
	private GeneralDateTime nextExecDateTime;

	/* 終了日.終了日日付指定 */
	private GeneralDate endDate;

	/* 終了日.実行タスク終了日区分 */
	private Integer endDateCls;

	/* 終了時刻.終了時刻 */
	private Integer endTime;

	/* 終了時刻.実行タスク終了時刻ありなし区分 */
	private Integer endTimeCls;

	/* 繰り返し内容 */
	private Integer repeatContent;

	/* 開始日 */
	private GeneralDate startDate;

	/* 開始時刻 */
	private Integer startTime;

	/* スケジュールID */
	private String scheduleId;

	/* 終了処理スケジュールID */
	private String endScheduleId;

	/* 日 */
	private boolean monday;

	/* 月 */
	private boolean tuesday;

	/* 火 */
	private boolean wednesday;

	/* 水 */
	private boolean thursday;

	/* 木 */
	private boolean friday;

	/* 金 */
	private boolean saturday;

	/* 土 */
	private boolean sunday;

	/* 1月 */
	private boolean january;

	/* 2月 */
	private boolean february;

	/* 3月 */
	private boolean march;

	/* 4月 */
	private boolean april;

	/* 5月 */
	private boolean may;

	/* 6月 */
	private boolean june;

	/* 7月 */
	private boolean july;

	/* 8月 */
	private boolean august;

	/* 9月 */
	private boolean september;

	/* 10月 */
	private boolean october;

	/* 11月 */
	private boolean november;

	/* 12月 */
	private boolean december;

	private List<Integer> repeatMonthDateList;

	public ExecutionTaskSetting toDomain() {
		List<RepeatMonthDaysSelect> days = repeatMonthDateList.stream()
				.map(x -> EnumAdaptor.valueOf(x, RepeatMonthDaysSelect.class)).collect(Collectors.toList());
		RepeatMonthSelect months = new RepeatMonthSelect(january, february, march, april, may, june, july, august,
				september, october, november, december);
		return ExecutionTaskSetting.builder().companyId(companyId)
				.content(EnumAdaptor.valueOf(repeatContent, RepeatContentItem.class))
				.detailSetting(
						new RepeatDetailSetting(
								new RepeatDetailSettingWeekly(new RepeatWeekDaysSelect(monday, tuesday, wednesday,
										thursday, friday, saturday, sunday)),
								new RepeatDetailSettingMonthly(days, months)))
				.enabledSetting(enabledSetting)
				.endDate(new TaskEndDate(EnumAdaptor.valueOf(endDateCls, EndDateClassification.class), endDate))
				.endScheduleId(Optional.ofNullable(endScheduleId))
				.endTime(new TaskEndTime(EnumAdaptor.valueOf(endTimeCls, EndTimeClassification.class),
						new EndTime(endTime)))
				.execItemCd(new ExecutionCode(execItemCd)).nextExecDateTime(
						Optional.empty())
				.oneDayRepInr(new OneDayRepeatInterval(
						oneDayRepInterval == null ? null
								: EnumAdaptor.valueOf(oneDayRepInterval, OneDayRepeatIntervalDetail.class),
						EnumAdaptor.valueOf(oneDayRepClassification, OneDayRepeatClassification.class)))
				.scheduleId(scheduleId).startDate(startDate).startTime(new StartTime(startTime)).build();
	}

	public SaveExecutionTaskSettingCommand convertToSaveCommand() {
		return SaveExecutionTaskSettingCommand.builder().april(april).august(august).companyId(companyId)
				.december(december).enabledSetting(enabledSetting).endDate(endDate).endDateCls(endDateCls)
				.endScheduleId(endScheduleId).endTime(endTime).endTimeCls(endTimeCls).execItemCd(execItemCd)
				.february(february).friday(friday).january(january).july(july).june(june).march(march).may(may)
				.monday(monday).newMode(false).november(november).october(october).oneDayRepCls(oneDayRepClassification)
				.oneDayRepInterval(oneDayRepInterval).repeatCls(true).repeatContent(repeatContent)
				.repeatMonthDateList(repeatMonthDateList).saturday(saturday).scheduleId(endScheduleId)
				.september(september).startDate(startDate).startTime(startTime).sunday(sunday).thursday(thursday)
				.tuesday(tuesday).wednesday(wednesday).build();
	}
}
