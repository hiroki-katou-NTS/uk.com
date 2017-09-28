/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service;

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
    public void copyWkpConfigInfoHist(String companyId, String latestHistIdCurrent, String newHistId);
	
}
