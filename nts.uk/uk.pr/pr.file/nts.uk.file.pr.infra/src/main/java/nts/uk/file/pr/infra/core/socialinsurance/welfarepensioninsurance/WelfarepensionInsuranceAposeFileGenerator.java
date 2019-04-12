package nts.uk.file.pr.infra.core.socialinsurance.welfarepensioninsurance;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class WelfarepensionInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements WelfarepensionInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008社会保険事業所の登録_厚生年金保険料率一覧.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_厚生年金保険料率一覧";
    private static final int ROW_IN_PAGE = 61;
    private static final int RECORD_IN_PAGE = 55;

    @Override
    public void generate(FileGeneratorContext generatorContext, WelfarepensionInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.setHeader(0,exportData.getCompanyName());
            String time = GeneralDateTime.now().toString();
            reportContext.setHeader(2,  time  + "\n page &P");
            Worksheet firstSheet = worksheets.get(0);
            firstSheet.setName(TextResource.localize("QMM008_212"));
            int pageHealthyData = exportData.getWelfarepensionInsuranceEmp().size() / RECORD_IN_PAGE;
            int pageBonusData = exportData.getWelfarepensionInsuranceBonus().size() / RECORD_IN_PAGE;
            createTable(firstSheet, pageHealthyData, pageBonusData);
            printDataWelfarePensionEmp(firstSheet, exportData.getWelfarepensionInsuranceEmp());
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
        int indexBonus = (ROW_IN_PAGE - 1) * (pageMonth + 2);
        try {
            for (int i = 0; i < pageBonus; i++) {
                cells.copyRows(cells, ROW_IN_PAGE - 1, indexBonus, ROW_IN_PAGE);
                indexBonus = indexBonus + ROW_IN_PAGE;
            }
            if (pageBonus == 0 && pageMonth != 0) {
                cells.copyRows(cells, ROW_IN_PAGE, indexBonus + 2, ROW_IN_PAGE);
            }
            if (pageMonth > 0) {
                cells.deleteRows(ROW_IN_PAGE, ROW_IN_PAGE);
            }
            for (int i = 0; i < pageMonth; i++) {
                cells.copyRows(cells, 0, indexMonth, ROW_IN_PAGE);
                indexMonth = indexMonth + ROW_IN_PAGE - 1;
            }
        } catch (Exception e) {

        }
    }

    private void printDataWelfarePensionEmp(Worksheet worksheet, List<Object[]> data) {
        Cells cells = worksheet.getCells();
        int rowStart = 4;
        int numColumn = 13;
        int columnStart = 1;
        fillData(cells, data, numColumn, rowStart, columnStart);
    }

    private void fillData(Cells cells, List<Object[]> data, int numColumn, int startRow, int startColumn) {
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
