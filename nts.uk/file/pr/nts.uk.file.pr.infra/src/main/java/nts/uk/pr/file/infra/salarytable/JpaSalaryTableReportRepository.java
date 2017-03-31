/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.salarytable;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.salarytable.SalaryTableRepository;
import nts.uk.file.pr.app.export.salarytable.data.EmployeeData;
import nts.uk.file.pr.app.export.salarytable.query.SalaryTableReportQuery;



/**
 * The Class JpaSalaryChartReportRepository.
 */
@Stateless
public class JpaSalaryTableReportRepository implements SalaryTableRepository {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qpp009.SalarychartRepository#getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qpp009.query.SalaryChartReportQuery)
	 */
	@Override
	public List<EmployeeData> getItems(String companyCode, SalaryTableReportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
