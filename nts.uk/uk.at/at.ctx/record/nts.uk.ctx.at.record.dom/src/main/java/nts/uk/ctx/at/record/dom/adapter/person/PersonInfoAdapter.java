/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.person;

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
}
