/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.AllArgsConstructor;

/**
 * The Enum CalcMethodOfNoWorkingDay.
 */
@AllArgsConstructor
public enum CalcMethodOfNoWorkingDay {
	
	// フレックス時間を計算しない
	IS_NOT_CALC_FLEX_TIME(0, "フレックス時間を計算しない"),
	// フレックス時間を計算する
	IS_CALC_FLEX_TIME(1, "フレックス時間を計算する");
	
	// The value
	public final int value;
	
	// The calc method
	public final String method;
}
