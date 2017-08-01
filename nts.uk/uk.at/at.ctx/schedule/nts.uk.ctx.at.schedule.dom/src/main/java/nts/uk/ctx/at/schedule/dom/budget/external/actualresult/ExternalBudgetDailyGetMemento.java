/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

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
     * Gets the process date.
     *
     * @return the process date
     */
    Date getProcessDate();

    /**
     * Gets the workplace id.
     *
     * @return the workplace id
     */
    String getWorkplaceId();
}
