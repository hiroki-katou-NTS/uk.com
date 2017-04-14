/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart;

import java.util.List;

import nts.uk.ctx.pr.screen.app.report.salarychart.data.EmployeeData;
import nts.uk.ctx.pr.screen.app.report.salarychart.query.SalaryChartReportQuery;

/**
 * The Interface SalarychartRepository.
 */
public interface SalarychartRepository {

	/**
	 * Gets the items.
	 *
	 * @param code the code
	 * @return the items
	 */
	List<EmployeeData> getItems(String companyCode, SalaryChartReportQuery query);
	
	
}
