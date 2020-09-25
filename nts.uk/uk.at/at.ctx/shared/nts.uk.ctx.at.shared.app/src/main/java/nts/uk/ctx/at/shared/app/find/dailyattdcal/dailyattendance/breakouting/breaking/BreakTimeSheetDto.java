package nts.uk.ctx.at.shared.app.find.dailyattdcal.dailyattendance.breakouting.breaking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class BreakTimeSheetDto {
	//休憩枠NO
	private int breakFrameNo;
	
	//開始 - 勤怠打刻(実打刻付き)
	private int startTime;
	
	//終了 - 勤怠打刻(実打刻付き)
	private int endTime;
	
	/** 休憩時間: 勤怠時間 */
	private int breakTime;
	
	public static BreakTimeSheetDto fromDomain(BreakTimeSheet breakTimeSheet) {
		return new BreakTimeSheetDto(
				breakTimeSheet.getBreakFrameNo().v(), 
				breakTimeSheet.getStartTime().v(), 
				breakTimeSheet.getEndTime().v(), 
				breakTimeSheet.getBreakTime().v());
	}
	
	public BreakTimeSheet toDomain() {
		return new BreakTimeSheet(
				new BreakFrameNo(breakFrameNo),
				new TimeWithDayAttr(startTime),
				new TimeWithDayAttr(endTime),
				new AttendanceTime(breakTime));
	}
}
