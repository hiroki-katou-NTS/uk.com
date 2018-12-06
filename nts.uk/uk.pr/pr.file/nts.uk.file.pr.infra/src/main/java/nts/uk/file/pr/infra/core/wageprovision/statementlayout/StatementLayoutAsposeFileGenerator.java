package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.LineByLineSettingExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByCtgExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByItemExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutSetExportData;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StatementLayoutAsposeFileGenerator extends AsposeCellsReportGenerator
		implements StatementLayoutFileGenerator {
	private static final String TEMPLATE_FILE = "report/明細レイアウトの作成.xlsx";

	private static final String REPORT_FILE_NAME = "明細レイアウトの作成.xlsx";

	@Inject
	private JapaneseErasAdapter japaneseErasAdapter;

	@Override
	public void generate(FileGeneratorContext fileContext, List<StatementLayoutSetExportData> exportData) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook wb = reportContext.getWorkbook();
			WorksheetCollection wsc = wb.getWorksheets();
			Worksheet ws = wsc.get(0);
			Range printRange = wsc.getRangeByName("statement_layout");
			RangeCustom newRange;
			HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();
			int offset = 0;
			StatementLayoutSetExportData firstStt = exportData.get(0);
			exportData.remove(0);
			for (StatementLayoutSetExportData stt : exportData) {
				newRange = this.copyRangeDown(printRange, offset);
				this.printStt(wsc, newRange, stt);
				pageBreaks.add(newRange.range.getFirstRow());
				offset = newRange.offset;
			}
			this.printStt(wsc, new RangeCustom(printRange, 0), firstStt);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printStt(WorksheetCollection wsc, RangeCustom customRange, StatementLayoutSetExportData stt)
			throws Exception {
		String statement = "【" + stt.getStatementCode() + stt.getStatementName() + "】";
		customRange.cell("statement").setValue(statement);
		String date = "基準年月：" + stt.getProcessingDate().year() + "年" + stt.getProcessingDate().month() + "月";
		customRange.cell("processingDate").setValue(date);
		this.printPaymentCtg(wsc, customRange, stt);
		Optional<SettingByCtgExportData> deductionCtgOtp = stt.getListSettingByCtg().stream()
				.filter(x -> CategoryAtr.DEDUCTION_ITEM.equals(x.getCtgAtr())).findFirst();
		Optional<SettingByCtgExportData> AttendCtgOtp = stt.getListSettingByCtg().stream()
				.filter(x -> CategoryAtr.ATTEND_ITEM.equals(x.getCtgAtr())).findFirst();
		Optional<SettingByCtgExportData> reportCtgOtp = stt.getListSettingByCtg().stream()
				.filter(x -> CategoryAtr.REPORT_ITEM.equals(x.getCtgAtr())).findFirst();

	}

	private void printPaymentCtg(WorksheetCollection wsc, RangeCustom customRange, StatementLayoutSetExportData stt)
			throws Exception {
		Range rowRange = customRange.range("paymentRow");
		int offset = 0;
		RangeCustom newRange;
		Optional<SettingByCtgExportData> paymentCtgOtp = stt.getListSettingByCtg().stream()
				.filter(x -> CategoryAtr.PAYMENT_ITEM.equals(x.getCtgAtr())).findFirst();
		if (!paymentCtgOtp.isPresent())
			return;

		SettingByCtgExportData paymentCtg = paymentCtgOtp.get();
		List<LineByLineSettingExportData> lines = paymentCtg.getListLineByLineSet();
		if (lines.isEmpty())
			return;
		lines.sort(Comparator.comparingInt(LineByLineSettingExportData::getLineNumber));
		LineByLineSettingExportData firstLine = paymentCtg.getListLineByLineSet().get(0);
		lines.remove(0);
		for (LineByLineSettingExportData line : lines) {
			newRange = this.insertCopyRangeDown(rowRange, offset);
			this.printPaymentRow(wsc, newRange, line);
			offset = newRange.offset;
		}
		this.printPaymentRow(wsc, new RangeCustom(rowRange, 0), firstLine);
	}

	private void printPaymentRow(WorksheetCollection wsc, RangeCustom customRange, LineByLineSettingExportData line) {
		for (SettingByItemExportData item : line.getListSetByItem()) {
			customRange.cell("paymentItem" + item.getItemPosition() + "_name").setValue(item.getItemName());
		}
	}

	private RangeCustom copyRangeDown(Range range, int extra) throws Exception {
		Range newRange = range.getWorksheet().getCells().createRange(range.getFirstRow() + range.getRowCount() + extra,
				range.getFirstColumn(), range.getRowCount(), range.getColumnCount());
		newRange.copyStyle(range);
		int offset = newRange.getFirstRow() - range.getFirstRow();
		return new RangeCustom(newRange, offset);
	}

	private RangeCustom insertCopyRangeDown(Range range, int extra) throws Exception {
		range.getWorksheet().getCells().insertRows(range.getFirstRow() + range.getRowCount() + extra,
				range.getRowCount());
		return this.copyRangeDown(range, extra);
	}
}
