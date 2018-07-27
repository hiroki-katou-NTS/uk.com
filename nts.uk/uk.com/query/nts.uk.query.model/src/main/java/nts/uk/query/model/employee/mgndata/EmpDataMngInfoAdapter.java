/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee.mgndata;

import java.util.List;

/**
 * The Interface EmpDataMngInfoAdapter.
 */
public interface EmpDataMngInfoAdapter {

	/**
	 * Gets the employee not delete in company.
	 *
	 * @param cId the c id
	 * @param sCd the s cd
	 * @return the employee not delete in company
	 */
	List<String> findNotDeletedBySCode(String cId, String sCd);

	/**
	 * Find by list person id.
	 *
	 * @param comId the com id
	 * @param pIds the ids
	 * @return the list
	 */
	List<String> findByListPersonId(String comId, List<String> pIds);
}
