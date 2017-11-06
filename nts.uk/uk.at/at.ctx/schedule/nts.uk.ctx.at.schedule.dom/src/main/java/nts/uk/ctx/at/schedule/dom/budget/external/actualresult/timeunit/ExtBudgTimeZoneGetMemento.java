/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Interface ExtBudgTimeZoneGetMemento.
 *
 * @param <T> the generic type
 */
public interface ExtBudgTimeZoneGetMemento<T> {

    /**
     * Gets the actual values.
     *
     * @param <T> the generic type
     * @return the actual values
     */
    List<ExternalBudgetTimeZoneVal<T>> getActualValues();

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
