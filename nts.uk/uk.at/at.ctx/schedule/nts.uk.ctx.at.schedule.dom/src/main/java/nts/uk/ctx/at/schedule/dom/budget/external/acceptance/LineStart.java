/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class LineStart.
 */
@IntegerRange(min = 1, max = 9999)
public class LineStart extends IntegerPrimitiveValue<LineStart> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new line start.
     *
     * @param rawValue the raw value
     */
    public LineStart(Integer rawValue) {
        super(rawValue);
    }

}
