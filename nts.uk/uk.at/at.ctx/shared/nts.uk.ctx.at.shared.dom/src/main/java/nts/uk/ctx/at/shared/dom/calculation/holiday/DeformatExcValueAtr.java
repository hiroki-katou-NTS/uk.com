/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;

/**
 * The Enum DeformatExcValueAtr.
 */
@AllArgsConstructor
public enum DeformatExcValueAtr {

	/** 就業時間として計算する*/
	CALC_AS_WORKING_HOURS(0),
	/** 残業時間として計算する*/
	CALC_AS_OVERTIME_HOURS(1);

	public final int value;
}
