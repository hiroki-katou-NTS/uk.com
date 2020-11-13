package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

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
	
	/* 終了処理スケジュールID*/
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
	
	public static ExecutionTaskSettingDto fromDomain(ExecutionTaskSetting domain) {
		List<Integer> repeatMonthDateList =
				domain.getDetailSetting().getMonthly().getDays()
							.stream().map(x -> x.value).collect(Collectors.toList());
		return new ExecutionTaskSettingDto(domain.getCompanyId(),
											domain.getExecItemCd().v(),
											domain.isEnabledSetting(),
											domain.getOneDayRepInr().getDetail().map(o -> o.value).orElse(null),
											domain.getOneDayRepInr().getOneDayRepCls().value,
											domain.getNextExecDateTime().orElse(null),
											domain.getEndDate().getEndDate(),
											domain.getEndDate().getEndDateCls().value,
											domain.getEndTime().getEndTime() == null ? null : domain.getEndTime().getEndTime().v(),
											domain.getEndTime().getEndTimeCls().value,
											domain.getContent().value,
											domain.getStartDate(),
											domain.getStartTime().v(),
											domain.getScheduleId(),
											domain.getEndScheduleId().orElse(null),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isMonday(),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isTuesday(),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isWednesday(),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isThursday(),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isFriday(),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isSaturday(),
											domain.getDetailSetting().getWeekly().getWeekdaySetting().isSunday(),
											domain.getDetailSetting().getMonthly().getMonth().isJanuary(),
											domain.getDetailSetting().getMonthly().getMonth().isFebruary(),
											domain.getDetailSetting().getMonthly().getMonth().isMarch(),
											domain.getDetailSetting().getMonthly().getMonth().isApril(),
											domain.getDetailSetting().getMonthly().getMonth().isMay(),
											domain.getDetailSetting().getMonthly().getMonth().isJune(),
											domain.getDetailSetting().getMonthly().getMonth().isJuly(),
											domain.getDetailSetting().getMonthly().getMonth().isAugust(),
											domain.getDetailSetting().getMonthly().getMonth().isSeptember(),
											domain.getDetailSetting().getMonthly().getMonth().isOctober(),
											domain.getDetailSetting().getMonthly().getMonth().isNovember(),
											domain.getDetailSetting().getMonthly().getMonth().isDecember(),
											repeatMonthDateList);
	}
}
