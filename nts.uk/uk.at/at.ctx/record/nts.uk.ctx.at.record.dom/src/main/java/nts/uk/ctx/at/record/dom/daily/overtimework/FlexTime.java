package nts.uk.ctx.at.record.dom.daily.overtimework;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * フレックス時間
 * @author keisuke_hoshina
 *
 */
@Value
public class FlexTime {
	private TimeWithCalculationMinusExist flexTime;
	private AttendanceTime beforeApplicationTime; 
}
