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
	PersonInfoImportedImport getPersonInfo(String employeeId);
	
	/**
	 * Gets the list person.
	 *
	 * @param listPersonId the list person id
	 * @return the list person
	 */
	List<PersonInfoImportedDto> getListPerson(List<String> listPersonId);
	
	/**
	 * Gets the list person info.
	 *
	 * @param listSid the list sid
	 * @return the list person info
	 */
	List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid);
}
