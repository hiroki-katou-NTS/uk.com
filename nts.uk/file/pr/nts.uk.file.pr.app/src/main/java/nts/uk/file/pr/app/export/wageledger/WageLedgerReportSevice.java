/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery.LayoutType;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.newlayout.TotalData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.DeductionData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.MonthlyData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageLedgerReportSevice.
 */
@Stateless
public class WageLedgerReportSevice extends ExportService<WageLedgerReportQuery>{
	
	/** The repository. */
	@Inject
	private WageLedgerDataRepository repository;
	
	/** The generator. */
	@Inject
	private WLOldLayoutReportGenerator oldGenerator;
	
	/** The new generator. */
	@Inject
	private WLNewLayoutReportGenerator newGenerator;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<WageLedgerReportQuery> context) {
		
		// TODO : validate query.
		WageLedgerReportQuery query = context.getQuery();
		String companyCode = AppContexts.user().companyCode();
		query.baseDate = new Date();
		query.employeeIds = Arrays.asList("test");
		if (!this.repository.hasReportData(companyCode, query)) {
			throw new RuntimeException("None Data!");
		}
		
		// Query Data.
		@SuppressWarnings("unused")
		List<WLOldLayoutReportData> reportData = this.repository.findReportDatas(companyCode, query, WLOldLayoutReportData.class);
		
		// Fake data.
		WLOldLayoutReportData fakeReportData = WLOldLayoutReportData.builder()
				.bonusMonthList(Arrays.asList(1, 3, 7, 9, 12))
				.salaryMonthList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))
				.headerData(HeaderReportData.builder()
						.departmentCode("DEP1")
						.departmentName("Department 1")
						.employeeCode("EM1")
						.employeeName("Employee 1")
						.position("Project manager")
						.sex("Man")
						.targetYear(query.targetYear)
						.build())
				.salaryPaymentData(PaymentData.builder()
						.totalTax(ReportItemDto.builder()
								.name("Total Tax")
								.monthlyDatas(this.fakeMonthlyData())
								.build())
						.totalSubsidy(ReportItemDto.builder()
								.monthlyDatas(this.fakeMonthlyData())
								.name("Total Subsidy")
								.build())
						.totalTaxExemption(ReportItemDto.builder()
								.monthlyDatas(this.fakeMonthlyData())
								.name("Total Tax Exemption")
								.build())
						.aggregateItemList(this.fakeReportItems())
						.build())
				.bonusPaymentData(PaymentData.builder()
						.totalTax(ReportItemDto.builder()
								.name("Total Tax")
								.monthlyDatas(this.fakeMonthlyData())
								.build())
						.totalSubsidy(ReportItemDto.builder()
								.monthlyDatas(this.fakeMonthlyData())
								.name("Total Subsidy")
								.build())
						.totalTaxExemption(ReportItemDto.builder()
								.monthlyDatas(this.fakeMonthlyData())
								.name("Total Tax Exemption")
								.build())
						.aggregateItemList(this.fakeReportItems())
						.build())
				.netSalaryData(ReportItemDto.builder()
						.name("Net salary")
						.monthlyDatas(this.fakeMonthlyData())
						.build())
				.salaryAttendanceDatas(this.fakeReportItems())
				.salaryDeductionData(DeductionData.builder()
						.aggregateItemList(this.fakeReportItems())
						.totalDeduction(ReportItemDto.builder()
								.name("Total Salary Deduction")
								.monthlyDatas(this.fakeMonthlyData())
								.build())
						.build())
				.bonusDeductionData(DeductionData.builder()
						.aggregateItemList(this.fakeReportItems())
						.totalDeduction(ReportItemDto.builder()
								.name("Total Bonus Deduction")
								.monthlyDatas(this.fakeMonthlyData())
								.build())
						.build())
				.totalBonusData(ReportItemDto.builder()
						.name("Total bonus")
						.monthlyDatas(this.fakeMonthlyData())
						.build())
				.bonusAttendanceDatas(this.fakeReportItems())
				.build();
		
		// Fake data for new layout report.
		WLNewLayoutReportData newLayoutReportData = WLNewLayoutReportData.builder()
				.headerData(HeaderReportData.builder()
						.departmentCode("DEP1")
						.departmentName("Department 1")
						.employeeCode("EM1")
						.employeeName("Employee 1")
						.position("Project manager")
						.sex("Man")
						.targetYear(query.targetYear)
						.build())
				.salaryTotalData(TotalData.builder()
						.totalDeduction(this.fakeReportItem("Salary Total Deduction"))
						.totalIncomeTax(this.fakeReportItem("Salary Total Income Tax"))
						.totalInhabitantTax(this.fakeReportItem("Salaty Total Inhabitant Tax"))
						.totalPayment(this.fakeReportItem("Salary Total"))
						.totalReal(this.fakeReportItem("Salary Total Real"))
						.totalSocialInsurance(this.fakeReportItem("Salary Total Social Insurance"))
						.totalTax(this.fakeReportItem("Salary Total Tax"))
						.totalTaxable(this.fakeReportItem("Salary Total Tax"))
						.totalTaxExemption(this.fakeReportItem("Salary Total Tax Exemption"))
						.build())
				.bonusTotalData(TotalData.builder()
						.totalDeduction(this.fakeReportItem("Salary Total Deduction"))
						.totalIncomeTax(this.fakeReportItem("Salary Total Income Tax"))
						.totalInhabitantTax(this.fakeReportItem("Salaty Total Inhabitant Tax"))
						.totalPayment(this.fakeReportItem("Salary Total"))
						.totalReal(this.fakeReportItem("Salary Total Real"))
						.totalSocialInsurance(this.fakeReportItem("Salary Total Social Insurance"))
						.totalTax(this.fakeReportItem("Salary Total Tax"))
						.totalTaxable(this.fakeReportItem("Salary Total Tax"))
						.totalTaxExemption(this.fakeReportItem("Salary Total Tax Exemption"))
						.build())
				.bonusAttendanceItems(this.fakeReportItems())
				.bonusDeductionItems(this.fakeReportItems())
				.bonusPaymentItems(this.fakeReportItems())
				.salaryAttendanceItems(this.fakeReportItems())
				.salaryPaymentItems(this.fakeReportItems())
				.salaryDeductionItems(this.fakeReportItems())
				.otherMoneyBeforeYearEnd((long)(Math.random() * 2000000))
				.positionMoneyBeforeYearEnd((long)(Math.random() * 2000000))
				.bonusPaymentDateMap(this.fakeDateMap(5))
				.salaryPaymentDateMap(this.fakeDateMap(12))
				.build();
		
		// Generate report.
		if (query.layoutType == LayoutType.NewLayout) {
			this.newGenerator.generate(context.getGeneratorContext(), newLayoutReportData, query);
		} else {
			this.oldGenerator.generate(context.getGeneratorContext(), fakeReportData, query);
		}
	}
	
	private List<ReportItemDto> fakeReportItems() {
		List<ReportItemDto> reportItemDtos = new ArrayList<>();
		for (int i = 0; i < 50 + (int)(Math.random() * 100); i++) {
			reportItemDtos.add(ReportItemDto.builder()
					.monthlyDatas(this.fakeMonthlyData())
					.name("Item" + i)
					.build());
		}
		return reportItemDtos;
	}
	
	private ReportItemDto fakeReportItem(String name) {
		return ReportItemDto.builder()
				.monthlyDatas(this.fakeMonthlyData())
				.name(name)
				.build();
	}
	
	private List<MonthlyData> fakeMonthlyData() {
		List<MonthlyData> monthlyDatas = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			monthlyDatas.add(MonthlyData.builder()
					.amount(100000 + (int)(Math.random() * 2000000))
					.month(i)
					.build());
		}
		return monthlyDatas;
	}
	
	private Map<Integer, Date> fakeDateMap(int month) {
		Map<Integer, Date> dateMap = new HashMap<>();
		for (int i = 0; i < month; i++) {
			dateMap.put(i + 1, new Date());
		}
		return dateMap;
	}
}
