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
	PersonInfoImport getPersonInfo(String employeeId);
	
	/**
	 * get list person by list person ID
	 * @param personId
	 * @return
	 */
	List<PersonInfoImport> getByListId(List<String> personId);
	
	/**
	 * Gets the list person info.
	 *
	 * @param listSid the list sid
	 * @return the list person info
	 */
	List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid);
	
	EmpBasicInfoImport getEmpBasicInfoImport(String sID);

}
