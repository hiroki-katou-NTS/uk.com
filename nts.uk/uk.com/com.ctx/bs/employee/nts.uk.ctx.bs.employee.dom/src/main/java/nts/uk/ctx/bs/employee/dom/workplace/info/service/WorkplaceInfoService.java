/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.info.service;

public interface WorkplaceInfoService {

	/**
	 * Copy workplace history.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param historyId the history id
	 */
	public void copyWorkplaceHistory(String companyId, String workplaceId,String historyId);
	
}
