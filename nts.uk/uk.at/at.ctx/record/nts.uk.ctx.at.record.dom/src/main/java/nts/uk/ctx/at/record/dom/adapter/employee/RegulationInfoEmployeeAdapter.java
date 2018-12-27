/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.employee;

import java.util.List;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface RegulationInfoEmployeeAdapter.
 */
public interface RegulationInfoEmployeeAdapter {
	
	/**
	 * Gets the list employee.
	 *
	 * @param comId the com id
	 * @param sIds the s ids
	 * @param orders the orders
	 * @param referenceDate the reference date
	 * @return the list employee
	 */
	List<String> sortEmployees(String comId, List<String> sIds, List<SortingConditionOrderImport> orders, GeneralDateTime referenceDate);
}
