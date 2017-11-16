package nts.uk.ctx.at.record.dom.daily.overtimework;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/**
 * フレックス時間
 * @author keisuke_hoshina
 *
 */
@Value
public class FlexTime {
	private AttendanceTimeOfExistMinus flexTime;
	private AttendanceTime beforeApplicationTime; 
}
