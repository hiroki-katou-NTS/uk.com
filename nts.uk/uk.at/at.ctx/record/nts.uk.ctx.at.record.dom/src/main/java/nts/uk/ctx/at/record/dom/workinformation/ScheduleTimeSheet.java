package nts.uk.ctx.at.record.dom.workinformation;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.TimeWithDayAtr;

/**
 * 
 * @author nampt
 * 予定時間帯
 *
 */
@Getter
public class ScheduleTimeSheet {
	
	private String workNo;
	
	private TimeWithDayAtr attendance;
	
	private TimeWithDayAtr leaveWork;
}
