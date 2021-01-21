/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;

/**
 * The Class ExtBudgetMoney.
 * 外部予算実績金額
 */
@IntegerRange(min = 0, max = 99999999)
public class ExtBudgetMoney extends IntegerPrimitiveValue<ExtBudgetMoney> implements ExtBudgetActualValues {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget amount.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetMoney(Integer rawValue) {
        super(rawValue);
    }

}
