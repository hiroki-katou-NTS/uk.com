/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.util.List;

import nts.arc.time.GeneralDate;

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
     * Find external budget log.
     *
     * @param employeeId the employee id
     * @param startDate the start date
     * @param listState the list state
     * @return the list
     */
    List<ExternalBudgetLog> findExternalBudgetLog(String employeeId, GeneralDate startDate,
            List<Integer> listState);
}
