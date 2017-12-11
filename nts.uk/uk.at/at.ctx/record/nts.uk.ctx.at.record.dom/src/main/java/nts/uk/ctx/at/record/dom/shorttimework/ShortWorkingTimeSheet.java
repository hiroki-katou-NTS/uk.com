package nts.uk.ctx.at.record.dom.shorttimework;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 短時間勤務時間帯
 *
 */
@Getter
public class ShortWorkingTimeSheet {

	//短時間勤務枠NO
	private ShortWorkTimFrameNo shortWorkTimeFrameNo;
	
	private ChildCareAttribute childCareAttr;

	private TimeWithDayAttr startTime;
	
	private TimeWithDayAttr endTime;
	
	private AttendanceTime deductionTime;
	
	private AttendanceTime shortTime;
	
}
