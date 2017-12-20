/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.auth.dom.wkpmanager;

import java.util.List;

import nts.uk.ctx.at.auth.dom.wkpmanager.dom.EmpBasicInfoImport;
import nts.uk.ctx.at.auth.dom.wkpmanager.dom.PersonInfoImport;

/**
 * The Interface PersonInfoAdapter.
 */
public interface EmpInfoAdapter {
	
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

}