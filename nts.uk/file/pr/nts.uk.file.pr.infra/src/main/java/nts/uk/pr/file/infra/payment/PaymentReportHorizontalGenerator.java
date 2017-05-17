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
 * The Class PaymentReportHorizontalGenerator.
 */
@Stateless(name = "PaymentReportHorizontalGenerator")
public class PaymentReportHorizontalGenerator implements PaymentGenerator {

	/** The Constant TOTAL_COLUMN_INLINE. */
	private static final int TOTAL_COLUMN_INLINE = 9;
	
	/** The start row. */
	private int startRow = 0;

	/** The start col. */
	private int startCol = 0;
	
	/** The column. */
	private int column = 0;

	/** The style payment header title. */
	private Style stylePaymentHeaderTitle;

	/** The style payment value header. */
	private Style stylePaymentValueHeader;
	
	/** The style payment value row. */
	private Style stylePaymentValueRow;

	/** The cells. */
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
		startCol = 8;
		cells.get(startRow, startCol).setValue("給与支給明細書");

		// set department title
		startRow++;
		startCol = 0;
		cells.get(startRow, startCol).setValue("部門コード");
		stylePaymentHeaderTitle = cells.getCellStyle(4, 0);
		stylePaymentValueHeader = cells.getCellStyle(4, 1);
		stylePaymentValueRow = cells.getCellStyle(5, 1);
		startCol += 2;
		cells.get(startRow, startCol).setValue("個人コード");
		startCol += 2;
		cells.get(startRow, startCol).setValue("氏名");

		List<PaymentReportDto> reportData = data.getReportData();
		PaymentReportDto dataFirst = reportData.get(0);

		// set department info
		startRow++;
		startCol = 0;
		cells.get(startRow, startCol).setValue(dataFirst.getDepartmentInfo().getDepartmentCode());

		// set employee info
		startCol += 2;
		cells.get(startRow, startCol).setValue(dataFirst.getEmployeeInfo().getEmployeeCode());
		startCol += 2;
		cells.get(startRow, startCol).setValue(dataFirst.getEmployeeInfo().getEmployeeName());

		// reset start cells
		startRow += 2;
		pushDataItem("支給", dataFirst.getPaymentItems());
		pushDataItem("控除", dataFirst.getDeductionItems());
		pushDataItem("勤怠", dataFirst.getAttendanceItems());
		startRow--;
		pushDataItem("記事", dataFirst.getArticleItems());

	}

	/**
	 * Push data.
	 *
	 * @param objectValue the object value
	 * @param startRow the start row
	 * @param startCol the start col
	 * @param style the style
	 */
	private void pushData(Object objectValue, int startRow, int startCol, Style style) {
		cells.get(startRow, startCol).setValue(objectValue);
		cells.get(startRow, startCol).setStyle(style);
	}

	/**
	 * Push data item.
	 *
	 * @param itemName the item name
	 * @param dataItem the data item
	 */
	private void pushDataItem(String itemName, List<SalaryItemDto> dataItem) {
		startCol = 0;
		pushData(itemName, startRow, startCol, stylePaymentHeaderTitle);
		startCol++;
		int startMerge = startRow;
		column = 0;
		dataItem.forEach(item->{
			// next row
			if (TOTAL_COLUMN_INLINE == column) {
				startCol = 0;
				pushData("", startRow + 1, 0, stylePaymentHeaderTitle);
				pushData("", startRow + 2, 0, stylePaymentHeaderTitle);
				startRow += 2;
				startCol += 1;
				column = 0;
			}
			pushDataValue(item, startRow, startCol);
			startCol += 2;
			column++;
		});
		pushData("", startRow + 1, 0, stylePaymentHeaderTitle);
		startRow += 2;
		cells.merge(startMerge, 0, startRow - startMerge, 1);
		startRow++;
	}

	/**
	 * Push data header.
	 *
	 * @param data the data
	 * @param startRow the start row
	 * @param startCol the start col
	 */
	private void pushDataValue(SalaryItemDto dto, int startRow, int startCol) {
		if(dto.isView()){
			
		pushData(dto.getItemName(), startRow, startCol, stylePaymentValueHeader);
		pushData("", startRow, startCol + 1, stylePaymentValueHeader);
		cells.merge(startRow, startCol, 1, 2);
		
		pushData(dto.getItemVal(), startRow + 1, startCol, stylePaymentValueRow);
		pushData("", startRow + 1, startCol + 1, stylePaymentValueRow);
		cells.merge(startRow + 1, startCol, 1, 2);
		}
		else {
			pushData("", startRow, startCol, stylePaymentValueHeader);
			pushData("", startRow, startCol + 1, stylePaymentValueHeader);
			cells.merge(startRow, startCol, 1, 2);
			pushData("", startRow + 1, startCol, stylePaymentValueRow);
			pushData("", startRow + 1, startCol + 1, stylePaymentValueRow);
			cells.merge(startRow + 1, startCol, 1, 2);
		}
	}
}
