package nts.uk.file.pr.infra.core.socialinsurance.salaryhealth;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SalaryHealthAposeFileGenerator extends AsposeCellsReportGenerator implements SalaryHealthFileGenerator {

    private static final String TEMPLATE_FILE_B = "report/QMM008社会保険事業所の登録_健康保険料率一覧.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM008-社会保険事業所の登録_健康保険料率一覧";

    @Override
    public void generate(FileGeneratorContext generatorContext, SalaryHealthExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_B)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.setHeader(0,exportData.getCompanyName());
            reportContext.setHeader(2, "&D　&T\n&P#{PAGE}");
            Worksheet firstSheet = worksheets.get(0);
            printSalaryHealthy(firstSheet, exportData.getData());
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void printSalaryHealthy(Worksheet worksheet, List<Object[]> data){
        Cells cells = worksheet.getCells();
        int numRow = 2;
        int numColumn = 12;
        int rowStart = 4;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }

    private void fillData(Cells cells, List<Object[]> data, int numRow, int numColumn, int startRow, int startColumn) {
        try {
            for (int i = 0; i < data.size(); i++) {
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    cells.get(i + startRow, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
