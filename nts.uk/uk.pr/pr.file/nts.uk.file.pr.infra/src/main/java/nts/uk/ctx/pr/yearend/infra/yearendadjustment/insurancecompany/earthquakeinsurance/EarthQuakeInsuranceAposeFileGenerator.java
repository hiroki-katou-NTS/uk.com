package nts.uk.ctx.pr.yearend.infra.yearendadjustment.insurancecompany.earthquakeinsurance;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance.EarthQuakeInsuranceExportData;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance.EarthQuakeInsuranceFileGenerator;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceExportData;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class EarthQuakeInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements EarthQuakeInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM031_EARTH_QUAKEI_INS.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final String FILE_NAME = "QMM031保険会社の登録_地震保険_";
    private static final int RECORD_IN_PAGE = 74;
    private static final int RECORD_IN_TABLE = 37;

    @Override
    public void generate(FileGeneratorContext generatorContext, EarthQuakeInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            int page = exportData.getEarthQuakeInsurances().size() == RECORD_IN_PAGE ? 1 : exportData.getEarthQuakeInsurances().size() / RECORD_IN_PAGE + 1;
            createTable(worksheets, page, exportData.getCompanyName());
            printDataHealthy(worksheets, exportData.getEarthQuakeInsurances());
            worksheets.removeAt(0);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable(WorksheetCollection worksheets,int page, String companyName) throws Exception {
        String sheetName = "earth_quakei_insurance";
        Worksheet worksheet = worksheets.get(0);
        settingPage(worksheet, companyName);
        for(int i = 0 ; i< page; i++) {
            worksheets.get(worksheets.addCopy(0)).setName(sheetName + i);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 "+ "保険会社の登録　地震保険");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataHealthy(WorksheetCollection worksheets, List<Object[]> data) {
        String sheetName = "earth_quakei_insurance";
        int numColumn = 2;
        fillData(worksheets, data, numColumn, sheetName);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, int numColumn, String sheetName) {
        try {
            int rowStart = 3;
            int startColumn = 1;
            Worksheet sheet = worksheets.get(0);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % RECORD_IN_PAGE == 0) {
                    sheet = worksheets.get(sheetName + i/RECORD_IN_PAGE);
                    cells = sheet.getCells();
                    rowStart = 3;
                    startColumn = 1;
                }
                if(i > 0 && i % RECORD_IN_TABLE == 0 && i % RECORD_IN_PAGE != 0) {
                    startColumn = 4;
                    rowStart = 3;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                }
                rowStart++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
