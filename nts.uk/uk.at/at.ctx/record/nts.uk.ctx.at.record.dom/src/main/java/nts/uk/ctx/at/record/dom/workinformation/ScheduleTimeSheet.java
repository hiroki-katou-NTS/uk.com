package nts.uk.ctx.at.record.dom.workinformation;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 予定時間帯
 *
 */
@Getter
public class ScheduleTimeSheet {
	
	private WorkNo workNo;
	
	private TimeWithDayAttr attendance;
	
	private TimeWithDayAttr leaveWork;

	public ScheduleTimeSheet(BigDecimal workNo, int attendance, int leaveWork) {
		super();
		this.workNo = new WorkNo(workNo);
		this.attendance = new TimeWithDayAttr(attendance);
		this.leaveWork = new TimeWithDayAttr(leaveWork);
	}
	
}
