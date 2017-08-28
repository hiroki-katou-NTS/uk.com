/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.util.Date;
import java.util.List;

/**
 * The Interface WorkplaceAdapter.
 */
public interface WorkplaceAdapter {
    
    /**
     * Find wpk id list.
     *
     * @param companyId the company id
     * @param wpkCode the wpk code
     * @param baseDate the bas date
     * @return the list
     */
    List<String> findWpkIdList(String companyId, String wpkCode, Date baseDate);
}
