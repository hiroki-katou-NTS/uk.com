/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Style;
import com.aspose.cells.Worksheet;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.SalaryItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class OneDemensionGenerator.
 */
@Stateless(name = "PaymentReportHorizontalGenerator")
public class PaymentReportHorizontalGenerator implements PaymentGenerator {

	/** The start row. */
	private int startRow = 0;

	/** The start col. */
	private int startCol = 0;

	/** The style header row. */
	private Style styleHeaderRow;

	/** The style value row. */
	private Style styleValueRow;

	/** The style payment header row. */
	private Style stylePaymentHeaderRow;

	/** The style payment value row. */
	private Style stylePaymentValueRow;

	private Cells cells;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.wagetable.Generator#generate(nts.uk.shr.infra.file.
	 * report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.ctx.pr.core.app.wagetable.command.WtUpdateCommand)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData data) {
		// Set worksheet name.
		Worksheet ws = context.getWorkbook().getWorksheets().get(0);
		ws.setName("PaymentService");

		// Set data.
		cells = ws.getCells();

		// set name
		startRow = 0;
		startCol = 5;
		cells.get(startRow, startCol).setValue("給与支給明細書");

		// set department title
		startRow++;
		startCol = 0;
		cells.get(startRow, startCol).setValue("部門コード");
		styleHeaderRow = cells.get(startRow, startCol).getStyle();
		styleValueRow = cells.get(startRow, startCol).getStyle();
		stylePaymentHeaderRow = cells.getCellStyle(4, 0);
		stylePaymentValueRow = cells.getCellStyle(4, 1);
		startCol++;
		cells.get(startRow, startCol).setValue("個人コード");
		startCol++;
		cells.get(startRow, startCol).setValue("氏名");

		List<PaymentReportDto> reportData = data.getReportData();
		PaymentReportDto dataFirst = reportData.get(0);

		// set department info
		startRow++;
		startCol = 0;
		cells.get(startRow, startCol).setValue(dataFirst.getDepartmentInfo().getDepartmentCode());

		// set employee info
		startCol++;
		cells.get(startRow, startCol).setValue(dataFirst.getEmployeeInfo().getEmployeeCode());
		startCol++;
		cells.get(startRow, startCol).setValue(dataFirst.getEmployeeInfo().getEmployeeName());

		// reset start cells
		startRow += 2;
		pushDataItem("支給", dataFirst.getPaymentItems());
		pushDataItem("控除", dataFirst.getDeductionItems());
		pushDataItem("勤怠", dataFirst.getAttendanceItems());
		startRow--;
		pushDataItem("記事", dataFirst.getArticleItems());
		
	}

	private void pushData(String objectValue, int startRow, int startCol, Style style) {
		cells.get(startRow, startCol).setValue(objectValue);
		cells.get(startRow, startCol).setStyle(style);
	}

	private void pushDataItem(String itemName, List<SalaryItemDto> dataItem) {
		startCol = 0;
		pushData(itemName, startRow, startCol, stylePaymentHeaderRow);
		startCol++;
		int startMert = startRow;
		for (int index = 0; index < dataItem.size(); index++) {
			// next row
			if (index > 0 && index % 9 == 0) {
				startCol = 0;
				pushData("", startRow + 1, 0, stylePaymentHeaderRow);
				pushData("", startRow + 2, 0, stylePaymentHeaderRow);
				startRow += 2;
				startCol++;
			}
			SalaryItemDto item = dataItem.get(index);
			pushData(item.getItemName(), startRow, startCol, stylePaymentValueRow);
			pushData(item.getItemVal().toString(), startRow + 1, startCol, styleValueRow);
			startCol++;
		}
		pushData("", startRow + 1, 0, stylePaymentHeaderRow);
		cells.merge(startMert, 0, startRow - startMert + 2, 1);
		startRow += 3;
	}
}
