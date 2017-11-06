package nts.uk.ctx.at.record.dom.actualworkinghours;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTimeOfDaily;
import nts.uk.ctx.at.record.dom.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の実働時間
 *
 */
@Getter
public class ActualWorkingTimeOfDaily {
	
	//割増時間
	private PremiumTimeOfDailyPerformance premiumTimeOfDailyPerformance;
	
	//拘束差異時間
	private AttendanceTime constraintDifferenceTime;
	
	//拘束時間
	private ConstraintTime constraintTime;
	
	//時差勤務時間
	private AttendanceTime timeDifferenceWorkingHours;
	
	//総労働時間
	private TotalWorkingTime totalWorkingTime;
	
	//代休発生情報
	private SubHolOccurrenceInfo subHolOccurrenceInfo;
	
	//乖離時間
	private DivergenceTimeOfDaily divTime;
}
