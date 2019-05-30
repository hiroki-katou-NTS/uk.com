package nts.uk.file.pr.infra.core.socialinsurance.socialinsuranceoffice;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Stateless
public class SocialInsuranceofficeAposeFileGenerator extends AsposeCellsReportGenerator implements SocialInsuranceOfficeFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008_SOCIAL_INS_OFFICE.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_社会保険事業所一覧_";
    private static final int NUM_ROW = 9;

    @Override
    public void generate(FileGeneratorContext generatorContext, SocialInsuranceOfficeExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            printDataSocialOffice(worksheets, exportData.getData(), exportData.getCompanyName());
            if(exportData.getData().size() > 0) {
                worksheets.removeAt(0);
            }
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + "社会保険事業所の登録");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/dd  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataSocialOffice(WorksheetCollection worksheets, List<Object[]> data, String companyName) {
        try {
            int startIndex = 1;
            String sheetName = "SocialOffice";
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, companyName);
            Cells cells = worksheet.getCells();
            // Main Data
            for (int page = 0; page < data.size(); page += 3) {
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + page/3);
                worksheet = worksheets.get(sheetName + page/3);
                settingPage(worksheet, companyName);
            }
            for (int i = 0; i < data.size(); i++) {
                if (i % 3 == 0) {
                    Worksheet sheet = worksheets.get(sheetName + i/3);
                    cells = sheet.getCells();
                    startIndex = 1;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < NUM_ROW; j++) {
                    if (j == 0) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[0], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[1], ""));
                    }
                    if (j == 1) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[2], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[3], ""));
                    }
                    if (j == 2) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[4], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[5], ""));
                    }
                    if (j == 3) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[6], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[7], ""));
                    }
                    if (j == 4) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[8], ""));
                        cells.get(startIndex + j, 13).setValue(Objects.toString(dataRow[9], ""));
                    }
                    if (j == 5) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[10], ""));
                        cells.get(startIndex + j, 5).setValue(getOfficeNumber(dataRow[11] == null? "" :
                                dataRow[11].toString(),  dataRow[12] == null ? "" : dataRow[12].toString()));
                        cells.get(startIndex + j, 8).setValue(Objects.toString(dataRow[13], ""));
                        cells.get(startIndex + j, 10).setValue(Objects.toString(dataRow[14], ""));
                        cells.get(startIndex + j, 11).setValue(Objects.toString(dataRow[15], ""));
                        cells.get(startIndex + j, 13).setValue(Objects.toString(dataRow[16], ""));
                    }
                    if (j == 6) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[17], ""));
                        cells.get(startIndex + j, 5).setValue(getOfficeNumber(dataRow[18] == null ? "" :
                                dataRow[18].toString(),  dataRow[19] == null ? "" : dataRow[19].toString()));
                        cells.get(startIndex + j, 8).setValue(Objects.toString(dataRow[20], ""));
                        cells.get(startIndex + j, 10).setValue(Objects.toString(dataRow[21], ""));
                        cells.get(startIndex + j, 11).setValue(Objects.toString(dataRow[22], ""));
                    }
                    if (j == 7) {
                        cells.get(startIndex + j, 1).setValue(dataRow[23] != null ? formatString(dataRow[23].toString()) : "");
                    }
                }
                startIndex = startIndex + NUM_ROW;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatString(String s){
        return s.replaceAll("\n", " ");
    }

    private String getOfficeNumber(String value1, String value2){
        return value1 + "  " + value2;
    }
}
