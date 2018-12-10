package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import java.util.List;

import com.aspose.cells.Border;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.UseRangeAtr;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.DeductionExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.ItemRangeSetExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.LineByLineSettingExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.PaymentExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByItemExportData;
import nts.uk.shr.com.i18n.TextResource;

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

	public int printPaymentItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.paymentRow, offset);
		Cell cell;
		Style style;
		switch (linePosition) {
		case FIRST:
			this.printCell("paymentLabel", TextResource.localize("QMM019_23"), offset + 2);
			cell = this.getLastCell("paymentHead", offset);
			style = cell.getStyle();
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			break;
		case MIDDLE:
			cell = this.getFirstCell("paymentHead", offset);
			style = cell.getStyle();
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			
			cell = this.getLastCell("paymentHead", offset);
			style = cell.getStyle();
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			break;
		case LAST:
			cell = this.getFirstCell("paymentHead", offset);
			style = cell.getStyle();
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			break;
		case FIRST_AND_LAST:
			break;
		}
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			PaymentExportData payment = item.getPayment();
			this.printCell("paymentItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
			if (payment == null)
				continue;
			ItemRangeSetExportData itemRangeSet = payment.getItemRangeSet();
			String errorSet = "エラー設定：";
			if (UseRangeAtr.USE.equals(itemRangeSet.getErrorUpperSettingAtr())
					&& UseRangeAtr.USE.equals(itemRangeSet.getErrorLowerSettingAtr())) {
				errorSet += "あり";
			} else {
				errorSet += "なし";
			}
			String alarmSet = "アラーム設定：";
			if (UseRangeAtr.USE.equals(itemRangeSet.getAlarmUpperSettingAtr())
					&& UseRangeAtr.USE.equals(itemRangeSet.getAlarmLowerSettingAtr())) {
				alarmSet += "あり";
			} else {
				alarmSet += "なし";
			}
			this.printCell("paymentItem" + item.getItemPosition() + "_info5", errorSet, offset + 5);
			this.printCell("paymentItem" + item.getItemPosition() + "_info6", alarmSet, offset + 6);
		}
		return this.paymentRow.getRowCount() + offset;
	}

	public int printDeductionItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.deductionRow, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			DeductionExportData deduction = item.getDeduction();
			this.printCell("deductionItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
			if (deduction == null)
				continue;
			ItemRangeSetExportData itemRangeSet = deduction.getItemRangeSet();
			String errorSet = "エラー設定：";
			if (UseRangeAtr.USE.equals(itemRangeSet.getErrorUpperSettingAtr())
					&& UseRangeAtr.USE.equals(itemRangeSet.getErrorLowerSettingAtr())) {
				errorSet += "あり";
			} else {
				errorSet += "なし";
			}
			String alarmSet = "アラーム設定：";
			if (UseRangeAtr.USE.equals(itemRangeSet.getAlarmUpperSettingAtr())
					&& UseRangeAtr.USE.equals(itemRangeSet.getAlarmLowerSettingAtr())) {
				alarmSet += "あり";
			} else {
				alarmSet += "なし";
			}
			this.printCell("deductionItem" + item.getItemPosition() + "_info5", errorSet, offset + 5);
			this.printCell("deductionItem" + item.getItemPosition() + "_info6", alarmSet, offset + 6);
		}
		return this.deductionRow.getRowCount() + offset;
	}

	public int printAttendItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.attendRow, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			this.printCell("attendItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
		}
		return this.attendRow.getRowCount() + offset;
	}

	public int printReportItem(LineByLineSettingExportData lineSet, int offset) {
		this.copyRange(this.reportRow, offset);
		this.ws.getCells().setRowHeightPixel(this.reportRow.getRowCount() + offset - 1, 36);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			this.printCell("reportItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
		}
		return this.reportRow.getRowCount() + offset;
	}

	private void printCell(String name, String value, int offset) {
		Cell orginCell = this.wsc.getRangeByName(name).get(0, 0);
		Cell cell = this.ws.getCells().get(offset, orginCell.getColumn());
		cell.setValue(value);
	}

	private Cell getFirstCell(String rangeName, int offset) {
		Range orginRange = this.wsc.getRangeByName(rangeName);
		Cell orginCell = orginRange.get(0, 0);
		return this.ws.getCells().get(offset, orginCell.getColumn());
	}

	private Cell getLastCell(String rangeName, int offset) {
		Range orginRange = this.wsc.getRangeByName(rangeName);
		Cell orginCell = orginRange.get(0, 0);
		return this.ws.getCells().get(orginRange.getRowCount() + offset - 1, orginCell.getColumn());
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
