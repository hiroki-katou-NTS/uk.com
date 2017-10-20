package nts.uk.ctx.at.record.dom.worktime;

import lombok.Getter;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
public class TimeLeavingWork {
	
	private String workNo;
	
	private TimeActualStamp attendanceStamp;
	
	private TimeActualStamp leaveStamp;
}
