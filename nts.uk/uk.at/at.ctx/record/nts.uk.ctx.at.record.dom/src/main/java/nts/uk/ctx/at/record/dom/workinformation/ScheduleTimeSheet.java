package nts.uk.ctx.at.record.dom.workinformation;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 予定時間帯
 *
 */
@Getter
@NoArgsConstructor
public class ScheduleTimeSheet extends DomainObject implements Serializable{
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	private WorkNo workNo;
	@Setter
	private TimeWithDayAttr attendance;
	@Setter
	private TimeWithDayAttr leaveWork;

	public ScheduleTimeSheet(Integer workNo, int attendance, int leaveWork) {
		super();
		this.workNo = new WorkNo(workNo);
		this.attendance = new TimeWithDayAttr(attendance);
		this.leaveWork = new TimeWithDayAttr(leaveWork);
	}
	
}
