/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import java.util.List;

/**
 * The Interface SearchEmployeePub.
 */
public interface SearchEmployeePub {

	/**
	 * Search by employee code.
	 *
	 * @param sCd the s cd
	 * @param systemType the system type
	 * @return the list
	 */
	// <<Public>> 社員コードで検索する
	public List<String> searchByEmployeeCode(String sCd, Integer systemType);
}
