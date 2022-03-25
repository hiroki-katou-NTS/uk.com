/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import lombok.AllArgsConstructor;

/**
 * フレックス計算方法
 * @author shuichi_ishida
 */
@AllArgsConstructor
public enum FlexCalcMethod {

	/** 所定時間を半日分とする */
	PREDETERMINED_TIME_HALF_DAY(0),
	/** 所定時間を1日分とする */
	PREDETERMINED_TIME_ONE_DAY(1);
	
	public final int value;
}