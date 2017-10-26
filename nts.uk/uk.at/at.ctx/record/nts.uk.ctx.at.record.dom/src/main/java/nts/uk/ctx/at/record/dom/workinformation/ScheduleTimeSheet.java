package nts.uk.ctx.at.record.dom.workinformation;

import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 予定時間帯
 *
 */
@Getter
public class ScheduleTimeSheet {
	
	private String workNo;
	
	private TimeWithDayAttr attendance;
	
	private TimeWithDayAttr leaveWork;

	public ScheduleTimeSheet(String workNo, int attendance, int leaveWork) {
		super();
		this.workNo = workNo;
		this.attendance = new TimeWithDayAttr(attendance);
		this.leaveWork = new TimeWithDayAttr(leaveWork);
	}
	
}
