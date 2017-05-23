/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Worksheet;

import nts.gul.text.StringUtil;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentSalaryItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class PaymentReportBaseGenerator.
 */
public abstract class PaymentReportBaseGenerator extends AsposeCellsReportGenerator {

	/** The Constant FIRST_SHEET. */
	public static final int FIRST_SHEET = 0;

	/** The Constant HEADER_WIDTH. */
	public static final int HEADER_WIDTH = 1;

	/** The Constant FIRST_COLUMN. */
	public static final int FIRST_COLUMN = 0;

	/** The Constant FIRST_ROW. */
	public static final int FIRST_ROW = 0;

	/** The Constant ITEMS_PER_ROW. */
	public static final int ITEMS_PER_ROW = 9;

	/** The Constant ITEM_HEIGHT. */
	public static final int ITEM_HEIGHT = 1;

	/** The template style. */
	protected TemplateStyle templateStyle;

	/** The work sheet. */
	protected Worksheet workSheet;

	/** The cells. */
	protected Cells cells;

	/** The employee. */
	protected PaymentReportDto employee;

	/** The page header range. */
	protected Range pageHeaderRange;

	/** The number of column per item. */
	private int numberOfColumnPerItem;

	/** The first row. */
	private int firstRow = FIRST_ROW;

	/** The current row. */
	private int currentRow = FIRST_ROW;

	/** The current column. */
	private int currentColumn = FIRST_COLUMN + HEADER_WIDTH;

	/** The max columns per item line. */
	private int maxColumnsPerItemLine;

	/** The max person per page. */
	private int maxPersonPerPage;

	/** The person count. */
	private int personCount;

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
	 * Gets the page header start cell.
	 *
	 * @return the page header start cell
	 */
	abstract String getPageHeaderStartCell();

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
	 * Gets the header template.
	 *
	 * @return the header template
	 */
	abstract List<CellValue> getHeaderTemplate();

	/**
	 * Inits the.
	 */
	protected void init() {
		// Set template style.
		this.templateStyle = new TemplateStyle();
		this.templateStyle.headerStyle = getStyle(getCategoryHeaderCell());
		this.templateStyle.nameStyle = getStyle(getItemNameCell());
		this.templateStyle.valueStyle = getStyle(getItemValueCell());
		this.templateStyle.remarkStyle = getStyle(getRemarkCell());

		// Set number of columns to merged.
		this.numberOfColumnPerItem = this.getNumberOfColumnPerItem();

		// Set page header range.
		String upperLeftCell = getPageHeaderStartCell();
		String lowerRightCell = getPageHeaderEndCell();
		if (StringUtil.isNullOrEmpty(upperLeftCell, true) || StringUtil.isNullOrEmpty(lowerRightCell, true)) {
			throw new RuntimeException("Page header range is not set.");
		}
		this.pageHeaderRange = cells.createRange(upperLeftCell, lowerRightCell);

		// Set max columns per item line.
		this.maxColumnsPerItemLine = ITEMS_PER_ROW * this.numberOfColumnPerItem + HEADER_WIDTH;

		// Set max number of person on each page.
		this.maxPersonPerPage = this.getPersonPerPage();
	}

	/**
	 * Sets the item width.
	 *
	 * @param width the new item width
	 */
	protected void setItemWidth(double width) {
		Range itemRange = cells.createRange(FIRST_COLUMN + HEADER_WIDTH, maxColumnsPerItemLine, false);
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
		Cell remark = cells.get(currentRow, FIRST_COLUMN);
		remark.setValue("備考: " + employee.getRemark());
		remark.setStyle(templateStyle.remarkStyle);
	}

	/**
	 * Prints the category content.
	 *
	 * @param listItem the list item
	 */
	protected void printCategoryContent(List<PaymentSalaryItemDto> listItem) {
		listItem.forEach(item -> {
			// Break line if current column = maxColumnsPerItemLine.
			if (currentColumn == maxColumnsPerItemLine) {
				nextItemLine();
			}

			// Get item cell.
			Range nameCell = cells.createRange(currentRow, currentColumn, ITEM_HEIGHT, numberOfColumnPerItem);
			Range valueCell = cells.createRange(currentRow + 1, currentColumn, ITEM_HEIGHT, numberOfColumnPerItem);

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
		firstRow += pageHeaderRowCount;
		currentRow += pageHeaderRowCount;
		personCount++;
	}

	/**
	 * Break lines.
	 *
	 * @param numberOfLine the number of line
	 */
	protected void breakLines(int numberOfLine) {
		currentRow += numberOfLine;
		firstRow += numberOfLine;
	}

	/**
	 * Next item line.
	 */
	protected void nextItemLine() {
		// Reset column & enter 2 rows.
		currentColumn = FIRST_COLUMN + HEADER_WIDTH;
		currentRow += ITEM_HEIGHT + ITEM_HEIGHT;
	}

	/**
	 * Next category.
	 */
	protected void nextCategory() {
		nextItemLine();
		mergeHeader();
		firstRow = currentRow;
	}

	/**
	 * Prints the section title.
	 *
	 * @param title the title
	 */
	protected void printSectionTitle(String title) {
		Cell sectionTitle = cells.get(firstRow - ITEM_HEIGHT, FIRST_COLUMN);
		sectionTitle.setValue(title);
		sectionTitle.setStyle(templateStyle.remarkStyle);
	}

	/**
	 * Prints the category header.
	 *
	 * @param headerName the header name
	 */
	protected void printCategoryHeader(String headerName) {
		cells.get(firstRow, FIRST_COLUMN).setValue(headerName);
	}

	/**
	 * Sets the current row height.
	 *
	 * @param height the new current row height
	 */
	protected void setCurrentRowHeight(double height) {
		cells.setRowHeight(currentRow, height);
	}

	/**
	 * Sets the margin top.
	 *
	 * @param height the new margin top
	 */
	protected void setMarginTop(double height) {
		cells.setRowHeight(FIRST_ROW, height);
	}

	/**
	 * Sets the margin left.
	 *
	 * @param height the new margin left
	 */
	protected void setMarginLeft(double height) {
		cells.setRowHeight(FIRST_COLUMN, height);
	}

	/**
	 * Prints the data.
	 */
	protected void printData() {
		printPageHeader();
		printPageContent();
		breakLines(1);
		if (personCount != 0 && personCount % maxPersonPerPage == 0) {
			breakPage();
		}
	}

	/**
	 * Gets the style.
	 *
	 * @param cellName the cell name
	 * @return the style
	 */
	protected Style getStyle(String cellName) {
		if (StringUtil.isNullOrEmpty(cellName, true)) {
			return new Style();
		}
		return cells.get(cellName).getStyle();
	}

	/**
	 * Merge header.
	 */
	private void mergeHeader() {
		int headerHeight = currentRow - firstRow;
		Range header = cells.createRange(firstRow, FIRST_COLUMN, headerHeight, HEADER_WIDTH);
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
