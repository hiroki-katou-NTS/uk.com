package nts.uk.pr.file.infra.payment;

import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.Range;
import com.aspose.cells.Style;

import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.SalaryItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public abstract class BasePaymentReportGenerator extends AsposeCellsReportGenerator {

	/** The Constant TOTAL_HEADER_COLUMNS. */
	public static final int HEADER_WIDTH = 1;

	public static final int ITEM_WIDTH = 1;

	/** The Constant CATEGORY_START_ROW. */
	public static final int CATEGORY_START_ROW = 9;

	/** The Constant FIRST_COLUMN. */
	public static final int FIRST_COLUMN = 0;

	/** The Constant ITEMS_PER_ROW. */
	public static final int ITEMS_PER_ROW = 9;

	public static final int ITEM_HEIGHT = 1;

	/** The template style. */
	protected TemplateStyle templateStyle;

	/** The cells. */
	protected Cells cells;

	/** The employee. */
	protected PaymentReportDto employee;

	protected HorizontalPageBreakCollection hPageBreaks;

	protected Range pageHeaderRange;

	/** The first row. */
	private int firstRow = CATEGORY_START_ROW;

	/** The current row. */
	private int currentRow = CATEGORY_START_ROW;

	/** The current column. */
	private int currentColumn = FIRST_COLUMN + HEADER_WIDTH;

	private int maxColumnsPerItemLine = ITEMS_PER_ROW * ITEM_WIDTH + HEADER_WIDTH;

	abstract void printPageContent();

	protected void breakPage() {
		hPageBreaks.add(currentRow);
	}

	/**
	 * Write remark.
	 */
	protected void printRemark() {
		Cell remark = cells.get(currentRow, FIRST_COLUMN);
		remark.setValue("備考:");
		remark.setStyle(templateStyle.remarkStyle);
	}

	/**
	 * Write category content.
	 *
	 * @param listItem
	 *            the list item
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
	 * Write page header.
	 */
	protected void printPageHeader() {
		if (currentRow == CATEGORY_START_ROW) {
			cells.get("D1").setValue("給与明細書");
			// TODO: convert to Japanese era.
			cells.get("D3").setValue(employee.getProcessingYm());
			printEmployeeInfo();
		} else {
			Range newPageHeaderRange = cells.createRange(currentRow, FIRST_COLUMN, pageHeaderRange.getRowCount(),
					pageHeaderRange.getColumnCount());
			try {
				newPageHeaderRange.copy(pageHeaderRange);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				firstRow += CATEGORY_START_ROW;
				currentRow += CATEGORY_START_ROW;
			}
		}
	}

	/**
	 * Write employee info.
	 */
	private void printEmployeeInfo() {
		cells.get("C6").setValue("部門コード");
		cells.get("F6").setValue("個人コード");
		cells.get("H6").setValue("氏名");
		cells.get("C7").setValue(employee.getDepartmentInfo().getDepartmentCode());
		cells.get("F7").setValue(employee.getEmployeeInfo().getEmployeeCode());
		cells.get("H7").setValue(employee.getEmployeeInfo().getEmployeeName());
	}

	/**
	 * Break lines.
	 *
	 * @param numberOfLine
	 *            the number of line
	 */
	void breakLines(int numberOfLine) {
		currentRow += numberOfLine;
		firstRow += numberOfLine;
	}

	/**
	 * Next item line.
	 */
	protected void nextItemLine() {
		// Reset column & enter 2 rows.
		currentColumn = FIRST_COLUMN + HEADER_WIDTH;
		currentRow += ITEM_HEIGHT * 2;
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
	 * Write section title.
	 *
	 * @param title
	 *            the title
	 */
	protected void printSectionTitle(String title) {
		Cell sectionTitle = cells.get(firstRow - 1, FIRST_COLUMN);
		sectionTitle.setValue(title);
		sectionTitle.setStyle(templateStyle.remarkStyle);
	}

	/**
	 * Write category header.
	 *
	 * @param headerName
	 *            the header name
	 */
	protected void printCategoryHeader(String headerName) {
		cells.get(firstRow, FIRST_COLUMN).setValue(headerName);
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

	protected void printData() {
		printPageHeader();
		printPageContent();
		breakLines(1);
		breakPage();
	}

	/**
	 * The Class TemplateStyle.
	 */
	protected class TemplateStyle {

		/** The section title style. */
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

}
