/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.service;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceService.
 */
public interface WorkplaceService {

    /**
     * Update previous history.
     *
     * @param companyId the company id
     * @param prevHistId the prev hist id
     * @param endĐate the end đate
     */
    void updatePreviousHistory(String companyId, String prevHistId, GeneralDate endĐate);
}
