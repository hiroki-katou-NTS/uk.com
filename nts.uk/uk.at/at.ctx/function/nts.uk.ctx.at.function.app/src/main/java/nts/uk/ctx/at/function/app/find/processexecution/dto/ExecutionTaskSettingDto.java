package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatWeekDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;

@Data
@AllArgsConstructor
public class ExecutionTaskSettingDto {

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
	private Boolean monday;

	/* 月 */
	private Boolean tuesday;

	/* 火 */
	private Boolean wednesday;

	/* 水 */
	private Boolean thursday;

	/* 木 */
	private Boolean friday;

	/* 金 */
	private Boolean saturday;

	/* 土 */
	private Boolean sunday;

	/* 1月 */
	private Boolean january;

	/* 2月 */
	private Boolean february;

	/* 3月 */
	private Boolean march;

	/* 4月 */
	private Boolean april;

	/* 5月 */
	private Boolean may;

	/* 6月 */
	private Boolean june;

	/* 7月 */
	private Boolean july;

	/* 8月 */
	private Boolean august;

	/* 9月 */
	private Boolean september;

	/* 10月 */
	private Boolean october;

	/* 11月 */
	private Boolean november;

	/* 12月 */
	private Boolean december;

	private List<Integer> repeatMonthDateList;

	private String trueDayString;
	
	private String trueMonthlyMonthString;
	
	private String trueMonthlyDayString;

	public static ExecutionTaskSettingDto fromDomain(ExecutionTaskSetting domain) {
		List<Integer> repeatMonthDateList = domain.getDetailSetting().getMonthly()
				.map(data -> data.getDays().stream().map(x -> x.value).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
		StringBuilder trueMonthlyDayString = new StringBuilder();
		for (int i = 0; i < repeatMonthDateList.size(); i++) {
			trueMonthlyDayString.append(repeatMonthDateList.get(i) + "日");
			if (i == repeatMonthDateList.size() - 1) {
				trueMonthlyDayString.append(", ");
			}
		}
		return new ExecutionTaskSettingDto(domain.getCompanyId(), domain.getExecItemCd().v(), domain.isEnabledSetting(),
				domain.getOneDayRepInr().getDetail().map(o -> o.value).orElse(null),
				domain.getOneDayRepInr().getOneDayRepCls().value, domain.getNextExecDateTime().orElse(null),
				domain.getEndDate().getEndDate().orElse(null), domain.getEndDate().getEndDateCls().value,
				domain.getEndTime().getEndTime().map(EndTime::v).orElse(null),
				domain.getEndTime().getEndTimeCls().value, domain.getContent().value, domain.getStartDate(),
				domain.getStartTime().v(), domain.getScheduleId(), domain.getEndScheduleId().orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getMonday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getTuesday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getWednesday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getThursday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getFriday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getSaturday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getWeekly().map(data -> data.getWeekdaySetting().getSunday())
						.map(RepeatWeekDaysSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getJanuary())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getFebruary())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getMarch())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getApril())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getMay())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getJune())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getJuly())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getAugust())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getSeptember())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getOctober())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getNovember())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getDecember())
						.map(RepeatMonthSelect::isTrue).orElse(null),
				repeatMonthDateList, domain.getDetailSetting().getWeekly()
						.map(data -> data.getWeekdaySetting().getTrueDayString()).orElse(null),
				domain.getDetailSetting().getMonthly().map(data -> data.getMonth().getTrueMonthString()).orElse(null),
				trueMonthlyDayString.toString());
	}
}
