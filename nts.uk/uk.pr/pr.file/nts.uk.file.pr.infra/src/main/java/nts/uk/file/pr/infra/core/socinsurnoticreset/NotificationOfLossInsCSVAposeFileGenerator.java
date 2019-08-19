package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Workbook;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.LossNotificationInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsCSVFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;

@Stateless
public class NotificationOfLossInsCSVAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsCSVFileGenerator {

    private static final String TEMPLATE_FILE = "report/被保険者資格喪失届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "被保険者資格喪失届_帳票テンプレート";

    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
            try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
                Workbook workbook = reportContext.getWorkbook();
                reportContext.processDesigner();
                reportContext.saveAsCSV(this.createNewFile(generatorContext,
                        FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
