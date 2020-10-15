/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;

/**
 * The Class ExtBudgetTime.
 * 外部予算実績時間
 */
@TimeRange(min = "00:00", max = "999:59")
public class ExtBudgetTime extends TimeDurationPrimitiveValue<ExtBudgetTime> implements ExtBudgetActualValues {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget time.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetTime(int rawValue) {
        super(rawValue);
    }

}
