/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service;

public interface WkpConfigInfoService {

	/**
	 * Copy workplace config history.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 */
	public void copyWorkplaceConfigHistory(String companyId,String historyId);
}
