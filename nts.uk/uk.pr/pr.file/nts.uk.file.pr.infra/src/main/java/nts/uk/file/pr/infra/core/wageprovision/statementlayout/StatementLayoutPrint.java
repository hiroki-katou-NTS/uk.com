package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import java.util.List;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.UseRangeAtr;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.DeductionExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.ItemRangeSetExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.LineByLineSettingExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.PaymentExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByItemExportData;
import nts.uk.shr.com.i18n.TextResource;

@Getter
public class StatementLayoutPrint {
	private WorksheetCollection wsc;
	private Worksheet ws;
	private Range statementLayout;
	private Range header;
	private Range paymentRow;
	private Range deductionRow;
	private Range attendRow;
	private Range reportRow;

	public StatementLayoutPrint(WorksheetCollection wsc, Worksheet ws) {
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
		this.printHeadItem(CategoryAtr.PAYMENT_ITEM, linePosition, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			PaymentExportData payment = item.getPayment();
			this.printCell("paymentItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
			if (payment == null)
				continue;

			// A2_3
			String totalObj = payment.getTotalObj().toString();
			this.printCell("paymentItem" + item.getItemPosition() + "_info1", totalObj, offset + 1);

			// A2_4
			String calcMethod = payment.getCalcMethod().toString();
			this.printCell("paymentItem" + item.getItemPosition() + "_info2", calcMethod, offset + 2);

			// A2_5
			String proportionalSet = "按分設定：";
			switch (payment.getProportionalAtr()) {
			case NOT_PROPORTIONAL:
				proportionalSet += "なし";
				break;
			case PROPORTIONAL:
			case PAYMENT_ONE_A_MONTH:
				proportionalSet += "あり";
				break;
			}
			this.printCell("paymentItem" + item.getItemPosition() + "_info4", proportionalSet, offset + 4);

			// A2_6, A2_7
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

			// A2_8
			String referInfo = "";
			switch (payment.getCalcMethod()) {
			case PERSON_INFO_REF:
				referInfo = "参照：";
				referInfo += payment.getPersonAmountName();
				break;
			case CACL_FOMULA:
				referInfo = "式：";
				referInfo += payment.getCalcFomulaName();
				break;
			case WAGE_TABLE:
				referInfo = "表：";
				referInfo += payment.getWageTblName();
				break;
			case COMMON_AMOUNT:
				referInfo = "金額：";
				referInfo += payment.getCommonAmount();
				break;
			default:
				referInfo = "";
			}
			this.printCell("paymentItem" + item.getItemPosition() + "_info3", referInfo, offset + 3);
		}
		return this.paymentRow.getRowCount() + offset;
	}

	public int printDeductionItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.deductionRow, offset);
		this.printHeadItem(CategoryAtr.DEDUCTION_ITEM, linePosition, offset);
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

	public int printAttendItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.attendRow, offset);
		this.printHeadItem(CategoryAtr.ATTEND_ITEM, linePosition, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			this.printCell("attendItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
		}
		return this.attendRow.getRowCount() + offset;
	}

	public int printReportItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.reportRow, offset);
		this.printHeadItem(CategoryAtr.REPORT_ITEM, linePosition, offset);
		this.ws.getCells().setRowHeightPixel(this.reportRow.getRowCount() + offset - 1, 36);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			this.printCell("reportItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
		}
		return this.reportRow.getRowCount() + offset;
	}

	private void printHeadItem(CategoryAtr ctg, LinePosition linePosition, int offset) {
		Cell cell;
		Style style;
		String labelName = "";
		String rangeName = "";
		String titleValue = "";
		int offsetLabel = 2;
		switch (ctg) {
		case PAYMENT_ITEM:
			labelName = "paymentLabel";
			rangeName = "paymentHead";
			// A2_1
			titleValue = TextResource.localize("QMM019_23");
			break;
		case DEDUCTION_ITEM:
			labelName = "paymentLabel";
			rangeName = "paymentHead";
			// A3_1
			titleValue = TextResource.localize("QMM019_25");
			break;
		case ATTEND_ITEM:
			labelName = "paymentLabel";
			rangeName = "paymentHead";
			offsetLabel = 1;
			// A4_1
			titleValue = TextResource.localize("QMM019_29");
			break;
		case REPORT_ITEM:
			labelName = "paymentHead";
			rangeName = "paymentHead";
			offsetLabel = 0;
			// A5_1
			titleValue = TextResource.localize("QMM019_31");
			break;
		default:
			break;
		}
		switch (linePosition) {
		case FIRST:
			this.printCell(labelName, titleValue, offset + offsetLabel);
			cell = this.getLastCell(rangeName, offset);
			style = cell.getStyle();
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			break;
		case MIDDLE:
			cell = this.getFirstCell(rangeName, offset);
			style = cell.getStyle();
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);

			cell = this.getLastCell(rangeName, offset);
			style = cell.getStyle();
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			break;
		case LAST:
			cell = this.getFirstCell(rangeName, offset);
			style = cell.getStyle();
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
			cell.setStyle(style);
			break;
		case FIRST_AND_LAST:
			this.printCell(labelName, titleValue, offset + offsetLabel);
			break;
		}
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
