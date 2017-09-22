/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service;

import nts.arc.time.GeneralDate;

/**
 * The Interface WkpConfigInfoService.
 */
public interface WkpConfigInfoService {

	/**
	 * Copy wkp config info hist.
	 *
	 * @param companyId the company id
	 * @param firstHistoryId the first history id
	 * @param addNewHistId the add new hist id
	 */
	public void copyWkpConfigInfoHist(String companyId,String firstHistoryId,String addNewHistId);
	
	/**
	 * Update previous.
	 *
	 * @param prevHistId the prev hist id
	 * @param addHistStart the add hist start
	 */
	public void updatePrevious(String prevHistId,GeneralDate addHistStart);
}
