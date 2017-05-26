/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
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

	/** The config. */
	protected PaymentRefundPaddingDto config;

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
	 * Gets the page top margin.
	 *
	 * @return the page top margin
	 */
	abstract BigDecimal getPageTopMargin();

	/**
	 * Gets the page left margin.
	 *
	 * @return the page left margin
	 */
	abstract BigDecimal getPageLeftMargin();

	/**
	 * Sets the item range width.
	 *
	 * @param width the new item range width
	 */
	// TODO Has not been used yet...
	protected void setItemRangeWidth(double width) {
		Range itemRange = this.cells.createRange(BaseGeneratorSetting.FIRST_ROW,
				BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.CATEGORY_HEADER_WIDTH,
				BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM, this.maxColumnsPerItemLine);
		itemRange.setColumnWidth(width);
	}

	/**
	 * Break page.
	 */
	protected void breakPage() {
		this.workSheet.getHorizontalPageBreaks().add(this.currentRow);
	}

	/**
	 * Prints the remark.
	 */
	protected void printRemark() {
		Range remark = this.cells.createRange(this.currentRow,
				BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL, this.remarkTotalRow,
				this.maxColumnsPerItemLine - BaseGeneratorSetting.MARGIN_CELL);
		remark.merge();
		remark.setValue(this.employee.getRemark());
		remark.setStyle(this.templateStyle.remarkStyle);
		this.currentRow += this.remarkTotalRow;
	}

	/**
	 * Prints the page header.
	 */
	protected void printPageHeader() {
		int pageHeaderRowCount = this.pageHeaderRange.getRowCount();
		// On first page.
		if (this.currentRow == BaseGeneratorSetting.FIRST_ROW) {
			this.printCellValue(this.getHeaderTemplate());
		}
		// On other pages.
		else {
			// Copy style
			Range newPageHeaderRange = this.cells.createRange(this.currentRow, BaseGeneratorSetting.FIRST_COLUMN,
					this.pageHeaderRange.getRowCount(), this.pageHeaderRange.getColumnCount());
			try {
				newPageHeaderRange.copy(this.pageHeaderRange);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Set value.
			this.printCellValue(this.getHeaderTemplate());
		}
		this.currentRow += pageHeaderRowCount;
		this.firstCategoryRow = this.currentRow;
	}

	/**
	 * Prints the payment items.
	 */
	protected void printPaymentItems() {
		this.printCategoryHeader("支給");
		this.printCategoryContent(this.employee.getPaymentItems());
		this.nextCategory();
	}

	/**
	 * Prints the deduction items.
	 */
	protected void printDeductionItems() {
		this.printCategoryHeader("控除");
		this.printCategoryContent(this.employee.getDeductionItems());
		this.nextCategory();
	}

	/**
	 * Prints the attendance items.
	 */
	protected void printAttendanceItems() {
		this.printCategoryHeader("勤怠");
		this.printCategoryContent(this.employee.getAttendanceItems());
		this.nextCategory();
	}

	/**
	 * Prints the article items.
	 */
	protected void printArticleItems() {
		this.printCategoryHeader("記事");
		this.printCategoryContent(this.employee.getArticleItems());
		this.nextCategory();
	}

	/**
	 * Prints the other items.
	 */
	protected void printOtherItems() {
		this.printCategoryHeader("他");
		this.printCategoryContent(this.employee.getOtherItems());
		this.nextCategory();
	}

	/**
	 * Break lines.
	 *
	 * @param numberOfLine the number of line
	 */
	protected void breakLines(int numberOfLine) {
		this.firstCategoryRow += numberOfLine;
		this.currentRow += numberOfLine;
	}

	/**
	 * Next item line.
	 */
	protected void nextItemLine() {
		// Reset column & enter 2 rows.
		this.currentColumn = BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL
				+ BaseGeneratorSetting.CATEGORY_HEADER_WIDTH;
		this.currentRow += BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM + BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM;
	}

	/**
	 * Next category.
	 */
	protected void nextCategory() {
		this.nextItemLine();
		this.mergeHeader();
		this.firstCategoryRow = this.currentRow;
	}

	/**
	 * Prints the section title.
	 *
	 * @param title the title
	 */
	protected void printSectionTitle(String title) {
		Cell sectionTitle = this.cells.get(this.firstCategoryRow - BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM,
				BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL);
		sectionTitle.setValue(title);
		sectionTitle.setStyle(this.templateStyle.remarkStyle);
	}

	/**
	 * Prints the category header.
	 *
	 * @param headerName the header name
	 */
	protected void printCategoryHeader(String headerName) {
		this.cells.get(this.firstCategoryRow, BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL)
		.setValue(headerName);
	}

	/**
	 * Prints the data.
	 */
	protected void printData() {
		// Prepare for print data.
		this.initialize();

		// Print data.
		this.reportData.forEach(item -> {
			// Set margin top.
			this.setCurrentRowHeight(this.toExcelRowUnit(this.getPageTopMargin()));
			this.employee = item;
			this.printPageHeader();
			this.printPageContent();
			this.personCount++;

			this.createSeparator();

			if (this.isPageHasEnoughPerson()) {
				this.breakPage();
			}
		});
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		// Check on initialization.
		this.checkOnInitialization();

		// Get cells.
		this.cells = this.workSheet.getCells();

		// Get page setup.
		this.pageSetup = this.workSheet.getPageSetup();
		this.resetMargin();

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
		this.currentRow = BaseGeneratorSetting.FIRST_ROW;

		/** The current column. */
		this.currentColumn = BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL
				+ BaseGeneratorSetting.CATEGORY_HEADER_WIDTH;

		// Set max columns per item line.
		this.maxColumnsPerItemLine = BaseGeneratorSetting.ITEMS_PER_ROW * this.numberOfColumnPerItem
				+ BaseGeneratorSetting.CATEGORY_HEADER_WIDTH + BaseGeneratorSetting.MARGIN_CELL;

		// Set report data & config.
		this.reportData = this.paymentReportData.getReportData();
		this.config = this.paymentReportData.getConfig();

		// Set page left margin.
		this.cells.setColumnWidth(BaseGeneratorSetting.FIRST_COLUMN, this.toExcelColUnit(this.getPageLeftMargin()));
	}

	/**
	 * Check on initialization.
	 */
	private void checkOnInitialization() {
		if (this.workSheet == null) {
			throw new RuntimeException("WorkSheet is not defined.");
		}
		if (this.paymentReportData == null) {
			throw new RuntimeException("paymentReportData is not defined.");
		}
	}

	/**
	 * Checks if is page has enough person.
	 *
	 * @return true, if is page has enough person
	 */
	private boolean isPageHasEnoughPerson() {
		return this.personCount != 0 && this.personCount % this.maxPersonPerPage == 0;
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
		return this.cells.get(cellName).getStyle();
	}

	/**
	 * Sets the current row height.
	 *
	 * @param height the new current row height
	 */
	private void setCurrentRowHeight(double height) {
		this.cells.setRowHeight(this.currentRow, height);
	}

	/**
	 * Merge header.
	 */
	private void mergeHeader() {
		int headerHeight = this.currentRow - this.firstCategoryRow;
		Range header = this.cells.createRange(this.firstCategoryRow,
				BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL, headerHeight,
				BaseGeneratorSetting.CATEGORY_HEADER_WIDTH);
		header.merge();
		header.setStyle(this.templateStyle.headerStyle);
	}

	/**
	 * Prints the cell value.
	 *
	 * @param list the list
	 */
	private void printCellValue(List<CellValue> list) {
		list.forEach(item -> {
			Cell cell = this.cells.get(item.row, item.col);
			cell.setValue(item.value);
		});

	}

	/**
	 * Sets the page header range.
	 */
	private void setPageHeaderRange() {
		String upperLeftCell = BaseGeneratorSetting.FIRST_CELL;
		String lowerRightCell = this.getPageHeaderEndCell();
		if (StringUtil.isNullOrEmpty(upperLeftCell, true) || StringUtil.isNullOrEmpty(lowerRightCell, true)) {
			throw new RuntimeException("Page header range is not set.");
		}
		this.pageHeaderRange = this.cells.createRange(upperLeftCell, lowerRightCell);
	}

	/**
	 * Sets the template style.
	 */
	private void setTemplateStyle() {
		this.templateStyle = new TemplateStyle();
		this.templateStyle.headerStyle = this.getStyle(this.getCategoryHeaderCell());
		this.templateStyle.nameStyle = this.getStyle(this.getItemNameCell());
		this.templateStyle.valueStyle = this.getStyle(this.getItemValueCell());
		this.templateStyle.remarkStyle = this.getStyle(this.getRemarkCell());
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
		return value.doubleValue() / BaseGeneratorSetting.EXCEL_ROW_UNIT_IN_MM;
	}

	/**
	 * To excel col unit.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double toExcelColUnit(BigDecimal value) {
		return value.doubleValue() / BaseGeneratorSetting.EXCEL_COL_UNIT_IN_MM;
	}

	/**
	 * Sets the padding two out.
	 *
	 * @param setting the new padding two out
	 */
	private void setPaddingTwoOut(RefundPaddingTwoDto setting) {
		if (this.isEndOfPerson(BaseGeneratorSetting.FIRST_PERSON)) {
			if (this.hasBreakLine(setting)) {
				this.setBreakLine(this.toExcelRowUnit(setting.getBreakLineMargin()));
			}
			// Set person 2 top margin.
			this.setCurrentRowHeight(this.toExcelRowUnit(setting.getUnderAreaPaddingTop()));
		}
	}

	/**
	 * Sets the padding three out.
	 *
	 * @param setting the new padding three out
	 */
	private void setPaddingThreeOut(RefundPaddingThreeDto setting) {
		if (this.isEndOfPerson(BaseGeneratorSetting.FIRST_PERSON)) {
			if (this.hasBreakLine(setting)) {
				this.setBreakLine(this.toExcelRowUnit(setting.getBreakLineMarginTop()));
			}
			// Set person 2 top margin.
			this.setCurrentRowHeight(this.toExcelRowUnit(setting.getMiddleAreaPaddingTop()));
		}
		if (this.isEndOfPerson(BaseGeneratorSetting.SECOND_PERSON)) {
			if (this.hasBreakLine(setting)) {
				this.setBreakLine(this.toExcelRowUnit(setting.getBreakLineMarginButtom()));
			}
			// Set person 3 top margin.
			this.setCurrentRowHeight(this.toExcelRowUnit(setting.getUnderAreaPaddingTop()));
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
		this.setCurrentRowHeight(marginTop);

		// Get break line range.
		Range breakLine = this.cells.createRange(this.currentRow,
				BaseGeneratorSetting.FIRST_COLUMN + BaseGeneratorSetting.MARGIN_CELL, 1,
				this.maxColumnsPerItemLine - BaseGeneratorSetting.MARGIN_CELL);
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
		this.breakLines(1);
	}

	/**
	 * Checks if is end of person.
	 *
	 * @param x the x
	 * @return true, if is end of person
	 */
	private boolean isEndOfPerson(int x) {
		return this.personCount % this.maxPersonPerPage == x;
	}

	/**
	 * Creates the separator.
	 */
	private void createSeparator() {
		switch (this.config.getPrintType()) {
		case 2:
			this.setPaddingTwoOut(this.config.getRefundPaddingTwoDto());
			break;
		case 3:
			this.setPaddingThreeOut(this.config.getRefundPaddingThreeDto());
			break;
		default:
			// Do nothing.
			break;
		}
	}

	/**
	 * Reset margin.
	 */
	private void resetMargin() {
		this.pageSetup.setTopMargin(BaseGeneratorSetting.DEFAULT_MARGIN);
		this.pageSetup.setLeftMargin(BaseGeneratorSetting.DEFAULT_MARGIN);
	}

	/**
	 * Prints the category content.
	 *
	 * @param listItem the list item
	 */
	private void printCategoryContent(List<PaymentSalaryItemDto> listItem) {
		listItem.forEach(item -> {
			// Break line if current column = maxColumnsPerItemLine.
			if (this.currentColumn == this.maxColumnsPerItemLine) {
				this.nextItemLine();
			}

			// Get item cell.
			Range nameCell = this.cells.createRange(this.currentRow, this.currentColumn,
					BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM, this.numberOfColumnPerItem);
			Range valueCell = this.cells.createRange(this.currentRow + BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM,
					this.currentColumn, BaseGeneratorSetting.NUMBER_OF_ROW_PER_ITEM, this.numberOfColumnPerItem);

			// Merge cell.
			nameCell.merge();
			valueCell.merge();

			// Set style.
			nameCell.setStyle(this.templateStyle.nameStyle);
			valueCell.setStyle(this.templateStyle.valueStyle);

			// Set value.
			if (item.isView()) {
				nameCell.setValue(item.getItemName());
				valueCell.setValue(item.getItemVal());
			} else {
				nameCell.setValue(" ");
				valueCell.setValue(" ");
			}

			// Next item.
			this.currentColumn += this.numberOfColumnPerItem;
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
			this.row = row + PaymentReportBaseGenerator.this.currentRow;
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
			this.row = PaymentReportBaseGenerator.this.cells.get(cellName).getRow()
					+ PaymentReportBaseGenerator.this.currentRow;
			this.col = PaymentReportBaseGenerator.this.cells.get(cellName).getColumn();
			this.value = value;
		}

	}

}
