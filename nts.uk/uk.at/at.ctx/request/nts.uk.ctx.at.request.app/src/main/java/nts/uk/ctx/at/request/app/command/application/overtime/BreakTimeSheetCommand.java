package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class BreakTimeSheetCommand {
	//休憩枠NO
	public Integer breakFrameNo;
	
	//開始 - 勤怠打刻(実打刻付き)
	public Integer startTime;
	
	//終了 - 勤怠打刻(実打刻付き)
	public Integer endTime;
	
	/** 休憩時間: 勤怠時間 */
	public Integer breakTime;
	
	public BreakTimeSheet toDomain() {
		
		return new BreakTimeSheet(
				new BreakFrameNo(breakFrameNo),
				new TimeWithDayAttr(startTime),
				new TimeWithDayAttr(endTime),
				new AttendanceTime(breakTime));
	}
}
