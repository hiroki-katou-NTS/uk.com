package nts.uk.screen.at.app.dailyperformance.correction.dto.workinfomation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Getter
@AllArgsConstructor
public class ScheduleTimeSheetDto {
	private int workNo;
	
	private TimeWithDayAttr attendance;
	
	private TimeWithDayAttr leaveWork;

	public ScheduleTimeSheetDto(int workNo, int attendance, int leaveWork) {
		this.workNo = workNo;
		this.attendance = new TimeWithDayAttr(attendance);
		this.leaveWork = new TimeWithDayAttr(leaveWork);
	}
}
