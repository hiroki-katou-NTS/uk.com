package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;

/**
 * 外出合計時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class OutingTotalTime {
	//合計時間
	TimeWithCalculation totalTime;
	//所定内合計時間
	WithinOutingTotalTime withinTotalTime;
	//所定外合計時間
	TimeWithCalculation excessTotalTime;
	
	/**
	 * Constructor 
	 */
	private OutingTotalTime(TimeWithCalculation totalTime, WithinOutingTotalTime withinTotalTime,
			TimeWithCalculation excessTotalTime) {
		super();
		this.totalTime = totalTime;
		this.withinTotalTime = withinTotalTime;
		this.excessTotalTime = excessTotalTime;
	}
	
	public static OutingTotalTime of(TimeWithCalculation totalTime, WithinOutingTotalTime withinTotalTime,TimeWithCalculation excessTotalTime) {
		return new OutingTotalTime(totalTime,withinTotalTime,excessTotalTime);
	}
}
