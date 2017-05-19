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

import lombok.Getter;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.SalaryItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class BasePaymentReportGenerator.
 */
public abstract class BasePaymentReportGenerator extends AsposeCellsReportGenerator {

	/** The Constant FIRST_SHEET. */
	public static final int FIRST_SHEET = 0;

	/** The Constant HEADER_WIDTH. */
	public static final int HEADER_WIDTH = 1;

	/** The Constant ITEM_WIDTH. */
	public static final int ITEM_WIDTH = 1;

	/** The Constant CATEGORY_START_ROW. */
	public static final int CATEGORY_START_ROW = 9;

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

	/** The first row. */
	private int firstRow = FIRST_ROW;

	/** The current row. */
	private int currentRow = FIRST_ROW;

	/** The current column. */
	private int currentColumn = FIRST_COLUMN + HEADER_WIDTH;

	/** The max columns per item line. */
	private int maxColumnsPerItemLine = ITEMS_PER_ROW * ITEM_WIDTH + HEADER_WIDTH;

	/**
	 * Prints the page content.
	 */
	abstract void printPageContent();

	/**
	 * Sets the page header range.
	 */
	abstract void setPageHeaderRange();

	/**
	 * Gets the header template.
	 *
	 * @return the header template
	 */
	abstract List<CellValue> getHeaderTemplate();

	/**
	 * Sets the template style.
	 */
	abstract void setTemplateStyle();

	/**
	 * Inits the.
	 */
	protected void init() {
		this.templateStyle = new TemplateStyle();
		this.setPageHeaderRange();
		this.setTemplateStyle();
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
	protected void printCategoryContent(List<SalaryItemDto> listItem) {
		listItem.forEach(item -> {
			// Break line if current column = maxColumnsPerItemLine.
			if (currentColumn == maxColumnsPerItemLine) {
				nextItemLine();
			}

			// Get item cell.
			Range nameCell = cells.createRange(currentRow, currentColumn, ITEM_HEIGHT, ITEM_WIDTH);
			Range valueCell = cells.createRange(currentRow + 1, currentColumn, ITEM_HEIGHT, ITEM_WIDTH);

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
			currentColumn += ITEM_WIDTH;
		});
	}

	/**
	 * Prints the page header.
	 */
	protected void printPageHeader() {
		// On first page.
		if (currentRow < CATEGORY_START_ROW) {
			printCellValue(getHeaderTemplate());
		}
		// On other pages.
		else {
			// Copy style
			Range newPageHeaderRange = cells.createRange(currentRow, FIRST_COLUMN, pageHeaderRange.getRowCount(),
					pageHeaderRange.getColumnCount());
			newPageHeaderRange.copyStyle(pageHeaderRange);

			// Set value.
			printCellValue(getHeaderTemplate());
		}
		firstRow += CATEGORY_START_ROW;
		currentRow += CATEGORY_START_ROW;
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
	 * Prints the data.
	 */
	protected void printData() {
		printPageHeader();
		printPageContent();
		breakLines(1);
		breakPage();
	}

	/**
	 * Gets the style.
	 *
	 * @param cellName the cell name
	 * @return the style
	 */
	protected Style getStyle(String cellName) {
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
			cells.get(item.getRow(), item.getCol()).setValue(item.getValue());
		});

	}

	/**
	 * The Class TemplateStyle.
	 */
	protected class TemplateStyle {

		/** The remark style. */
		protected Style remarkStyle;

		/** The header style. */
		protected Style headerStyle;

		/** The name style. */
		protected Style nameStyle;

		/** The value style. */
		protected Style valueStyle;

		/**
		 * Instantiates a new template style.
		 */
		public TemplateStyle() {
			this.remarkStyle = new Style();
			this.headerStyle = new Style();
			this.nameStyle = new Style();
			this.valueStyle = new Style();
		}
	}

	/**
	 * The Class CellValue.
	 */

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Getter
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

	}

}
