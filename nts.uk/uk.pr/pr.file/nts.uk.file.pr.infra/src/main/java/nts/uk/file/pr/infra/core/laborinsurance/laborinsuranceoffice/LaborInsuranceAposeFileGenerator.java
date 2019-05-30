package nts.uk.file.pr.infra.core.laborinsurance.laborinsuranceoffice;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Stateless
public class LaborInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements LaborInsuranceFileGenerator {
    private static final String TEMPLATE_FILE = "report/QMM010.xlsx";
    private static final String FILE_NAME = "QMM010労働保険事業所の登録_";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final int NUM_ROW = 8;


    @Override
    public void generate(FileGeneratorContext generatorContext, LaborInsuranceExportData exportData) {

        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            printData(worksheets, exportData.getData(), exportData.getCompanyName());
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

    private String setPhoneOffice(Object[] obj){
        StringBuilder phone = new StringBuilder();
        phone.append(obj[12] != null ? obj[12].toString() : "");
        phone.append("-");
        phone.append(obj[13] != null ? obj[13].toString() : "");
        phone.append("-");
        phone.append(obj[14] != null ? obj[14].toString() : "");
        return phone.toString();
    }

    private void settingPage(Worksheet worksheet, String companyName){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + "労働保険事業所の登録");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/dd  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printData(WorksheetCollection worksheets, List<Object[]> data,
                           String companyName) {
        try {
            String sheetName = "Labor";
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, companyName);
            Cells cells = worksheet.getCells();
            int startIndex = 1;
            for(int page = 0; page < data.size() ; page += 2) {
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + page/2);
                worksheet = worksheets.get(sheetName + page/2);
                settingPage(worksheet, companyName);
            }
            for (int i = 0; i < data.size(); i++) {
                if( i % 2 == 0) {
                    Worksheet sheet = worksheets.get(sheetName + i/2);
                    cells = sheet.getCells();
                    startIndex = 1;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < NUM_ROW; j++) {
                    if( j == 0) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[0], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[1], ""));
                        cells.get(startIndex + j, 7).setValue(Objects.toString(dataRow[2], ""));
                    }
                    if(j == 1 ) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[3], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[4], ""));
                        cells.get(startIndex + j, 7).setValue(Objects.toString(dataRow[5], ""));
                    }
                    if(j == 2) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[6], ""));
                    }
                    if(j == 3) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[7], ""));
                    }
                    if(j == 4) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[8], ""));
                    }
                    if(j == 5) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[9], ""));
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[10], ""));
                        cells.get(startIndex + j, 5).setValue(Objects.toString(dataRow[11], ""));
                        cells.get(startIndex + j, 6).setValue(setPhoneOffice(dataRow));
                    }
                    if(j == 6) {
                        cells.get(startIndex + j, 1).setValue(dataRow[15] == null ? "" : formatString(dataRow[15].toString()) );
                    }
                }
                startIndex = startIndex + NUM_ROW ;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String formatString(String s){
        return s.replaceAll("\n", " ");
    }
}
