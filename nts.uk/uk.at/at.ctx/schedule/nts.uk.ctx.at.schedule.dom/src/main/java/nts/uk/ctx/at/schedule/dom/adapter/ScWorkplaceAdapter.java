/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter;

import java.util.Date;
import java.util.List;

/**
 * The Interface ScWorkplaceAdapter.
 */
public interface ScWorkplaceAdapter {
    
    /**
     * Find wpk id list.
     *
     * @param companyId the company id
     * @param wpkCode the wpk code
     * @param baseDate the base date
     * @return the list
     */
    List<String> findWpkIdList(String companyId, String wpkCode, Date baseDate);
}
