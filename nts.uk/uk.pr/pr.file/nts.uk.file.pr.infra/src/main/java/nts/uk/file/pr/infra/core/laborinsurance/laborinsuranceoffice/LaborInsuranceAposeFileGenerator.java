package nts.uk.file.pr.infra.core.laborinsurance.laborinsuranceoffice;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;

@Stateless
public class LaborInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements LaborInsuranceFileGenerator {
    private static final String TEMPLATE_FILE = "report/QMM010労働保険事業所の登録.xlsx";
    private static final String FILE_NAME = "QMM010労働保険事業所の登録_";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final int NUM_ROW = 8;
    private static final int NUM_LINE = 19;


    @Override
    public void generate(FileGeneratorContext generatorContext, LaborInsuranceExportData exportData) {

        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet normalSheet = worksheets.get(0);
            reportContext.setHeader(0,exportData.getCompanyName());
            //reportContext.setHeader(2, GeneralDateTime.now().toString());
            String time = GeneralDateTime.now().toString();

            reportContext.setHeader(2,  time  + "\n page &P");
            printData(normalSheet, exportData.getData(), TextResource.localize("QMM010_36"));
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String setPhoneOffice(Object[] obj){
        StringBuilder phone = new StringBuilder();
        phone.append(obj[12] != null ? obj[12].toString() : "");
        phone.append(obj[13] != null ? "-" + obj[13].toString() : "");
        phone.append(obj[14] != null ? "-" + obj[14].toString() : "");
        return phone.toString();
    }

    private void printData(Worksheet worksheet, List<Object[]> data,
                           String sheetName) {
        try {
            worksheet.setName(sheetName);
            Cells cells = worksheet.getCells();
            int startIndex = 1;
            int copyToRow = NUM_LINE - 1;
            // Main Data
            for(int page = 2; page < data.size() ; page += 2) {
                cells.copyRows(cells, 0, copyToRow , NUM_LINE);
                copyToRow = copyToRow + NUM_LINE - 1;
            }
            for (int i = 0; i < data.size(); i++) {
                if( i % 2 == 0) {
                    if(i>0) {
                        startIndex = startIndex + 2;
                    }
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
                        cells.get(startIndex + j, 1).setValue(Objects.toString(dataRow[15], ""));
                    }
                }
                startIndex = startIndex + NUM_ROW ;
            }
           // cells.deleteRows(startIndex - 1, NUM_ROW + 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
