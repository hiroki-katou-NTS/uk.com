/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.DeductionData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.MonthlyData;
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
	private static final int ROW_START_REPORT = 5;
	
	/** The Constant COLUMN_START_REPORT. */
	private static final int COLUMN_START_REPORT = 0;
	
	/** The Constant MAX_ROW_ON_ONE_PAGE. */
	private static final int MAX_ROW_ON_ONE_PAGE = 45;
	
	/** The Constant MAX_RECORD_ON_ONE_PAGE. */
	private static final int MAX_RECORD_ON_ONE_PAGE = 35;
	
	/** The Constant BLUE_COLOR. */
	private static final Color BLUE_COLOR = Color.fromArgb(197, 241, 247);
	
	/** The Constant GREEN_COLOR. */
	private static final Color GREEN_COLOR = Color.getGreen();

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
			this.fillSalaryHeaderTable(reportContext, currentRow);
			this.fillPaymentDataContentOldLayout(reportContext, reportData.salaryPaymentData, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Salary deduction, salary attendance content and net salary.
			this.fillDeductionAndAttendanceDataContentOldLayout(reportContext, reportData.salaryDeductionData,
					reportData.netSalaryData, reportData.salaryAttendanceDatas, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Bonus payment content.
			this.fillBonusHeaderTable(reportContext);
			this.fillPaymentDataContentOldLayout(reportContext, reportData.bonusPaymentData, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Bonus deduction, bonus attendance content and total bonus.
			this.fillDeductionAndAttendanceDataContentOldLayout(reportContext, reportData.bonusDeductionData,
					reportData.totalBonusData,reportData.salaryAttendanceDatas, currentRow);
			
			// Set print area.
			
			// process data binginds in template
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Fill payment data content old layout.
	 *
	 * @param reportContext the report context
	 * @param paymentData the payment data
	 * @param currentRow the current row
	 */
	private void fillPaymentDataContentOldLayout(AsposeCellsReportContext reportContext, PaymentData paymentData,
			Integer currentRow) {
		List<ReportItemDto> reportItems = paymentData.aggregateItemList;
		reportItems.add(paymentData.totalTax);
		reportItems.add(paymentData.totalTaxExemption);
		reportItems.add(paymentData.totalSubsidy);
		
		// Fill item list.
		this.fillReportItemsData(reportContext, reportItems, currentRow, "支給");
	}
	
	/**
	 * Fill deduction and attendance data content old layout.
	 *
	 * @param reportContext the report context
	 * @param deductionData the deduction data
	 * @param totalData the total data
	 * @param attendanceData the attendance data
	 * @param currentRow the current row
	 */
	private void fillDeductionAndAttendanceDataContentOldLayout(AsposeCellsReportContext reportContext,
			DeductionData deductionData, ReportItemDto totalData, List<ReportItemDto> attendanceData, Integer currentRow) {
		
		// Fill Deduction data.
		List<ReportItemDto> reportItems = deductionData.aggregateItemList;
		reportItems.add(deductionData.totalDeduction);
		this.fillReportItemsData(reportContext, reportItems, currentRow, "控除");
		
		// Fill total salary (net salary) item.
		this.fillItemData(reportContext, totalData, currentRow, COLUMN_START_REPORT, GREEN_COLOR);
		
		// Fill Atendance Data.
		this.fillReportItemsData(reportContext, attendanceData, currentRow, "勤怠");
	}
	
	/**
	 * Fill report items data.
	 *
	 * @param reportContext the report context
	 * @param reportItems the report items
	 * @param currentRow the current row
	 * @param contentName the content name
	 */
	private void fillReportItemsData(AsposeCellsReportContext reportContext, List<ReportItemDto> reportItems,
			Integer currentRow, String contentName) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int totalItemData = reportItems.size();
		int fromIndex = 0;
		int currentColumn = COLUMN_START_REPORT;
		
		while (totalItemData > 0) {
			int toIndex = fromIndex + MAX_RECORD_ON_ONE_PAGE;
			List<ReportItemDto> items = this.safeSubList(reportItems, fromIndex, toIndex);
			
			// Fill Payment label cell.
			if (contentName != null) {
				int totalRow = items.size();
				Range paymentLabelRange = cells.createRange(currentRow, 0, totalRow, 1);
				paymentLabelRange.get(0, 0).setValue(contentName);
				this.setStyleRange(paymentLabelRange, null);
				currentColumn++;
			}
			
			// Fill report item list.
			for (int i = 0; i < reportItems.size(); i++) {
				ReportItemDto item = reportItems.get(i);
				
				// Fill item name cell.
				Cell nameCell = cells.get(currentRow, currentColumn);
				nameCell.setValue(item.name);
				this.setStyleCell(nameCell, null, WLBorderType.Outside);
				currentColumn++;
				
				// Fill items
				Color backgroundColor = i % 2 == 0 ? GREEN_COLOR : null;
				this.fillItemData(reportContext, item, currentRow, currentColumn, backgroundColor);
				
				// Next Row.
				currentRow++;
			}
			
			// Caculate to next page.
			totalItemData -= MAX_RECORD_ON_ONE_PAGE;
			fromIndex += MAX_RECORD_ON_ONE_PAGE;
		}
		
	}
	
	/**
	 * Fill item data.
	 *
	 * @param reportContext the report context
	 * @param item the item
	 * @param currentRow the current row
	 * @param currentColumn the current column
	 * @param backgroundColor the background color
	 */
	private void fillItemData(AsposeCellsReportContext reportContext, ReportItemDto item, Integer currentRow,
			Integer currentColumn, Color backgroundColor) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		
		// Fill item data cells.
		
		Map<Integer, MonthlyData> dataMap = item.monthlyDatas.stream()
				.collect(Collectors.toMap(d -> d.month, Function.identity()));
		for (int j = 0; j < item.monthlyDatas.size(); j++) {
			MonthlyData data = dataMap.get(j + 1);
			Cell monthCell = cells.get(currentColumn, currentColumn);
			monthCell.setValue(data.amount);

			// Set style for cell.
			WLBorderType borderType = j == item.monthlyDatas.size() - 1 ? WLBorderType.RightBoderOnly
					: WLBorderType.None;
			this.setStyleCell(monthCell, backgroundColor, borderType);

			// Next column.
			currentColumn++;
		}

		// Fill Total Cell.
		Cell totalCell = cells.get(currentRow, currentColumn);
		item.calculateTotal();
		totalCell.setValue(item.getTotal());
		this.setStyleCell(totalCell, backgroundColor, WLBorderType.Outside);
		
		// Check row is last of page.
		if (currentRow % MAX_ROW_ON_ONE_PAGE == (ROW_START_REPORT + MAX_RECORD_ON_ONE_PAGE)) {
			this.breakPage(reportContext, currentRow);
		}
	}
	
	/**
	 * Break page.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 */
	private void breakPage(AsposeCellsReportContext reportContext, Integer currentRow) {
		int currentPage = currentRow / MAX_ROW_ON_ONE_PAGE + 1;
		currentRow = currentPage * MAX_ROW_ON_ONE_PAGE + ROW_START_REPORT;
	}
	
	/**
	 * Fill salary header table.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 */
	private void fillSalaryHeaderTable(AsposeCellsReportContext reportContext, Integer currentRow) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int startColumn = COLUMN_START_REPORT;
		
		// Create Salary label cell.
		Range salaryLabelRange = cells.createRange(currentRow, COLUMN_START_REPORT, startColumn, 2);
		salaryLabelRange.get(0, 0).setValue("【給与支給】");
		salaryLabelRange.merge();
		this.setStyleRange(salaryLabelRange, BLUE_COLOR);
		startColumn++;
		
		// Create 12 month cells.
		for (int i = 1; i <= 12; i++) {
			Cell monthCell = cells.get(currentRow, startColumn);
			monthCell.setValue(i + " 月");
			this.setStyleCell(monthCell, BLUE_COLOR, WLBorderType.Outside);
			startColumn++;
		}
		
		// Create Total cell.
		Cell totalCell = cells.get(currentRow, startColumn);
		totalCell.setValue("合計");
		this.setStyleCell(totalCell, BLUE_COLOR, WLBorderType.Outside);
		
		// Next row.
		currentRow++;
	}
	
	/**
	 * Fill bonus header table.
	 *
	 * @param reportContext the report context
	 */
	private void fillBonusHeaderTable(AsposeCellsReportContext reportContext) {
		// TODO: wait confirm with Ba Vu san.
	}
	
	/**
	 * Sets the style range.
	 *
	 * @param range the range
	 * @param backgroundColor the background color
	 */
	private void setStyleRange(Range range, Color backgroundColor) {
		range.setOutlineBorders(CellBorderType.THIN, Color.getBlack());
		Style style = new Style();
		style.setTextWrapped(true);
		if (backgroundColor != null) {
			style.setForegroundColor(backgroundColor);
			style.setPattern(BackgroundType.SOLID);
		}
		range.setStyle(style);
	}
	
	/**
	 * Sets the style cell.
	 *
	 * @param cell the cell
	 * @param backgroundColor the background color
	 * @param borderType the border type
	 */
	private void setStyleCell(Cell cell, Color backgroundColor, WLBorderType borderType) {
		Style style = cell.getStyle();
		if (backgroundColor != null) {
			style.setForegroundColor(backgroundColor);
			style.setPattern(BackgroundType.SOLID);
		}
		switch (borderType) {
		case None:
			break;
		case Outside:
			// Setting the line style of the top border
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
			// Setting the line style of the bottom border
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			// Setting the line style of the left border
			style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
			// Setting the line style of the right border
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
			break;
		case RightBoderOnly:
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOTTED, Color.getBlack());
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator
	 * #generateWithNewLayout(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData)
	 */
	@Override
	public void generateWithNewLayout(FileGeneratorContext fileContext, WLNewLayoutReportData reportData) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			
			//Integer currentRow = ROW_START_REPORT;
			
			// Fill header data.
			reportContext.setDataSource("Employee", reportData.headerData);
			
			// Fill Salary Total Items content.
			
			// Fill Bonus Total Items content.
			
			// Fill Other Items before year end.
			
			// Fill Salary Payment items.
			
			// Fill Salary Deduction items.
			
			// Fill Salary Attendance items.
			
			// Fill Bonus payment and bonus deduction items.
			
			// Fill Bonus Attendance items
			
			// process data binginds in template
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Safe sub list.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @return the list
	 */
	protected <T> List<T> safeSubList(List<T> list, int fromIndex, int toIndex) {
		int size = list.size();
		if (fromIndex >= size || toIndex <= 0 || fromIndex >= toIndex) {
			return new ArrayList<>();
		}

		fromIndex = Math.max(0, fromIndex);
		toIndex = Math.min(size, toIndex);
		return list.subList(fromIndex, toIndex);
	}
	
	/**
	 * The Enum WLBorderType.
	 */
	private enum WLBorderType {
		
		/** The None. */
		None,
		
		/** The Outside. */
		Outside,
		
		/** The Right boder only. */
		RightBoderOnly
		
	}

}
