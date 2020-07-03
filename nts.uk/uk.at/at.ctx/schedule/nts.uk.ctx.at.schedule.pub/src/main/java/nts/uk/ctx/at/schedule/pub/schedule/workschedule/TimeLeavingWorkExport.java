package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

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
//	private Optional<TimeActualStamp> attendanceStamp;
	//退勤
//	private Optional<TimeActualStamp> leaveStamp;
	
	
}
