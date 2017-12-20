/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class PlanYearHolidayFrameNo.
 */
//計画年休枠NO
@DecimalRange(min = "1", max = "5")
@DecimalMantissaMaxLength(1)
public class PlanYearHolidayFrameNo extends DecimalPrimitiveValue<PlanYearHolidayFrameNo> {
	
	/**
	 * Instantiates a new plan year holiday frame no.
	 *
	 * @param rawValue the raw value
	 */
	public PlanYearHolidayFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}
