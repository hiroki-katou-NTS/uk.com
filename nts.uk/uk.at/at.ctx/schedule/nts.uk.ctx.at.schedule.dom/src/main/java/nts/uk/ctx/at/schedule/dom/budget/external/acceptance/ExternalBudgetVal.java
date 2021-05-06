/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetMoneyValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetTimeValue;

/**
 * The Class ExternalBudgetVal.
 *
 * @param <T> the generic type
 */
public class ExternalBudgetVal<T> {
    
    /** The object. */
    private T object;
    
    /**
     * Instantiates a new external budget val.
     *
     * @param value the value
     */
    public ExternalBudgetVal(T object) {
        this.object = object;
    }
    
    /**
     * Gets the raw value.
     *
     * @return the raw value
     */
    public Integer getRawValue() {
        if (this.object instanceof ExternalBudgetTimeValue) {
            return ((ExternalBudgetTimeValue) this.object).v();
        }
        if (this.object instanceof ExtBudgetNumberPerson) {
            return ((ExtBudgetNumberPerson) this.object).v();
        }
        if (this.object instanceof ExternalBudgetMoneyValue) {
            return ((ExternalBudgetMoneyValue) this.object).v();
        }
        if (this.object instanceof ExtBudgetNumericalVal) {
            return ((ExtBudgetNumericalVal) this.object).v();
        }
        if (this.object instanceof ExtBudgetUnitPrice) {
            return ((ExtBudgetUnitPrice) this.object).v();
        }
        throw new RuntimeException("Not external budget attribute");
    }
}
