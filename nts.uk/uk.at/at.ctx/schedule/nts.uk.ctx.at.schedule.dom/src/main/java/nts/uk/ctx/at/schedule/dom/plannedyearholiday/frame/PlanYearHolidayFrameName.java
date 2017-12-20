/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class PlanYearHolidayFrameName.
 */
//休職休業枠名称
@StringMaxLength(12)
public class PlanYearHolidayFrameName extends StringPrimitiveValue<PlanYearHolidayFrameName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3072948248326997648L;

	/**
	 * Instantiates a new plan year holiday frame name.
	 *
	 * @param rawValue the raw value
	 */
	public PlanYearHolidayFrameName(String rawValue) {
		super(rawValue);
	}
	
}
