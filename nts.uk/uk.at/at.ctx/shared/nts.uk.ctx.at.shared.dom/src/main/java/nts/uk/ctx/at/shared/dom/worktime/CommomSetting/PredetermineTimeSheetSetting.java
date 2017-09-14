package nts.uk.ctx.at.shared.dom.worktime.CommomSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanList;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間帯設定
 * @author keisuke_hoshina
 *
 */
@EqualsAndHashCode(callSuper = false)
public class PredetermineTimeSheetSetting extends AggregateRoot{
	
	private final TimeSheetList timeSheets;
	
	@Getter
	private final TimeWithDayAttr AMEndTime;
	
	@Getter
	private final TimeWithDayAttr PMStartTime;
	
	public PredetermineTimeSheetSetting(List<TimeSheetWithUseAtr> timeSheets, TimeWithDayAttr AMEndTime, TimeWithDayAttr PMStartTime) {
		this.timeSheets = new TimeSheetList(timeSheets);
		this.AMEndTime = AMEndTime;
		this.PMStartTime = PMStartTime;
	}
	
	public List<TimeSheetWithUseAtr> getTimeSheets() {
		return this.timeSheets.getTimeSpanList();
	}
	
	/**
	 * 引数のNoと一致している勤務Noを持つ時間帯(使用区分付き)を取得する
	 * @param workNo
	 * @return 時間帯(使用区分付き)
	 */
	public TimeSheetWithUseAtr getMatchWorkNoTimeSheet(int workNo) {
		List<TimeSheetWithUseAtr> timeSheetWithUseAtrList = getTimeSheets().stream().filter(tc -> tc.getCount() == workNo).collect(Collectors.toList());
		if(timeSheetWithUseAtrList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return timeSheetWithUseAtrList.get(0);
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
	
	/**
	 * 
	 * @author keisuke_hoshina
	 *
	 */
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
}
