package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

/** 月労働時間 */
@Getter
public class MonthlyLaborTime {

	/** 法定労働時間 */
	private MonthlyEstimateTime legalLaborTime; 
	
	/** 所定労働時間 */
	/** 勤務区分がフレックスの場合、必ず所定労働時間と週平均時間が存在する */
	private Optional<MonthlyEstimateTime> withinLaborTime;
	
	/** 週平均時間 */
	/** 勤務区分がフレックスの場合、必ず所定労働時間と週平均時間が存在する */
	private Optional<MonthlyEstimateTime> weekAvgTime;
	
	private MonthlyLaborTime (MonthlyEstimateTime legalTime,
			Optional<MonthlyEstimateTime> withinLaborTime, 
			Optional<MonthlyEstimateTime> weekAvgTime) {
		
		this.legalLaborTime = legalTime;
		this.withinLaborTime = withinLaborTime;
		this.weekAvgTime = weekAvgTime;
	}
	
	public static MonthlyLaborTime of (MonthlyEstimateTime legalTime) {
		
		return new MonthlyLaborTime(legalTime, Optional.empty(), Optional.empty());
	}
	
	public static MonthlyLaborTime of (MonthlyEstimateTime legalTime,
			Optional<MonthlyEstimateTime> withinLaborTime, 
			Optional<MonthlyEstimateTime> weekAvgTime) {
		
		return new MonthlyLaborTime(legalTime, withinLaborTime, weekAvgTime);
	}
	
	public void setWithinLaborTime(MonthlyEstimateTime time) {
		this.withinLaborTime = Optional.of(time);
	}
	
	public void setWeekAvgTime(MonthlyEstimateTime time) {
		this.weekAvgTime = Optional.of(time);
	}
}
