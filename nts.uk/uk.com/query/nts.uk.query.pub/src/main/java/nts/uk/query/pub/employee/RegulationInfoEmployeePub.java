/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import java.util.List;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface RegulationInfoEmployeePub.
 */
public interface RegulationInfoEmployeePub {

	/**
	 * Find.
	 *
	 * @param query the query
	 * @return the list
	 */
	// <<Public>> 管理者条件で社員を検索して並び替える
	// <<Public>> 個人情報条件で社員を検索して並び替える
	// <<Public>> 就業条件で社員を検索して並び替える
	public List<RegulationInfoEmployeeExport> find(EmployeeSearchQueryDto query);

	/**
	 * Sort employee.
	 *
	 * @param comId the com id
	 * @param sIds the s ids
	 * @param systemType the system type
	 * @param orderNo the order no
	 * @param nameType the name type
	 * @param referenceDate the reference date
	 * @return the list
	 */
	// <<Public>> 社員を並び替える
	public List<String> sortEmployee(String comId, List<String> sIds, Integer systemType, Integer orderNo,
			Integer nameType, GeneralDateTime referenceDate);

	/**
	 * Sort employee.
	 *
	 * @param comId the com id
	 * @param sIds the s ids
	 * @param orders the orders
	 * @param referenceDate the reference date
	 * @return the list
	 */
	// <<Public>> 社員を並び替える(任意)
	public List<String> sortEmployee(String comId, List<String> sIds, List<SortingConditionOrderDto> orders,
			GeneralDateTime referenceDate);
}
