/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart;

import java.util.ArrayList;
import java.util.Arrays;
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
		
		List<Integer> selectedLevels = Arrays.asList(1, 5, 6);
		SalaryChartReportQuery query1 = new SalaryChartReportQuery();
			query1.setIsCalculateTotal(true);
			query1.setIsPrintDepHierarchy(true);
			query1.setIsPrintTotalOfDepartment(true);
			query1.setSelectedBreakPageCode(2);
			query1.setSelectedUse2000yen(0);
			query1.setIsPrintDepHierarchy(true);
			query1.setSelectedLevels(selectedLevels);
			query1.setTargetYear(2016);

		List<EmployeeData> empList = this.createData(query1);

		if (items == null) {
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
	private Map<Denomination, Long> divisionDeno(Double paymentAmount, SalaryChartReportQuery query) {
		Map<Denomination, Long> deno = new HashMap<Denomination, Long>();
		Double amount = paymentAmount;
		for (Denomination d : Denomination.values()) {
			if (amount >= d.deno) {
				if((query.getSelectedUse2000yen() == 0) && (d.deno == 2000)){
					deno.put(d, 0l);
					continue;
				}
				Long quantity = (long) (amount / d.deno);
				deno.put(d, quantity);
				amount = amount % d.deno;
			} else {
				deno.put(d, 0l);
			}
		}
		return deno;
	}
	
	private List<EmployeeData> createData( SalaryChartReportQuery query) {
		List<EmployeeData> empList = new ArrayList<>();
		List<DepartmentData> depData = new ArrayList<>();
		List<String> depPath = new ArrayList<>();
		depPath.add("A");
		depPath.add("A_B");		
		depPath.add("A_B_C");
		depPath.add("A_B_C_D");
		depPath.add("A_B_C_D_E");
		depPath.add("A_B_cc");
		depPath.add("A_B_cc_dd");
		depPath.add("A_B_cc_dd_ee");
		depPath.add("A_B_cc_dd_ee_ff");
		depPath.add("A_bb");
		
		depPath.add("a");
		depPath.add("a_b");
		depPath.add("a_b_c");
		depPath.add("a_B");
		
		List<String> depCode = new ArrayList<>();
		depCode.add("A-A1");
		depCode.add("A-B2");
		depCode.add("A-C3");
		depCode.add("A-D4");
		depCode.add("A-E5");
		depCode.add("A-cc3");
		depCode.add("A-dd4");
		depCode.add("A-ee5");
		depCode.add("A-ff6");
		depCode.add("A-bb2");
		
		depCode.add("a-1");
		depCode.add("a-b2");
		depCode.add("a-c3");
		depCode.add("a-B2");
		
		List<Integer> depLevels = Arrays.asList(1, 2, 3, 4, 5, 3, 4, 5, 6, 2, 1, 2, 3, 2);
		
		for (int i = 0; i < depCode.size(); i++) {
			DepartmentData dep = DepartmentData.builder()
					.depCode(depCode.get(i))
					.depName("部門 " + (i + 1))
					.depPath(depPath.get(i))
					.depLevel(depLevels.get(i))
					.build();
			depData.add(dep);
		}
		
		for(int i = 0; i < depCode.size(); i++){
			// Emp 
			EmployeeData emp = EmployeeData.builder()
					.empCode("E0000000" + (i + 1))
					.empName("E社員 " + (i + 1))
					.paymentAmount(17809.0 + 1234 * i)
					.departmentData(depData.get(i))
					.denomination(null)
					.build();
			emp.setDenomination(divisionDeno(emp.getPaymentAmount(), query));
			empList.add(emp);
			
			// Emp 1
			EmployeeData emp1 = EmployeeData.builder()
					.empCode("e0000000" + (i + 1))
					.empName("e社員 " + (i + 1))
					.paymentAmount(36778.0 + 3454 * i)
					.departmentData(depData.get(i))
					.denomination(null)
					.build();
			emp1.setDenomination(divisionDeno(emp1.getPaymentAmount(), query));
			empList.add(emp1);
		}	
		return empList;
	}
	
}
