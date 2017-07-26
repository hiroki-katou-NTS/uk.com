/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

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
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    void setCompanyId(String companyId);

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
     * Sets the process date.
     *
     * @param processDate the new process date
     */
    void setProcessDate(Date processDate);

    /**
     * Sets the workplace id.
     *
     * @param workplaceId the new workplace id
     */
    void setWorkplaceId(String workplaceId);
}
