/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;

/**
 * The Class ExternalBudgetTimeZoneVal.
 * 外部予算実績時間帯値
 *
 * @param <T> the generic type
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = {"timePeriod"})
public class ExternalBudgetTimeZoneVal<T> extends DomainObject {

    /** The time period. */
    // 外部予算実績時間帯NO
    private int timePeriod;
    
    /** The actual value. */
    // 外部予算実績値
    private ExternalBudgetVal<T> actualValue;
    
    /**
     * Instantiates a new external budget time zone val.
     *
     * @param memento the memento
     */
    public ExternalBudgetTimeZoneVal(ExtBudgTimeZoneValGetMemento<T> memento) {
        this.timePeriod = memento.getTimePeriod();
        this.actualValue = memento.getActualValue();
    }
    
    /**
     * Save to mememto.
     *
     * @param memento the memento
     */
    public void saveToMememto(ExtBudgTimeZoneValSetMemento<T> memento) {
        memento.setTimePeriod(this.timePeriod);
        memento.setActualValue(this.actualValue);
    }
}
