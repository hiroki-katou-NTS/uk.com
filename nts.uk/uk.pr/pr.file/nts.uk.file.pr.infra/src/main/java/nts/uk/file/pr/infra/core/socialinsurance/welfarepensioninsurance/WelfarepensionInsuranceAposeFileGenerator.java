package nts.uk.file.pr.infra.core.socialinsurance.welfarepensioninsurance;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceFileGenerator;
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
public class WelfarepensionInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements WelfarepensionInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008_EPI_RATE.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_厚生年金保険料率一覧_";
    private static final int ROW_IN_PAGE = 60;
    private static final int RECORD_IN_PAGE = 55;

    @Override
    public void generate(FileGeneratorContext generatorContext, WelfarepensionInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            int pageHealthyData = exportData.getWelfarepensionInsuranceEmp().size() == RECORD_IN_PAGE ? 1 :exportData.getWelfarepensionInsuranceEmp().size() / RECORD_IN_PAGE + 1;
            int pageBonusData = exportData.getWelfarepensionInsuranceBonus().size() == RECORD_IN_PAGE ? 1 : exportData.getWelfarepensionInsuranceBonus().size() / RECORD_IN_PAGE + 1;
            createTableEmp(worksheets, pageHealthyData, exportData.getStartDate(), exportData.getCompanyName());
            createTableBonus(worksheets, pageBonusData, exportData.getStartDate(), exportData.getCompanyName());
            printDataWelfarePensionEmp(worksheets, exportData.getWelfarepensionInsuranceEmp());
            printDataWelfarePensionBonus(worksheets, exportData.getWelfarepensionInsuranceBonus());
            worksheets.removeAt("sheetEmp");
            worksheets.removeAt("sheetBonus");
            worksheets.removeAt(0);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                   FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void createTableEmp(WorksheetCollection worksheets,int pageMonth, int startDate, String companyName) throws Exception {
        String sheetName = "sheetEmp";
        worksheets.get(worksheets.addCopy(0)).setName(sheetName);
        Worksheet worksheet = worksheets.get(sheetName);
        worksheet.getCells().deleteRows(ROW_IN_PAGE - 1, ROW_IN_PAGE);
        settingPage(worksheet, startDate, companyName);
        for(int i = 0 ; i< pageMonth; i++) {
            worksheets.get(worksheets.addCopy(sheetName)).setName(sheetName+ i);
        }
    }

    private void createTableBonus(WorksheetCollection worksheets,int pageBonus, int startDate, String companyName) throws Exception {
        String sheetName = "sheetBonus";
        worksheets.get(worksheets.addCopy(0)).setName(sheetName);
        Worksheet worksheet = worksheets.get(sheetName);
        worksheet.getCells().deleteRows(0, ROW_IN_PAGE - 1);
        settingPage(worksheet, startDate, companyName);
        for(int i = 0 ; i< pageBonus; i++) {
            worksheets.get(worksheets.addCopy(sheetName)).setName(sheetName+ i);
        }
    }

    private String convertYearMonth(Integer startYearMonth){
         return startYearMonth.toString().substring(0,4) + "/" + startYearMonth.toString().substring(4,6);
    }

    private void settingPage(Worksheet worksheet, int startDate, String companyName){
        worksheet.getCells().get(0,1).setValue("対象年月：　"+ convertYearMonth(startDate));
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1,"&\"ＭＳ ゴシック\"&16" + "厚生年金保険料率一覧");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataWelfarePensionEmp(WorksheetCollection worksheets, List<Object[]> data) {
        int numColumn = 17;
        String sheetName = "sheetEmp";
        fillData(worksheets, data, numColumn, sheetName);
    }

    private void printDataWelfarePensionBonus(WorksheetCollection worksheets, List<Object[]> data) {
        int numColumn = 18;
        String sheetName = "sheetBonus";
        fillData(worksheets, data, numColumn, sheetName);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, int numColumn, String sheetName) {
        try {
            int columnStart = 1;
            int rowStart = 4;
            Worksheet sheet = worksheets.get(sheetName);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % RECORD_IN_PAGE == 0) {
                    sheet = worksheets.get(sheetName + i/RECORD_IN_PAGE);
                    cells = sheet.getCells();
                    rowStart = 4;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    if(j == 2) {
                        cells.get(rowStart, j + columnStart).setValue(dataRow[j] != null ? ((BigDecimal)dataRow[j]).intValue() == 1 ? TextResource.localize("QMM008_54") : TextResource.localize("QMM008_55") : "");
                    }
                    if(j == 9 || j == 16) {
                        cells.get(rowStart, j + columnStart).setValue(dataRow[j] != null
                                ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), InsurancePremiumFractionClassification.class).nameId) : "");
                    }
                    if(j != 2 && j != 9 && j != 16) {
                        cells.get(rowStart, j + columnStart).setValue(dataRow[j] != null ? dataRow[j] : "");
                    }
                    if(j == 4 || j == 5 || j == 7 || j == 8 || j == 11|| j == 12 || j == 14 || j == 15) {
                        cells.get(rowStart, j + columnStart).setValue(dataRow[2] != null && ((BigDecimal)dataRow[2]).intValue() == 1 ? dataRow[j] : "-");
                    }
                }
                rowStart++;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
