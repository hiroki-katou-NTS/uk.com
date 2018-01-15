/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;

/**
 * The Interface ExtBudgTimeZoneValGetMemento.
 *
 * @param <T> the generic type
 */
public interface ExtBudgTimeZoneValGetMemento<T> {

    /**
     * Gets the time period.
     *
     * @return the time period
     */
    int getTimePeriod();

    /**
     * Gets the actual value.
     *
     * @return the actual value
     */
    ExternalBudgetVal<T> getActualValue();
}
