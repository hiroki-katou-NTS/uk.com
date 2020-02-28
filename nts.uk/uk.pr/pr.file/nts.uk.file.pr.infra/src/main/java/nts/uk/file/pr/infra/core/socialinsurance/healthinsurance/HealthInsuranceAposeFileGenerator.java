package nts.uk.file.pr.infra.core.socialinsurance.healthinsurance;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class HealthInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements HealthInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008_EHI_RATE.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_健康保険料率一覧_";
    private static final int ROW_IN_PAGE = 49;
    private static final int RECORD_IN_PAGE = 45;

    @Override
    public void generate(FileGeneratorContext generatorContext, HealthInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            int pageHealthyData = exportData.getHealthMonth().size() == RECORD_IN_PAGE ? 1 : exportData.getHealthMonth().size() / RECORD_IN_PAGE + 1;
            int pageBonusData = exportData.getBonusHealth().size() == RECORD_IN_PAGE ? 1 : exportData.getBonusHealth().size() / RECORD_IN_PAGE + 1;
            createTableHealthy(worksheets, pageHealthyData, exportData.getStartDate(), exportData.getCompanyName());
            createTableBonus(worksheets, pageBonusData, exportData.getStartDate(), exportData.getCompanyName());
            printDataHealthy(worksheets, exportData.getHealthMonth());
            printDataBonus(worksheets, exportData.getBonusHealth());
            worksheets.removeAt(0);
            worksheets.removeAt("healthy");
            worksheets.removeAt("bonus");
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String convertYearMonth(Integer startYearMonth){
        return startYearMonth.toString().substring(0,4) + "/" + startYearMonth.toString().substring(4,6);
    }

    private void createTableHealthy(WorksheetCollection worksheets,int pageMonth, int startDate, String companyName) throws Exception {
        String sheetName = "healthy";
        worksheets.get(worksheets.addCopy(0)).setName(sheetName);
        Worksheet worksheet = worksheets.get(sheetName);
        worksheet.getCells().deleteRows(ROW_IN_PAGE - 1, ROW_IN_PAGE);
        settingPage(worksheet, startDate, companyName);
        for(int i = 0 ; i< pageMonth; i++) {
            worksheets.get(worksheets.addCopy(sheetName)).setName(sheetName + i);
        }
    }

    private void createTableBonus(WorksheetCollection worksheets,int pageBonus, int startDate, String companyName) throws Exception {
        String sheetName = "bonus";
        worksheets.get(worksheets.addCopy(0)).setName(sheetName);
        Worksheet worksheet = worksheets.get(sheetName);
        worksheet.getCells().deleteRows(0, ROW_IN_PAGE - 1);
        settingPage(worksheet, startDate, companyName);
        for(int i = 0 ; i< pageBonus; i++) {
            worksheets.get(worksheets.addCopy(sheetName)).setName(sheetName + i);
        }
    }

    private void settingPage(Worksheet worksheet, int startDate, String companyName){
        worksheet.getCells().get(0,1).setValue("対象年月：　"+ convertYearMonth(startDate));
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 "+ "健康保険料率一覧");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataHealthy(WorksheetCollection worksheets, List<Object[]> data) {
        String sheetName = "healthy";
        int numColumn = 12;
        int columnStart = 1;
        fillData(worksheets, data, numColumn, columnStart, sheetName);
    }

    private void printDataBonus(WorksheetCollection worksheets, List<Object[]> data) {
        String sheetName = "bonus";
        int numColumn = 12;
        int columnStart = 1;
        fillData(worksheets, data, numColumn, columnStart, sheetName);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, int numColumn, int startColumn, String sheetName) {
        try {
            int rowStart = 3;
            Worksheet sheet = worksheets.get(sheetName);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % RECORD_IN_PAGE == 0) {
                    sheet = worksheets.get(sheetName + i/RECORD_IN_PAGE);
                    cells = sheet.getCells();
                    rowStart = 3;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    if(j == 6 || j == 11) {
                        cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null
                                ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), InsurancePremiumFractionClassification.class).nameId) : "");
                    } else {
                        cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                    }
                }
                rowStart++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
