package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.LineByLineSettingExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByCtgExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutSetExportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Stateless
public class StatementLayoutAsposeFileGenerator extends AsposeCellsReportGenerator
		implements StatementLayoutFileGenerator {
	private static final String TEMPLATE_FILE = "report/明細レイアウトの作成.xlsx";

	private static final String REPORT_FILE_NAME = "明細レイアウトの作成.xlsx";

	@Override
	public void generate(FileGeneratorContext fileContext, List<StatementLayoutSetExportData> exportData) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook wb = reportContext.getWorkbook();
			WorksheetCollection wsc = wb.getWorksheets();
			Worksheet ws = wsc.get(0);
			StatementLayoutPrint sttPrint = new StatementLayoutPrint(wsc, ws);
			HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();
			int offset = sttPrint.getStatementLayout().getRowCount();
			for (StatementLayoutSetExportData stt : exportData) {				
				offset = sttPrint.printHeader(stt.getStatementCode(), stt.getStatementName(), stt.getProcessingDate(), offset);
				offset = this.printCtg(sttPrint, stt, CategoryAtr.PAYMENT_ITEM, offset) + 1;
				offset = this.printCtg(sttPrint, stt, CategoryAtr.DEDUCTION_ITEM, offset) + 1;
				offset = this.printCtg(sttPrint, stt, CategoryAtr.ATTEND_ITEM, offset);
				offset = this.printCtg(sttPrint, stt, CategoryAtr.REPORT_ITEM, offset);
				pageBreaks.add(offset);
			}
			sttPrint.deleteOrginRange();
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int printCtg(StatementLayoutPrint sttPrint, StatementLayoutSetExportData stt, CategoryAtr ctg, int offset) {
		Optional<SettingByCtgExportData> setByCtgOtp = stt.getListSettingByCtg().stream()
				.filter(x -> ctg.equals(x.getCtgAtr())).findFirst();
		if (!setByCtgOtp.isPresent()) {
			return offset;
		}
		SettingByCtgExportData setByCtg = setByCtgOtp.get();
		List<LineByLineSettingExportData> listLineByLineSet = setByCtg.getListLineByLineSet();
		if (listLineByLineSet.isEmpty()) {
			return offset;
		}
		listLineByLineSet.sort(Comparator.comparingInt(LineByLineSettingExportData::getLineNumber));
		int firstLine = listLineByLineSet.get(0).getLineNumber();
		int lastLine = listLineByLineSet.get(listLineByLineSet.size() - 1).getLineNumber();		
		for (LineByLineSettingExportData line : listLineByLineSet) {
			LinePosition pos = LinePosition.MIDDLE;
			if (line.getLineNumber() == firstLine) {
				pos = LinePosition.FIRST;
				if (line.getLineNumber() == lastLine) {
					pos = LinePosition.FIRST_AND_LAST;
				}
			} else if (line.getLineNumber() == lastLine) {
				pos = LinePosition.LAST;
			}
			offset = this.printLine(sttPrint, line, ctg, pos, offset);
		}

		return offset;
	}

	private int printLine(StatementLayoutPrint sttPrint, LineByLineSettingExportData line, CategoryAtr ctg,
			LinePosition pos, int offset) {
		switch (ctg) {
		case PAYMENT_ITEM:
			return sttPrint.printPaymentItem(line, pos, offset);
		case DEDUCTION_ITEM:
			return sttPrint.printDeductionItem(line, pos, offset);
		case ATTEND_ITEM:
			return sttPrint.printAttendItem(line, pos, offset);
		case REPORT_ITEM:
			return sttPrint.printReportItem(line, pos, offset);
		case OTHER_ITEM:
			return offset;
		}
		return offset;
	}
}
