/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.dailyunit;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;

/**
 * The Interface ExternalBudgetDailyGetMemento.
 *
 * @param <T> the generic type
 */
public interface ExternalBudgetDailyGetMemento<T> {
    
    /**
     * Gets the actual value.
     *
     * @return the actual value
     */
    ExternalBudgetVal<T> getActualValue();

    /**
     * Gets the ext budget code.
     *
     * @return the ext budget code
     */
    ExternalBudgetCd getExtBudgetCode();

    /**
     * Gets the actual date.
     *
     * @return the actual date
     */
    Date getActualDate();

    /**
     * Gets the workplace id.
     *
     * @return the workplace id
     */
    String getWorkplaceId();
}
