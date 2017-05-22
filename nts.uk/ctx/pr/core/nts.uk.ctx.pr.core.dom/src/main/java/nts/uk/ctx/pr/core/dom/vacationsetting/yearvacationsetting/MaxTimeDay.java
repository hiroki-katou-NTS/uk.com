/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class MaxTimeDay.
 */
@IntegerRange(max = 99, min = 0)
public class MaxTimeDay extends IntegerPrimitiveValue<MaxTimeDay> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1378529469055542099L;
    
    /**
     * Instantiates a new max time day.
     *
     * @param rawValue the raw value
     */
    public MaxTimeDay(Integer rawValue) {
        super(rawValue);
    }

}
