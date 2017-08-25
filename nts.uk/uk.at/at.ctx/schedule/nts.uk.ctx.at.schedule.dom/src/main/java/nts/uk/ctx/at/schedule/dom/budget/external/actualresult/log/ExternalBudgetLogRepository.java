/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

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
     * Update.
     *
     * @param domain the domain
     */
    void update(ExternalBudgetLog domain);
    
    /**
     * Find ext budget log by execute id.
     *
     * @param executeId the execute id
     * @return the list
     */
    Optional<ExternalBudgetLog> findExtBudgetLogByExecuteId(String executeId);
    
    /**
     * Find external budget log.
     *
     * @param employeeId the employee id
     * @param startDateTime the start date
     * @param endDateTime the end date
     * @param listState the list state
     * @return the list
     */
    List<ExternalBudgetLog> findExternalBudgetLog(String employeeId, GeneralDateTime startDateTime,
            GeneralDateTime endDateTime, List<Integer> listState);
    
    /**
     * Checks if is existed.
     *
     * @param executeId the execute id
     * @return true, if is existed
     */
    boolean isExisted(String executeId);
}
