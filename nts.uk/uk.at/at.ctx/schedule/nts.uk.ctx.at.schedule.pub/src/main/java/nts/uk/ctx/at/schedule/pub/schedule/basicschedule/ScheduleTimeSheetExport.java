package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleTimeSheetExport {
	
	//勤務NO
	private int workNo;
	
	//開始時刻
	private TimeWithDayAttr attendance;
	
	//終了時刻
	private TimeWithDayAttr leaveWork;

	public ScheduleTimeSheetExport(int workNo, TimeWithDayAttr attendance, TimeWithDayAttr leaveWork) {
		super();
		this.workNo = workNo;
		this.attendance = attendance;
		this.leaveWork = leaveWork;
	}
	
}
