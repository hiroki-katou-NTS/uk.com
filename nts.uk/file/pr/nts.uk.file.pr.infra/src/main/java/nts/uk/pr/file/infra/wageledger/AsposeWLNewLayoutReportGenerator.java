/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang3.mutable.MutableInt;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WLNewLayoutReportGenerator;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.newlayout.TotalData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

@Stateless
public class AsposeWLNewLayoutReportGenerator extends WageLedgerBaseGenerator implements WLNewLayoutReportGenerator{
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/WageLegerNewLayoutReportTemplate.xlsx";
	
	/** The Constant ROW_START_REPORT. */
	private static final int ROW_START_REPORT = 5;
	
	/** The Constant COLUMN_START_REPORT. */
	private static final int COLUMN_START_REPORT = 0;
	
	/** The Constant AMOUNT_COLUMN_BONUS_PART. */
	private static final int AMOUNT_COLUMN_BONUS_PART = 6;
	
	@Override
	public void generate(FileGeneratorContext fileContext, WLNewLayoutReportData reportData, WageLedgerReportQuery query) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			
			MutableInt currentRow = new MutableInt(ROW_START_REPORT);
			
			// ======================== Fill header data.========================
			this.fillHeaderData(reportContext, reportData.headerData);
			
			// ======================== Fill Total part.========================
			this.setDataSourceForTotalPart(reportContext, reportData);
			
			// ======================== Fill Salary payment part.========================
			this.fillHeaderTable(reportContext, currentRow, COLUMN_START_REPORT,
					reportData.salaryPaymentDateMap, "給与明細");
			this.fillReportItemsData(reportContext, reportData.salaryPaymentItems, COLUMN_START_REPORT,
					currentRow, "SalaryPayment");
			this.brealPage(reportContext, currentRow, query);
			
			// ======================== Fill Salary Deduction part.========================
			this.fillReportItemsData(reportContext, reportData.salaryDeductionItems, COLUMN_START_REPORT,
					currentRow, "SalaryDeduction");
			this.brealPage(reportContext, currentRow, query);
			
			// ======================== Fill Salary Attendance part.========================
			this.fillReportItemsData(reportContext, reportData.salaryAttendanceItems, COLUMN_START_REPORT,
					currentRow, "SalaryAttendance");
			this.brealPage(reportContext, currentRow, query);
			
			// ======================== Fill Bonus Payment part and Bonus Deduction part.========================
			MutableInt rowStartThisPart = new MutableInt(currentRow);
			// Bonus payment part.
			this.fillHeaderTable(reportContext, currentRow, COLUMN_START_REPORT,
					reportData.bonusPaymentDateMap, "賞与明細");
			this.fillReportItemsData(reportContext, reportData.bonusPaymentItems, COLUMN_START_REPORT,
					currentRow, "BonusPayment");
			// Bonus deduction part.
			this.fillHeaderTable(reportContext, rowStartThisPart, COLUMN_START_REPORT + 6,
					reportData.bonusPaymentDateMap, "賞与明細");
			this.fillReportItemsData(reportContext, reportData.bonusDeductionItems,
					COLUMN_START_REPORT + AMOUNT_COLUMN_BONUS_PART, rowStartThisPart, "BonusDeduction");
			this.brealPage(reportContext, currentRow, query);
			
			// ======================== Fill Bonus Attendance part.========================
			this.fillReportItemsData(reportContext, reportData.bonusAttendanceItems, COLUMN_START_REPORT,
					currentRow, "BonusAttendance");
			
			// process data binginds in template
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setDataSourceForTotalPart(AsposeCellsReportContext reportContext, WLNewLayoutReportData reportData) {
		WorkbookDesigner designer = reportContext.getDesigner();
		//======================== Set Salary Data source.========================
		// Salary Payment Date.
		reportData.salaryPaymentDateMap.forEach((month, paymentDate) -> {
			designer.setDataSource("SalaryPaymenDate" + month, this.formartDate(paymentDate, "給与"));
		});
		// Salary part.
		this.setDataSourceForTotalItems(designer, reportData.salaryTotalData, true);
		
		// ======================== Set Bonus Data Source.========================
		// Bonus Payment Date.
		reportData.bonusPaymentDateMap.forEach((month, paymentDate) -> {
			designer.setDataSource("BonusPaymenDate" + month, this.formartDate(paymentDate, "賞与"));
		});
		// Salary part.
		this.setDataSourceForTotalItems(designer, reportData.bonusTotalData, false);
	}
	
	private void setDataSourceForTotalItems(WorkbookDesigner designer, TotalData totalData, boolean isSalary) {
		String dataSourceName = isSalary ? "Salary" : "Bonus";
		
		// Total tax.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalTax");
		// Total Tax Exemption.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalTaxExemption");
		// Total Payment.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalPayment");
		// Total Social Insurance.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalSocialInsurance");
		// Total Taxable.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalTaxable");
		// Total Income Tax.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalIncomeTax");
		// Total Inhabitant Tax.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalInhabitantTax");
		// Total Deduction.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalDeduction");
		// Total Real.
		this.setDataSourceForItem(designer, totalData.totalTax, dataSourceName + "TotalReal");
	}
	
	private void setDataSourceForItem(WorkbookDesigner designer, ReportItemDto item, String dataName) {
		item.monthlyDatas.forEach((month) -> {
			designer.setDataSource(dataName + month.month, month.amount);
		});
	}
	
	private void fillReportItemsData(AsposeCellsReportContext reportContext, List<ReportItemDto> reportItems,
			int startColumn, MutableInt startRow, String contentName) {
		
	}
	
	private void fillHeaderTable(AsposeCellsReportContext reportContext, MutableInt startRow,
			int startColumn, Map<Integer, Date> paymentDateMap, String contentName) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		
		// Fill content name.
		Cell contentCell = cells.get(startRow.intValue(), startColumn);
		contentCell.setValue(contentName);
		startRow.increment();
		
		// Fill Header.
		
	}
	
	private void brealPage(AsposeCellsReportContext reportContext, MutableInt currentRow, WageLedgerReportQuery query) {
		
	}
	
	@SuppressWarnings("deprecation")
	private String formartDate(Date paymentDate, String paymenType) {
		return String.format("%s日%s月 " + paymenType, paymentDate.getDate(), paymentDate.getMonth());
	}
	
}
