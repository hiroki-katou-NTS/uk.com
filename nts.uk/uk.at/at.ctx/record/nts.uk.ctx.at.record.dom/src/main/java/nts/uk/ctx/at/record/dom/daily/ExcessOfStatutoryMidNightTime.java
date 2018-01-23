package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 法定外深夜時間
 * @author keisuke_hoshina
 *
 */
@Value
public class ExcessOfStatutoryMidNightTime {
	private TimeWithCalculation time;
	private AttendanceTime beforeApplicationTime;
}
