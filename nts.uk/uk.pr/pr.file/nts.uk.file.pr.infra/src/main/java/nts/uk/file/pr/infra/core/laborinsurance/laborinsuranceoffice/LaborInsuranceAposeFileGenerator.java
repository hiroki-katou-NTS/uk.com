package nts.uk.file.pr.infra.core.laborinsurance.laborinsuranceoffice;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;

@Stateless
public class LaborInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements LaborInsuranceFileGenerator {
    private static final String TEMPLATE_FILE = "report/TEMPLATE_QMM010.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final int NUM_ROW = 8;


    @Override
    public void generate(FileGeneratorContext generatorContext, LaborInsuranceExportData exportData) {

        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet normalSheet = worksheets.get(0);
            reportContext.setHeader(0,exportData.getCompanyName());
            printData(normalSheet, exportData.getData(),"出力イメージ");
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    "労働保険事業所の登録(デザイン改)" + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void printData(Worksheet worksheet, List<Object[]> data,
                           String sheetName) {
        try {
            worksheet.setName(sheetName);
            Cells cells = worksheet.getCells();
            int startIndex = 1;
            HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
            // Main Data
            for (int i = 0; i < data.size(); i++) {
                if( i % 2 == 0 && i > 2) {
                    pageBreaks.add(startIndex);
                }
                Object[] dataRow = data.get(i);
                cells.copyRows(cells, startIndex , startIndex + NUM_ROW, NUM_ROW);
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
                        cells.get(startIndex + j, 2).setValue(Objects.toString(dataRow[11], ""));
                    }
                    if(j == 5) {
                        cells.get(startIndex + j, 4).setValue(Objects.toString(dataRow[8], ""));
                        cells.get(startIndex + j, 5).setValue(Objects.toString(dataRow[9], ""));
                        cells.get(startIndex + j, 6).setValue(Objects.toString(dataRow[10], ""));
                    }
                    if(j == 6) {
                        cells.get(startIndex + j, 1).setValue(Objects.toString(dataRow[12], ""));
                    }
                }
                startIndex += NUM_ROW;
            }
            cells.deleteRows(startIndex, NUM_ROW);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
