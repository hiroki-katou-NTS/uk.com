package nts.uk.ctx.at.record.dom.worktime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@Getter
public class TimeLeavingWork extends DomainObject{
	
	//勤務NO
	private WorkNo workNo;
	
	private TimeActualStamp attendanceStamp;
	
	private TimeActualStamp leaveStamp;
}
