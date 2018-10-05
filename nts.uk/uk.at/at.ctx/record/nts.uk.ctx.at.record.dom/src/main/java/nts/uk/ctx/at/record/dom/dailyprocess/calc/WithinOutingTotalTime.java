package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;

/**
 * 所定内外出合計時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinOutingTotalTime {
	//合計時間
	TimeWithCalculation totalTime;
	//コア内時間
	TimeWithCalculation withinCoreTime;
	//コア外時間
	TimeWithCalculation excessCoreTime;
	
	/**
	 * Constructor 
	 */
	private WithinOutingTotalTime(TimeWithCalculation totalTime, TimeWithCalculation withinCoreTime,
			TimeWithCalculation excessCoreTime) {
		super();
		this.totalTime = totalTime;
		this.withinCoreTime = withinCoreTime;
		this.excessCoreTime = excessCoreTime;
	}
	
	public static WithinOutingTotalTime of(TimeWithCalculation totalTime, TimeWithCalculation withinCoreTime,TimeWithCalculation excessCoreTime) {
		return new WithinOutingTotalTime(totalTime, withinCoreTime, excessCoreTime);
	}
	
	public static WithinOutingTotalTime sameTime(TimeWithCalculation time) {
		return new WithinOutingTotalTime(time, time, time);
	}
	
}
