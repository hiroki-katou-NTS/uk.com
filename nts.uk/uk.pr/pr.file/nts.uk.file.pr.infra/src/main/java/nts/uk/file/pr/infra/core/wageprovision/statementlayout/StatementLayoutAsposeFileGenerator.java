package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.StatementLayoutSetExportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StatementLayoutAsposeFileGenerator extends AsposeCellsReportGenerator implements StatementLayoutFileGenerator {
    private static final String TEMPLATE_FILE = "report/明細レイアウトの作成.xlsx";

    private static final String REPORT_FILE_NAME = "明細レイアウトの作成.xlsx";

    @Override
    public void generate(FileGeneratorContext fileContext, List<StatementLayoutSetExportData> exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            Range printRange = wsc.getRangeByName("Print_Area");
            RangeCustom newRange;
            int offset = 0;
            for (StatementLayoutSetExportData stt : exportData) {
                //newRange = copyRangeDown(printRange, offset);
                //this.print(wsc, newRange, stt);
            }

            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void print(WorksheetCollection wsc, RangeCustom range, StatementLayoutSetExportData stt) {
        String statement = "【" + stt.getStatementCode() + stt.getStatementName() + "】";
        range.cell("statement").putValue(statement);


    }

    private RangeCustom copyRangeDown(Range range, int extra) throws Exception {
        Range newRange = range.getWorksheet().getCells().createRange(range.getFirstRow() + range.getRowCount() + extra,
                range.getFirstColumn(), range.getRowCount(), range.getColumnCount());
        newRange.copyStyle(range);
        int offset = newRange.getFirstRow() - range.getFirstRow();
        return new RangeCustom(newRange, offset);
    }
}
