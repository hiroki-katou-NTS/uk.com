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
	//時間
	private TimeWithCalculation time;
	//事前申請時間
	private AttendanceTime beforeApplicationTime;
}
