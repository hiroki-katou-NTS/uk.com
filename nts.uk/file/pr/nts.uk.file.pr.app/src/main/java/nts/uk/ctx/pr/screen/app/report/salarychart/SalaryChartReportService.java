/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.Denomination;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.DepartmentData;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.EmployeeData;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.SalaryChartDataSource;
import nts.uk.ctx.pr.screen.app.report.salarychart.query.SalaryChartReportQuery;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SalaryChartReportService.
 */
@Stateless
public class SalaryChartReportService extends ExportService<SalaryChartReportQuery> {

	/** The generator. */
	@Inject
	private SalaryChartReportGenerator generator;

	/** The repository. */
	@Inject
	private SalarychartRepository repository;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<SalaryChartReportQuery> context) {

		// Get Query
		SalaryChartReportQuery query = context.getQuery();	

		// Query data.
		List<EmployeeData> items = this.repository.getItems(AppContexts.user().companyCode(), query);
		List<EmployeeData> empList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			DepartmentData depData = DepartmentData
					.builder()
					.depCode("d0000000" + (i + 1))
					.depName("部門 " + (i + 1))
					.depPath("depPath" + (i + 1))
					.build();
			// Employee Emp
			EmployeeData emp = EmployeeData.builder()
					.empCode("e0000000" + (i + 1))
					.empName("社員 " + (i + 1))
					.paymentAmount(3000.0 + 100 * i)
					.departmentData(depData)
					.denomination(null)
					.build();
			emp.setDenomination(divisionDeno(emp.getPaymentAmount()));
			
			// Employee Emp1
			EmployeeData emp1 = EmployeeData
					.builder()
					.empCode("E0000000" + (i + 1))
					.empName("社員E " + (i + 1))
					.paymentAmount(36780.0 + 100 * i)
					.departmentData(depData)
					.denomination(null)
					.build();
			emp1.setDenomination(divisionDeno(emp1.getPaymentAmount()));
			empList.add(emp);
			empList.add(emp1);
		}
		if(items == null){
			items = empList;
		}

		// Create header object.

		// Create Data Source
		val dataSource = SalaryChartDataSource.builder().salaryChartHeader(null).employeeList(items).build();

		// Call generator.
		this.generator.generate(context.getGeneratorContext(), dataSource, query);
	}
	
	/**
	 * Division deno.
	 *
	 * @param paymentAmount the payment amount
	 * @return the map
	 */
	private Map<Denomination, Long> divisionDeno(Double paymentAmount) {
		Map<Denomination, Long> deno = new HashMap<Denomination, Long>();		
		Double amount = paymentAmount;
		for(Denomination d: Denomination.values()){
			if (amount >= d.deno){
				Long quantity = (long) (amount / d.deno);
				deno.put(d, quantity);
				amount = amount % d.deno;
			} else{
				deno.put(d, 0l);
			}
		}
		return deno;
	}
	
}
