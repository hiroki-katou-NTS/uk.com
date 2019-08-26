package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.LossNotificationInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;

@Stateless
public class NotificationOfLossInsPDFAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsFileGenerator {

    private static final String TEMPLATE_FILE = "report/被保険者資格喪失届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "被保険者資格喪失届_帳票テンプレート";
    private static final int A1_1_ROW = 4;
    private static final int A1_1_COLUMN = 2;
    private static final int A1_2_ROW = 6;
    private static final int A1_2_COLUMN = 7;
    private static final int A1_3_ROW = 6;
    private static final int A1_3_COLUMN = 19;
    private static final int A1_4_ROW = 6;
    private static final int A1_4_COLUMN = 35;
    private static final int A1_5_ROW = 11;
    private static final int A1_5_COLUMN = 9;
    private static final int A1_6_ROW = 12;
    private static final int A1_6_COLUMN = 7;
    private static final int A1_7_ROW = 16;
    private static final int A1_7_COLUMN = 7;
    private static final int A1_8_ROW = 20;
    private static final int A1_8_COLUMN = 7;
    private static final int A1_9_ROW = 24;
    private static final int A1_9_COLUMN = 12;
    private static final int A2_1_ROW = 27;
    private static final int A2_1_COLUMN = 13;
    private static final int A2_2_ROW = 27;
    private static final int A2_2_COLUMN = 27;
    private static final int A2_3_ROW = 27;
    private static final int A2_3_COLUMN = 47;
    private static final int A2_4_ROW = 29;
    private static final int A2_4_COLUMN = 27;
    private static final int A2_5_ROW = 29;
    private static final int A2_5_COLUMN = 47;
    private static final int A2_8_ROW = 84;
    private static final int A2_8_COLUMN = 23;
    private static final int A2_9_ROW = 16;
    private static final int A2_9_COLUMN = 12;
    private static final int A2_12_ROW = 25;
    private static final int A2_12_COLUMN = 19;
    private static final int A2_14_ROW = 26;
    private static final int A2_14_COLUMN = 77;
    private static final int A2_20_ROW = 6;
    private static final int A2_20_COLUMN = 7;
    private static final int A2_21_ROW = 6;
    private static final int A2_21_COLUMN = 19;
    private static final int A2_22_ROW = 6;
    private static final int A2_22_COLUMN = 35;
    private static final int A2_24_ROW = 12;
    private static final int A2_24_COLUMN = 7;



    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
            try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
                Workbook workbook = reportContext.getWorkbook();
                reportContext.processDesigner();
                reportContext.saveAsPdf(this.createNewFile(generatorContext,
                        FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    private void fillData(WorksheetCollection worksheets, List<InsLossDataExport> data) {
        try {
            String sheetName = "OTHER_70";
            Worksheet worksheet = worksheets.get(0);
            Cells cells = worksheet.getCells();
            // Main Data
            for (int page = 0; page < data.size(); page += 4) {
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + page/4);
                worksheet = worksheets.get(sheetName + page/4);
            }
            for (int i = 0; i < data.size(); i++) {
                if (i % 4 == 0) {
                    Worksheet sheet = worksheets.get(sheetName + i/4);
                    cells = sheet.getCells();
                }
                InsLossDataExport  dataRow = data.get(i);
                fillDataCompany(cells, dataRow);
                fillDataEmployee(cells, dataRow);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataCompany(Cells cells, InsLossDataExport data){
        cells.get(A1_1_ROW , A1_1_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_2_ROW , A1_2_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_3_ROW , A1_3_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_4_ROW , A1_4_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_5_ROW , A1_5_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_6_ROW , A1_6_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_7_ROW , A1_7_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_8_ROW , A1_8_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A1_9_ROW , A1_9_COLUMN).setValue(Objects.toString("", ""));
    }

    private void fillDataEmployee(Cells cells, InsLossDataExport data){
        cells.get(A2_1_ROW , A2_1_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_2_ROW , A2_2_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_3_ROW , A2_3_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_4_ROW , A2_4_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_5_ROW , A2_5_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_2_ROW , A2_2_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_8_ROW , A2_8_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_9_ROW , A2_9_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_12_ROW , A2_12_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_14_ROW , A2_14_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_20_ROW , A2_20_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_21_ROW , A2_21_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_22_ROW , A2_22_COLUMN).setValue(Objects.toString("", ""));
        cells.get(A2_24_ROW , A2_24_COLUMN).setValue(Objects.toString("", ""));
    }
}
