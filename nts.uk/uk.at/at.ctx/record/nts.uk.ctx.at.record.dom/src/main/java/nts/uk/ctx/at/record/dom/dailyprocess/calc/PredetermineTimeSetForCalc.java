package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanList;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.SetAdditionToWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.TimeSheetWithUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSheetSetting.TimeSheetList;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間設定(計算用クラス)
 * @author ken_takasu
 *
 */
@Value
public class PredetermineTimeSetForCalc {
	
	private final TimeSheetList timeSheets;

	private final SetAdditionToWorkTime attendanceTime;
	
	private final TimeWithDayAttr AMEndTime;

	private final TimeWithDayAttr PMStartTime;
	
	private PredetermineTime additionSet;

	/**
	 * 所定時間帯の時間を更新する
	 * @param dateStartTime
	 * @param rangeTimeDay
	 * @param predetermineTimeSheet
	 * @param additionSet
	 * @param timeSheets
	 */
	public PredetermineTimeSetForCalc(
			SetAdditionToWorkTime additionSet,
			TimeSheetList timeSheets,
			TimeWithDayAttr AMEndTime,
			TimeWithDayAttr PMStartTime) {
		this.timeSheets = timeSheets;
		this.AMEndTime = AMEndTime;
		this.PMStartTime = PMStartTime;
		this.additionSet = additionSet;	
	}
	
	
	/**
	 * 勤務の単位を基に時間帯の開始、終了を補正
	 * @param dailyWork 1日の勤務
	 */
	public void correctPredetermineTimeSheet(DailyWork dailyWork) {
		
		if (dailyWork.getAttendanceHolidayAttr().isHalfDayWorking()) {
			val workingTimeSheet = this.getHalfDayWorkingTimeSheetOf(dailyWork.getAttendanceHolidayAttr());
			this.timeSheets.correctTimeSheet(workingTimeSheet.getStart(), workingTimeSheet.getEnd());
		}
	}

	/**
	 * 午前出勤、午後出勤の判定
	 * @param attr
	 * @return
	 */
	private TimeSpanForCalc getHalfDayWorkingTimeSheetOf(AttendanceHolidayAttr attr) {
		switch (attr) {
		case MORNING:
			return new TimeSpanForCalc(this.timeSheets.startOfDay(), this.AMEndTime);
		case AFTERNOON:
			return new TimeSpanForCalc(this.PMStartTime, this.timeSheets.endOfDay());
		default:
			throw new RuntimeException("半日専用のメソッドです: " + attr);
		}
	}
	
	
	private static class TimeSheetList implements HasTimeSpanList<TimeSheetWithUseAtr> {

		private final List<TimeSheetWithUseAtr> timeSheets;
		
		public TimeSheetList(List<TimeSheetWithUseAtr> timeSheets) {
			this.timeSheets = new ArrayList<>(timeSheets);
		}
		
		@Override
		public List<TimeSheetWithUseAtr> getTimeSpanList() {
			return this.timeSheets;
		}
		
		public void correctTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end) {
			val corrected = this.extractBetween(start, end);
			this.timeSheets.clear();
			this.timeSheets.addAll(corrected);
		}
		
		public TimeWithDayAttr startOfDay() {
			return this.timeSheets.get(0).getStartTime();
		}
		
		public TimeWithDayAttr endOfDay() {
			if (this.timeSheets.size() == 1) {
				return this.timeSheets.get(0).getEndTime();
			} else {
				return this.timeSheets.get(1).getEndTime();
			}
		}		
	}
	
	/**
	 * 所定時間の取得
	 * @return
	 */
	public AttendanceTime getpredetermineTime(DailyWork dailyWork) {
		AttendanceTime returnTime;
		switch(dailyWork.getAttendanceHolidayAttr()) {
		case FULL_TIME:
			returnTime = new AttendanceTime();
		case MORNING:
			returnTime = new AttendanceTime();
		case AFTERNOON:
			returnTime = new AttendanceTime();
		default:
			returnTime = new AttendanceTime(0);
		}
	}
	
	
}
