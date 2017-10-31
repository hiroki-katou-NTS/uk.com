/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Interface ExtBudgTimeZoneSetMemento.
 *
 * @param <T> the generic type
 */
public interface ExtBudgTimeZoneSetMemento<T> {
    
    /**
     * Sets the actual values.
     *
     * @param actualValues the new actual values
     */
    void setActualValues(List<ExternalBudgetTimeZoneVal<T>> actualValues);

    /**
     * Sets the ext budget code.
     *
     * @param extBudgetCode the new ext budget code
     */
    void setExtBudgetCode(ExternalBudgetCd extBudgetCode);

    /**
     * Sets the actual date.
     *
     * @param actualDate the new actual date
     */
    void setActualDate(Date actualDate);

    /**
     * Sets the workplace id.
     *
     * @param workplaceId the new workplace id
     */
    void setWorkplaceId(String workplaceId);
}
