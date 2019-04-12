package nts.uk.file.pr.infra.core.socialinsurance.socialinsuranceoffice;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Stateless
public class SocialInsuranceofficeAposeFileGenerator extends AsposeCellsReportGenerator implements SocialInsuranceOfficeFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008社会保険事業所の登録_社会保険事業所一覧.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_社会保険事業所一覧";
    private static final int NUM_ROW = 9;
    private static final int NUM_LINE = 28;
    private static final String sheetName = "出力イメージ";

    @Override
    public void generate(FileGeneratorContext generatorContext, SocialInsuranceOfficeExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.setHeader(0,exportData.getCompanyName());
            String time = GeneralDateTime.now().toString();
            reportContext.setHeader(2,  time  + "\n page &P");
            Worksheet firstSheet = worksheets.get(0);
            firstSheet.setName(sheetName);
            printDataSocialOffice(firstSheet, exportData.getData());
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void printDataSocialOffice(Worksheet worksheet, List<Object[]> data) {
        try {
            Cells cells = worksheet.getCells();
            int startIndex = 1;
            int copyToRow = NUM_LINE - 1;
            // Main Data
            for (int page = 3; page < data.size(); page += 3) {
                cells.copyRows(cells, 0, copyToRow, NUM_LINE);
                copyToRow = copyToRow + NUM_LINE - 1;
            }
            for (int i = 0; i < data.size(); i++) {
                if (i % 3 == 0 && i > 0) {
                    startIndex++;
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
                        cells.get(startIndex + j, 9).setValue(Objects.toString(dataRow[9], ""));
                    }
                    if (j == 5) {
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[10], ""));
                        cells.get(startIndex + j, 5).setValue(getOfficeNumber(dataRow[11] == null? "" :
                                dataRow[11].toString(),  dataRow[12] == null? "" : dataRow[12].toString()));
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
                        cells.get(startIndex + j, 1).setValue(Objects.toString(dataRow[23], ""));
                    }
                }
                startIndex = startIndex + NUM_ROW;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getOfficeNumber(String value1, String value2){
        return value1 + "  " + value2;
    }
}
