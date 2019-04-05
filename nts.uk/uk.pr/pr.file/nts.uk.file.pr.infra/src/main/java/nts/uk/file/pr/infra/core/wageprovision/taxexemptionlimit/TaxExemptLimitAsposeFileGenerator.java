package nts.uk.file.pr.infra.core.wageprovision.taxexemptionlimit;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit.TaxExemptLimitFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit.TaxExemptLimitSetExportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TaxExemptLimitAsposeFileGenerator extends AsposeCellsReportGenerator
        implements TaxExemptLimitFileGenerator {

    private static final String TEMPLATE_FILE = "report/マスタリスト設計書-QMM023-非課税限度額の登録(デザイン改).xlsx";

    private static final String REPORT_FILE_NAME = "マスタリスト設計書-QMM023-非課税限度額の登録(デザイン改).xlsx";

    private static final int COLUMN_START = 1;

    @Override
    public void generate(FileGeneratorContext fileContext, List<TaxExemptLimitSetExportData> exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            int rowStart = 2;
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            ws.setName("phucnx");
            Cells cells = ws.getCells();
            // fill data
            for(int i = 0 ; i < exportData.size() ; i++){
                TaxExemptLimitSetExportData e = exportData.get(i);
                cells.get(rowStart,COLUMN_START).setValue(e.getTaxFreeAmountCode());
                cells.get(rowStart,COLUMN_START+1).setValue(e.getTaxExemptionName());
                cells.get(rowStart,COLUMN_START+2).setValue(e.getTaxExemption()+"¥");
                rowStart++;
            }
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
