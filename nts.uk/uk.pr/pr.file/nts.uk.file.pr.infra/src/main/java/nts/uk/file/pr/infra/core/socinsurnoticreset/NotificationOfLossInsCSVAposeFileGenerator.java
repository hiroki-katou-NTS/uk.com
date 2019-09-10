package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.LossNotificationInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsCSVFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class NotificationOfLossInsCSVAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsCSVFileGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";
    private static final String FILE_NAME = "TEMP";
    private static final int ROW_START = 0;
    private static final int CODE = 1;
    private static final int OFFICE_SYMBOL = 2;
    private static final int SERIAL_NUMBER = 3;
    private static final int CREATE_DATE = 4;
    private static final int REP_CODE = 5;
    private static final int OFFICE_IDENT_CD = 1;
    private static final int RESERVE = 1;
    private static final int NUMBER_OFFICE = 2;


    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        val reportContext = this.createEmptyContext(REPORT_ID);
        val workbook = reportContext.getWorkbook();
        val sheet = workbook.getWorksheets().get(0);
        reportContext.processDesigner();
        reportContext.saveAsCSV(this.createNewFile(generatorContext, FILE_NAME + ".CSV"));
    }

    private void fillPensionOffice(List<InsLossDataExport> healthInsLoss, Worksheet worksheet){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsLoss.size(); i++){
            InsLossDataExport data = healthInsLoss.get(i);
            cells.get(startRow, CODE).setValue(data.getHealInsNumber());
            cells.get(startRow, OFFICE_SYMBOL).setValue(data.getHealInsNumber());
            cells.get(startRow, SERIAL_NUMBER).setValue(data.getHealInsNumber());
            cells.get(startRow, CREATE_DATE).setValue(data.getHealInsNumber());
            cells.get(startRow, REP_CODE).setValue(data.getHealInsNumber());
            startRow = startRow + 1;
            cells.get(startRow, OFFICE_IDENT_CD).setValue(data.getHealInsNumber());
            cells.get(startRow, RESERVE).setValue(data.getHealInsNumber());
            cells.get(startRow, NUMBER_OFFICE).setValue(data.getHealInsNumber());
        }
    }

    private String getPreferCode(){
        return "";
    }

}


