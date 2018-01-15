/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service;

import nts.uk.ctx.bs.employee.dom.workplace.CreateWorkpceType;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;

/**
 * The Interface WkpConfigInfoService.
 */
public interface WkpConfigInfoService {

	/**
	 * Copy wkp config info hist.
	 *
	 * @param companyId the company id
	 * @param latestHistIdCurrent the latest hist id current
	 * @param newHistId the new hist id
	 */
    void copyWkpConfigInfoHist(String companyId, String latestHistIdCurrent, String newHistId);
	
    /**
     * Update wkp hierarchy.
     *
     * @param companyId the company id
     * @param historyId the history id
     * @param wkpIdSelected the wkp id selected
     * @param createType the create type
     */
    void updateWkpHierarchy(String historyId, String wkpIdSelected, Workplace newWorkplace, CreateWorkpceType createType);
}
