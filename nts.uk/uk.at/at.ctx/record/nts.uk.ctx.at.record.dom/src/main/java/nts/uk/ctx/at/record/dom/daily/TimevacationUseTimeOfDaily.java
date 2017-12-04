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
	
	//時間年休使用時間
	private AttendanceTime TimeAnnualLeaveUseTime;
	//時間代休使用時間
	private AttendanceTime TimeCompensatoryLeaveUseTime;
	//超過有休使用時間
	private AttendanceTime sixtyHourExcessHolidayUseTime;
	//特別休暇使用時間
	private AttendanceTime TimeSpecialHolidayUseTime;
}
