/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class SuperHDOccUnit.
 */
// 60H超休発生単位
@TimeRange(min = "0:00", max = "99:59")
public class SuperHDOccUnit extends TimeDurationPrimitiveValue<SuperHDOccUnit>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new super HD occ unit.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public SuperHDOccUnit(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
