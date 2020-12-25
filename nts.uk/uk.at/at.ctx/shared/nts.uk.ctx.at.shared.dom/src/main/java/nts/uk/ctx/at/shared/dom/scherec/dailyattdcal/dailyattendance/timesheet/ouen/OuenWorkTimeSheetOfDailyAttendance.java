package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
/** 日別勤怠の応援作業時間帯 */
public class OuenWorkTimeSheetOfDailyAttendance implements DomainObject {

	/** 応援勤務枠No: 応援勤務枠No */
	private OuenFrameNo workNo;

	/** 作業内容: 作業内容 */
	private WorkContent workContent;
	
	/** 時間帯: 時間帯別勤怠の時間帯 */
	private TimeSheetOfAttendanceEachOuenSheet timeSheet;

	private OuenWorkTimeSheetOfDailyAttendance(OuenFrameNo workNo, WorkContent workContent, 
			TimeSheetOfAttendanceEachOuenSheet timeSheet) {
		super();
		this.workNo = workNo;
		this.workContent = workContent;
		this.timeSheet = timeSheet;
	}
	
	public static OuenWorkTimeSheetOfDailyAttendance create(OuenFrameNo workNo, WorkContent workContent, 
			TimeSheetOfAttendanceEachOuenSheet timeSheet) {
		
		return new OuenWorkTimeSheetOfDailyAttendance(workNo, workContent, timeSheet);
	}
	
	/**
	 * 作業時間帯の開始終了を取得する
	 * @return 開始～終了
	 */
	public Optional<TimeSpanForDailyCalc> getStartAndEnd() {
		if(!this.getStartTimeWithDayAttr().isPresent())
			return Optional.empty();
		
		if(!this.getEndTimeWithDayAttr().isPresent())
			return Optional.empty();
		
		return Optional.of(new TimeSpanForDailyCalc(this.getStartTimeWithDayAttr().get(), this.getEndTimeWithDayAttr().get()));
	}
	
	/**
	 * 開始時刻を取得する
	 * @return 開始時刻
	 */
	public Optional<TimeWithDayAttr> getStartTimeWithDayAttr() {
		if(!this.timeSheet.getStart().isPresent())
			return Optional.empty();
		
		return this.timeSheet.getStart().get().getTimeWithDay();
	}
	
	/**
	 * 終了時刻を取得する
	 * @return 終了時刻
	 */
	public Optional<TimeWithDayAttr> getEndTimeWithDayAttr() {
		if(!this.timeSheet.getEnd().isPresent())
			return Optional.empty();
		
		return this.timeSheet.getEnd().get().getTimeWithDay();
	}
}
