/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.dailyunit;

import nts.arc.time.GeneralDate;

/**
 * The Interface ExternalBudgetDailyRepository.
 */
public interface ExternalBudgetDailyRepository {
    
    /**
     * Adds the.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T> void add(ExternalBudgetDaily<T> domain);
    
    /**
     * Update.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T>void update(ExternalBudgetDaily<T> domain);
    
    /**
     * Checks if is existed.
     *
     * @param workplaceId the workplace id
     * @param actualDate the actual date
     * @param extBudgetCd the ext budget cd
     * @return true, if is existed
     */
    boolean isExisted(String workplaceId, GeneralDate actualDate, String extBudgetCd);
}
