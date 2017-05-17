/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageOrientationType;
import com.aspose.cells.PageSetup;
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

	/** The Constant FIRST_ITEM. */
	public static final int FIRST_ITEM = 0;

	/** The Constant SHEET_NAME. */
	public static final String SHEET_NAME = "SHEET 1";

	/** The Constant FIRST_COLUMN. */
	public static final int FIRST_COLUMN = 0;

	/** The Constant ITEMS_PER_ROW. */
	public static final int ITEMS_PER_ROW = 9;

	/** The section title style. */
	private Style sectionTitleStyle;

	/** The header style. */
	private Style headerStyle;

	/** The name style. */
	private Style nameStyle;

	/** The value style. */
	private Style valueStyle;

	/** The cells. */
	private Cells cells;

	/** The employee. */
	private PaymentReportDto employee;

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

		// Setup page.
		this.setupPage(worksheet);

		// Get first employee
		this.employee = reportData.getReportData().get(FIRST_ITEM);

		// Get cells.
		this.cells = worksheet.getCells();

		// Get style from template.
		this.valueStyle = cells.get("B11").getStyle();
		this.nameStyle = cells.get("B10").getStyle();
		this.headerStyle = cells.get("A10").getStyle();
		this.sectionTitleStyle = cells.get("A8").getStyle();

		this.setPageHeader();
		this.setEmployeeInfo();
		this.setCategoryContent();

	}

	/**
	 * Sets the page header.
	 */
	private void setPageHeader() {
		cells.get("D1").setValue("給与明細書");
		// TODO: convert to Japanese era.
		cells.get("D2").setValue(employee.getProcessingYm());
	}

	/**
	 * Sets the employee info.
	 */
	private void setEmployeeInfo() {
		cells.get("C4").setValue("部門コード");
		cells.get("F4").setValue("個人コード");
		cells.get("H4").setValue("氏名");
		cells.get("C5").setValue(employee.getDepartmentInfo().getDepartmentCode());
		cells.get("F5").setValue(employee.getEmployeeInfo().getEmployeeCode());
		cells.get("H5").setValue(employee.getEmployeeInfo().getEmployeeName());

	}

	/**
	 * Sets the category content.
	 */
	private void setCategoryContent() {
		CategoryWriter writer = new CategoryWriter();

		// Print Payment items.
		writer.writeSectionTitle("支給額");
		writer.writeCategoryHeader("支給");
		writer.writeCategoryContent(employee.getPaymentItems());
		writer.nextRow();
		writer.nextRow();

		// Print Deduction items.
		writer.writeSectionTitle("控除");
		writer.writeCategoryHeader("控除");
		writer.writeCategoryContent(employee.getDeductionItems());
		writer.nextRow();
		writer.nextRow();
		writer.nextRow();

		writer.writeSectionTitle("勤怠/記事");
		// Print Attendance items.
		writer.writeCategoryHeader("勤怠");
		writer.writeCategoryContent(employee.getAttendanceItems());
		// Print Article items.
		writer.writeCategoryHeader("記事");
		writer.writeCategoryContent(employee.getArticleItems());
		writer.nextRow();

		// Remarks.
		Cell remark = cells.get(writer.currentRow, FIRST_COLUMN);
		remark.setValue("備考:");
		remark.setStyle(sectionTitleStyle);
	}

	/**
	 * Sets the up page.
	 *
	 * @param worksheet the new up page
	 */
	private void setupPage(Worksheet worksheet) {
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setOrientation(PageOrientationType.PORTRAIT);
	}

	/**
	 * The Class CategoryWriter.
	 */
	private class CategoryWriter {

		/** The first row. */
		protected int firstRow = 9;

		/** The current row. */
		protected int currentRow = firstRow;

		/** The current column. */
		protected int currentColumn = FIRST_COLUMN + 1;

		/**
		 * Enter.
		 */
		// Reset column & enter 2 rows.
		protected void enter() {
			currentColumn = FIRST_COLUMN + 1;
			currentRow += 2;
		}

		/**
		 * Next row.
		 */
		protected void nextRow() {
			currentRow += 1;
			firstRow += 1;
		}

		/**
		 * Write category content.
		 *
		 * @param listItem the list item
		 */
		protected void writeCategoryContent(List<SalaryItemDto> listItem) {
			listItem.forEach(item -> {
				if (currentColumn == ITEMS_PER_ROW + 1) {
					enter();
				}
				Cell nameCell = cells.get(currentRow, currentColumn);
				Cell valueCell = cells.get(currentRow + 1, currentColumn);
				nameCell.setStyle(nameStyle);
				valueCell.setStyle(valueStyle);
				if (item.isView()) {
					nameCell.setValue(currentRow);
					valueCell.setValue(item.getItemVal());
				} else {
					nameCell.setValue(currentRow);
					valueCell.setValue(" ");
				}
				currentColumn++;
			});
			enter();
			mergeHeader();
			firstRow = currentRow;
		}

		/**
		 * Write section title.
		 *
		 * @param title the title
		 */
		protected void writeSectionTitle(String title) {
			Cell sectionTitle = cells.get(firstRow - 1, FIRST_COLUMN);
			sectionTitle.setValue(title);
			sectionTitle.setStyle(sectionTitleStyle);
		}

		/**
		 * Write category header.
		 *
		 * @param headerName the header name
		 */
		protected void writeCategoryHeader(String headerName) {
			cells.get(firstRow, FIRST_COLUMN).setValue(headerName);
		}

		/**
		 * Merge header.
		 */
		protected void mergeHeader() {
			final int totalColumns = 1;
			final int totalRows = currentRow - firstRow;
			int temp = firstRow;
			while (temp < currentRow) {
				cells.get(temp, FIRST_COLUMN).setStyle(headerStyle);
				temp++;
			}
			cells.merge(firstRow, FIRST_COLUMN, totalRows, totalColumns);
		}
	}
}
