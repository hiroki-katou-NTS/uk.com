/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class NumberDayCalendar.
 */
@IntegerRange(max = 99, min = 0)
public class DispOrder extends IntegerPrimitiveValue<DispOrder> {

	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new number day calendar.
     *
     * @param rawValue the raw value
     */
    public DispOrder(Integer rawValue) {
        super(rawValue);
    }
    
}
