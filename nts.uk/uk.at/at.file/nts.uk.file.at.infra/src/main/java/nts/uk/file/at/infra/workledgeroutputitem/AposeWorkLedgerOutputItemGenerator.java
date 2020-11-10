package nts.uk.file.at.infra.workledgeroutputitem;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.workledgeroutputitem.WorkLedgerOutputItemGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public class AposeWorkLedgerOutputItemGenerator extends AsposeCellsReportGenerator implements WorkLedgerOutputItemGenerator {

    private static final String TEMPLATE_FILE_ADD = "report/KWR005.xlsx";
    private static final String REPORT_FILE_NAME = "帳票設計書-KWR005_勤務状況表";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String DAY_OF_WEEK_FORMAT_JP = "E";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "A1:AJ";
    private static final int MAX_EMP_IN_PAGE = 10;
    @Override
    public void generate(FileGeneratorContext generatorContext, Object dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            //printContents(worksheet, dataSource);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            String fileName = REPORT_FILE_NAME;
//            if (dataSource.getMode() == EXPORT_EXCEL) {
//                // save as excel file
//                reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));
//            } else if (dataSource.getMode() == EXPORT_PDF) {
//                // save as PDF file
//                reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + PDF_EXT));
//            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
