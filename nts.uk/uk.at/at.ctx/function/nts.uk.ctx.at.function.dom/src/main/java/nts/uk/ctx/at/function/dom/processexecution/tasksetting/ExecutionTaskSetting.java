package nts.uk.ctx.at.function.dom.processexecution.tasksetting;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
//import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;

/**
 * 実行タスク設定
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
public class ExecutionTaskSetting extends AggregateRoot {
	final static String SPACE = " ";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

	/* 会社ID */
	private String companyId;

	/* コード */
	private ExecutionCode execItemCd;

	/* 更新処理有効設定 */
	private boolean enabledSetting;

	/* 1日の繰り返し間隔 */
	private OneDayRepeatInterval oneDayRepInr;

	/* 次回実行日時 */
	private Optional<GeneralDateTime> nextExecDateTime;

	/* 終了日 */
	private TaskEndDate endDate;

	/* 終了時刻 */
	private TaskEndTime endTime;

	/* 繰り返し内容 */
	private RepeatContentItem content;

	/* 繰り返し詳細設定 */
	private RepeatDetailSetting detailSetting;

	/* 開始日 */
	private GeneralDate startDate;

	/* 開始時刻 */
	private StartTime startTime;

	/* スケジュールID */
	private String scheduleId;

	/* 終了処理スケジュールID */
	private Optional<String> endScheduleId;

	@Override
	public void validate() {
		// 「実行タスク設定．繰り返し内容 = 曜日指定」の場合、
		// 「実行タスク設定．繰り返し詳細設定．繰り返し詳細設定(毎週)．曜日」のいずれかが「する」でなければならない。
		// #Msg_842
		if (this.getContent().equals(RepeatContentItem.SPECIFIED_WEEK_DAYS)) {
			if (!this.getDetailSetting().getWeekly().getWeekdaySetting().isCheckedAtLeastOne()) {
				throw new BusinessException("Msg_842");
			}
		}
		// 「実行タスク設定．繰り返し内容 = 月日指定」の場合、
		// 「実行タスク設定．繰り返し詳細設定．繰り返し詳細設定(毎月)．月」のいずれかが「する」でなければならない。
		// #Msg_843
		if (this.getContent().equals(RepeatContentItem.SPECIFIED_IN_MONTH)) {
			if (!this.getDetailSetting().getMonthly().getMonth().isCheckedAtLeastOne()) {
				throw new BusinessException("Msg_843");
			}
		}
		// 「実行タスク設定．1日の繰り返し間隔．指定区分 = あり」 かつ「実行タスク設定．終了時刻設定．指定区分 = あり」の場合、
		// 「実行タスク設定．1日の繰り返し間隔．繰り返し間隔」 >=
		// 「実行タスク設定．開始時刻～実行タスク設定．終了時刻設定．終了時刻」の時間を登録することはできない。
		// #Msg_848
		if (this.getOneDayRepInr().getOneDayRepCls().equals(OneDayRepeatClassification.YES)
				&& this.getEndTime().getEndTimeCls().equals(EndTimeClassification.YES)) {
			int distance = this.getEndTime().getEndTime().valueAsMinutes() - this.getStartTime().valueAsMinutes();
			if (this.getOneDayRepInr().getDetail().isPresent()
					&& this.getOneDayRepInr().getDetail().get().getMinuteValue() >= distance) {
				throw new BusinessException("Msg_848");
			}
		}
		// 「実行タスク設定．繰り返し内容 = 月日指定」の場合、
		// 「実行タスク設定．繰り返し詳細設定．繰り返し詳細設定(毎月)．日」<>emptyでなければならない
		// #Msg_846
		if (this.getContent().equals(RepeatContentItem.SPECIFIED_IN_MONTH)) {
			if (this.getDetailSetting().getMonthly().getDays().isEmpty()) {
				throw new BusinessException("Msg_846");
			}
		}
		// 「実行タスク設定．繰り返し内容 = 月日指定」の場合、
		// 「実行タスク設定．繰り返し詳細設定．繰り返し詳細設定(毎月)．月 + 日」が「実行タスク設定．開始日 ～
		// 実行タスク設定．終了日．終了日」の範囲内でなければならない。
		// #Msg_1266
		if (this.getContent().equals(RepeatContentItem.SPECIFIED_IN_MONTH)) {
			List<Integer> days = this.getDetailSetting().getMonthly().getDays().stream().map(item -> item.value)
					.collect(Collectors.toList());
			List<Integer> months = this.getDetailSetting().getMonthly().getMonth().getMonthsAfterEqualsStartMonth(1);
			DatePeriod range = new DatePeriod(this.getStartDate(), this.getEndDate().getEndDate());
			int currentYear = GeneralDateTime.now().year();
			boolean isValid = true;
			for (int day : days) {
				for (int month : months) {
					GeneralDate dateToCompare = GeneralDate.ymd(currentYear, month, day);
					if (!range.contains(dateToCompare)) {
						isValid = false;
						break;
					}
				}
				if (!isValid) {
					throw new BusinessException("Msg_1266");
				}
			}
		}
		// 「実行タスク設定．終了時刻設定．指定区分 = あり」の場合、
		// 「実行タスク設定．開始時刻」 > 「実行タスク設定．終了時刻設定．終了時刻」を登録することはできない。
		// #Msg_849
		if (this.getEndTime().getEndTimeCls().equals(EndTimeClassification.YES)) {
			if (this.getStartTime().greaterThan(this.getEndTime().getEndTime())) {
				throw new BusinessException("Msg_849");
			}
		}
		// 「実行タスク設定．終了日．終了日区分 = 日付指定」の場合、
		// 「実行タスク設定．開始日 >= 実行タスク設定．終了日．終了日」を登録することはできない。
		// #Msg_662
		if (this.getEndDate().getEndDateCls().equals(EndDateClassification.DATE)) {
			if (this.getStartDate().after(this.getEndDate().getEndDate())) {
				throw new BusinessException("Msg_662");
			}
		}
	}

	/**
	 * 次回実行日時の求め
	 */
	public void setNextExecDateTime() {
		GeneralDateTime now = GeneralDateTime.now();
		if (!this.isEnabledSetting()) {
			this.nextExecDateTime = null;
		}

		/*
		 * ◆◆事後条件 ・設定された次回実行日時が終了日を過ぎていたら次回実行日時 ＝ NULL とする
		 */
		if (this.nextExecDateTime != null && this.nextExecDateTime.isPresent()
				&& this.nextExecDateTime.get().before(now)) {
			this.nextExecDateTime = null;
		}
	}

	private GeneralDateTime buildGeneralDateTime(GeneralDate date) {
		return GeneralDateTime.fromString(date.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT)
				.addMinutes(this.startTime.valueAsMinutes());
	}

	/**
	 * 毎日のパターン
	 * 
	 * @param startDate
	 * @param today
	 * @param now
	 * @return 次回実行日時
	 */
	private GeneralDateTime getNextExecDateTimeByDay(GeneralDate today, GeneralDateTime now) {
		/*
		 * //GeneralDateTime tempDateTime = this.buildGeneralDateTime(this.startDate);
		 * GeneralDateTime tempDateTime =
		 * GeneralDateTime.legacyDateTime(this.startDate.date()); if
		 * (nextExecDateTime.before(now)) { /*tempDateTime =
		 * this.buildGeneralDateTime(today); if (tempDateTime.before(now)) {
		 * tempDateTime = tempDateTime.addDays(1); }
		 * 
		 * tempDateTime = GeneralDateTime.ymdhms(nextExecDateTime.year(),
		 * nextExecDateTime.month(), nextExecDateTime.day()+1, 0, 0,
		 * 0).addMinutes(this.startTime.minute()); };
		 */
		GeneralDateTime startDateTime = GeneralDateTime.legacyDateTime(this.startDate.date());
		GeneralDateTime tempDateTime = GeneralDateTime.ymdhms(this.startDate.year(), this.startDate.month(),
				this.startDate.day(), this.startTime.hour(), this.startTime.minute(), 0);
		if (startDateTime.before(now)) {
			if (this.startTime.hour() * 60 + this.startTime.minute() < now.hours() * 60 + now.minutes()) {
				tempDateTime = GeneralDateTime.ymdhms(now.year(), now.month(), now.day() + 1, this.startTime.hour(),
						this.startTime.minute(), 0);
			} else {
				tempDateTime = GeneralDateTime.ymdhms(now.year(), now.month(), now.day(), this.startTime.hour(),
						this.startTime.minute(), 0);
			}

		}

		return tempDateTime;
	}

	/**
	 * 毎週のパターン
	 * 
	 * @param startDate
	 * @param now
	 * @return 次回実行日時
	 */
	private GeneralDateTime getNextExecDateTimeByWeek(GeneralDate startDate, GeneralDateTime now) {
		// get first day of week (sunday)
		LocalDate sunday = startDate.localDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

		// get 7 weekdays from the sunday to saturday
		List<LocalDate> allWeekDays = IntStream.range(0, 7).mapToObj(sunday::plusDays).collect(Collectors.toList());

		// get picked weekdays from setting screen
		List<DayOfWeek> pickedWeekDays = this.detailSetting.getWeekly().getWeekdaySetting().setWeekDaysList();
		GeneralDateTime startDateTime = this.buildGeneralDateTime(this.startDate);

		GeneralDateTime tempDateTime = null;
		for (LocalDate date : allWeekDays) {
			// if current date is the weekday checked from screen
			if (pickedWeekDays.contains(date.getDayOfWeek())) {
				tempDateTime = this.buildGeneralDateTime(GeneralDate.localDate(date));
				// if the date time is after both start date time and now then break loop and
				// assign to the next date time
				if (tempDateTime.after(now) && tempDateTime.afterOrEquals(startDateTime)) {
					break;
				}
			}
		}
		// If next exec date time is still before now, then try to find after add
		// interval week
		if (tempDateTime.before(now)) {
			GeneralDate nextStartDate = GeneralDate.localDate(startDate.localDate().plusWeeks(1));
			tempDateTime = getNextExecDateTimeByWeek(nextStartDate, now);
		}
		return tempDateTime;
	}

	/**
	 * 毎月のパターン
	 * 
	 * @param startDate
	 * @param now
	 * @return 次回実行日時
	 */
	private GeneralDateTime getNextExecDateTimeByMonth(GeneralDate startDate, GeneralDateTime now) {
		GeneralDateTime tempDateTime = null;
		GeneralDateTime startDateTime = this.buildGeneralDateTime(this.startDate);
		List<Integer> pickedMonths = this.detailSetting.getMonthly().getMonth()
				.getMonthsAfterEqualsStartMonth(startDate.month());
		List<RepeatMonthDaysSelect> pickedDates = this.detailSetting.getMonthly().getDays();
		for (Integer month : pickedMonths) {
			// init date by first day of current month
			tempDateTime = this.buildGeneralDateTime(GeneralDate.ymd(startDate.year(), month, 1));
			for (RepeatMonthDaysSelect day : pickedDates) {
				try {
					if (day.value == RepeatMonthDaysSelect.LAST_DAY.value) {
						tempDateTime = this.buildGeneralDateTime(
								GeneralDate.ymd(startDate.year(), month, tempDateTime.toLocalDate().lengthOfMonth()));
					} else {
						tempDateTime = this.buildGeneralDateTime(GeneralDate.ymd(startDate.year(), month, day.value));
					}
				} catch (DateTimeException dte) {
					tempDateTime = this.buildGeneralDateTime(
							GeneralDate.ymd(startDate.year(), month, tempDateTime.toLocalDate().lengthOfMonth()));
				}
				if (tempDateTime.after(now) && tempDateTime.afterOrEquals(startDateTime)) {
					return tempDateTime;
				}
			}
		}
		return tempDateTime;
	}

	public ExecutionTaskSetting(OneDayRepeatInterval oneDayRepInr, ExecutionCode execItemCd, String companyId,
			boolean enabledSetting, GeneralDateTime nextExecDateTime, TaskEndDate endDate, TaskEndTime endTime,
			RepeatContentItem content, RepeatDetailSetting detailSetting, GeneralDate startDate, StartTime startTime,
			String scheduleId, String endScheduleId) {
		super();
		this.oneDayRepInr = oneDayRepInr;
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.enabledSetting = enabledSetting;
		this.nextExecDateTime = Optional.ofNullable(nextExecDateTime);
		this.endDate = endDate;
		this.endTime = endTime;
		this.content = content;
		this.detailSetting = detailSetting;
		this.startDate = startDate;
		this.startTime = startTime;
		this.scheduleId = scheduleId;
		this.endScheduleId = Optional.ofNullable(endScheduleId);
	}

	public ExecutionTaskSetting(OneDayRepeatInterval oneDayRepInr, ExecutionCode execItemCd, String companyId,
			boolean enabledSetting, GeneralDateTime nextExecDateTime, TaskEndDate endDate, TaskEndTime endTime,
			RepeatContentItem content, RepeatDetailSetting detailSetting, GeneralDate startDate, StartTime startTime) {
		super();
		this.oneDayRepInr = oneDayRepInr;
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.enabledSetting = enabledSetting;
		this.nextExecDateTime = Optional.ofNullable(nextExecDateTime);
		this.endDate = endDate;
		this.endTime = endTime;
		this.content = content;
		this.detailSetting = detailSetting;
		this.startDate = startDate;
		this.startTime = startTime;
	}
}
