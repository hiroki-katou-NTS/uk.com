/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class YearVacationAge.
 */
@DecimalRange(min = "0", max = "99.5")
@DecimalMantissaMaxLength(1)
public class YearVacationAge extends DecimalPrimitiveValue<YearVacationAge> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3113312047461252275L;
    
    /**
     * Instantiates a new year vacation age.
     *
     * @param rawValue the raw value
     */
    public YearVacationAge(BigDecimal rawValue) {
        super(rawValue);
    }
}
