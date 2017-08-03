/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class TimeWithDayAttr.
 */
@TimeRange(max = "71:59", min = "-12:00")
public class TimeWithDayAttr extends TimeDurationPrimitiveValue<TimeWithDayAttr> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new time with day attr.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public TimeWithDayAttr(Long rawValue) {
		super(rawValue);
	}
}
