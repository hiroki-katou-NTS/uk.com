package nts.uk.ctx.at.record.dom.worktime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
@NoArgsConstructor
public class TimeLeavingWork extends DomainObject{
	
	/*
	 * 勤務NO
	 */
	private WorkNo workNo;
	
	private TimeActualStamp attendanceStamp;
	
	private TimeActualStamp leaveStamp;

	public TimeLeavingWork(WorkNo workNo, TimeActualStamp attendanceStamp, TimeActualStamp leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = attendanceStamp;
		this.leaveStamp = leaveStamp;
	}
	
	public void setTimeLeavingWork(WorkNo workNo, TimeActualStamp attendanceStamp, TimeActualStamp leaveStamp){
		this.workNo = workNo;
		this.attendanceStamp = attendanceStamp;
		this.leaveStamp = leaveStamp;
	}
	
}
