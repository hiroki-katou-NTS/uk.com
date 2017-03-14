/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery.LayoutType;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.DeductionData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.MonthlyData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;

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
	private WageLedgerReportGenerator generator;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<WageLedgerReportQuery> context) {
		
		// TODO : validate query.
		WageLedgerReportQuery query = context.getQuery();
		
		// Query Data.
		WLOldLayoutReportData reportData = this.repository.findReportData(query);
		
		// Fake data.
		reportData = WLOldLayoutReportData.builder()
				.headerData(HeaderReportData.builder()
						.departmentCode("DEP1")
						.departmentName("Department 1")
						.employeeCode("EM1")
						.employeeName("Employee 1")
						.position("Project manager")
						.sex("Man")
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
		
		// Generate report.
		if (query.layoutType == LayoutType.NewLayout) {
			this.generator.generateWithNewLayout(context.getGeneratorContext(), null);
		} else {
			this.generator.generateWithOldLayout(context.getGeneratorContext(), reportData);
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
	
	private List<MonthlyData> fakeMonthlyData() {
		List<MonthlyData> monthlyDatas = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			monthlyDatas.add(MonthlyData.builder()
					.amount(1000000 + (int)(Math.random() * 20000000))
					.month(i)
					.build());
		}
		return monthlyDatas;
	}
}
