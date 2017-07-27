/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

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
    public ExternalBudgetVal(Long value) {
        this.initObject(value);
    }
    
    /**
     * Inits the object.
     *
     * @param value the value
     */
    @SuppressWarnings("unchecked")
    private void initObject(Long value) {
        if (this.object instanceof ExtBudgetTime) {
            this.object = (T) new ExtBudgetTime(value);
        }
        if (this.object instanceof ExtBudgetNumberPerson) {
            this.object = (T) new ExtBudgetNumberPerson(value.intValue());
        }
        if (this.object instanceof ExtBudgetMoney) {
            this.object = (T) new ExtBudgetMoney(value.intValue());
        }
        if (this.object instanceof ExtBudgetNumericalVal) {
            this.object = (T) new ExtBudgetNumericalVal(value.intValue());
        }
        if (this.object instanceof ExtBudgetUnitPrice) {
            this.object = (T) new ExtBudgetUnitPrice(value.intValue());
        }
        throw new RuntimeException("Not external budget attribute");
    }
    
    /**
     * Gets the raw value.
     *
     * @return the raw value
     */
    public Long getRawValue() {
        if (this.object instanceof ExtBudgetTime) {
            return ((ExtBudgetTime) this.object).v();
        }
        if (this.object instanceof ExtBudgetNumberPerson) {
            return new Long(((ExtBudgetNumberPerson) this.object).v());
        }
        if (this.object instanceof ExtBudgetMoney) {
            return new Long(((ExtBudgetMoney) this.object).v());
        }
        if (this.object instanceof ExtBudgetNumericalVal) {
            return new Long(((ExtBudgetNumericalVal) this.object).v());
        }
        if (this.object instanceof ExtBudgetUnitPrice) {
            return new Long(((ExtBudgetUnitPrice) this.object).v());
        }
        throw new RuntimeException("Not external budget attribute");
    }
}
