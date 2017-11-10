/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fluid;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class AttendenceTime.
 */
@TimeRange(max = "48:00", min = "00:00")
public class AttendanceTime extends TimeDurationPrimitiveValue<AttendanceTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendence time.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public AttendanceTime(Integer rawValue) {
		super(rawValue);
	}
}
