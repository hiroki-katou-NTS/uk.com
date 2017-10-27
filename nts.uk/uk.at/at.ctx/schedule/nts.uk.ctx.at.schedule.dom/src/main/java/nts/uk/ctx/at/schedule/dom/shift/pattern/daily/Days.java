/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class NumberDayCalendar.
 */
@IntegerRange(max = 31, min = 1)
public class Days extends IntegerPrimitiveValue<Days> {

	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new number day calendar.
     *
     * @param rawValue the raw value
     */
    public Days(Integer rawValue) {
        super(rawValue);
    }

}
