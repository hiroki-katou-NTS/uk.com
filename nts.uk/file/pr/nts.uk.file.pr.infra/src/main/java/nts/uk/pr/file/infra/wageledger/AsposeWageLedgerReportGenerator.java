/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.mutable.MutableInt;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.StyleFlag;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.DeductionData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
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
	private static final int MAX_ROW_ON_ONE_PAGE = 47;
	
	/** The Constant MAX_RECORD_ON_ONE_PAGE. */
	private static final int MAX_RECORD_ON_ONE_PAGE = 35;
	
	/** The Constant BLUE_COLOR. */
	private static final Color BLUE_COLOR = Color.fromArgb(197, 241, 247);
	
	/** The Constant GREEN_COLOR. */
	private static final Color GREEN_COLOR = Color.fromArgb(199, 243, 145);
	
	/** The amount item left on current page. */
	private int amountItemLeftOnCurrentPage = MAX_RECORD_ON_ONE_PAGE;
	
	/** The is green row. */
	private boolean isGreenRow;

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator
	 * #generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 *  nts.uk.file.pr.app.export.wageledger.data.WageLedgerReportData)
	 */
	@Override
	public void generateWithOldLayout(FileGeneratorContext fileContext, WLOldLayoutReportData reportData) {
		
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
			
			MutableInt currentRow = new MutableInt(ROW_START_REPORT);
			HeaderReportData headerData = reportData.headerData;
			
			// Fill header data.
			reportContext.getDesigner().setDataSource("HEADER", Arrays.asList(reportData.headerData));
			
			// Fill Salary payment content.
			this.fillSalaryOrBonusHeaderTable(reportContext, currentRow, headerData.salaryMonthList, "【給与支給】");
			this.fillPaymentDataContentOldLayout(reportContext, reportData.salaryPaymentData,
					currentRow, headerData.salaryMonthList);
			this.breakPage(reportContext, currentRow, headerData.salaryMonthList.size());
			
			// Fill Salary deduction, salary attendance content and net salary.
			this.fillDeductionAndAttendanceDataContentOldLayout(reportContext, reportData.salaryDeductionData,
					reportData.netSalaryData, reportData.salaryAttendanceDatas, currentRow, headerData.salaryMonthList);
			
			// Create end line of salary part.
			Range salaryEndRowRange = ws.getCells().createRange(currentRow.intValue() - 1, COLUMN_START_REPORT + 2, 1,
					headerData.salaryMonthList.size() + 1);
			salaryEndRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			this.breakPage(reportContext, currentRow, headerData.bonusMonthList.size());
			
			// Fill Bonus payment content.
			this.fillSalaryOrBonusHeaderTable(reportContext, currentRow, headerData.bonusMonthList, "【賞与支給】");
			this.fillPaymentDataContentOldLayout(reportContext, reportData.bonusPaymentData,
					currentRow, headerData.bonusMonthList);
			this.breakPage(reportContext, currentRow, headerData.bonusMonthList.size());
			
			// Fill Bonus deduction, bonus attendance content and total bonus.
			this.fillDeductionAndAttendanceDataContentOldLayout(reportContext, reportData.bonusDeductionData,
					reportData.totalBonusData,reportData.salaryAttendanceDatas, currentRow, headerData.bonusMonthList);
			
			// Create end line of bonus part.
			Range bonusEndRowRange = ws.getCells().createRange(currentRow.intValue() - 1, COLUMN_START_REPORT + 2, 1,
					headerData.bonusMonthList.size() + 1);
			bonusEndRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			
			// Set print area.
			PageSetup pageSetup = ws.getPageSetup();
			Cell endCell = ws.getCells().get(currentRow.intValue(), 14);
			pageSetup.setPrintArea("A1:" + endCell.getName());
			
			// process data binginds in template.
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.getWorkbook().save("C:\\test.xlsx");
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
	 * @param monthList the month list
	 */
	private void fillPaymentDataContentOldLayout(AsposeCellsReportContext reportContext,
			PaymentData paymentData, MutableInt currentRow, List<Integer> monthList) {
		List<ReportItemDto> reportItems = paymentData.aggregateItemList;
		reportItems.add(paymentData.totalTax);
		reportItems.add(paymentData.totalTaxExemption);
		reportItems.add(paymentData.totalSubsidy);
		
		// Fill item list.
		this.fillReportItemsData(reportContext, reportItems, currentRow, "支給", monthList);
	}
	
	/**
	 * Fill deduction and attendance data content old layout.
	 *
	 * @param reportContext the report context
	 * @param deductionData the deduction data
	 * @param totalData the total data
	 * @param attendanceData the attendance data
	 * @param currentRow the current row
	 * @param monthList the month list
	 */
	private void fillDeductionAndAttendanceDataContentOldLayout(AsposeCellsReportContext reportContext,
			DeductionData deductionData, ReportItemDto totalData, List<ReportItemDto> attendanceData,
			MutableInt currentRow, List<Integer> monthList) {
		
		// Fill Deduction data.
		List<ReportItemDto> reportItems = deductionData.aggregateItemList;
		reportItems.add(deductionData.totalDeduction);
		this.fillReportItemsData(reportContext, reportItems, currentRow, "控除", monthList);
		
		// Fill total salary (net salary) item.
		this.fillItemData(reportContext, totalData, currentRow, COLUMN_START_REPORT, true, monthList);
		
		// Fill Atendance Data.
		this.fillReportItemsData(reportContext, attendanceData, currentRow, "勤怠", monthList);
	}
	
	/**
	 * Fill report items data.
	 *
	 * @param reportContext the report context
	 * @param reportItems the report items
	 * @param currentRow the current row
	 * @param contentName the content name
	 * @param monthList the month list
	 */
	private void fillReportItemsData(AsposeCellsReportContext reportContext, List<ReportItemDto> reportItems,
			MutableInt currentRow, String contentName, List<Integer> monthList) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int totalItemData = reportItems.size();
		int fromIndex = 0;
		
		while (totalItemData > 0) {
			int amountItemOnPage = Math.min(this.amountItemLeftOnCurrentPage, MAX_RECORD_ON_ONE_PAGE);
			int toIndex = fromIndex + amountItemOnPage;
			List<ReportItemDto> items = this.safeSubList(reportItems, fromIndex, toIndex);
			int currentColumn = COLUMN_START_REPORT;
			
			// Fill Payment label cell.
			if (contentName != null) {
				int totalRow = items.size();
				Range paymentLabelRange = cells.createRange(currentRow.intValue(), 0, totalRow, 1);
				paymentLabelRange.get(0, 0).setValue(contentName);
				paymentLabelRange.merge();
				this.setStyleRange(paymentLabelRange, null);
				currentColumn++;
			}
			
			// Fill report item list.
			for (int i = 0; i < items.size(); i++) {
				ReportItemDto item = items.get(i);
				
				// Fill items
				this.fillItemData(reportContext, item, currentRow, currentColumn, false, monthList);
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
	 * @param isTotalData the is total data
	 * @param monthList the month list
	 */
	private void fillItemData(AsposeCellsReportContext reportContext, ReportItemDto item, MutableInt currentRow,
			Integer currentColumn, Boolean isTotalData, List<Integer> monthList) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		Color backgroundColor = this.isGreenRow ? GREEN_COLOR : null;
		this.isGreenRow = !this.isGreenRow;
		
		
		// Fill item name cell.
		int amountColumn = isTotalData ? 2 : 1;
		Color nameColor = isTotalData ? GREEN_COLOR : backgroundColor;
		Range nameCell = cells.createRange(currentRow.intValue(), currentColumn, 1, amountColumn);
		nameCell.get(0, 0).setValue(item.name);
		nameCell.merge();
		this.setStyleRange(nameCell, nameColor);
		currentColumn += amountColumn;
		
		// Fill item data cells.
		Map<Integer, MonthlyData> dataMap = item.monthlyDatas.stream()
				.collect(Collectors.toMap(d -> d.month, Function.identity()));
		for (int j = 0; j < monthList.size(); j++) {
			MonthlyData data = dataMap.get(monthList.get(j));
			Cell monthCell = cells.get(currentRow.intValue(), currentColumn);
			monthCell.setValue(data.amount);

			// Set style for cell.
			StyleModel dataCellStyle = StyleModel
					.createItemCellStyle(backgroundColor, j != item.monthlyDatas.size() - 1);
			this.setStyleCell(monthCell, dataCellStyle);

			// Next column.
			currentColumn++;
		}

		// Fill Total Cell.
		Cell totalCell = cells.get(currentRow.intValue(), currentColumn);
		item.calculateTotal();
		totalCell.setValue(item.getTotal());
		StyleModel totalCellStyle = StyleModel.createTotalCellStyle(backgroundColor);
		this.setStyleCell(totalCell, totalCellStyle);
		
		// Next row.
		currentRow.increment();
		// Calculate amount item left on page.
		this.amountItemLeftOnCurrentPage--;
		
		// Check row is last of page.
		if (this.amountItemLeftOnCurrentPage == 0) {
			this.breakPage(reportContext, currentRow, monthList.size());
		}
	}
	
	/**
	 * Break page.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 */
	private void breakPage(AsposeCellsReportContext reportContext, MutableInt currentRow, Integer amountMonth) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		// Create line end page.
		Range endRowRange = cells.createRange(currentRow.intValue() - 1, COLUMN_START_REPORT + 2, 1, amountMonth + 1);
		endRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());

		int currentPage = currentRow.intValue() / MAX_ROW_ON_ONE_PAGE + 1;
		currentRow.setValue(currentPage * MAX_ROW_ON_ONE_PAGE + ROW_START_REPORT);
		this.amountItemLeftOnCurrentPage = MAX_RECORD_ON_ONE_PAGE;

		// Create line start page.
		Range startRowRange = cells.createRange(currentRow.intValue(), COLUMN_START_REPORT + 2, 1, amountMonth + 1);
		startRowRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
	}
	
	
	/**
	 * Fill salary or bonus header table.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 * @param monthList the month list
	 */
	private void fillSalaryOrBonusHeaderTable(AsposeCellsReportContext reportContext, MutableInt currentRow,
			List<Integer> monthList, String contentLabel) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int startColumn = COLUMN_START_REPORT;
		
		// Create Salary label cell.
		Range salaryLabelRange = cells.createRange(currentRow.decrementAndGet(), startColumn, 1, 2);
		salaryLabelRange.get(0, 0).setValue(contentLabel);
		salaryLabelRange.merge();
		this.setStyleRange(salaryLabelRange, BLUE_COLOR);
		startColumn += 2;
		
		// Create 12 month cells.
		for (int i = 0; i < monthList.size(); i++) {
			Cell monthCell = cells.get(currentRow.intValue(), startColumn);
			monthCell.setValue(monthList.get(i) + " 月");
			this.setStyleCell(monthCell, StyleModel.createHeaderCellStyle(false));
			startColumn++;
		}
		
		// Create Total cell.
		Cell totalCell = cells.get(currentRow.intValue(), startColumn);
		totalCell.setValue("合計");
		this.setStyleCell(totalCell, StyleModel.createHeaderCellStyle(true));
		
		// Next row.
		currentRow.increment();
	}
	
	/**
	 * Sets the style range.
	 *
	 * @param range the range
	 * @param backgroundColor the background color
	 */
	private void setStyleRange(Range range, Color backgroundColor) {
		Style style = new Style();
		style.setTextWrapped(true);
		
		// Setting the line style of the top border
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		// Setting the line style of the bottom border
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		// Setting the line style of the left border
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		// Setting the line style of the right border
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		
		// Set background Color.
		if (backgroundColor != null) {
			style.setForegroundColor(backgroundColor);
			style.setPattern(BackgroundType.SOLID);
		}
		
		// Set Alignment.
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		style.setHorizontalAlignment(TextAlignmentType.CENTER);
		
		// Style flag.
		StyleFlag styleFlag = new StyleFlag();
		styleFlag.setCellShading(true);
		styleFlag.setBorders(true);
		styleFlag.setHorizontalAlignment(true);
		styleFlag.setVerticalAlignment(true);
		styleFlag.setWrapText(true);
		range.applyStyle(style, styleFlag);;
	}
	
	/**
	 * Sets the style cell.
	 *
	 * @param cell the cell
	 * @param styleModel the style model
	 */
	private void setStyleCell(Cell cell, StyleModel styleModel) {
		Style style = cell.getStyle();
		
		// Background Color
		if (styleModel.backgroundColor != null) {
			style.setForegroundColor(styleModel.backgroundColor);
			style.setPattern(BackgroundType.SOLID);
		}
		
		// Border
		switch (styleModel.borderType) {
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
		case ItemCellBorder:
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
			break;
		case ToTalCellBorder:
			// Setting the line style of the left border
			style.setBorder(BorderType.LEFT_BORDER, CellBorderType.DOUBLE, Color.getBlack());
			// Setting the line style of the right border
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
			break;
		case TotalHeaderBorder:
			// Setting the line style of the top border
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
			// Setting the line style of the bottom border
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			// Setting the line style of the left border
			style.setBorder(BorderType.LEFT_BORDER, CellBorderType.DOUBLE, Color.getBlack());
			// Setting the line style of the right border
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		default:
			break;
		}
		
		// Alignment.
		if (styleModel.isCenterText) {
			style.setVerticalAlignment(TextAlignmentType.CENTER);
			style.setHorizontalAlignment(TextAlignmentType.CENTER);
		} else {
			style.setHorizontalAlignment(TextAlignmentType.RIGHT);
		}
		if (styleModel.isWrapText) {
			style.setTextWrapped(true);
		}
		
		// Number formart.
		if (styleModel.isNummeric) {
			style.setCustom("#,##0;-#,##0");
		}
		cell.setStyle(style);
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
			
			//MutableInt currentRow = ROW_START_REPORT;
			
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
		
		/** The Item cell border. */
		ItemCellBorder,
		
		/** The To tal cell border. */
		ToTalCellBorder,
		
		/** The Total header border. */
		TotalHeaderBorder
	}
	
	/**
	 * The Class StyleModel.
	 */
	public static class StyleModel {
		
		/** The background color. */
		public Color backgroundColor;
		
		/** The border type. */
		public WLBorderType borderType;
		
		/** The is center text. */
		public boolean isCenterText;
		
		/** The is wrap text. */
		public boolean isWrapText;
		
		/** The is nummeric. */
		public boolean isNummeric;
		
		/**
		 * Creates the header cell style.
		 *
		 * @return the style model
		 */
		public static StyleModel createHeaderCellStyle(boolean isTotalCell) {
			StyleModel style = new StyleModel();
			style.backgroundColor = BLUE_COLOR;
			style.borderType = isTotalCell ? WLBorderType.TotalHeaderBorder : WLBorderType.Outside;
			style.isCenterText = true;
			return style;
		}
		
		/**
		 * Creates the total cell style.
		 *
		 * @param backgroundColor the background color
		 * @return the style model
		 */
		public static StyleModel createTotalCellStyle(Color backgroundColor) {
			StyleModel style = new StyleModel();
			style.backgroundColor = backgroundColor;
			style.borderType = WLBorderType.ToTalCellBorder;
			style.isNummeric = true;
			return style;
		}
		
		/**
		 * Creates the item cell style.
		 *
		 * @param backgroundColor the background color
		 * @param isSetBorder the is set border
		 * @return the style model
		 */
		public static StyleModel createItemCellStyle(Color backgroundColor, boolean isSetBorder) {
			StyleModel style = new StyleModel();
			style.backgroundColor = backgroundColor;
			style.borderType = isSetBorder ? WLBorderType.ItemCellBorder : WLBorderType.None;
			style.isNummeric = true;
			return style;
		}
	}

}
