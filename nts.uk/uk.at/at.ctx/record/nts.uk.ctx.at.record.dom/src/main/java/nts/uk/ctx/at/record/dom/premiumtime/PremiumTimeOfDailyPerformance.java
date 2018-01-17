package nts.uk.ctx.at.record.dom.premiumtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の割増時間
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PremiumTimeOfDailyPerformance {
	
	//割増時間NO - primitive value
	private Integer premiumTimeNo;
	
	//割増時間
	private AttendanceTime premitumTime;

}
