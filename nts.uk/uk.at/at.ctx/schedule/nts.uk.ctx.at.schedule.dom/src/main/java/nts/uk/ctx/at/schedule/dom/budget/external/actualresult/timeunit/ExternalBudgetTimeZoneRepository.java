/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import nts.arc.time.GeneralDate;

/**
 * The Interface ExternalBudgetTimeZoneRepository.
 */
public interface ExternalBudgetTimeZoneRepository {
    
    /**
     * Adds the.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T> void add(ExternalBudgetTimeZone<T> domain);
    
    /**
     * Update.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T> void update(ExternalBudgetTimeZone<T> domain);
    
    /**
     * Checks if is existed.
     *
     * @param workplaceId the workplace id
     * @param actualDate the actual date
     * @param extBudgetCode the ext budget code
     * @return true, if is existed
     */
    boolean isExisted(String workplaceId, GeneralDate actualDate, String extBudgetCode);
}
