/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageOrientationType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
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

	private static final int FIRST_ITEM = 0;
	private static final String SHEET_NAME = "SHEET 1";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.wagetable.Generator#generate(nts.uk.shr.infra.file.
	 * report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.ctx.pr.core.app.wagetable.command.WtUpdateCommand)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData reportData) {
		Workbook workbook = context.getWorkbook();
		Worksheet worksheet = workbook.getWorksheets().get(FIRST_ITEM);
		this.setupPage(worksheet);
		worksheet.setName(SHEET_NAME);
		Cells cells = worksheet.getCells();
		Style defaultStyle = cells.get("A2").getStyle();

		// Get first employee
		PaymentReportDto dto = reportData.getReportData().get(FIRST_ITEM);

		// Payment
		// Cell cell = cells.get("A1");
		// cell.setValue("支給項目 ");
		List<SalaryItemDto> paymentItems = dto.getPaymentItems();
		List<SalaryItemDto> deductionItems = dto.getDeductionItems();
		List<SalaryItemDto> attendanceItems = dto.getAttendanceItems();
		List<SalaryItemDto> articleItems = dto.getArticleItems();

		// Fill data.
		Ahihi ahihi = new Ahihi();
		ahihi.style = defaultStyle;
		ahihi.cells = cells;

		// Print
		ahihi.printHeader("payment");
		ahihi.print(paymentItems);
		ahihi.nextRow();

		ahihi.printHeader("deduction");
		ahihi.print(deductionItems);
		ahihi.nextRow();

		ahihi.printHeader("attendance");
		ahihi.print(attendanceItems);

		ahihi.printHeader("article");
		ahihi.print(articleItems);

	}

	private void setupPage(Worksheet worksheet) {
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setOrientation(PageOrientationType.PORTRAIT);
	}

}

class Ahihi {
	public static int firstColumn = 0;
	public static int totalColumns = 1;
	int firstRow = 10;
	int currentRow = firstRow;
	int currentColumn = firstColumn + 1;
	Style style;
	Cells cells;

	public void enter() {
		//cells.get(currentRow, currentColumn).setStyle(style);
		//cells.get(currentRow+1, currentColumn).setStyle(style);
		currentColumn = firstColumn + 1;
		currentRow += 2;
	}

	public void nextRow() {
		currentRow += 1;
		firstRow +=1;
	}

	public void print(List<SalaryItemDto> listItem) {
		for (int i = 0; i < listItem.size(); i++) {
			if (currentColumn == 9) {
				enter();
			}
			SalaryItemDto item = listItem.get(i);
			Cell nameCell = cells.get(currentRow, currentColumn);
			Cell valueCell = cells.get(currentRow + 1, currentColumn);
			nameCell.setStyle(style);
			valueCell.setStyle(style);

			nameCell.setValue(item.getItemName());
			valueCell.setValue(item.getItemVal());
			currentColumn++;
		}
		merge();
		enter();
		firstRow = currentRow;
	}

	public void printHeader(String val) {
		Cell cell = cells.get(firstRow, firstColumn);
		cell.setValue(val);
	}

	public void merge() {
		int row = firstRow;
		int totalRows = currentRow+2-firstRow;
		while (row < currentRow+2) {
			cells.get(row, firstColumn).setStyle(style);
			row++;
		}
		cells.merge(firstRow, firstColumn, totalRows, totalColumns);
	}

}
