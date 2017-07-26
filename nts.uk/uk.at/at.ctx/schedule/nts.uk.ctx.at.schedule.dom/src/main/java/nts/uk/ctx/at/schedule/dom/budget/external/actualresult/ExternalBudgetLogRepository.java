/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.util.List;

/**
 * The Interface ExternalBudgetLogRepository.
 */
public interface ExternalBudgetLogRepository {
    
    /**
     * Adds the.
     *
     * @param domain the domain
     */
    void add(ExternalBudgetLog domain);
    
    /**
     * Find all by company id.
     *
     * @param companyId the company id
     * @return the list
     */
    List<ExternalBudgetLog> findAllByCompanyId(String companyId);
}
