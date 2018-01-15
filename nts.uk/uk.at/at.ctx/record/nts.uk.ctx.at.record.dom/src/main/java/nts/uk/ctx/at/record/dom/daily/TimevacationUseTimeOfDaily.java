package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の時間休暇使用時間
 * @author ken_takasu
 *
 */
@Value
public class TimevacationUseTimeOfDaily {
	
	private AttendanceTime TimeAnnualLeaveUseTime;
	private AttendanceTime TimeCompensatoryLeaveUseTime;
	private AttendanceTime sixtyHourExcessHolidayUseTime;
	private AttendanceTime TimeSpecialHolidayUseTime;
}
