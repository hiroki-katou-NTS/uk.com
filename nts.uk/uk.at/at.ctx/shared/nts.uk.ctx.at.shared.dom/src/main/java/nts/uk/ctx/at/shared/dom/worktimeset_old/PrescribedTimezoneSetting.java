package nts.uk.ctx.at.shared.dom.worktimeset_old;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

//所定時間帯設定
@Getter
public class PrescribedTimezoneSetting extends DomainObject{

	/** The morning end time. */
	//午前終了時刻
	private TimeWithDayAttr morningEndTime;
	
	/** The afternoon start time. */
	//午後開始時刻
	private TimeWithDayAttr afternoonStartTime;
	
	/** The timezone. */
	//時間帯
	private List<Timezone> timezone;

	/**
	 * Instantiates a new prescribed timezone setting.
	 *
	 * @param morningEndTime the morning end time
	 * @param afternoonStartTime the afternoon start time
	 * @param timezone the timezone
	 */
	public PrescribedTimezoneSetting(TimeWithDayAttr morningEndTime, TimeWithDayAttr afternoonStartTime,
			List<Timezone> timezone) {
		super();
		this.morningEndTime = morningEndTime;
		this.afternoonStartTime = afternoonStartTime;
		this.timezone = timezone;
	}
	
	/**
	 * 引数のNoと一致している勤務Noを持つ時間帯(使用区分付き)を取得する
	 * @param workNo
	 * @return 時間帯(使用区分付き)
	 */
	public Timezone getMatchWorkNoTimeSheet(int workNo) {
		List<Timezone> timeSheetWithUseAtrList = timezone.stream().filter(tc -> tc.getWorkNo() == workNo).collect(Collectors.toList());
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
}
