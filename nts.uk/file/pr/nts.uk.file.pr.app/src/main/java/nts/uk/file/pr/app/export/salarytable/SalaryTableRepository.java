/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.salarytable;

import java.util.List;

import nts.uk.file.pr.app.export.salarytable.data.EmployeeData;
import nts.uk.file.pr.app.export.salarytable.query.SalaryTableReportQuery;


/**
 * The Interface SalarychartRepository.
 */
public interface SalaryTableRepository {

	/**
	 * Gets the items.
	 *
	 * @param code the code
	 * @return the items
	 */
	List<EmployeeData> getItems(String companyCode, SalaryTableReportQuery query);
	
	
}
