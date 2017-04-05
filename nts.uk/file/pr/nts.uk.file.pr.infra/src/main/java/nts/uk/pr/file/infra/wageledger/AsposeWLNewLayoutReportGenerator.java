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

import org.apache.commons.lang3.mutable.MutableInt;

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
	
	/** The amount item left on current page. */
	private int amountItemLeftOnCurrentPage = MAX_RECORD_ON_ONE_PAGE;
	
	/** The Constant AMOUNT_COLUMN_FIRST_PAGE. */
	private static final int AMOUNT_COLUMN_FIRST_PAGE = 60;
	
	/** The query. */
	private WageLedgerReportQuery query;
	
	/** The is green row. */
	private boolean isGreenRow;
	
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
			this.query = query;
			MutableInt currentRow = new MutableInt(AMOUNT_COLUMN_FIRST_PAGE + ROW_START_REPORT);
			
			// ======================== Fill header data.========================
			this.fillHeaderData(reportContext, reportData.headerData);
			
			// ======================== Fill Total part.========================
			this.setDataSourceForTotalPart(reportContext, reportData);
			
			// ======================== Fill Salary payment part.========================
			this.fillHeaderTable(reportContext, currentRow, COLUMN_START_REPORT,
					reportData.salaryPaymentDateMap, "給与明細");
			this.fillReportItemsData(reportContext, reportData.salaryPaymentItems, COLUMN_START_REPORT,
					currentRow, "【支給】", reportData.salaryPaymentDateMap);
			this.breakPage(reportContext, currentRow, true);
			
			// ======================== Fill Salary Deduction part.========================
			this.fillReportItemsData(reportContext, reportData.salaryDeductionItems, COLUMN_START_REPORT,
					currentRow, "【控除】", reportData.salaryPaymentDateMap);
			this.breakPage(reportContext, currentRow, true);
			
			// ======================== Fill Salary Attendance part.========================
			this.fillReportItemsData(reportContext, reportData.salaryAttendanceItems, COLUMN_START_REPORT,
					currentRow, "【勤怠】", reportData.salaryPaymentDateMap);
			this.breakPage(reportContext, currentRow, true);
			
			// ======================== Fill Bonus Payment part and Bonus Deduction part.========================
			MutableInt rowStartThisPart = new MutableInt(currentRow);
			int amountItemLeftOnThisPart = this.amountItemLeftOnCurrentPage;
			
			// Bonus payment part.
			this.fillHeaderTable(reportContext, currentRow, COLUMN_START_REPORT,
					reportData.bonusPaymentDateMap, "賞与明細");
			this.fillReportItemsData(reportContext, reportData.bonusPaymentItems, COLUMN_START_REPORT,
					currentRow, "【支給】", reportData.bonusPaymentDateMap);
			int amountItemLeftOnThisPage = this.amountItemLeftOnCurrentPage;
			
			// Bonus deduction part.
			this.amountItemLeftOnCurrentPage = amountItemLeftOnThisPart;
			this.fillHeaderTable(reportContext, rowStartThisPart, COLUMN_START_REPORT + AMOUNT_COLUMN_BONUS_PART + 1,
					reportData.bonusPaymentDateMap, "賞与明細");
			this.fillReportItemsData(reportContext, reportData.bonusDeductionItems,
					COLUMN_START_REPORT + AMOUNT_COLUMN_BONUS_PART + 1, rowStartThisPart,
					"【控除】】", reportData.bonusPaymentDateMap);
			this.amountItemLeftOnCurrentPage = amountItemLeftOnThisPage;
			this.breakPage(reportContext, currentRow, true);
			
			// ======================== Fill Bonus Attendance part.========================
			this.fillReportItemsData(reportContext, reportData.bonusAttendanceItems, COLUMN_START_REPORT,
					currentRow, "【勤怠】", reportData.bonusPaymentDateMap);
			
			// ======================== Setting report.========================
			// Set print area.
			Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
			PageSetup pageSetup = ws.getPageSetup();
			Cell endCell = ws.getCells().get(currentRow.intValue(), 12);
			pageSetup.setPrintArea("A1:" + endCell.getName());
			
			// Set header.
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			ws.getPageSetup().setHeader(2,"&\"IPAPGothic\"&13 " + dateFormat.format(new Date()) + "\r\n&P ページ");
			
			// process data binginds in template
			reportContext.getDesigner().process(false);
			reportContext.getWorkbook().calculateFormula(true);
			reportContext.getDesigner().setUpdateReference(true);

			// save as PDF file
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
	private void fillReportItemsData(AsposeCellsReportContext reportContext, List<ReportItemDto> reportItems,
			int startColumn, MutableInt startRow, String contentName, Map<Integer, Date> paymentDateMap) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int totalItemData = reportItems.size();
		int fromIndex = 0;
		
		// Fill content name cell.
		Cell contentNameCell = cells.get(startRow.intValue(), startColumn);
		contentNameCell.setValue(contentName);
		startRow.increment();
		
		while (totalItemData > 0) {
			int amountItemOnPage = Math.min(this.amountItemLeftOnCurrentPage, MAX_RECORD_ON_ONE_PAGE);
			int toIndex = fromIndex + amountItemOnPage;
			List<ReportItemDto> items = this.safeSubList(reportItems, fromIndex, toIndex);
			int startRowOnThisPage = startRow.intValue();
			
			
			// Fill report item list.
			for (int i = 0; i < items.size(); i++) {
				ReportItemDto item = items.get(i);
				
				// Draw begin line on page.
				if (i == 0) {
					Range beginRowRange = cells.createRange(startRow.intValue(), startColumn + 1, 1, paymentDateMap.size());
					beginRowRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
				}
				
				// Fill items.
				this.fillItemData(reportContext, item, startRow, startColumn,
						new ArrayList<>(paymentDateMap.keySet()));
			}
			
			// Draw horizontal line end page.
			Range endColumnRange = cells.createRange(startRowOnThisPage, startColumn + paymentDateMap.size(), items.size(), 1);
			endColumnRange.setOutlineBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
			
			// Caculate to next page.
			totalItemData -= MAX_RECORD_ON_ONE_PAGE;
			fromIndex += MAX_RECORD_ON_ONE_PAGE;
		}
		
		// Draw vertical line end page.
		Range endRowRange = cells.createRange(startRow.intValue() - 1, startColumn + 1, 1, paymentDateMap.size());
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
	private void fillItemData(AsposeCellsReportContext reportContext, ReportItemDto item, MutableInt row,
			Integer startColumn, List<Integer> monthList) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		Color backgroundColor = this.isGreenRow ? GREEN_COLOR : null;
		this.isGreenRow = !this.isGreenRow;
		
		
		// Fill item name cell.
		Cell nameCell = cells.get(row.intValue(), startColumn);
		nameCell.setValue(item.name);
		this.setStyleCell(nameCell, StyleModel.createNameCellStyle(backgroundColor));
		startColumn++;
		
		// Fill item data cells.
		Map<Integer, MonthlyData> dataMap = item.monthlyDatas.stream()
				.collect(Collectors.toMap(d -> d.month, Function.identity()));
		for (int j = 0; j < monthList.size(); j++) {
			MonthlyData data = dataMap.get(monthList.get(j));
			Cell monthCell = cells.get(row.intValue(), startColumn);
			monthCell.setValue(data.amount);

			// Set style for cell.
			StyleModel dataCellStyle = StyleModel
					.createItemCellStyle(backgroundColor, j != item.monthlyDatas.size() - 1);
			this.setStyleCell(monthCell, dataCellStyle);

			// Next column.
			startColumn++;
		}
		
		// Next row.
		row.increment();
		// Calculate amount item left on page.
		this.amountItemLeftOnCurrentPage--;
		
		// Check row is last of page.
		if (this.amountItemLeftOnCurrentPage == 0) {
			Range endRowRange = cells.createRange(row.intValue() - 1, 1, 1, monthList.size());
			endRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			this.breakPage(reportContext, row, false);
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
	private void fillHeaderTable(AsposeCellsReportContext reportContext, MutableInt startRow,
			int startColumn, Map<Integer, Date> paymentDateMap, String contentName) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		
		// Fill content name.
		Cell contentCell = cells.get(startRow.intValue(), startColumn);
		contentCell.setValue(contentName);
		startRow.increment();
		
		// ======================== Fill Header.========================
		int monthRow = startRow.intValue();
		int paymentDateRow = startRow.incrementAndGet();
		StyleModel totalCellStyle = StyleModel.createHeaderCellStyle(false);
		
		// Fill first column.
		Cell blankCell = cells.get(monthRow, startColumn);
		this.setStyleCell(blankCell, totalCellStyle);
		Cell paymentDateLabelCell = cells.get(paymentDateRow, startColumn);
		paymentDateLabelCell.setValue("支払日");
		this.setStyleCell(paymentDateLabelCell, totalCellStyle);
		startColumn++;
		
		// Fill content header.
		for (Integer month : paymentDateMap.keySet()) {
			Date paymentDate = paymentDateMap.get(month);
			// Month cell.
			Cell monthCell = cells.get(monthRow, startColumn);
			monthCell.setValue(month + " 月");
			this.setStyleCell(monthCell, totalCellStyle);
			
			// Payment Date Cell.
			Cell paymentDateCell = cells.get(paymentDateRow, startColumn);
			paymentDateCell.setValue(this.formartDate(paymentDate));
			this.setStyleCell(paymentDateCell, totalCellStyle);
			
			// Next Column.
			startColumn++;
		}
		
		// Next Row.
		startRow.increment();
	}
	
	/**
	 * Break page.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 * @param isCheckBreakPageCondition the is check break page condition
	 */
	private void breakPage(AsposeCellsReportContext reportContext, MutableInt currentRow,
			boolean isCheckBreakPageCondition) {
		if (!isCheckBreakPageCondition 
				|| (isCheckBreakPageCondition && this.query.isPageBreakIndicator)) {
			int currentPage = currentRow.intValue() / MAX_ROW_ON_ONE_PAGE + 1;
			currentRow.setValue(AMOUNT_COLUMN_FIRST_PAGE 
					+ (currentPage - 1) * MAX_ROW_ON_ONE_PAGE + ROW_START_REPORT);
			this.amountItemLeftOnCurrentPage = MAX_RECORD_ON_ONE_PAGE;
		} else {
			currentRow.increment();
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
	
}
