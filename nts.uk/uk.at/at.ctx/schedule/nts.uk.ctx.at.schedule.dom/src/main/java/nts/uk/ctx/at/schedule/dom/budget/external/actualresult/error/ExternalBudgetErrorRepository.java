/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error;

import java.util.List;

/**
 * The Interface ExternalBudgetErrorRepository.
 */
public interface ExternalBudgetErrorRepository {

    /**
     * Adds the.
     *
     * @param domain the domain
     */
    void add(ExternalBudgetError domain);
    
    /**
     * Find by execution id.
     *
     * @param executionId the execution id
     * @return the list
     */
    List<ExternalBudgetError> findByExecutionId(String executionId);
}
