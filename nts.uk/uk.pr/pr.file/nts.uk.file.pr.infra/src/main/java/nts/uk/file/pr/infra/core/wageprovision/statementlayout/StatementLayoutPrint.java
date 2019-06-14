package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import com.aspose.cells.*;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.DefaultAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.TaxAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentCaclMethodAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.WorkingAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.RangeSettingEnum;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.*;
import nts.uk.shr.com.i18n.TextResource;

import java.util.List;

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

	StatementLayoutPrint(WorksheetCollection wsc, Worksheet ws) {
		this.wsc = wsc;
		this.ws = ws;
		this.statementLayout = wsc.getRangeByName("statement_layout");
		this.header = wsc.getRangeByName("header");
		this.paymentRow = wsc.getRangeByName("paymentRow");
		this.deductionRow = wsc.getRangeByName("deductionRow");
		this.attendRow = wsc.getRangeByName("attendRow");
		this.reportRow = wsc.getRangeByName("reportRow");
	}

    public int printHeader(String code, String name, YearMonth ym, int offset, boolean isFirstHeader) {
        String statement = TextResource.localize("QMM019_245") + code + "　" + name + TextResource.localize("QMM019_246");
        String processingDate = TextResource.localize("QMM019_204") + ym.year() + "年" + ym.month() + "月";
		if (isFirstHeader) {
			this.copyRange(this.header, offset);
		} else {
			this.insertRange(this.header, offset);
		}
        this.printCell("statement", statement, offset);
        this.printCell("processingDate", processingDate, offset);
        return this.header.getFirstRow() + this.header.getRowCount();
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

			// ※補足4
			if (DefaultAtr.USER_CREATE.equals(item.getDefaultAtr())) {
				// A2_3
				String totalObj = TextResource.localize(payment.getTotalObj().nameId);
				this.printCell("paymentItem" + item.getItemPosition() + "_info1", totalObj, offset + 1);

                // A2_4, A2_9
                this.printCell("paymentItem" + item.getItemPosition() + "_info2", this.getA2_4_A2_9(payment), offset + 2);

				// A2_5
				String proportionalSet = TextResource.localize("QMM019_238");
				switch (payment.getProportionalAtr()) {
					case NOT_PROPORTIONAL:
						proportionalSet += TextResource.localize("QMM019_116");
						break;
					case PROPORTIONAL:
                        proportionalSet += TextResource.localize("QMM019_117");
                        break;
					case PAYMENT_ONE_A_MONTH:
                        proportionalSet += TextResource.localize("QMM019_118");
                        break;
				}
				this.printCell("paymentItem" + item.getItemPosition() + "_info4", proportionalSet, offset + 4);

				// ※補足1
				// A2_8
				String referInfo;
				switch (payment.getCalcMethod()) {
					case PERSON_INFO_REF:
						referInfo = TextResource.localize("QMM019_241");
						referInfo += payment.getPersonAmountName();
						break;
					case CACL_FOMULA:
						referInfo = TextResource.localize("QMM019_242");
						referInfo += payment.getCalcFomulaName();
						break;
					case WAGE_TABLE:
						referInfo = TextResource.localize("QMM019_243");
						referInfo += payment.getWageTblName();
						break;
					case COMMON_AMOUNT:
						referInfo = TextResource.localize("QMM019_244");
						referInfo += payment.getCommonAmount();
						break;
					default:
						referInfo = "";
				}
				this.printCell("paymentItem" + item.getItemPosition() + "_info3", referInfo, offset + 3);
			}

			// A2_6, A2_7
			if (payment.getItemRangeSet().isPresent()) {
				ItemRangeSetExportData itemRangeSet = payment.getItemRangeSet().get();
				String errorSet = TextResource.localize("QMM019_239");
				if (RangeSettingEnum.USE.equals(itemRangeSet.getErrorUpperSettingAtr())
						|| RangeSettingEnum.USE.equals(itemRangeSet.getErrorLowerSettingAtr())) {
					errorSet += "あり";
				} else {
					errorSet += "なし";
				}
				String alarmSet = TextResource.localize("QMM019_240");
				if (RangeSettingEnum.USE.equals(itemRangeSet.getAlarmUpperSettingAtr())
						|| RangeSettingEnum.USE.equals(itemRangeSet.getAlarmLowerSettingAtr())) {
					alarmSet += "あり";
				} else {
					alarmSet += "なし";
				}
				this.printCell("paymentItem" + item.getItemPosition() + "_info5", errorSet, offset + 5);
				this.printCell("paymentItem" + item.getItemPosition() + "_info6", alarmSet, offset + 6);
			} else {
				String errorSet = TextResource.localize("QMM019_239");
				errorSet += "なし";
				String alarmSet = TextResource.localize("QMM019_240");
				alarmSet += "なし";
				this.printCell("paymentItem" + item.getItemPosition() + "_info5", errorSet, offset + 5);
				this.printCell("paymentItem" + item.getItemPosition() + "_info6", alarmSet, offset + 6);
			}

		}
		return this.paymentRow.getRowCount();
	}

    private String getA2_4_A2_9(PaymentExportData payment) {
        String calcMethod = TextResource.localize(payment.getCalcMethod().nameId);
        String workingAtr = "";
        if (!payment.getTaxAtr().isPresent()) return calcMethod;
        TaxAtr taxAtr = payment.getTaxAtr().get();
        // ※補足2
        if (TaxAtr.COMMUTING_EXPENSES_MANUAL.equals(taxAtr) || TaxAtr.COMMUTING_EXPENSES_USING_COMMUTER.equals(taxAtr)) {
            if (PaymentCaclMethodAtr.PERSON_INFO_REF.equals(payment.getCalcMethod())) {
                workingAtr += "　";
				workingAtr += TextResource.localize("QMM019_226");
			} else if (payment.getWorkingAtr().isPresent()) {
				workingAtr += "　";
				WorkingAtr work = payment.getWorkingAtr().get();
				if (WorkingAtr.TRANSPORT_FACILITIES.equals(work)) {
					workingAtr += TextResource.localize("QMM019_227");
				} else if (WorkingAtr.TRANSPORT_EQUIPMENT.equals(work)) {
					workingAtr += TextResource.localize("QMM019_228");
				}
			}
        }
        return calcMethod + workingAtr;
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

			// ※補足4
			if (!DefaultAtr.SYSTEM_DEFAULT.equals(item.getDefaultAtr())) {
				// A3_3
				String totalObj = TextResource.localize(deduction.getTotalObj().nameId);
				this.printCell("deductionItem" + item.getItemPosition() + "_info1", totalObj, offset + 1);

				// A3_4
				String calcMethod = TextResource.localize(deduction.getCalcMethod().nameId);
				this.printCell("deductionItem" + item.getItemPosition() + "_info2", calcMethod, offset + 2);

				// A3_5
				String proportionalSet = TextResource.localize("QMM019_238");
				switch (deduction.getProportionalAtr()) {
					case NOT_PROPORTIONAL:
                        proportionalSet += TextResource.localize("QMM019_116");
						break;
					case PROPORTIONAL:
                        proportionalSet += TextResource.localize("QMM019_117");
                        break;
					case DEDUCTION_ONCE_A_MONTH:
                        proportionalSet += TextResource.localize("QMM019_162");
                        break;
				}
				this.printCell("deductionItem" + item.getItemPosition() + "_info4", proportionalSet, offset + 4);

				// ※補足1
				// A3_8
				String referInfo;
				switch (deduction.getCalcMethod()) {
					case PERSON_INFO_REF:
                        referInfo = TextResource.localize("QMM019_241");
						referInfo += deduction.getPersonAmountName();
						break;
					case CACL_FOMULA:
                        referInfo = TextResource.localize("QMM019_242");
						referInfo += deduction.getCalcFomulaName();
						break;
					case WAGE_TABLE:
                        referInfo = TextResource.localize("QMM019_243");
						referInfo += deduction.getWageTblName();
						break;
					case COMMON_AMOUNT:
                        referInfo = TextResource.localize("QMM019_244");
						referInfo += deduction.getCommonAmount();
						break;
					case SUPPLY_OFFSET:
                        referInfo = TextResource.localize("QMM019_241");
						referInfo += deduction.getSupplyOffsetName();
						break;
					default:
						referInfo = "";
				}
				this.printCell("deductionItem" + item.getItemPosition() + "_info3", referInfo, offset + 3);
			}

			// A3_6, A3_7
            if (deduction.getItemRangeSet().isPresent()) {
                ItemRangeSetExportData itemRangeSet = deduction.getItemRangeSet().get();
                String errorSet = TextResource.localize("QMM019_239");
                if (RangeSettingEnum.USE.equals(itemRangeSet.getErrorUpperSettingAtr())
						|| RangeSettingEnum.USE.equals(itemRangeSet.getErrorLowerSettingAtr())) {
                    errorSet += "あり";
                } else {
                    errorSet += "なし";
                }
                String alarmSet = TextResource.localize("QMM019_240");
                if (RangeSettingEnum.USE.equals(itemRangeSet.getAlarmUpperSettingAtr())
						|| RangeSettingEnum.USE.equals(itemRangeSet.getAlarmLowerSettingAtr())) {
                    alarmSet += "あり";
                } else {
                    alarmSet += "なし";
                }
                this.printCell("deductionItem" + item.getItemPosition() + "_info5", errorSet, offset + 5);
                this.printCell("deductionItem" + item.getItemPosition() + "_info6", alarmSet, offset + 6);
            } else {
				String errorSet = TextResource.localize("QMM019_239");
				errorSet += "なし";
				String alarmSet = TextResource.localize("QMM019_240");
				alarmSet += "なし";
				this.printCell("deductionItem" + item.getItemPosition() + "_info5", errorSet, offset + 5);
				this.printCell("deductionItem" + item.getItemPosition() + "_info6", alarmSet, offset + 6);
			}
		}
		return this.deductionRow.getRowCount();
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
            if (attend.getItemRangeSet().isPresent()) {
                ItemRangeSetExportData itemRangeSet = attend.getItemRangeSet().get();
                String errorSet = TextResource.localize("QMM019_239");
                if (RangeSettingEnum.USE.equals(itemRangeSet.getErrorUpperSettingAtr())
						|| RangeSettingEnum.USE.equals(itemRangeSet.getErrorLowerSettingAtr())) {
                    errorSet += "あり";
                } else {
                    errorSet += "なし";
                }
                String alarmSet = TextResource.localize("QMM019_240");
                if (RangeSettingEnum.USE.equals(itemRangeSet.getAlarmUpperSettingAtr())
						|| RangeSettingEnum.USE.equals(itemRangeSet.getAlarmLowerSettingAtr())) {
                    alarmSet += "あり";
                } else {
                    alarmSet += "なし";
                }
                this.printCell("attendItem" + item.getItemPosition() + "_info1", errorSet, offset + 1);
                this.printCell("attendItem" + item.getItemPosition() + "_info2", alarmSet, offset + 2);
            } else {
				String errorSet = TextResource.localize("QMM019_239");
				errorSet += "なし";
				String alarmSet = TextResource.localize("QMM019_240");
				alarmSet += "なし";
				this.printCell("attendItem" + item.getItemPosition() + "_info1", errorSet, offset + 1);
				this.printCell("attendItem" + item.getItemPosition() + "_info2", alarmSet, offset + 2);
			}
		}
		return this.attendRow.getRowCount();
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
		return this.reportRow.getRowCount();
	}

	public void printHeadItem(CategoryAtr ctg, LinePosition linePosition, int offset) {
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

            cell = this.getFirstCell(rangeName, offset);
            style = cell.getStyle();
            style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getEmpty());
            cell.setStyle(style);

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

            cell = this.getLastCell(rangeName, offset);
            style = cell.getStyle();
            style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getEmpty());
            cell.setStyle(style);
			break;
		case FIRST_AND_LAST:
			this.printCell(labelName, titleValue, offset + offsetLabel);

            cell = this.getFirstCell(rangeName, offset);
            style = cell.getStyle();
            style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getEmpty());
            cell.setStyle(style);

            cell = this.getLastCell(rangeName, offset);
            style = cell.getStyle();
            style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getEmpty());
            cell.setStyle(style);
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

	private void insertRange(Range range, int firstRow) {
		ws.getCells().insertRows(firstRow, range.getRowCount());
		this.copyRange(range, firstRow);
	}

	public void deleteOrginRange() {
		ws.getCells().deleteRows(this.statementLayout.getFirstRow(), this.statementLayout.getRowCount());
	}
}
