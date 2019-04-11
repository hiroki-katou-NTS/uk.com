package nts.uk.file.pr.infra.core.socialinsurance.healthinsurance;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class HealthInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements HealthInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008社会保険事業所の登録_健康保険料率一覧.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM008-社会保険事業所の登録_健康保険料率一覧";
    private static final int ROW_IN_PAGE = 49;
    /*private static final int LINE_START_PAGE2 = 49;*/

    @Override
    public void generate(FileGeneratorContext generatorContext, HealthInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.setHeader(0, exportData.getCompanyName());
            reportContext.setHeader(2, "&D　&T\n&P#{PAGE}");
            Worksheet firstSheet = worksheets.get(0);
            int pageHealthyData = exportData.getHealthMonth().size() / ROW_IN_PAGE;
            int pageBonusData = exportData.getBonusHealth().size() / ROW_IN_PAGE;
            createTable(firstSheet, pageHealthyData, pageBonusData);
            printDataHealthy(firstSheet, exportData.getHealthMonth());
            firstSheet.setName(TextResource.localize("QMM008_212"));
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable(Worksheet worksheet,int pageMonth, int pageBonus){
        Cells cells = worksheet.getCells();
        int indexMonth = ROW_IN_PAGE - 1;
        int indexBonus = (ROW_IN_PAGE - 1) * (pageMonth + 1);
        for(int i = 0 ; i< pageBonus; i++) {
                try {
                    cells.copyRows(cells, ROW_IN_PAGE, indexBonus , ROW_IN_PAGE);
                    indexBonus = indexBonus + ROW_IN_PAGE;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        if( pageBonus == 0 && pageMonth != 0) {
            try {
                cells.copyRows(cells, ROW_IN_PAGE, indexBonus + 1 , ROW_IN_PAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(pageMonth > 0){
            cells.deleteRows(ROW_IN_PAGE, ROW_IN_PAGE);
        }
        for(int i = 0 ; i< pageMonth; i++) {
            try {
                cells.copyRows(cells, 0, indexMonth , ROW_IN_PAGE);
                indexMonth = indexMonth + ROW_IN_PAGE - 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void printDataHealthy(Worksheet worksheet, List<Object[]> data) {
        Cells cells = worksheet.getCells();
        int numColumn = 12;
        int rowStart = 3;
        int columnStart = 1;
        fillData(cells, data, numColumn, rowStart, columnStart);
    }

    private void fillData(Cells cells, List<Object[]> data, int numColumn, int startRow, int startColumn) {
        try {
            /*for (int i = 0; i < data.size(); i++) {
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    cells.get(i + startRow, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                }
            }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
