/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import java.util.List;

/**
 * The Interface EmployeeInformationPub.
 */
public interface EmployeeInformationPub {

	/**
	 * Find.
	 *
	 * @param param the param
	 * @return the list
	 */
	// <<Public>> 社員の情報を取得する
	public List<EmployeeInformationExport> find(EmployeeInformationQueryDto param);
}
