package nts.uk.ctx.at.record.dom.premiumtime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の割増時間
 *
 */
@Getter
public class PremiumTimeOfDailyPerformance {
	
	//割増時間NO - primitive value
	private Integer premiumTimeNo;
	
	//割増時間
	private AttendanceTime premitumTime;

}
