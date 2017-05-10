/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable;

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
import nts.arc.time.GeneralDate;
import nts.uk.file.pr.app.export.denominationtable.data.DenoTableHeaderData;
import nts.uk.file.pr.app.export.denominationtable.data.Denomination;
import nts.uk.file.pr.app.export.denominationtable.data.DepartmentData;
import nts.uk.file.pr.app.export.denominationtable.data.EmployeeData;
import nts.uk.file.pr.app.export.denominationtable.data.DenominationTableData;
import nts.uk.file.pr.app.export.denominationtable.query.DenoTableReportQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseErasProvider;

/**
 * The Class SalaryChartReportService.
 */
@Stateless
public class DenoTableReportService extends ExportService<DenoTableReportQuery> {

	/** The generator. */
	@Inject
	private DenoTableReportGenerator generator;

	/** The repository. */
	@Inject
	private DenoTableRepository repository;
	
	private static final int TWO_THOUSANDS = 2000;
	
	/** The japanese provider. */
    @Inject
    private JapaneseErasProvider japaneseProvider;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<DenoTableReportQuery> context) {

		// GET QUERY
		DenoTableReportQuery query = context.getQuery();	
		String[] personIds = { "99900000-0000-0000-0000-000000000001", "99900000-0000-0000-0000-000000000002",
				"99900000-0000-0000-0000-000000000003", "99900000-0000-0000-0000-000000000004",
				"99900000-0000-0000-0000-000000000005", "99900000-0000-0000-0000-000000000006",
				"99900000-0000-0000-0000-000000000007", "99900000-0000-0000-0000-000000000008",
				"99900000-0000-0000-0000-000000000009", "99900000-0000-0000-0000-000000000010"};
			query.setPIdList(Arrays.asList(personIds));

		// QUERY DATA
		List<EmployeeData> items = this.repository.getItems(AppContexts.user().companyCode(), query);
		
		List<Integer> selectedLevels = Arrays.asList(1, 2, 3, 4, 5, 6);
		DenoTableReportQuery query1 = new DenoTableReportQuery();
			query1.setIsCalculateTotal(true);
			query1.setIsPrintDepHierarchy(true);
			query1.setIsPrintTotalOfDepartment(true);
			query1.setSelectedBreakPageCode(3);
			query1.setSelectedUse2000yen(0);
			query1.setIsPrintDepHierarchy(true);
			query1.setSelectedLevels(selectedLevels);
			query1.setYearMonth(2016);
			query1.setSelectedBreakPageHierarchyCode(4);
			query1.setIsPrintDetailItem(true);

		List<EmployeeData> empList = this.createData(query1);

		if (items == null) {
			items = empList;
			query = query1;
		}
		
		// DIVISION DENOMINATION
		items.stream().forEach(emp -> {
			emp.setDenomination(this.divisionDeno(emp.getPaymentAmount(), context.getQuery()));
		});

		//  CONVERT YEARMONTH JAPANESE 
        StringBuilder japanYM = new StringBuilder("【処理年月：");
        japanYM.append(convertYearMonthJP(query.getYearMonth()));
		// CREATE HEADER OBJECT
		DenoTableHeaderData headerData = DenoTableHeaderData.builder()
				.departmentInfo("【部門： A部門～C部門（3部門）】")
				.categoryInfo("【分類： 正社員～アルバイト（５分類）】")
				.positionInfo("【職位： 部長～一般（12職位）】")
				.targetYearMonth(japanYM.toString())
				.build();

		// CREATE DATA SOURCE
		val dataSource = DenominationTableData.builder()
				.salaryChartHeader(headerData)
				.employeeList(items)
				.build();

		// GENERATE REPORT
		this.generator.generate(context.getGeneratorContext(), dataSource, query);
	}
	
	private String convertYearMonthJP(Integer yearMonth) {
        String firstDay = "01";
        String tmpDate = yearMonth.toString().concat(firstDay);
        String dateFormat = "yyyyMMdd";
        GeneralDate generalDate = GeneralDate.fromString(tmpDate, dateFormat);
        JapaneseDate japaneseDate = this.japaneseProvider.toJapaneseDate(generalDate);
        return japaneseDate.era() + japaneseDate.year() + "年 " + japaneseDate.month() + "月度給与】";
    }
	
	/**
	 * Division deno.
	 *
	 * @param paymentAmount the payment amount
	 * @return the map
	 */
	private Map<Denomination, Long> divisionDeno(Double paymentAmount, DenoTableReportQuery query) {
		Map<Denomination, Long> deno = new HashMap<>();
		Double amount = paymentAmount;
		for (Denomination d : Denomination.values()) {
			if (amount >= d.deno) {
				if((query.getSelectedUse2000yen() == 0) && (d.deno == TWO_THOUSANDS)){
					deno.put(d, 0L);
					continue;
				}
				Long quantity = (long) (amount / d.deno);
				deno.put(d, quantity);
				amount = amount % d.deno;
			} 
			else {
				deno.put(d, 0L);
			}
		}
		return deno;
	}
	
	private List<EmployeeData> createData( DenoTableReportQuery query) {
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
			emp.setDenomination(this.divisionDeno(emp.getPaymentAmount(), query));
			empList.add(emp);
			
			// Emp 1
			EmployeeData emp1 = EmployeeData.builder()
					.empCode("e0000000" + (i + 1))
					.empName("e社員 " + (i + 1))
					.paymentAmount(36778.0 + 3454 * i)
					.departmentData(depData.get(i))
					.denomination(null)
					.build();
			emp1.setDenomination(this.divisionDeno(emp1.getPaymentAmount(), query));
			empList.add(emp1);
		}	
		return empList;
	}
	
}
