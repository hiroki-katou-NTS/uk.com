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

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerReportGenerator
	 * #generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 *  nts.uk.file.pr.app.export.wageledger.data.WageLedgerReportData)
	 */
	@Override
	public void generateWithOldLayout(FileGeneratorContext fileContext, WLOldLayoutReportData reportData) {
		
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			
			MutableInt currentRow = new MutableInt(ROW_START_REPORT);
			
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
			this.fillBonusHeaderTable(reportContext, reportData.headerData, currentRow);
			this.fillPaymentDataContentOldLayout(reportContext, reportData.bonusPaymentData, currentRow);
			this.breakPage(reportContext, currentRow);
			
			// Fill Bonus deduction, bonus attendance content and total bonus.
			this.fillDeductionAndAttendanceDataContentOldLayout(reportContext, reportData.bonusDeductionData,
					reportData.totalBonusData,reportData.salaryAttendanceDatas, currentRow);
			
			// Set print area.
			Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
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
	 */
	private void fillPaymentDataContentOldLayout(AsposeCellsReportContext reportContext, PaymentData paymentData,
			MutableInt currentRow) {
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
			DeductionData deductionData, ReportItemDto totalData, List<ReportItemDto> attendanceData, MutableInt currentRow) {
		
		// Fill Deduction data.
		List<ReportItemDto> reportItems = deductionData.aggregateItemList;
		reportItems.add(deductionData.totalDeduction);
		this.fillReportItemsData(reportContext, reportItems, currentRow, "控除");
		
		// Fill total salary (net salary) item.
		this.fillItemData(reportContext, totalData, currentRow, COLUMN_START_REPORT, GREEN_COLOR, true);
		
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
			MutableInt currentRow, String contentName) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int totalItemData = reportItems.size();
		int fromIndex = 0;
		
		while (totalItemData > 0) {
			int toIndex = fromIndex + MAX_RECORD_ON_ONE_PAGE;
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
				Color backgroundColor = i % 2 == 0 ? GREEN_COLOR : null;
				this.fillItemData(reportContext, item, currentRow, currentColumn, backgroundColor, false);
			}
			
			
			// Caculate to next page.
			totalItemData -= MAX_RECORD_ON_ONE_PAGE;
			fromIndex += MAX_RECORD_ON_ONE_PAGE;
			
			// Next page.
			if (totalItemData > 0) {
				this.breakPage(reportContext, currentRow);
			}
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
	private void fillItemData(AsposeCellsReportContext reportContext, ReportItemDto item, MutableInt currentRow,
			Integer currentColumn, Color backgroundColor, Boolean isTotalData) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		
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
		for (int j = 0; j < item.monthlyDatas.size(); j++) {
			MonthlyData data = dataMap.get(j + 1);
			Cell monthCell = cells.get(currentRow.intValue(), currentColumn);
			monthCell.setValue(data.amount);

			// Set style for cell.
			StyleModel dataCellStyle = new StyleModel();
			dataCellStyle.borderType = j == item.monthlyDatas.size() - 1 ? WLBorderType.RightBoderOnly
					: WLBorderType.None;
			dataCellStyle.backgroundColor = backgroundColor;
			this.setStyleCell(monthCell, dataCellStyle);

			// Next column.
			currentColumn++;
		}

		// Fill Total Cell.
		Cell totalCell = cells.get(currentRow.intValue(), currentColumn);
		item.calculateTotal();
		totalCell.setValue(item.getTotal());
		StyleModel totalCellStyle = new StyleModel();
		totalCellStyle.backgroundColor = backgroundColor;
		totalCellStyle.borderType = WLBorderType.Outside;
		this.setStyleCell(totalCell, totalCellStyle);
		
		// Next row.
		currentRow.increment();
	}
	
	/**
	 * Break page.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 */
	private void breakPage(AsposeCellsReportContext reportContext, MutableInt currentRow) {
		int currentPage = currentRow.intValue() / MAX_ROW_ON_ONE_PAGE + 1;
		currentRow.setValue(currentPage * MAX_ROW_ON_ONE_PAGE + ROW_START_REPORT);
	}
	
	/**
	 * Fill salary header table.
	 *
	 * @param reportContext the report context
	 * @param currentRow the current row
	 */
	private void fillSalaryHeaderTable(AsposeCellsReportContext reportContext, MutableInt currentRow) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int startColumn = COLUMN_START_REPORT;
		
		// Create Salary label cell.
		Range salaryLabelRange = cells.createRange(currentRow.intValue(), startColumn, 1, 2);
		salaryLabelRange.get(0, 0).setValue("【給与支給】");
		salaryLabelRange.merge();
		this.setStyleRange(salaryLabelRange, BLUE_COLOR);
		startColumn += 2;
		
		// Create 12 month cells.
		for (int i = 1; i <= 12; i++) {
			Cell monthCell = cells.get(currentRow.intValue(), startColumn);
			monthCell.setValue(i + " 月");
			this.setStyleCell(monthCell, StyleModel.createHeaderCellStyle());
			startColumn++;
		}
		
		// Create Total cell.
		Cell totalCell = cells.get(currentRow.intValue(), startColumn);
		totalCell.setValue("合計");
		this.setStyleCell(totalCell, StyleModel.createHeaderCellStyle());
		
		// Next row.
		currentRow.increment();
	}
	
	/**
	 * Fill bonus header table.
	 *
	 * @param reportContext the report context
	 * @param headerData the header data
	 * @param currentRow the current row
	 */
	private void fillBonusHeaderTable(AsposeCellsReportContext reportContext, HeaderReportData headerData,
			MutableInt currentRow) {
		Worksheet ws = reportContext.getDesigner().getWorkbook().getWorksheets().get(0);
		Cells cells = ws.getCells();
		int startColumn = COLUMN_START_REPORT;
		
		// Create Bunus label cell.
		Range bonusLabelRange = cells.createRange(currentRow.intValue(), startColumn, 1, 2);
		bonusLabelRange.get(0, 0).setValue("【賞与支給】");
		bonusLabelRange.merge();
		this.setStyleRange(bonusLabelRange, BLUE_COLOR);
		startColumn++;
		
		// Create bonus month cells.
		for (int i = 0; i < headerData.bonusMonthList.size(); i++) {
			Cell monthCell = cells.get(currentRow.intValue(), startColumn);
			monthCell.setValue(headerData.bonusMonthList.get(i) + " 月");
			this.setStyleCell(monthCell, StyleModel.createHeaderCellStyle());
			startColumn++;
		}
		
		// Create Total cell.
		Cell totalCell = cells.get(currentRow.intValue(), startColumn);
		totalCell.setValue("合計");
		this.setStyleCell(totalCell, StyleModel.createHeaderCellStyle());
		
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
		range.applyStyle(style, styleFlag);;
	}
	
	/**
	 * Sets the style cell.
	 *
	 * @param cell the cell
	 * @param backgroundColor the background color
	 * @param borderType the border type
	 */
	private void setStyleCell(Cell cell, StyleModel styleModel) {
		Style style = cell.getStyle();
		if (styleModel.backgroundColor != null) {
			style.setForegroundColor(styleModel.backgroundColor);
			style.setPattern(BackgroundType.SOLID);
		}
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
		case RightBoderOnly:
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOTTED, Color.getBlack());
			break;
		default:
			break;
		}
		if (styleModel.isCenterText) {
			style.setVerticalAlignment(TextAlignmentType.CENTER);
			style.setHorizontalAlignment(TextAlignmentType.CENTER);
		}
		if (styleModel.isWrapText) {
			style.setTextWrapped(true);
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
		
		/** The Right boder only. */
		RightBoderOnly,
		
		
		
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
		
		/**
		 * Creates the header cell style.
		 *
		 * @return the style model
		 */
		public static StyleModel createHeaderCellStyle() {
			StyleModel style = new StyleModel();
			style.backgroundColor = BLUE_COLOR;
			style.borderType = WLBorderType.Outside;
			style.isCenterText = true;
			return style;
		}
	}

}
