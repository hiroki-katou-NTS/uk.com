package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementPrintAtr;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.LineByLineSettingExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByCtgExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutSetExportData;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class StatementLayoutAsposeFileGenerator extends AsposeCellsReportGenerator
		implements StatementLayoutFileGenerator {

	@Inject
	private CompanyAdapter company;

	private static final String TEMPLATE_FILE = "report/明細レイアウトの作成.xlsx";

	private static final String REPORT_FILE_NAME = "明細レイアウトの作成.xlsx";

	private static final int MAX_ROW_PER_PAGE = 55;

	@Override
	public void generate(FileGeneratorContext fileContext, List<StatementLayoutSetExportData> exportData) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook wb = reportContext.getWorkbook();
			WorksheetCollection wsc = wb.getWorksheets();
			Worksheet ws = wsc.get(0);
			StatementLayoutPrint sttPrint = new StatementLayoutPrint(wsc, ws);
			HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();

			String companyName = this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse("");
			reportContext.setHeader(0, "&11&\"ＭＳ 明朝\"" + companyName);

			int offset = sttPrint.getStatementLayout().getRowCount();
			for (StatementLayoutSetExportData stt : exportData) {
                int totalLineInPage = 0;
				int newLine;
				int offsetBefore;

				offset += sttPrint.printHeader(stt.getStatementCode(), stt.getStatementName(), stt.getProcessingDate(), offset, true);
				newLine = this.printCtg(sttPrint, stt, CategoryAtr.PAYMENT_ITEM, offset, pageBreaks) + 1;
				offset += newLine;
				totalLineInPage += newLine;

				offsetBefore = offset;
				newLine = this.printCtg(sttPrint, stt, CategoryAtr.DEDUCTION_ITEM, offset, pageBreaks) + 1;
				offset += newLine;
				totalLineInPage += newLine;
				if (totalLineInPage > MAX_ROW_PER_PAGE){
					int newLineHeader = sttPrint.printHeader(stt.getStatementCode(), stt.getStatementName(), stt.getProcessingDate(), offsetBefore, false);
					offset += newLineHeader;
					pageBreaks.add(offsetBefore);
					totalLineInPage = newLine + newLineHeader;
				}

                offsetBefore = offset;
                newLine = this.printCtg(sttPrint, stt, CategoryAtr.ATTEND_ITEM, offset, pageBreaks);
                offset += newLine;
                totalLineInPage += newLine;
                newLine = this.printCtg(sttPrint, stt, CategoryAtr.REPORT_ITEM, offset, pageBreaks);
                offset += newLine;
                totalLineInPage += newLine * 3;
                if (totalLineInPage > MAX_ROW_PER_PAGE) {
                    offset += sttPrint.printHeader(stt.getStatementCode(), stt.getStatementName(), stt.getProcessingDate(), offsetBefore, false);
                    pageBreaks.add(offsetBefore);
                }

				pageBreaks.add(offset);
			}
			sttPrint.deleteOrginRange();
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int printCtg(StatementLayoutPrint sttPrint, StatementLayoutSetExportData stt, CategoryAtr ctg, int offset,
			HorizontalPageBreakCollection pageBreaks) {
		int totalLine = 0;
		int totalLineInPage = 0;

		Optional<SettingByCtgExportData> setByCtgOtp = stt.getListSettingByCtg().stream()
				.filter(x -> ctg.equals(x.getCtgAtr())).findFirst();
		if (!setByCtgOtp.isPresent()) {
			return totalLine;
		}
		SettingByCtgExportData setByCtg = setByCtgOtp.get();
		// ※補足3
		List<LineByLineSettingExportData> listLineByLineSet = setByCtg.getListLineByLineSet().stream()
                .filter(x -> StatementPrintAtr.PRINT.equals(x.getPrintSet())).collect(Collectors.toList());
		if (listLineByLineSet.isEmpty()) {
			return totalLine;
		}
		listLineByLineSet.sort(Comparator.comparingInt(LineByLineSettingExportData::getLineNumber));
		int firstLine = listLineByLineSet.get(0).getLineNumber();
		int lastLine = listLineByLineSet.get(listLineByLineSet.size() - 1).getLineNumber();

		for (LineByLineSettingExportData line : listLineByLineSet) {
			int newLine;
			int offsetBefore = offset + totalLine;
			LinePosition pos = LinePosition.MIDDLE;
			if (line.getLineNumber() == firstLine) {
				pos = LinePosition.FIRST;
				if (line.getLineNumber() == lastLine) {
					pos = LinePosition.FIRST_AND_LAST;
				}
			} else if (line.getLineNumber() == lastLine) {
				pos = LinePosition.LAST;
			}
			newLine = this.printLine(sttPrint, line, ctg, pos, offset + totalLine);
			totalLine += newLine;
			totalLineInPage += newLine;
			if (totalLineInPage > MAX_ROW_PER_PAGE){
                // A2_1
                sttPrint.printHeadItem(ctg, LinePosition.LAST, offsetBefore - newLine);
                if (pos == LinePosition.LAST) {
                    sttPrint.printHeadItem(ctg, LinePosition.FIRST_AND_LAST, offsetBefore);
                } else {
                    sttPrint.printHeadItem(ctg, LinePosition.FIRST, offsetBefore);
                }
				int newLineHeader = sttPrint.printHeader(stt.getStatementCode(), stt.getStatementName(), stt.getProcessingDate(), offsetBefore, false);
				totalLine += newLineHeader;
                pageBreaks.add(offsetBefore);
                totalLineInPage = newLine + newLineHeader;
            }
		}

		return totalLine;
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
			return 0;
		}
		return 0;
	}
}
