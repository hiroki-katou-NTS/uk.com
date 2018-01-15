/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class NumberDayNursing.
 */
@IntegerRange(max = 99, min = 0)
public class NumberDayNursing extends IntegerPrimitiveValue<NumberDayNursing> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new number day nursing.
     *
     * @param rawValue the raw value
     */
    public NumberDayNursing(Integer rawValue) {
        super(rawValue);
    }
}
