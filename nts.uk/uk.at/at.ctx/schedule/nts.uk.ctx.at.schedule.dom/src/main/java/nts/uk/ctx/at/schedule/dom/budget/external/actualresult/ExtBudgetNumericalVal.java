/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;

/**
 * The Class ExtBudgetNumericalVal.
 * 外部予算実績数値
 */
@IntegerRange(min = 0, max = 99999)
public class ExtBudgetNumericalVal extends IntegerPrimitiveValue<ExtBudgetNumericalVal> implements ExtBudgetActualValues {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget numerical val.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetNumericalVal(Integer rawValue) {
        super(rawValue);
    }

}