package nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork;

import lombok.AllArgsConstructor;

/**
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
public enum OutputCheckRangeReflectAttd {
	/**
	 * 1回目勤務の出勤打刻反映範囲内
	 */
	FIRST_TIME,
	/**
	 * 2回目勤務の出勤打刻反映範囲内
	 */
	SECOND_TIME,
	/**
	 * 範囲外
	 */
	OUT_OF_RANGE;
	
}
