package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
@NoArgsConstructor
public class TimeLeavingWorkExport extends DomainObject{
	
	/*
	 * 勤務NO
	 */
	private int workNo;
	//出勤
	private Optional<TimeActualStampExport> attendanceStamp;
	//退勤
	private Optional<TimeActualStampExport> leaveStamp;
	
	public TimeLeavingWorkExport(int workNo, TimeActualStampExport attendanceStamp,
			TimeActualStampExport leaveStamp) {
		super();
		this.workNo = workNo;
		this.attendanceStamp = Optional.ofNullable(attendanceStamp);
		this.leaveStamp = Optional.ofNullable(leaveStamp);
	}
	
	
	
}
