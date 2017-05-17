/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.SalaryItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class PaymentReportVerticalGenerator.
 */
public class PaymentReportVerticalGenerator extends AsposeCellsReportGenerator implements PaymentGenerator {

	/** The Constant TOTAL_HEADER_COLUMNS. */
	public static final int TOTAL_HEADER_COLUMNS = 1;

	/** The Constant CATEGORY_START_ROW. */
	public static final int CATEGORY_START_ROW = 9;

	/** The Constant FIRST_ITEM. */
	public static final int FIRST_ITEM = 0;

	/** The Constant SHEET_NAME. */
	public static final String SHEET_NAME = "SHEET 1";

	/** The Constant FIRST_COLUMN. */
	public static final int FIRST_COLUMN = 0;

	/** The Constant ITEMS_PER_ROW. */
	public static final int ITEMS_PER_ROW = 9;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentGenerator#generate(nts.uk.shr.infra.
	 * file.report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.file.pr.app.export.payment.data.PaymentReportData)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData reportData) {
		Workbook workbook = context.getWorkbook();
		Worksheet worksheet = workbook.getWorksheets().get(FIRST_ITEM);
		worksheet.setName(SHEET_NAME);

		// Get first employee
		PaymentReportDto employee = reportData.getReportData().get(FIRST_ITEM);

		// Get cells.
		Cells cells = worksheet.getCells();

		// Get style from template.
		TemplateStyle templateStyle = new TemplateStyle();
		templateStyle.headerStyle = (cells.get("A10").getStyle());
		templateStyle.nameStyle = cells.get("B10").getStyle();
		templateStyle.sectionTitleStyle = cells.get("A8").getStyle();
		templateStyle.valueStyle = cells.get("B11").getStyle();

		PrintData data = new PrintData();
		data.cells = cells;
		data.employee = employee;
		data.templateStyle = templateStyle;

		this.printContent(data);

	}

	/**
	 * Prints the content.
	 *
	 * @param printData the print data
	 */
	private void printContent(PrintData printData) {
		ContentWriter writer = new ContentWriter(printData);

		// Print header.
		writer.writePageHeader();
		writer.writeEmployeeInfo();

		// Print content.
		writer.printPayment();
		writer.printDeduction();
		writer.printAttendance();
		writer.printArticle();

		// Print remark;
		writer.writeRemark();

	}

	/**
	 * The Class TemplateStyle.
	 */
	protected class TemplateStyle {

		/** The section title style. */
		protected Style sectionTitleStyle;

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
			this.sectionTitleStyle = new Style();
			this.headerStyle = new Style();
			this.nameStyle = new Style();
			this.valueStyle = new Style();
		}
	}

	/**
	 * The Class PrintData.
	 */
	protected class PrintData {

		/** The template style. */
		protected TemplateStyle templateStyle;

		/** The cells. */
		protected Cells cells;

		/** The employee. */
		protected PaymentReportDto employee;
	}

	/**
	 * The Class ContentWriter.
	 */
	private class ContentWriter {

		/** The template style. */
		private TemplateStyle templateStyle;

		/** The cells. */
		private Cells cells;

		/** The employee. */
		private PaymentReportDto employee;

		/** The first row. */
		private int firstRow = CATEGORY_START_ROW;

		/** The current row. */
		private int currentRow = CATEGORY_START_ROW;

		/** The current column. */
		private int currentColumn = FIRST_COLUMN + TOTAL_HEADER_COLUMNS;

		/**
		 * Instantiates a new content writer.
		 *
		 * @param reportData the report data
		 */
		public ContentWriter(PrintData reportData) {
			this.templateStyle = reportData.templateStyle;
			this.cells = reportData.cells;
			this.employee = reportData.employee;
		}

		/**
		 * Write remark.
		 */
		private void writeRemark() {
			Cell remark = cells.get(currentRow, FIRST_COLUMN);
			remark.setValue("備考:");
			remark.setStyle(templateStyle.sectionTitleStyle);
		}

		/**
		 * Next item line.
		 */
		// Reset column & enter 2 rows.
		private void nextItemLine() {
			currentColumn = FIRST_COLUMN + TOTAL_HEADER_COLUMNS;
			currentRow += 2;
		}

		/**
		 * Break lines.
		 *
		 * @param numberOfLine the number of line
		 */
		private void breakLines(int numberOfLine) {
			currentRow += numberOfLine;
			firstRow += numberOfLine;
		}

		/**
		 * Write category content.
		 *
		 * @param listItem the list item
		 */
		private void writeCategoryContent(List<SalaryItemDto> listItem) {
			listItem.forEach(item -> {
				if (currentColumn == ITEMS_PER_ROW + TOTAL_HEADER_COLUMNS) {
					nextItemLine();
				}

				// Get cell.
				Cell nameCell = cells.get(currentRow, currentColumn);
				Cell valueCell = cells.get(currentRow + 1, currentColumn);

				// Set style & value.
				nameCell.setStyle(templateStyle.nameStyle);
				valueCell.setStyle(templateStyle.valueStyle);
				if (item.isView()) {
					nameCell.setValue(item.getItemName());
					valueCell.setValue(item.getItemVal());
				} else {
					nameCell.setValue(" ");
					valueCell.setValue(" ");
				}
				currentColumn++;
			});
		}

		/**
		 * Prints the payment.
		 */
		private void printPayment() {
			writeSectionTitle("支給額");
			writeCategoryHeader("支給");
			writeCategoryContent(employee.getPaymentItems());
			nextCategory();
			breakLines(2);

		};

		/**
		 * Prints the deduction.
		 */
		private void printDeduction() {
			writeSectionTitle("控除");
			writeCategoryHeader("控除");
			writeCategoryContent(employee.getDeductionItems());
			nextCategory();
			breakLines(3);
		};

		/**
		 * Prints the attendance.
		 */
		private void printAttendance() {
			writeSectionTitle("勤怠/記事");
			writeCategoryHeader("勤怠");
			writeCategoryContent(employee.getAttendanceItems());
			nextCategory();
		};

		/**
		 * Prints the article.
		 */
		private void printArticle() {
			writeCategoryHeader("記事");
			writeCategoryContent(employee.getArticleItems());
			nextCategory();
			breakLines(1);
		};

		/**
		 * Write page header.
		 */
		private void writePageHeader() {
			cells.get("D1").setValue("給与明細書");
			// TODO: convert to Japanese era.
			cells.get("D2").setValue(employee.getProcessingYm());
		}

		/**
		 * Write employee info.
		 */
		private void writeEmployeeInfo() {
			cells.get("C4").setValue("部門コード");
			cells.get("F4").setValue("個人コード");
			cells.get("H4").setValue("氏名");
			cells.get("C5").setValue(employee.getDepartmentInfo().getDepartmentCode());
			cells.get("F5").setValue(employee.getEmployeeInfo().getEmployeeCode());
			cells.get("H5").setValue(employee.getEmployeeInfo().getEmployeeName());

		}

		/**
		 * Next category.
		 */
		private void nextCategory() {
			nextItemLine();
			mergeHeader();
			firstRow = currentRow;
		}

		/**
		 * Write section title.
		 *
		 * @param title the title
		 */
		private void writeSectionTitle(String title) {
			Cell sectionTitle = cells.get(firstRow - 1, FIRST_COLUMN);
			sectionTitle.setValue(title);
			sectionTitle.setStyle(templateStyle.sectionTitleStyle);
		}

		/**
		 * Write category header.
		 *
		 * @param headerName the header name
		 */
		private void writeCategoryHeader(String headerName) {
			cells.get(firstRow, FIRST_COLUMN).setValue(headerName);
		}

		/**
		 * Merge header.
		 */
		private void mergeHeader() {
			final int totalHeaderRows = currentRow - firstRow;
			int temp = firstRow;
			while (temp < currentRow) {
				cells.get(temp, FIRST_COLUMN).setStyle(templateStyle.headerStyle);
				temp++;
			}
			cells.merge(firstRow, FIRST_COLUMN, totalHeaderRows, TOTAL_HEADER_COLUMNS);
		}
	}
}
