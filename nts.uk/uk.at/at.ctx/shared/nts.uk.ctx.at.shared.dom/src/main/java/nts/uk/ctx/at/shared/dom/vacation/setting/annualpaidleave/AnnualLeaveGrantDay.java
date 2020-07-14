/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * The Class YearVacationAge.
 */
@HalfIntegerRange(min = 0, max = 99.5)
public class AnnualLeaveGrantDay extends HalfIntegerPrimitiveValue<AnnualLeaveGrantDay> implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    
    /**
     * Instantiates a new annual leave grant day.
     *
     * @param rawValue the raw value
     */
    public AnnualLeaveGrantDay(Double rawValue) {
        super(rawValue);
    }
}
