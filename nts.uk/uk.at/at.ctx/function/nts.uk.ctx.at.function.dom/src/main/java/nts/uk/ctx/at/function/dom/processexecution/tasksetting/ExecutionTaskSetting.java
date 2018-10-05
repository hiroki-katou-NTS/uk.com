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
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.StartTime;

/**
 * 実行タスク設定
 */
@Getter
public class ExecutionTaskSetting extends AggregateRoot {
	final static String SPACE = " ";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	
	/* 1日の繰り返し間隔 */
	private OneDayRepeatInterval oneDayRepInr;
	
	/* コード */
	private ExecutionCode execItemCd;
	
	/* 会社ID */
	private String companyId;
	
	/* 更新処理有効設定 */
	private boolean enabledSetting;
	
	/* 次回実行日時 */
	private Optional<GeneralDateTime> nextExecDateTime;
	
	/* 終了日 */
	private TaskEndDate endDate;
	
	/* 終了時刻 */
	private TaskEndTime endTime;
	
	/* 繰り返しする */
	private boolean repeat;
	
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
	
	/* 終了処理スケジュールID*/
	private Optional<String> endScheduleId;
	

	public void validate() {
		if (startTime != null && endTime.getEndTime() != null) {
			// 画面項目「C2_6：終了時刻入力欄」に開始時刻より前の時刻を入力し、登録することはできない。
			if (endTime.getEndTime().lessThan(startTime)) {
				throw new BusinessException("Msg_849");
			}
			
			if (oneDayRepInr.getDetail() != null && oneDayRepInr.getDetail().isPresent()) {
				// 画面項目「C2_18：繰り返し間隔入力欄」に開始時刻～終了時刻以上の時間を入力し、登録することはできない
				
				int oneDayRepInrLoop = 1;
				switch (oneDayRepInr.getDetail().get().value) {
				case 0:
					oneDayRepInrLoop =1;
					break;
				case 1:
					oneDayRepInrLoop =5;
					break;
				case 2:
					oneDayRepInrLoop =10;
					break;
				case 3:
					oneDayRepInrLoop =15;
					break;
				case 4:
					oneDayRepInrLoop =30;
					break;
				case 5:
					oneDayRepInrLoop =60;
					break;	
				default:
					oneDayRepInrLoop = 1;
					break;
				}
				
				int totalMinuteEndTime	= endTime.getEndTime().hour()*60+ endTime.getEndTime().minute();
				int totalMinuteStartTime = startTime.hour()*60+startTime.minute();
				if (oneDayRepInrLoop > (totalMinuteEndTime  - totalMinuteStartTime)) {
					throw new BusinessException("Msg_848");
				}
			}
		}
		
		if (endDate.getEndDate() != null && startDate != null) {
			// 画面項目「C2_23：終了日入力欄」に開始日より前の日付を入力し、登録することはできない。
			if (endDate.getEndDate().before(startDate) || endDate.getEndDate().compareTo(startDate)==0 ) {
				throw new BusinessException("Msg_662");
			}
		}
		
		// 画面項目「C2_12：繰り返し内容リスト」が毎週のときC4_6~C4_12のチェックボックスのいずれかがTRUE状態でなければならない。
		if (content != null) {
			if (content.value == 1 && !this.isCheckedAtLeastOneWeekDay()) {
				throw new BusinessException("Msg_842");
			}
			// 画面項目「C2_12：画面項目内容リスト」が毎月のときC6_3~C6_14のチェックボックスのいずれかがTRUE状態でなければならない。
			else if (content.value == 2 && !this.isCheckedAtLeastOneMonthDay()) {
				throw new BusinessException("Msg_843");
			}
		}
	}
	
	private boolean isCheckedAtLeastOneWeekDay() {
		return detailSetting.getWeekly().getWeekdaySetting().isCheckedAtLeastOne();
	}
	
	private boolean isCheckedAtLeastOneMonthDay() {
		return detailSetting.getMonthly().getMonth().isCheckedAtLeastOne();
	}
	
	/**
	 * 次回実行日時の求め
	 */
	public void setNextExecDateTime() {
		GeneralDate today = GeneralDate.today();
		GeneralDateTime now = GeneralDateTime.now();
		if (this.isEnabledSetting()) {
			if (this.repeat) {
				// ・毎日のパターン
				if (this.content.value == RepeatContentItem.DAILY.value) {
					// 画面から実行タスク設定の登録時
					this.nextExecDateTime = Optional.ofNullable(getNextExecDateTimeByDay(today, now));
				}
				// ・毎週のパターン
				else if (this.content.value == RepeatContentItem.WEEKLY.value) {
					this.nextExecDateTime = Optional.ofNullable(getNextExecDateTimeByWeek(this.startDate, now));
				}
				// ・毎月のパターン
				else if (this.content.value == RepeatContentItem.MONTHLY.value) {
					this.nextExecDateTime = Optional.ofNullable(getNextExecDateTimeByMonth(this.startDate, now));
				}
			}
		} else {
			this.nextExecDateTime = null;
		}
		
		/*
		 * ◆◆事後条件
		 * ・設定された次回実行日時が終了日を過ぎていたら次回実行日時　＝　NULL　とする
		 */
		if (this.nextExecDateTime != null && this.nextExecDateTime.isPresent() && this.nextExecDateTime.get().before(now)) {
			this.nextExecDateTime = null;
		}
	}
	
	private GeneralDateTime buildGeneralDateTime(GeneralDate date) {
		return GeneralDateTime.fromString(date.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT)
				.addMinutes(this.startTime.valueAsMinutes());
	}
	
	/**
	 * 毎日のパターン
	 * @param startDate
	 * @param today
	 * @param now
	 * @return 次回実行日時
	 */
	private GeneralDateTime getNextExecDateTimeByDay( GeneralDate today, GeneralDateTime now) {
		/*
		//GeneralDateTime tempDateTime = this.buildGeneralDateTime(this.startDate);
		GeneralDateTime tempDateTime =  GeneralDateTime.legacyDateTime(this.startDate.date());
		if (nextExecDateTime.before(now)) {
			/*tempDateTime = this.buildGeneralDateTime(today);
			if (tempDateTime.before(now)) {
				tempDateTime = tempDateTime.addDays(1);
			}
			
			tempDateTime = GeneralDateTime.ymdhms(nextExecDateTime.year(), nextExecDateTime.month(), nextExecDateTime.day()+1, 0, 0, 0).addMinutes(this.startTime.minute());
		};
		*/
		GeneralDateTime startDateTime =  GeneralDateTime.legacyDateTime(this.startDate.date());
		GeneralDateTime tempDateTime = GeneralDateTime.ymdhms(this.startDate.year(),this.startDate.month(),this.startDate.day(),this.startTime.hour(),this.startTime.minute(),0);
		if(startDateTime.before(now)){
			if(this.startTime.hour()*60+this.startTime.minute()< now.hours()*60+now.minutes()){
				tempDateTime = GeneralDateTime.ymdhms(now.year(),now.month(),now.day()+1,this.startTime.hour(),this.startTime.minute(),0);
			}else{
				tempDateTime = GeneralDateTime.ymdhms(now.year(),now.month(),now.day(),this.startTime.hour(),this.startTime.minute(),0);
			}
			
		}
		
		return tempDateTime;
	}
	
	/**
	 * 毎週のパターン
	 * @param startDate
	 * @param now
	 * @return 次回実行日時
	 */
	private GeneralDateTime getNextExecDateTimeByWeek(GeneralDate startDate, GeneralDateTime now) {
		// get first day of week (sunday)
		LocalDate sunday = startDate.localDate()
	            						.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
	    
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
				// if the date time is after both start date time and now then break loop and assign to the next date time
				if (tempDateTime.after(now) && tempDateTime.afterOrEquals(startDateTime)) {
					break;
				}
			}
		}
		// If next exec date time is still before now, then try to find after add interval week
		if (tempDateTime.before(now)) {
			GeneralDate nextStartDate = GeneralDate.localDate(
					startDate.localDate().plusWeeks(1));
			tempDateTime = getNextExecDateTimeByWeek(nextStartDate, now);
		}
		return tempDateTime;
	}
	
	/**
	 * 毎月のパターン
	 * @param startDate
	 * @param now
	 * @return 次回実行日時
	 */
	private GeneralDateTime getNextExecDateTimeByMonth(GeneralDate startDate, GeneralDateTime now) {
		GeneralDateTime tempDateTime = null;
		GeneralDateTime startDateTime = this.buildGeneralDateTime(this.startDate);
		List<Integer> pickedMonths = this.detailSetting.getMonthly().getMonth().getMonthsAfterEqualsStartMonth(startDate.month());
		List<RepeatMonthDaysSelect> pickedDates = this.detailSetting.getMonthly().getDays();
		for (Integer month : pickedMonths) {
			// init date by first day of current month
			tempDateTime = this.buildGeneralDateTime(GeneralDate.ymd(startDate.year(), month, 1));
			for (RepeatMonthDaysSelect day : pickedDates) {
				try {
					if (day.value == RepeatMonthDaysSelect.DAY_32.value) {
						tempDateTime = this.buildGeneralDateTime(GeneralDate.ymd(startDate.year(), month, tempDateTime.toLocalDate().lengthOfMonth()));
					} else {
						tempDateTime = this.buildGeneralDateTime(GeneralDate.ymd(startDate.year(), month, day.value));
					}
				} catch (DateTimeException dte) {
					tempDateTime = this.buildGeneralDateTime(GeneralDate.ymd(startDate.year(), month, tempDateTime.toLocalDate().lengthOfMonth()));
				}
				if (tempDateTime.after(now) && tempDateTime.afterOrEquals(startDateTime)) {
					return tempDateTime;
				}
			}
		}
		return tempDateTime;
	}

	public ExecutionTaskSetting(OneDayRepeatInterval oneDayRepInr, ExecutionCode execItemCd, String companyId,
			boolean enabledSetting, GeneralDateTime nextExecDateTime, TaskEndDate endDate,
			TaskEndTime endTime, boolean repeat, RepeatContentItem content, RepeatDetailSetting detailSetting,
			GeneralDate startDate, StartTime startTime, String scheduleId, String endScheduleId) {
		super();
		this.oneDayRepInr = oneDayRepInr;
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.enabledSetting = enabledSetting;
		this.nextExecDateTime = Optional.ofNullable(nextExecDateTime);
		this.endDate = endDate;
		this.endTime = endTime;
		this.repeat = repeat;
		this.content = content;
		this.detailSetting = detailSetting;
		this.startDate = startDate;
		this.startTime = startTime;
		this.scheduleId = scheduleId;
		this.endScheduleId = Optional.ofNullable(endScheduleId);
	}
	public ExecutionTaskSetting(OneDayRepeatInterval oneDayRepInr, ExecutionCode execItemCd, String companyId,
			boolean enabledSetting, GeneralDateTime nextExecDateTime, TaskEndDate endDate,
			TaskEndTime endTime, boolean repeat, RepeatContentItem content, RepeatDetailSetting detailSetting,
			GeneralDate startDate, StartTime startTime) {
		super();
		this.oneDayRepInr = oneDayRepInr;
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.enabledSetting = enabledSetting;
		this.nextExecDateTime = Optional.ofNullable(nextExecDateTime);
		this.endDate = endDate;
		this.endTime = endTime;
		this.repeat = repeat;
		this.content = content;
		this.detailSetting = detailSetting;
		this.startDate = startDate;
		this.startTime = startTime;
	}

	public void setOneDayRepInr(OneDayRepeatInterval oneDayRepInr) {
		this.oneDayRepInr = oneDayRepInr;
	}

	public void setExecItemCd(ExecutionCode execItemCd) {
		this.execItemCd = execItemCd;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setEnabledSetting(boolean enabledSetting) {
		this.enabledSetting = enabledSetting;
	}

	public void setNextExecDateTime(GeneralDateTime nextExecDateTime) {
		this.nextExecDateTime = Optional.ofNullable(nextExecDateTime) ;
	}
	public void setNextExecDateTime(Optional<GeneralDateTime> nextExecDateTime) {
		this.nextExecDateTime = nextExecDateTime ;
	}

	public void setEndDate(TaskEndDate endDate) {
		this.endDate = endDate;
	}

	public void setEndTime(TaskEndTime endTime) {
		this.endTime = endTime;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void setContent(RepeatContentItem content) {
		this.content = content;
	}

	public void setDetailSetting(RepeatDetailSetting detailSetting) {
		this.detailSetting = detailSetting;
	}

	public void setStartDate(GeneralDate startDate) {
		this.startDate = startDate;
	}

	public void setStartTime(StartTime startTime) {
		this.startTime = startTime;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setEndScheduleId(String endScheduleId) {
		this.endScheduleId = Optional.ofNullable(endScheduleId) ;
	}
	
}
