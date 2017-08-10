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
     * @param wpkCode the wpk code
     * @param date the date
     * @return the list
     */
    List<String> findWpkIdList(String wpkCode, Date date);
}
