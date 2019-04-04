package nts.uk.file.pr.infra.core.wageprovision.unitpricename;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPerUnitFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPerUnitSetExportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SalaryPerUnitAsposeFileGenerator extends AsposeCellsReportGenerator
        implements SalaryPerUnitFileGenerator {

    private static final String TEMPLATE_FILE = "report/マスタリスト設計書-QMM013-単価名の登録(デザイン改).xlsx";

    private static final String REPORT_FILE_NAME = "マスタリスト設計書-QMM013-単価名の登録(デザイン改).xlsx";

    private static final int COLUMN_START = 1;

    @Override
    public void generate(FileGeneratorContext fileContext, List<SalaryPerUnitSetExportData> exportData) {

        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            int rowStart = 3;
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            ws.setName("phucnx");
            Cells cells = ws.getCells();
            // fill data
            for (int i = 0; i < exportData.size(); i++) {
                SalaryPerUnitSetExportData e = exportData.get(i);
                cells.get(rowStart, COLUMN_START).setValue(e.getCode());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getName());
                cells.get(rowStart, COLUMN_START + 2).setValue(I18NText.getText(e.getAbolition().nameId));
                cells.get(rowStart, COLUMN_START + 3).setValue(e.getEveryoneEqualSet().isPresent() ? e.getEveryoneEqualSet().get().getTargetClassification().nameId : "-");
                cells.get(rowStart, COLUMN_START + 4).setValue(e.getPerSalaryContractType().isPresent() ? e.getPerSalaryContractType().get().getMonthlySalary().nameId : "-");
                cells.get(rowStart, COLUMN_START + 5).setValue(e.getPerSalaryContractType().isPresent() ? e.getPerSalaryContractType().get().getMonthlySalaryPerday().nameId : "-");
                cells.get(rowStart, COLUMN_START + 6).setValue(e.getPerSalaryContractType().isPresent() ? e.getPerSalaryContractType().get().getDayPayee().nameId : "-");
                cells.get(rowStart, COLUMN_START + 7).setValue(e.getPerSalaryContractType().isPresent() ? e.getPerSalaryContractType().get().getHourlyPay().nameId : "-");
                cells.get(rowStart, COLUMN_START + 8).setValue(e.getUnitPriceType().nameId);
                rowStart++;
            }
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
