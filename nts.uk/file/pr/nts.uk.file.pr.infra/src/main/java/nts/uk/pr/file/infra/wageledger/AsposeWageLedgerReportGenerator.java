/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.DeductionData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeWageLedgerReportGenerator.
 */
@Stateless
public class AsposeWageLedgerReportGenerator extends AsposeCellsReportGenerator implements WageLedgerReportGenerator {
	
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/WageLegerReportTemplate.xlsx";
	
	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "サンプル帳票.pdf";
	
	/** The Constant ROW_START_REPORT. */
	private static final int ROW_START_REPORT = 10;
	
	/** The Constant MAX_ROW_ON_ONE_PAGE. */
	private static final int MAX_ROW_ON_ONE_PAGE = 30;

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator
	 * #generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 *  nts.uk.file.pr.app.export.wageledger.data.WageLedgerReportData)
	 */
	@Override
	public void generateWithOldLayout(FileGeneratorContext fileContext, WLOldLayoutReportData reportData) {
		
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			
			Integer currentRow = ROW_START_REPORT;
			
			// Fill header data.
			reportContext.setDataSource("Employee", reportData.headerData);
			
			// Fill Salary payment content.
			this.fillSalaryHeaderTable(reportContext);
			this.fillPaymentDataContentOldLayout(reportContext, reportData.salaryPaymentData, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Salary deduction, salary attendance content and net salary.
			this.fillDeductionDataContentOldLayout(reportContext, reportData.salaryDeductionData, currentRow);
			this.fillTotalItemData(reportContext, reportData.netSalaryData, currentRow);
			this.fillReportItemsData(reportContext, reportData.salaryAttendanceDatas, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Bonus payment content.
			this.fillBonusHeaderTable(reportContext);
			this.fillPaymentDataContentOldLayout(reportContext, reportData.bonusPaymentData, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Bonus deduction, bonus attendance content and total bonus.
			this.fillDeductionDataContentOldLayout(reportContext, reportData.bonusDeductionData, currentRow);
			this.fillTotalItemData(reportContext, reportData.totalBonusData, currentRow);
			this.fillReportItemsData(reportContext, reportData.bonusAttendanceDatas, currentRow);
			
			// process data binginds in template
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void fillPaymentDataContentOldLayout(AsposeCellsReportContext reportContext, PaymentData paymentData, Integer currentRow) {
		
	}
	
	private void fillDeductionDataContentOldLayout(AsposeCellsReportContext reportContext, DeductionData deductionData, Integer currentRow) {
		
	}
	
	private void fillReportItemsData(AsposeCellsReportContext reportContext, List<ReportItemDto> reportItems, Integer currentRow) {
		
	}
	
	private void fillTotalItemData(AsposeCellsReportContext reportContext, ReportItemDto totalItem, Integer currentRow) {
		
	}
	
	private void breakPage(AsposeCellsReportContext reportContext, Integer currentRow) {
		
	}
	
	private void fillSalaryHeaderTable(AsposeCellsReportContext reportContext) {
		
	}
	
	private void fillBonusHeaderTable(AsposeCellsReportContext reportContext) {
		
	}

	@Override
	public void generateWithNewLayout(FileGeneratorContext fileContext, WLNewLayoutReportData reportData) {
		
	}

}
