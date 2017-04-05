/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WLNewLayoutReportGenerator;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.newlayout.TotalData;
import nts.uk.file.pr.app.export.wageledger.data.share.MonthlyData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class AsposeWLNewLayoutReportGenerator.
 */
@Stateless
public class AsposeWLNewLayoutReportGenerator extends WageLedgerBaseGenerator implements WLNewLayoutReportGenerator{
	
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/WageLedgerNewLayoutTemplate.xlsx";
	
	/** The Constant ROW_START_REPORT. */
	private static final int ROW_START_REPORT = 5;
	
	/** The Constant COLUMN_START_REPORT. */
	private static final int COLUMN_START_REPORT = 0;
	
	/** The Constant AMOUNT_COLUMN_BONUS_PART. */
	private static final int AMOUNT_COLUMN_BONUS_PART = 6;
	
	/** The Constant MAX_ROW_ON_ONE_PAGE. */
	private static final int MAX_ROW_ON_ONE_PAGE = 56;
	
	/** The Constant MAX_RECORD_ON_ONE_PAGE. */
	private static final int MAX_RECORD_ON_ONE_PAGE = 40;
	
	/** The Constant AMOUNT_COLUMN_FIRST_PAGE. */
	private static final int AMOUNT_COLUMN_FIRST_PAGE = 60;
	
	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WLNewLayoutReportGenerator
	 * #generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 *  nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData,
	 *   nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, WLNewLayoutReportData reportData, WageLedgerReportQuery query) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			// Create Print Data.
			PrintData printData = new PrintData();
			printData.amountItemLeftOnCurrentPage = MAX_RECORD_ON_ONE_PAGE;
			printData.currentColumn = COLUMN_START_REPORT;
			printData.currentRow = AMOUNT_COLUMN_FIRST_PAGE + ROW_START_REPORT;
			printData.reportData = reportData;
			printData.reportContext = reportContext;
			printData.isSalaryPath = true;
			printData.query = query;
			
			// ======================== Fill header data.========================
			this.fillHeaderData(reportContext, reportData.headerData);
			
			// ======================== Fill Total part.========================
			this.setDataSourceForTotalPart(reportContext, reportData);
			
			// ======================== Fill Salary payment part.========================
			printData.headerLabel = "給与明細";
			this.fillHeaderTable(printData);
			printData.headerLabel = "【支給】";
			printData.currentColumn = COLUMN_START_REPORT;
			this.fillReportItemsData(reportData.salaryPaymentItems, printData);
			printData.isCheckBreakPage = true;
			this.breakPage(printData);
			
			// ======================== Fill Salary Deduction part.========================
			printData.headerLabel = "【控除】";
			printData.currentColumn = COLUMN_START_REPORT;
			this.fillReportItemsData(reportData.salaryDeductionItems, printData);
			printData.isCheckBreakPage = true;
			this.breakPage(printData);
			
			// ======================== Fill Salary Attendance part.========================
			printData.headerLabel = "【勤怠】";
			printData.currentColumn = COLUMN_START_REPORT;
			this.fillReportItemsData(reportData.salaryAttendanceItems, printData);
			printData.isCheckBreakPage = true;
			this.breakPage(printData);
			
			// ======================== Fill Bonus Payment part and Bonus Deduction part.========================
			printData.isSalaryPath = false;
			int rowStartThisPart = printData.currentRow;
			int amountItemLeftOnThisPart = printData.amountItemLeftOnCurrentPage;
			
			// Bonus payment part.
			printData.headerLabel = "賞与明細";
			printData.currentColumn = COLUMN_START_REPORT;
			this.fillHeaderTable(printData);
			printData.headerLabel = "【支給】";
			printData.currentColumn = COLUMN_START_REPORT;
			this.fillReportItemsData(reportData.bonusPaymentItems, printData);
			int amountItemLeftOnThisPage = printData.amountItemLeftOnCurrentPage;
			int rowEndThisPart = printData.currentRow;
			
			// Bonus deduction part.
			printData.headerLabel = "賞与明細";
			printData.amountItemLeftOnCurrentPage = amountItemLeftOnThisPart;
			printData.currentColumn = COLUMN_START_REPORT + AMOUNT_COLUMN_BONUS_PART + 1;
			printData.currentRow = rowStartThisPart;
			this.fillHeaderTable(printData);
			printData.headerLabel = "【控除】";
			printData.currentColumn = COLUMN_START_REPORT + AMOUNT_COLUMN_BONUS_PART + 1;
			this.fillReportItemsData(reportData.bonusDeductionItems, printData);
			printData.amountItemLeftOnCurrentPage = amountItemLeftOnThisPage;
			printData.currentRow = rowEndThisPart;
			printData.isCheckBreakPage = true;
			this.breakPage(printData);
			
			// ======================== Fill Bonus Attendance part.========================
			printData.headerLabel = "【勤怠】";
			printData.currentColumn = COLUMN_START_REPORT;
			this.fillReportItemsData(reportData.bonusAttendanceItems, printData);
			
			// ======================== Setting report.========================
			// Set print area.
			Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
			PageSetup pageSetup = ws.getPageSetup();
			Cell endCell = ws.getCells().get(printData.currentRow, 12);
			pageSetup.setPrintArea("A1:" + endCell.getName());
			
			// Set header.
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			ws.getPageSetup().setHeader(2,"&\"IPAPGothic\"&13 " + dateFormat.format(new Date()) + "\r\n&P ページ");
			
			// process data binginds in template
			reportContext.getDesigner().process(false);
			reportContext.getWorkbook().calculateFormula(true);
			reportContext.getDesigner().setUpdateReference(true);

			// save as PDF file
			reportContext.getWorkbook().save("C:\\Test.xlsx");
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Sets the data source for total part.
	 *
	 * @param reportContext the report context
	 * @param reportData the report data
	 */
	private void setDataSourceForTotalPart(AsposeCellsReportContext reportContext, WLNewLayoutReportData reportData) {
		WorkbookDesigner designer = reportContext.getDesigner();
		//======================== Set Salary Data source.========================
		// Salary Payment Date.
		reportData.salaryPaymentDateMap.forEach((month, paymentDate) -> {
			designer.setDataSource("SalaryPaymenDate" + month, this.formartDate(paymentDate));
		});
		// Salary part.
		this.setDataSourceForTotalItems(designer, reportData.salaryTotalData, true);
		
		// ======================== Set Bonus Data Source.========================
		// Bonus Payment Date.
		reportData.bonusPaymentDateMap.forEach((month, paymentDate) -> {
			designer.setDataSource("BonusPaymenDate" + month, this.formartDate(paymentDate));
		});
		// Salary part.
		this.setDataSourceForTotalItems(designer, reportData.bonusTotalData, false);
	}
	
	/**
	 * Sets the data source for total items.
	 *
	 * @param designer the designer
	 * @param totalData the total data
	 * @param isSalary the is salary
	 */
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
	
	/**
	 * Sets the data source for item.
	 *
	 * @param designer the designer
	 * @param item the item
	 * @param dataName the data name
	 */
	private void setDataSourceForItem(WorkbookDesigner designer, ReportItemDto item, String dataName) {
		item.monthlyDatas.forEach((month) -> {
			designer.setDataSource(dataName + month.month, month.amount);
		});
	}
	
	/**
	 * Fill report items data.
	 *
	 * @param reportContext the report context
	 * @param reportItems the report items
	 * @param startColumn the start column
	 * @param startRow the start row
	 * @param contentName the content name
	 * @param paymentDateMap the payment date map
	 */
	private void fillReportItemsData(List<ReportItemDto> reportItems, PrintData printData) {
		Worksheet ws = printData.reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int totalItemData = reportItems.size();
		int fromIndex = 0;
		Map<Integer, Date> paymentDateMap = printData.isSalaryPath ? printData.reportData.salaryPaymentDateMap
				: printData.reportData.bonusPaymentDateMap;
		
		// Fill content name cell.
		Cell contentNameCell = cells.get(printData.currentRow, printData.currentColumn);
		contentNameCell.setValue(printData.headerLabel);
		printData.currentRow++;
		
		while (totalItemData > 0) {
			int amountItemOnPage = Math.min(printData.amountItemLeftOnCurrentPage, MAX_RECORD_ON_ONE_PAGE);
			int toIndex = fromIndex + amountItemOnPage;
			List<ReportItemDto> items = this.safeSubList(reportItems, fromIndex, toIndex);
			int startRowOnThisPage = printData.currentRow;
			
			
			// Fill report item list.
			for (int i = 0; i < items.size(); i++) {
				ReportItemDto item = items.get(i);
				
				// Draw begin line on page.
				if (i == 0) {
					Range beginRowRange = cells.createRange(printData.currentRow, printData.currentColumn + 1,
							1, paymentDateMap.size());
					beginRowRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
				}
				
				// Fill items.
				this.fillItemData(item, printData);
			}
			
			// Draw horizontal line end page.
			Range endColumnRange = cells.createRange(startRowOnThisPage,
					printData.currentColumn + paymentDateMap.size(), items.size(), 1);
			endColumnRange.setOutlineBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
			
			// Calculate to next page.
			totalItemData -= MAX_RECORD_ON_ONE_PAGE;
			fromIndex += MAX_RECORD_ON_ONE_PAGE;
		}
		
		// Draw vertical line end page.
		Range endRowRange = cells.createRange(printData.currentRow - 1,
				printData.currentColumn + 1, 1, paymentDateMap.size());
		endRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
	}
	
	/**
	 * Fill item data.
	 *
	 * @param reportContext the report context
	 * @param item the item
	 * @param row the row
	 * @param startColumn the start column
	 * @param monthList the month list
	 */
	private void fillItemData(ReportItemDto item, PrintData printData) {
		Worksheet ws = printData.reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		Color backgroundColor = printData.isGreenRow ? GREEN_COLOR : null;
		printData.isGreenRow  = !printData.isGreenRow;
		int startColumn = printData.currentColumn;
		
		// Fill item name cell.
		Cell nameCell = cells.get(printData.currentRow, printData.currentColumn);
		nameCell.setValue(item.name);
		this.setStyleCell(nameCell, StyleModel.createNameCellStyle(backgroundColor));
		printData.currentColumn++;
		
		// Fill item data cells.
		Map<Integer, MonthlyData> dataMap = item.monthlyDatas.stream()
				.collect(Collectors.toMap(d -> d.month, Function.identity()));
		Map<Integer, Date> paymentDateMap = printData.isSalaryPath ? printData.reportData.salaryPaymentDateMap
				: printData.reportData.bonusPaymentDateMap;
		List<Integer> monthList = new ArrayList<>(paymentDateMap.keySet());
		for (int j = 0; j < paymentDateMap.size(); j++) {
			MonthlyData data = dataMap.get(monthList.get(j));
			Cell monthCell = cells.get(printData.currentRow, printData.currentColumn);
			monthCell.setValue(data.amount);

			// Set style for cell.
			StyleModel dataCellStyle = StyleModel
					.createItemCellStyle(backgroundColor, j != item.monthlyDatas.size() - 1);
			this.setStyleCell(monthCell, dataCellStyle);

			// Next column.
			printData.currentColumn++;
		}
		
		// Next row.
		printData.currentRow++;
		// Calculate amount item left on page.
		printData.amountItemLeftOnCurrentPage--;
		printData.currentColumn = startColumn;
		
		// Check row is last of page.
		if (printData.amountItemLeftOnCurrentPage == 0) {
			Range endRowRange = cells.createRange(printData.currentRow - 1,
					printData.currentColumn + 1, 1, paymentDateMap.size());
			endRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			printData.isCheckBreakPage = false;
			this.breakPage(printData);
		}
	}
	
	/**
	 * Fill header table.
	 *
	 * @param reportContext the report context
	 * @param startRow the start row
	 * @param startColumn the start column
	 * @param paymentDateMap the payment date map
	 * @param contentName the content name
	 */
	private void fillHeaderTable(PrintData printData) {
		Worksheet ws = printData.reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		
		// Fill content name.
		Cell contentCell = cells.get(printData.currentRow, printData.currentColumn);
		contentCell.setValue(printData.headerLabel);
		printData.currentRow++;
		
		// ======================== Fill Header.========================
		int monthRow = printData.currentRow;
		int paymentDateRow = ++printData.currentRow;
		StyleModel totalCellStyle = StyleModel.createHeaderCellStyle(false);
		
		// Fill first column.
		Cell blankCell = cells.get(monthRow, printData.currentColumn);
		this.setStyleCell(blankCell, totalCellStyle);
		Cell paymentDateLabelCell = cells.get(paymentDateRow, printData.currentColumn);
		paymentDateLabelCell.setValue("支払日");
		this.setStyleCell(paymentDateLabelCell, totalCellStyle);
		printData.currentColumn++;
		
		// Fill content header.
		Map<Integer, Date> paymentDateMap = printData.isSalaryPath ? printData.reportData.salaryPaymentDateMap
				: printData.reportData.bonusPaymentDateMap;
		for (Integer month : paymentDateMap.keySet()) {
			Date paymentDate = paymentDateMap.get(month);
			// Month cell.
			Cell monthCell = cells.get(monthRow, printData.currentColumn);
			monthCell.setValue(month + " 月");
			this.setStyleCell(monthCell, totalCellStyle);
			
			// Payment Date Cell.
			Cell paymentDateCell = cells.get(paymentDateRow, printData.currentColumn);
			paymentDateCell.setValue(this.formartDate(paymentDate));
			this.setStyleCell(paymentDateCell, totalCellStyle);
			
			// Next Column.
			printData.currentColumn++;
		}
		
		// Next Row.
		printData.currentRow++;
	}
	
	/**
	 * Break page.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 * @param isCheckBreakPageCondition the is check break page condition
	 */
	private void breakPage(PrintData printData) {
		if (!printData.isCheckBreakPage 
				|| (printData.isCheckBreakPage && printData.query.isPageBreakIndicator)) {
			int currentPage = printData.currentRow / MAX_ROW_ON_ONE_PAGE + 1;
			printData.currentRow = AMOUNT_COLUMN_FIRST_PAGE 
					+ (currentPage - 1) * MAX_ROW_ON_ONE_PAGE + ROW_START_REPORT;
			printData.amountItemLeftOnCurrentPage = MAX_RECORD_ON_ONE_PAGE;
		} else {
			printData.currentRow++;
		}
	}
	
	/**
	 * Formart date.
	 *
	 * @param paymentDate the payment date
	 * @return the string
	 */
	@SuppressWarnings("deprecation")
	private String formartDate(Date paymentDate) {
		return String.format("%s日%s月 支給", paymentDate.getDate(), paymentDate.getMonth());
	}
	
	/**
	 * The Class PrintData.
	 */
	private class PrintData {
		
		/** The report context. */
		AsposeCellsReportContext reportContext;
		
		/** The report data. */
		WLNewLayoutReportData reportData;
		
		/** The query. */
		WageLedgerReportQuery query;
		
		/** The current row. */
		int currentRow;
		
		/** The current column. */
		int currentColumn;
		
		/** The amount item left on current page. */
		int amountItemLeftOnCurrentPage;
		
		/** The is green row. */
		boolean isGreenRow;
		
		/** The is salary path. */
		boolean isSalaryPath;
		
		/** The is total item. */
		boolean isCheckBreakPage;
		
		/** The header label. */
		String headerLabel;
	}
}
