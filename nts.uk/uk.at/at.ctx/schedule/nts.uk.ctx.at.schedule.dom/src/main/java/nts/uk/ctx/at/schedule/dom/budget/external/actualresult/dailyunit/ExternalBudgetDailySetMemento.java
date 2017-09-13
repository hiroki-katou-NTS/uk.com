/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.dailyunit;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;

/**
 * The Interface ExternalBudgetDailySetMemento.
 *
 * @param <T> the generic type
 */
public interface ExternalBudgetDailySetMemento<T> {

    /**
     * Sets the actual value.
     *
     * @param <T> the generic type
     * @param actualValue the new actual value
     */
    void setActualValue(ExternalBudgetVal<T> actualValue);

    /**
     * Sets the ext budget code.
     *
     * @param extBudgetCode the new ext budget code
     */
    void setExtBudgetCode(ExternalBudgetCd extBudgetCode);

    /**
     * Sets the actual date.
     *
     * @param processDate the new actual date
     */
    void setActualDate(Date processDate);

    /**
     * Sets the workplace id.
     *
     * @param workplaceId the new workplace id
     */
    void setWorkplaceId(String workplaceId);
}
