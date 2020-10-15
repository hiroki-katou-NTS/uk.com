/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;

/**
 * The Class ExtBudgetNumberPerson.
 * 外部予算実績人数
 */
@IntegerRange(min = 0, max = 99999)
public class ExtBudgetNumberPerson extends IntegerPrimitiveValue<ExtBudgetNumberPerson> implements ExtBudgetActualValues {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget number person.
     *
     * @param rawValue the raw value
     */
    public ExtBudgetNumberPerson(Integer rawValue) {
        super(rawValue);
    }

}