package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;

@Data
@AllArgsConstructor
public class ExecutionTaskSettingDto {
	
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private String execItemCd;
	
	/* 開始日 */
	private GeneralDate startDate;
	
	/* 開始時刻 */
	private Integer startTime;
	
	/* 実行タスク終了時刻設定 */
	private Integer endTimeCls;
	
	/* 終了時刻 */
	private Integer endTime;
	
	/* 実行タスク終了時刻設定 */
	private Integer oneDayRepCls;
	
	/* 繰り返し間隔 */
	private Integer oneDayRepInterval;
	
	/* 繰り返しする */
	private boolean repeatCls;
	
	/* 繰り返し内容 */
	private Integer repeatContent;
	
	/* 実行タスク終了日区分 */
	private Integer endDateCls;
	
	/* 終了日日付指定 */
	private GeneralDate endDate;
	
	/* 更新処理有効設定 */
	private boolean enabledSetting;
	
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
											domain.getStartDate(),
											domain.getStartTime().v(),
											domain.getEndTime().getEndTimeCls().value,
											domain.getEndTime().getEndTime() == null ? null : domain.getEndTime().getEndTime().v(),
											domain.getOneDayRepInr().getOneDayRepCls().value,
											domain.getOneDayRepInr().getDetail() == null||!domain.getOneDayRepInr().getDetail().isPresent() ? null : domain.getOneDayRepInr().getDetail().get().value,
											domain.isRepeat(),
											domain.getContent().value,
											domain.getEndDate().getEndDateCls().value,
											domain.getEndDate().getEndDate(),
											domain.isEnabledSetting(),
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
