/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import java.util.List;

/**
 * The Interface EmployeeInformationRepository.
 */
public interface EmployeeInformationRepository {

	/**
	 * Find.
	 *
	 * @param param the param
	 * @return the list
	 */
	// <<Public>> 社員の情報を取得する
	public List<EmployeeInformation> find(EmployeeInformationQuery param);
}
