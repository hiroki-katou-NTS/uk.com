/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.person;

/**
 * The Interface PersonAdapter.
 */
public interface PersonInfoAdapter {

	/**
	 * Gets the person info.
	 *
	 * @param sID the s ID
	 * @return the person info
	 */
	PersonInfoImportedDto getPersonInfo(String sID);
}
