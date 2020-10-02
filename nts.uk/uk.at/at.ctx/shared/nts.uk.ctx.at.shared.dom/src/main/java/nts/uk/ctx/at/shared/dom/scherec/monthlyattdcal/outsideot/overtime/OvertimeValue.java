/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class OvertimeValue.
 */
// 超過時間
@TimeRange(min = "0:00", max = "999:59")
public class OvertimeValue extends TimeDurationPrimitiveValue<OvertimeValue>{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new overtime value.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public OvertimeValue(int timeAsMinutes) {
		super(timeAsMinutes);
	}
}
