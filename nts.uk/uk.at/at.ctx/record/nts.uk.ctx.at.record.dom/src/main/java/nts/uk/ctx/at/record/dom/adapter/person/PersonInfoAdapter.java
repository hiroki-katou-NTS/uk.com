/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.person;

import java.util.List;

/**
 * The Interface PersonInfoAdapter.
 */
public interface PersonInfoAdapter {

	
	/**
	 * Gets the person info.
	 *
	 * @param employeeId the employee id
	 * @return the person info
	 */
	PersonInfoImportedDto getPersonInfo(String employeeId);
	/**
	 * get list person info by list employeeId (sid)
	 * @param sid
	 * @return
	 */
	List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid);
}
