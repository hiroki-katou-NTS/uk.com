package nts.uk.ctx.at.record.dom.daily.holidaywork;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 休出枠時間
 * @author keisuke_hoshina
 *
 */
@Value
public class HolidayWorkFrameTime {
	private HolidayWorkFrameNo holidayFrameNo;
	
	private TimeWithCalculation transferTime;
	
	private TimeWithCalculation holidayWorkTime;
	
	private AttendanceTime beforeApplicationTime;
	
}
