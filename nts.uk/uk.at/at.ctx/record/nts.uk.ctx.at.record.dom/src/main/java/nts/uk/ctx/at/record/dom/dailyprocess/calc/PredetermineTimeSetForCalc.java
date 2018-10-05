package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間設定(計算用クラス)
 * @author ken_takasu
 *
 */
@Getter
public class PredetermineTimeSetForCalc {
	
	//時間帯(使用区分付き)
	private final List<TimezoneUse> timeSheets;
	
	//午前終了時刻
	private final TimeWithDayAttr AMEndTime;

	//午後開始時刻
	private final TimeWithDayAttr PMStartTime;
	
	//所定時間
	private PredetermineTime additionSet;
	
	//1日の計算範囲
	private AttendanceTime oneDayRange;
	
	//日付開始時間
	private TimeWithDayAttr startOneDayTime;
	
	//残業を含めた所定時間を設定する
	private boolean isIncludeOverTimePred = false;
	
	//夜勤区分
	private boolean nightWorkAtr = false;

	/**
	 * 所定時間帯の時間を更新する
	 * @param dateStartTime
	 * @param rangeTimeDay
	 * @param predetermineTimeSheet
	 * @param additionSet
	 * @param timeSheets
	 */
	public PredetermineTimeSetForCalc(
			List<TimezoneUse> timeSheets,
			TimeWithDayAttr AMEndTime,
			TimeWithDayAttr PMStartTime,
			PredetermineTime addtionSet,
            AttendanceTime oneDayRange,
			TimeWithDayAttr startOneDayTime) {
		this.timeSheets = timeSheets;
		this.AMEndTime = AMEndTime;
		this.PMStartTime = PMStartTime;
		this.additionSet = addtionSet;
		this.oneDayRange = oneDayRange;
		this.startOneDayTime = startOneDayTime;
	}
	
	/**
	 * Aggregateの所定時間から計算用所定時間クラスへの変換
	 */
	public static PredetermineTimeSetForCalc convertFromAggregatePremiumTime(PredetemineTimeSetting predetermineTimeSet){
		return new PredetermineTimeSetForCalc(predetermineTimeSet.getPrescribedTimezoneSetting().getLstTimezone()
											  ,predetermineTimeSet.getPrescribedTimezoneSetting().getMorningEndTime()
											  ,predetermineTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime()
											  ,predetermineTimeSet.getPredTime()
											  ,predetermineTimeSet.getRangeTimeDay()
											  ,predetermineTimeSet.getStartDateClock());
	}
	
	/**
	 * 所定終了時間を所定開始時間と同じ時刻に変更する
	 */
	public void endTimeSetStartTime() {
		val copyTimeSheet = this.getTimeSheets();
		this.timeSheets.clear();
		for(TimezoneUse timeSheet : copyTimeSheet) {
			this.timeSheets.add(new TimezoneUse(timeSheet.getStart(),
												timeSheet.getStart(),
												timeSheet.getUseAtr(),
												timeSheet.getWorkNo()));
		}
	}
	
	/**
	 * 勤務の単位を基に時間帯の開始、終了を補正
	 * @param dailyWork 1日の勤務
	 */
	public void correctPredetermineTimeSheet(DailyWork dailyWork,int workNo) {
		
		if (dailyWork.getAttendanceHolidayAttr().isHalfDayWorking()) {
			val workingTimeSheet = this.getHalfDayWorkingTimeSheetOf(dailyWork.getAttendanceHolidayAttr(),workNo);
			correctTimeSheet(workingTimeSheet.getStart(), workingTimeSheet.getEnd());
		}
	}

	
	public void correctTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end) {
		  val corrected = extractBetween(start, end);
		  this.timeSheets.clear();
		  this.timeSheets.addAll(corrected);
	 }

	
	private List<TimezoneUse> extractBetween(TimeWithDayAttr start, TimeWithDayAttr end) {
		val targetSpan = new TimeSpanForCalc(start, end);
		List<TimezoneUse> result = new ArrayList<>();
		
		this.timeSheets.stream().forEach(source -> {
			source.timeSpan().getDuplicatedWith(targetSpan).ifPresent(duplicated -> {
				source.updateStartTime(duplicated.getStart());
				source.updateEndTime(duplicated.getEnd());
				result.add(source);
			});
		});
		return result;
	}
	
	/**
	 * 午前出勤、午後出勤の判定
	 * @param attr
	 * @return
	 */
	private TimeSpanForCalc getHalfDayWorkingTimeSheetOf(AttendanceHolidayAttr attr,int workNo) {
		switch (attr) {
		case MORNING:
			return new TimeSpanForCalc(this.timeSheets.stream().filter(tc -> tc.getWorkNo() == workNo).map(tc -> tc.getStart()).collect(Collectors.toList()).get(0), this.AMEndTime);
		case AFTERNOON:
			return new TimeSpanForCalc(this.PMStartTime, this.timeSheets.stream().filter(tc -> tc.getWorkNo() == workNo).map(tc -> tc.getEnd()).collect(Collectors.toList()).get(0));
		case FULL_TIME:
		case HOLIDAY:
			return new TimeSpanForCalc(this.timeSheets.stream().filter(tc -> tc.getWorkNo() == workNo).map(tc -> tc.getStart()).collect(Collectors.toList()).get(0),
										this.timeSheets.stream().filter(tc -> tc.getWorkNo() == workNo).map(tc -> tc.getEnd()).collect(Collectors.toList()).get(0));
		default:
			throw new RuntimeException("unknown attr:" + attr);
		}
	}
	
	/**
	 * 所定時間の取得
	 * @return
	 */
	public AttendanceTime getpredetermineTime(DailyWork dailyWork) {
		switch(dailyWork.decisionWorkTypeRange()) {
		case ONEDAY:
			return additionSet.getPredTime().getOneDay();
		case MORNING:
			return additionSet.getPredTime().getMorning();
		case AFTERNOON:
			return additionSet.getPredTime().getAfternoon();
		case NOTHING:
			return new AttendanceTime(0);
		default:
			throw new RuntimeException("unknown workTypeRange");
		}
	}
	
	/**
	 * 出勤系などの～～～刑による所定時間の取得
	 * @param attendanceAtr
	 * @return
	 */
	public AttendanceTime getPredetermineTimeByAttendanceAtr(AttendanceHolidayAttr attendanceAtr) {
		switch(attendanceAtr) {
		case FULL_TIME:
			return additionSet.getPredTime().getOneDay();
		case MORNING:
			return additionSet.getPredTime().getMorning();
		case AFTERNOON:
			return additionSet.getPredTime().getAfternoon();
		case HOLIDAY:
			return new AttendanceTime(0);
		default:
				throw new RuntimeException("unknown workTypeRange");
		}
	}
	
	
	/**
	 * 所定時間設定を所定時間設定(計算用)に変換する
	 * @param master 所定時間設定
	 */
	public static PredetermineTimeSetForCalc convertMastarToCalc(PredetemineTimeSetting master) {
		return new PredetermineTimeSetForCalc(master.getPrescribedTimezoneSetting().getLstTimezone(),
											  master.getPrescribedTimezoneSetting().getMorningEndTime(),
											  master.getPrescribedTimezoneSetting().getAfternoonStartTime(),
											  master.getPredTime(),
											  master.getRangeTimeDay(),
											  master.getStartDateClock());
	}
	
	/**
	 * workNoに一致する所定時間を取得する
	 * @param workNo
	 * @return
	 */
	public Optional<TimezoneUse> getTimeSheets(AttendanceHolidayAttr attr,int workNo) {
		
		Optional<TimezoneUse> timeSheet = this.timeSheets.stream().filter(t -> t.getWorkNo()==workNo).findFirst();
		
		switch (attr) {
		case MORNING:
			if(timeSheet.isPresent()) {
				return Optional.of(new TimezoneUse(timeSheet.get().getStart(),this.AMEndTime,timeSheet.get().getUseAtr(),timeSheet.get().getWorkNo()));
			}
			return Optional.empty();
		case AFTERNOON:
			if(timeSheet.isPresent()) {
				return Optional.of(new TimezoneUse(this.PMStartTime,timeSheet.get().getEnd(),timeSheet.get().getUseAtr(),timeSheet.get().getWorkNo()));
			}
			return Optional.empty();
		case FULL_TIME:
		case HOLIDAY:
			return timeSheet;
		default:
			throw new RuntimeException("unknown attr:" + attr);
		}
		
	}
	
	/**
	 * 1日の範囲時間帯を作成する
	 * @return　1日の範囲時間帯
	 */
	public TimeSpanForCalc getOneDayTimeSpan() {
		return new TimeSpanForCalc(this.getStartOneDayTime(), this.getStartOneDayTime().forwardByMinutes(this.getOneDayRange().valueAsMinutes()));
	}
	
	
}
