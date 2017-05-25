/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.math.BigDecimal;
import java.util.List;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.StyleFlag;
import com.aspose.cells.Worksheet;

import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingOnceDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingThreeDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingTwoDto;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.ShowBreakLine;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentRefundPaddingDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentSalaryItemDto;

/**
 * The Class PaymentReportBaseGenerator.
 */
public abstract class PaymentReportBaseGenerator extends BaseGeneratorSetting {

	/** The template style. */
	protected TemplateStyle templateStyle;

	/** The work sheet. */
	protected Worksheet workSheet;

	/** The cells. */
	protected Cells cells;

	/** The page setup. */
	protected PageSetup pageSetup;

	/** The employee. */
	protected PaymentReportDto employee;

	/** The page header range. */
	protected Range pageHeaderRange;

	/** The payment report data. */
	protected PaymentReportData paymentReportData;

	/** The number of column per item. */
	private int numberOfColumnPerItem;

	/** The first category row. */
	private int firstCategoryRow;

	/** The current row. */
	private int currentRow;

	/** The current column. */
	private int currentColumn;

	/** The max columns per item line. */
	private int maxColumnsPerItemLine;

	/** The max person per page. */
	private int maxPersonPerPage;

	/** The person count. */
	private int personCount;

	/** The remark total row. */
	private int remarkTotalRow;

	/** The report data. */
	private List<PaymentReportDto> reportData;

	/** The config. */
	private PaymentRefundPaddingDto config;

	/**
	 * Gets the number of column per item.
	 *
	 * @return the number of column per item
	 */
	abstract int getNumberOfColumnPerItem();

	/**
	 * Gets the person per page.
	 *
	 * @return the person per page
	 */
	abstract int getPersonPerPage();

	/**
	 * Gets the page header end cell.
	 *
	 * @return the page header end cell
	 */
	abstract String getPageHeaderEndCell();

	/**
	 * Gets the category header cell.
	 *
	 * @return the category header cell
	 */
	abstract String getCategoryHeaderCell();

	/**
	 * Gets the item name cell.
	 *
	 * @return the item name cell
	 */
	abstract String getItemNameCell();

	/**
	 * Gets the item value cell.
	 *
	 * @return the item value cell
	 */
	abstract String getItemValueCell();

	/**
	 * Gets the remark cell.
	 *
	 * @return the remark cell
	 */
	abstract String getRemarkCell();

	/**
	 * Prints the page content.
	 */
	abstract void printPageContent();

	/**
	 * Gets the remark total row.
	 *
	 * @return the remark total row
	 */
	abstract int getRemarkTotalRow();

	/**
	 * Gets the header template.
	 *
	 * @return the header template
	 */
	abstract List<CellValue> getHeaderTemplate();

	/**
	 * Sets the item range width.
	 *
	 * @param width the new item range width
	 */
	// TODO Has not been used yet...
	protected void setItemRangeWidth(double width) {
		Range itemRange = cells.createRange(FIRST_ROW, FIRST_COLUMN + CATEGORY_HEADER_WIDTH, NUMBER_OF_ROW_PER_ITEM,
				maxColumnsPerItemLine);
		itemRange.setColumnWidth(width);
	}

	/**
	 * Break page.
	 */
	protected void breakPage() {
		workSheet.getHorizontalPageBreaks().add(currentRow);
	}

	/**
	 * Prints the remark.
	 */
	protected void printRemark() {
		Range remark = cells.createRange(currentRow, FIRST_COLUMN + MARGIN_CELL, remarkTotalRow,
				maxColumnsPerItemLine - MARGIN_CELL);
		remark.merge();
		remark.setValue(employee.getRemark());
		remark.setStyle(templateStyle.remarkStyle);
		currentRow += remarkTotalRow;
	}

	/**
	 * Prints the page header.
	 */
	protected void printPageHeader() {
		int pageHeaderRowCount = pageHeaderRange.getRowCount();
		// On first page.
		if (currentRow == FIRST_ROW) {
			printCellValue(getHeaderTemplate());
		}
		// On other pages.
		else {
			// Copy style
			Range newPageHeaderRange = cells.createRange(currentRow, FIRST_COLUMN, pageHeaderRange.getRowCount(),
					pageHeaderRange.getColumnCount());
			try {
				newPageHeaderRange.copy(pageHeaderRange);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Set value.
			printCellValue(getHeaderTemplate());
		}
		currentRow += pageHeaderRowCount;
		firstCategoryRow = currentRow;
	}

	/**
	 * Prints the payment items.
	 */
	protected void printPaymentItems() {
		printCategoryHeader("支給");
		printCategoryContent(employee.getPaymentItems());
		nextCategory();
	}

	/**
	 * Prints the deduction items.
	 */
	protected void printDeductionItems() {
		printCategoryHeader("控除");
		printCategoryContent(employee.getDeductionItems());
		nextCategory();
	}

	/**
	 * Prints the attendance items.
	 */
	protected void printAttendanceItems() {
		printCategoryHeader("勤怠");
		printCategoryContent(employee.getAttendanceItems());
		nextCategory();
	}

	/**
	 * Prints the article items.
	 */
	protected void printArticleItems() {
		printCategoryHeader("記事");
		printCategoryContent(employee.getArticleItems());
		nextCategory();
	}

	/**
	 * Prints the other items.
	 */
	protected void printOtherItems() {
		printCategoryHeader("他");
		printCategoryContent(employee.getOtherItems());
		nextCategory();
	}

	/**
	 * Break lines.
	 *
	 * @param numberOfLine the number of line
	 */
	protected void breakLines(int numberOfLine) {
		firstCategoryRow += numberOfLine;
		currentRow += numberOfLine;
	}

	/**
	 * Next item line.
	 */
	protected void nextItemLine() {
		// Reset column & enter 2 rows.
		currentColumn = FIRST_COLUMN + MARGIN_CELL + CATEGORY_HEADER_WIDTH;
		currentRow += NUMBER_OF_ROW_PER_ITEM + NUMBER_OF_ROW_PER_ITEM;
	}

	/**
	 * Next category.
	 */
	protected void nextCategory() {
		nextItemLine();
		mergeHeader();
		firstCategoryRow = currentRow;
	}

	/**
	 * Prints the section title.
	 *
	 * @param title the title
	 */
	protected void printSectionTitle(String title) {
		Cell sectionTitle = cells.get(firstCategoryRow - NUMBER_OF_ROW_PER_ITEM, FIRST_COLUMN + MARGIN_CELL);
		sectionTitle.setValue(title);
		sectionTitle.setStyle(templateStyle.remarkStyle);
	}

	/**
	 * Prints the category header.
	 *
	 * @param headerName the header name
	 */
	protected void printCategoryHeader(String headerName) {
		cells.get(firstCategoryRow, FIRST_COLUMN + MARGIN_CELL).setValue(headerName);
	}

	/**
	 * Prints the data.
	 */
	protected void printData() {
		// Prepare for print data.
		initialize();

		// Print data.
		reportData.forEach(item -> {
			employee = item;
			printPageHeader();
			printPageContent();
			personCount++;
			setPadding();

			if (isPageHasEnoughPerson()) {
				breakPage();
			}
		});
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		// Check on initialization.
		checkOnInitialization();

		// Get cells.
		this.cells = workSheet.getCells();

		// Get page setup.
		this.pageSetup = workSheet.getPageSetup();
		resetMargin();

		// Set template style.
		this.setTemplateStyle();

		// Set page header range.
		this.setPageHeaderRange();

		// Set total row of remark.
		this.remarkTotalRow = this.getRemarkTotalRow();

		// Set max number of person on each page.
		this.maxPersonPerPage = this.getPersonPerPage();

		// Set number of columns to merged.
		this.numberOfColumnPerItem = this.getNumberOfColumnPerItem();

		/** The current row. */
		this.currentRow = FIRST_ROW;

		/** The current column. */
		this.currentColumn = FIRST_COLUMN + MARGIN_CELL + CATEGORY_HEADER_WIDTH;

		// Set max columns per item line.
		this.maxColumnsPerItemLine = ITEMS_PER_ROW * this.numberOfColumnPerItem + CATEGORY_HEADER_WIDTH + MARGIN_CELL;

		// Set report data & config.
		this.reportData = paymentReportData.getReportData();
		this.config = paymentReportData.getConfig();
	}

	/**
	 * Check on initialization.
	 */
	private void checkOnInitialization() {
		if (workSheet == null) {
			throw new RuntimeException("WorkSheet is not defined.");
		}
		if (paymentReportData == null) {
			throw new RuntimeException("paymentReportData is not defined.");
		}
	}

	/**
	 * Checks if is page has enough person.
	 *
	 * @return true, if is page has enough person
	 */
	private boolean isPageHasEnoughPerson() {
		return personCount != 0 && personCount % maxPersonPerPage == 0;
	}

	/**
	 * Gets the style.
	 *
	 * @param cellName the cell name
	 * @return the style
	 */
	private Style getStyle(String cellName) {
		if (StringUtil.isNullOrEmpty(cellName, true)) {
			return new Style();
		}
		return cells.get(cellName).getStyle();
	}

	/**
	 * Sets the current row height.
	 *
	 * @param height the new current row height
	 */
	private void setCurrentRowHeight(double height) {
		cells.setRowHeight(currentRow, height);
	}

	/**
	 * Merge header.
	 */
	private void mergeHeader() {
		int headerHeight = currentRow - firstCategoryRow;
		Range header = cells.createRange(firstCategoryRow, FIRST_COLUMN + MARGIN_CELL, headerHeight,
				CATEGORY_HEADER_WIDTH);
		header.merge();
		header.setStyle(templateStyle.headerStyle);
	}

	/**
	 * Prints the cell value.
	 *
	 * @param list the list
	 */
	private void printCellValue(List<CellValue> list) {
		list.forEach(item -> {
			Cell cell = cells.get(item.row, item.col);
			cell.setValue(item.value);
		});

	}

	/**
	 * Sets the page header range.
	 */
	private void setPageHeaderRange() {
		String upperLeftCell = FIRST_CELL;
		String lowerRightCell = getPageHeaderEndCell();
		if (StringUtil.isNullOrEmpty(upperLeftCell, true) || StringUtil.isNullOrEmpty(lowerRightCell, true)) {
			throw new RuntimeException("Page header range is not set.");
		}
		this.pageHeaderRange = cells.createRange(upperLeftCell, lowerRightCell);
	}

	/**
	 * Sets the template style.
	 */
	private void setTemplateStyle() {
		this.templateStyle = new TemplateStyle();
		this.templateStyle.headerStyle = getStyle(getCategoryHeaderCell());
		this.templateStyle.nameStyle = getStyle(getItemNameCell());
		this.templateStyle.valueStyle = getStyle(getItemValueCell());
		this.templateStyle.remarkStyle = getStyle(getRemarkCell());
		// In case remark is too long.
		this.templateStyle.remarkStyle.setTextWrapped(true);
	}

	/**
	 * To excel row unit.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double toExcelRowUnit(BigDecimal value) {
		return value.doubleValue() / EXCEL_ROW_UNIT_IN_MM;
	}

	/**
	 * To excel col unit.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double toExcelColUnit(BigDecimal value) {
		return value.doubleValue() / EXCEL_COL_UNIT_IN_MM;
	}

	/**
	 * Sets the padding one out.
	 *
	 * @param setting the new padding one out
	 */
	private void setPaddingOneOut(RefundPaddingOnceDto setting) {
		cells.setRowHeight(FIRST_ROW, toExcelRowUnit(setting.getPaddingTop()));
		cells.setColumnWidth(FIRST_COLUMN, toExcelColUnit(setting.getPaddingLeft()));
	}

	/**
	 * Sets the padding two out.
	 *
	 * @param setting the new padding two out
	 */
	private void setPaddingTwoOut(RefundPaddingTwoDto setting) {
		cells.setRowHeight(FIRST_ROW, toExcelRowUnit(setting.getUpperAreaPaddingTop()));
		cells.setColumnWidth(FIRST_COLUMN, toExcelColUnit(setting.getPaddingLeft()));

		if (isFirstPersonOnPage()) {
			if (isFirstPersonOnPage()) {
				if (hasBreakLine(setting)) {
					setBreakLine(toExcelRowUnit(setting.getBreakLineMargin()));
				}
				setCurrentRowHeight(toExcelRowUnit(setting.getUnderAreaPaddingTop()));
			}
			// Set person 2 top margin.
			setCurrentRowHeight(toExcelRowUnit(setting.getUnderAreaPaddingTop()));
		}
	}

	/**
	 * Sets the padding three out.
	 *
	 * @param setting the new padding three out
	 */
	private void setPaddingThreeOut(RefundPaddingThreeDto setting) {
		cells.setRowHeight(FIRST_ROW, toExcelRowUnit(setting.getUpperAreaPaddingTop()));
		cells.setColumnWidth(FIRST_COLUMN, toExcelColUnit(setting.getPaddingLeft()));

		if (isFirstPersonOnPage()) {
			if (hasBreakLine(setting)) {
				setBreakLine(toExcelRowUnit(setting.getBreakLineMarginTop()));
			}
			// Set person 2 top margin.
			setCurrentRowHeight(toExcelRowUnit(setting.getMiddleAreaPaddingTop()));
		}
		if (isSecondPersonOnPage()) {
			if (hasBreakLine(setting)) {
				setBreakLine(toExcelRowUnit(setting.getBreakLineMarginButtom()));
			}
			// Set person 3 top margin.
			setCurrentRowHeight(toExcelRowUnit(setting.getUnderAreaPaddingTop()));
		}
	}

	/**
	 * Checks for break line.
	 *
	 * @param setting the setting
	 * @return true, if successful
	 */
	private boolean hasBreakLine(RefundPaddingThreeDto setting) {
		return setting.getIsShowBreakLine() == ShowBreakLine.DISPLAY.value;
	}

	/**
	 * Checks for break line.
	 *
	 * @param setting the setting
	 * @return true, if successful
	 */
	private boolean hasBreakLine(RefundPaddingTwoDto setting) {
		return setting.getIsShowBreakLine() == ShowBreakLine.DISPLAY.value;
	}

	/**
	 * Sets the break line.
	 *
	 * @param marginTop the new break line
	 */
	private void setBreakLine(double marginTop) {
		// Set break line top margin.
		setCurrentRowHeight(marginTop);

		// Get break line range.
		Range breakLine = cells.createRange(currentRow, FIRST_COLUMN + MARGIN_CELL, 1,
				maxColumnsPerItemLine - MARGIN_CELL);
		breakLine.merge();

		// Set break line style
		Style style = new Style();
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.DASHED, Color.getBlack());

		// Set style flag.
		StyleFlag styleFlag = new StyleFlag();
		styleFlag.setBorders(true);

		// Apply style.
		breakLine.applyStyle(style, styleFlag);

		// next line.
		breakLines(1);
	}

	/**
	 * Checks if is first person on page.
	 *
	 * @return true, if is first person on page
	 */
	private boolean isFirstPersonOnPage() {
		return personCount % maxPersonPerPage == FIRST_PERSON;
	}

	/**
	 * Checks if is second person on page.
	 *
	 * @return true, if is second person on page
	 */
	private boolean isSecondPersonOnPage() {
		return personCount % maxPersonPerPage == SECOND_PERSON;
	}

	/**
	 * Sets the padding.
	 */
	private void setPadding() {
		switch (config.getPrintType()) {
		case 1:
			setPaddingOneOut(config.getRefundPaddingOnceDto());
			break;
		case 2:
			setPaddingTwoOut(config.getRefundPaddingTwoDto());
			break;
		case 3:
			setPaddingThreeOut(config.getRefundPaddingThreeDto());
			break;
		default:
			break;
		}
	}

	/**
	 * Reset margin.
	 */
	private void resetMargin() {
		pageSetup.setTopMargin(0);
		pageSetup.setLeftMargin(0);
	}

	/**
	 * Prints the category content.
	 *
	 * @param listItem the list item
	 */
	private void printCategoryContent(List<PaymentSalaryItemDto> listItem) {
		listItem.forEach(item -> {
			// Break line if current column = maxColumnsPerItemLine.
			if (currentColumn == maxColumnsPerItemLine) {
				nextItemLine();
			}

			// Get item cell.
			Range nameCell = cells.createRange(currentRow, currentColumn, NUMBER_OF_ROW_PER_ITEM,
					numberOfColumnPerItem);
			Range valueCell = cells.createRange(currentRow + NUMBER_OF_ROW_PER_ITEM, currentColumn,
					NUMBER_OF_ROW_PER_ITEM, numberOfColumnPerItem);

			// Merge cell.
			nameCell.merge();
			valueCell.merge();

			// Set style.
			nameCell.setStyle(templateStyle.nameStyle);
			valueCell.setStyle(templateStyle.valueStyle);

			// Set value.
			if (item.isView()) {
				nameCell.setValue(item.getItemName());
				valueCell.setValue(item.getItemVal());
			} else {
				nameCell.setValue(" ");
				valueCell.setValue(" ");
			}

			// Next item.
			currentColumn += numberOfColumnPerItem;
		});
	}

	/**
	 * The Class TemplateStyle.
	 */
	private class TemplateStyle {

		/** The remark style. */
		private Style remarkStyle;

		/** The header style. */
		private Style headerStyle;

		/** The name style. */
		private Style nameStyle;

		/** The value style. */
		private Style valueStyle;

	}

	/**
	 * The Class CellValue.
	 */
	protected class CellValue {

		/** The row. */
		private int row;

		/** The col. */
		private int col;

		/** The value. */
		private Object value;

		/**
		 * Instantiates a new cell value.
		 *
		 * @param row the row
		 * @param col the col
		 * @param value the value
		 */
		public CellValue(int row, int col, Object value) {
			this.row = row + currentRow;
			this.col = col;
			this.value = value;
		}

		/**
		 * Instantiates a new cell value.
		 *
		 * @param cellName the cell name
		 * @param value the value
		 */
		public CellValue(String cellName, Object value) {
			this.row = cells.get(cellName).getRow() + currentRow;
			this.col = cells.get(cellName).getColumn();
			this.value = value;
		}

	}

}
