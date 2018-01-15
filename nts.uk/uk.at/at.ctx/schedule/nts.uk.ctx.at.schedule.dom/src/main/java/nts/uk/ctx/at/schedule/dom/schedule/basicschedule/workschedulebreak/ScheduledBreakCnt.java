/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;


/**
 * The Class ScheduledBreakCnt.
 */
// 予定休憩回数
@IntegerRange(min = 1, max = 10)
public class ScheduledBreakCnt extends IntegerPrimitiveValue<ScheduledBreakCnt>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Instantiates a new scheduled break cnt.
	 *
	 * @param rawValue the raw value
	 */
	public ScheduledBreakCnt(Integer rawValue) {
		super(rawValue);
	}

}
