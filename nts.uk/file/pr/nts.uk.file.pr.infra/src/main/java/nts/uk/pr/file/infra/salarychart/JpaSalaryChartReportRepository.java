/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.salarychart;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.screen.app.report.salarychart.SalarychartRepository;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.EmployeeData;
import nts.uk.ctx.pr.screen.app.report.salarychart.query.SalaryChartReportQuery;

/**
 * The Class JpaSalaryChartReportRepository.
 */
@Stateless
public class JpaSalaryChartReportRepository implements SalarychartRepository {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qpp009.SalarychartRepository#getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qpp009.query.SalaryChartReportQuery)
	 */
	@Override
	public List<EmployeeData> getItems(String companyCode, SalaryChartReportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
