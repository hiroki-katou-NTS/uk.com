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
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.UseRangeAtr;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.AttendExportData;
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

	public int printHeader(String code, String name, YearMonth ym, int offset) {
		String statement = "【" + code + "　" + name + "】";
		String processingDate = TextResource.localize("QMM019_204") + "：" + ym.year() + "年" + ym.month() + "月";
		this.copyRange(this.header, offset);
		this.printCell("statement", statement, offset);
		this.printCell("processingDate", processingDate, offset);
		return this.header.getFirstRow() + this.header.getRowCount() + offset;
	}

	public int printPaymentItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.paymentRow, offset);
		// A2_1
		this.printHeadItem(CategoryAtr.PAYMENT_ITEM, linePosition, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			PaymentExportData payment = item.getPayment();
			// A2_2
			this.printCell("paymentItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
			if (payment == null)
				continue;

			// A2_3
			String totalObj = TextResource.localize(payment.getTotalObj().nameId);
			this.printCell("paymentItem" + item.getItemPosition() + "_info1", totalObj, offset + 1);

			// A2_4
			String calcMethod = TextResource.localize(payment.getCalcMethod().nameId);
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
		// A3_1
		this.printHeadItem(CategoryAtr.DEDUCTION_ITEM, linePosition, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			DeductionExportData deduction = item.getDeduction();
			// A3_2
			this.printCell("deductionItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
			if (deduction == null)
				continue;

			// A3_3
			String totalObj = TextResource.localize(deduction.getTotalObj().nameId);
			this.printCell("deductionItem" + item.getItemPosition() + "_info1", totalObj, offset + 1);

			// A3_4
			String calcMethod = TextResource.localize(deduction.getCalcMethod().nameId);
			this.printCell("deductionItem" + item.getItemPosition() + "_info2", calcMethod, offset + 2);

			// A3_5
			String proportionalSet = "按分設定：";
			switch (deduction.getProportionalAtr()) {
			case NOT_PROPORTIONAL:
				proportionalSet += "なし";
				break;
			case PROPORTIONAL:
			case DEDUCTION_ONCE_A_MONTH:
				proportionalSet += "あり";
				break;
			}
			this.printCell("deductionItem" + item.getItemPosition() + "_info4", proportionalSet, offset + 4);

			// A3_6, A3_7
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

			// A3_8
			String referInfo = "";
			switch (deduction.getCalcMethod()) {
			case PERSON_INFO_REF:
				referInfo = "参照：";
				referInfo += deduction.getPersonAmountName();
				break;
			case CACL_FOMULA:
				referInfo = "式：";
				referInfo += deduction.getCalcFomulaName();
				break;
			case WAGE_TABLE:
				referInfo = "表：";
				referInfo += deduction.getWageTblName();
				break;
			case COMMON_AMOUNT:
				referInfo = "金額：";
				referInfo += deduction.getCommonAmount();
				break;
			case SUPPLY_OFFSET:
				referInfo = "対象：";
				referInfo += deduction.getSupplyOffsetName();
			default:
				referInfo = "";
			}
			this.printCell("deductionItem" + item.getItemPosition() + "_info3", referInfo, offset + 3);
		}
		return this.deductionRow.getRowCount() + offset;
	}

	public int printAttendItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.attendRow, offset);
		// A4_1
		this.printHeadItem(CategoryAtr.ATTEND_ITEM, linePosition, offset);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			AttendExportData attend = item.getAttend();
			// A4_2
			this.printCell("attendItem" + item.getItemPosition() + "_name", item.getItemName(), offset);
			
			// A4_3, A4_4
			ItemRangeSetExportData itemRangeSet = attend.getItemRangeSet();
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
			this.printCell("attendItem" + item.getItemPosition() + "_info1", errorSet, offset + 1);
			this.printCell("attendItem" + item.getItemPosition() + "_info2", alarmSet, offset + 2);
		}
		return this.attendRow.getRowCount() + offset;
	}

	public int printReportItem(LineByLineSettingExportData lineSet, LinePosition linePosition, int offset) {
		this.copyRange(this.reportRow, offset);
		// A5_1
		this.printHeadItem(CategoryAtr.REPORT_ITEM, linePosition, offset);
		this.ws.getCells().setRowHeightPixel(this.reportRow.getRowCount() + offset - 1, 36);
		List<SettingByItemExportData> items = lineSet.getListSetByItem();
		for (SettingByItemExportData item : items) {
			// A5_2
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
			labelName = "deductionLabel";
			rangeName = "deductionHead";
			// A3_1
			titleValue = TextResource.localize("QMM019_25");
			break;
		case ATTEND_ITEM:
			labelName = "attendLabel";
			rangeName = "attendHead";
			offsetLabel = 1;
			// A4_1
			titleValue = TextResource.localize("QMM019_29");
			break;
		case REPORT_ITEM:
			labelName = "reportHead";
			rangeName = "reportHead";
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
