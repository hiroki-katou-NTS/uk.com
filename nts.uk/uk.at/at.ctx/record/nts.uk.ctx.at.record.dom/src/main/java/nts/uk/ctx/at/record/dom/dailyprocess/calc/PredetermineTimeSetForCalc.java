package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.TimeSheetList;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
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
	
	private final TimeWithDayAttr AMEndTime;

	private final TimeWithDayAttr PMStartTime;
	
	private PredetermineTime additionSet;
	
	private AttendanceTime oneDayRange;
	
	private TimeWithDayAttr startOneDayTime;

	/**
	 * 所定時間帯の時間を更新する
	 * @param dateStartTime
	 * @param rangeTimeDay
	 * @param predetermineTimeSheet
	 * @param additionSet
	 * @param timeSheets
	 */
	public PredetermineTimeSetForCalc(
			TimeSheetList timeSheets,
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
	public static PredetermineTimeSetForCalc convertFromAggregatePremiumTime(PredetermineTimeSet predetermineTimeSet){
		return new PredetermineTimeSetForCalc(predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets()
											  ,predetermineTimeSet.getSpecifiedTimeSheet().getAMEndTime()
											  ,predetermineTimeSet.getSpecifiedTimeSheet().getPMStartTime()
											  ,predetermineTimeSet.getAdditionSet()
											  ,predetermineTimeSet.getRangeTimeDay()
											  ,predetermineTimeSet.getDateStartTime());
	}
	
	/**
	 * 勤務の単位を基に時間帯の開始、終了を補正
	 * @param dailyWork 1日の勤務
	 */
	public void correctPredetermineTimeSheet(DailyWork dailyWork,int workNo) {
		
		if (dailyWork.getAttendanceHolidayAttr().isHalfDayWorking()) {
			val workingTimeSheet = this.getHalfDayWorkingTimeSheetOf(dailyWork.getAttendanceHolidayAttr(),workNo);
			this.timeSheets.correctTimeSheet(workingTimeSheet.getStart(), workingTimeSheet.getEnd());
		}
	}

	/**
	 * 午前出勤、午後出勤の判定
	 * @param attr
	 * @return
	 */
	private TimeSpanForCalc getHalfDayWorkingTimeSheetOf(AttendanceHolidayAttr attr,int workNo) {
		switch (attr) {
		case MORNING:
			return new TimeSpanForCalc(this.timeSheets.startOfDay(workNo), this.AMEndTime);
		case AFTERNOON:
			return new TimeSpanForCalc(this.PMStartTime, this.timeSheets.endOfDay(workNo));
		case FULL_TIME:
		case HOLIDAY:
			return new TimeSpanForCalc(this.timeSheets.startOfDay(workNo),this.timeSheets.endOfDay(workNo));
		default:
			throw new RuntimeException("unknown attr:" + attr);
		}
	}
	
	/**
	 * 所定時間の取得
	 * @return
	 */
	public AttendanceTime getpredetermineTime(DailyWork dailyWork) {
		switch(dailyWork.getAttendanceHolidayAttr()) {
		case FULL_TIME:
			return additionSet.getTime().getOneDay();
		case MORNING:
			return additionSet.getTime().getMorning();
		case AFTERNOON:
			return additionSet.getTime().getAfternoon();
		default:
			return new AttendanceTime(0);
		}
	}
	
	
}
