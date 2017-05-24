/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class AnnualNumberDay.
 */
@DecimalRange(min = "0", max = "366")
@DecimalMantissaMaxLength(1)
public class AnnualNumberDay extends DecimalPrimitiveValue<AnnualNumberDay> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2655855726224248279L;
    
    /**
     * Instantiates a new annual number day.
     *
     * @param rawValue the raw value
     */
    public AnnualNumberDay(BigDecimal rawValue) {
        super(rawValue);
    }
}
