/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.service;

import nts.arc.time.GeneralDate;

/**
 * The Interface WkpConfigService.
 */
public interface WkpConfigService {

	/**
	 * Validate add history.
	 *
	 * @param companyId the company id
	 * @param addHistStart the add hist start
	 * @return the string
	 */
	String validateAddHistory(String companyId, GeneralDate addHistStart);
	
	/**
	 * Update prev history.
	 *
	 * @param prevHistId the prev hist id
	 * @param endĐate the end đate
	 */
	void updatePrevHistory(String companyId,String prevHistId,GeneralDate endĐate);
}
