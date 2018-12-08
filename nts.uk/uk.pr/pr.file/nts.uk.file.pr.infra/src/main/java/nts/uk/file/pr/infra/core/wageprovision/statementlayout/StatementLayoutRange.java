package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import com.aspose.cells.Cell;
import com.aspose.cells.Range;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.Getter;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.LineByLineSettingExportData;

@Getter
public class StatementLayoutRange {
	private WorksheetCollection wsc;
	private Worksheet ws;
	private Range statementLayout;
	private Range header;
	private Range paymentRow;
	private Range deductionRow;
	private Range attendRow;
	private Range reportRow;

	public StatementLayoutRange(WorksheetCollection wsc, Worksheet ws) {
		this.wsc = wsc;
		this.ws = ws;
		this.statementLayout = wsc.getRangeByName("statement_layout");
		this.header = wsc.getRangeByName("header");
		this.paymentRow = wsc.getRangeByName("paymentRow");
		this.deductionRow = wsc.getRangeByName("deductionRow");
		this.attendRow = wsc.getRangeByName("attendRow");
		this.reportRow = wsc.getRangeByName("reportRow");
	}

	public int printHeader(String statement, String processingDate, int offset) {
		this.copyRange(this.header, offset);
		this.printCell("statement", statement, offset);
		this.printCell("processingDate", processingDate, offset);
		return this.header.getFirstRow() + this.header.getRowCount() + offset;
	}

	public int printPaymentItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.paymentRow, offset);
		return this.paymentRow.getRowCount() + offset;
	}

	public int printDeductionItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.deductionRow, offset);
		return this.deductionRow.getRowCount() + offset;
	}

	public int printAttendItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.attendRow, offset);
		return this.attendRow.getRowCount() + offset;
	}

	public int printReportItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.reportRow, offset);
		this.ws.getCells().setRowHeightPixel(this.reportRow.getRowCount() + offset - 1, 36);
		return this.reportRow.getRowCount() + offset;
	}

	private void printCell(String name, String value, int offset) {
		Cell orginCell = this.wsc.getRangeByName(name).get(0, 0);
		Cell cell = this.ws.getCells().get(orginCell.getRow() + offset, orginCell.getColumn());
		cell.setValue(value);
	}

	private void copyRange(Range range, int firstRow) {
		Range newRange = ws.getCells().createRange(firstRow, range.getFirstColumn(), range.getRowCount(),
				range.getColumnCount());
		newRange.copyStyle(range);
	}

	public void deleteOrginRange() {
		ws.getCells().deleteRows(this.statementLayout.getFirstRow(), this.statementLayout.getRowCount());
	}
}
