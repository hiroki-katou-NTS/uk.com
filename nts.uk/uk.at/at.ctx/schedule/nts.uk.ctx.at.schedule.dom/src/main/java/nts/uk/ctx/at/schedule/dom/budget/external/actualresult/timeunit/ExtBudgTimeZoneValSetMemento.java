/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;

/**
 * The Interface ExtBudgTimeZoneValSetMemento.
 *
 * @param <T> the generic type
 */
public interface ExtBudgTimeZoneValSetMemento<T> {

    /**
     * Sets the time period.
     *
     * @param timePeriod the new time period
     */
    void setTimePeriod(int timePeriod);

    /**
     * Sets the actual value.
     *
     * @param actualValue the new actual value
     */
    void setActualValue(ExternalBudgetVal<T> actualValue);
}
