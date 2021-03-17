/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * 年間日数
 */
@HalfIntegerMinValue(0.0)
@HalfIntegerMaxValue(366.0)
public class YearLyOfNumberDays extends HalfIntegerPrimitiveValue<YearLyOfNumberDays> implements Serializable{

	public YearLyOfNumberDays(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}
