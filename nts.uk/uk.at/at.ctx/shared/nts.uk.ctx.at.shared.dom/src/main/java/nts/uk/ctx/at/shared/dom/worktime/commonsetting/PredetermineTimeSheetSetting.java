package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間帯設定
 * @author keisuke_hoshina
 *
 */
@EqualsAndHashCode(callSuper = false)
public class PredetermineTimeSheetSetting extends AggregateRoot{
	@Getter
	private final TimeSheetList timeSheets;
	
	@Getter
	private final TimeWithDayAttr AMEndTime;
	
	@Getter
	private final TimeWithDayAttr PMStartTime;
	
	public PredetermineTimeSheetSetting(List<Timezone> timeSheets, TimeWithDayAttr AMEndTime, TimeWithDayAttr PMStartTime) {
		this.timeSheets = new TimeSheetList(timeSheets);
		this.AMEndTime = AMEndTime;
		this.PMStartTime = PMStartTime;
	}
//	
//	public List<TimeSheetWithUseAtr> getTimeSheets() {
//		return this.timeSheets.getTimeSpanList();
//	}
//	
	/**
	 * 引数のNoと一致している勤務Noを持つ時間帯(使用区分付き)を取得する
	 * @param workNo
	 * @return 時間帯(使用区分付き)
	 */
	public Timezone getMatchWorkNoTimeSheet(int workNo) {
		List<Timezone> timeSheetWithUseAtrList = timeSheets.getTimeSpanList().stream().filter(tc -> tc.getWorkNo() == workNo).collect(Collectors.toList());
		if(timeSheetWithUseAtrList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return timeSheetWithUseAtrList.get(0);
	}

//	/**
//	 * 勤務の単位を基に時間帯の開始、終了を補正
//	 * @param dailyWork 1日の勤務
//	 */
//	public PredetermineTimeSetForCalc convertToPredetermineForCalc(DailyWork dailyWork) {
//		return 
//	}
//
//	/**
//	 * 午前出勤、午後出勤の判定
//	 * @param attr
//	 * @return
//	 */
//	private TimeSpanForCalc getHalfDayWorkingTimeSheetOf(AttendanceHolidayAttr attr) {
//		switch (attr) {
//		case MORNING:
//			return new TimeSpanForCalc(this.timeSheets.startOfDay(), this.AMEndTime);
//		case AFTERNOON:
//			return new TimeSpanForCalc(this.PMStartTime, this.timeSheets.endOfDay());
//		default:
//			throw new RuntimeException("半日専用のメソッドです: " + attr);
//		}
//	}
//	
}
