package nts.uk.ctx.pr.core.infra.generator.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export.StatementLayoutSetExportData;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export.StatementLayoutFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StatementLayoutAsposeFileGenerator extends AsposeCellsReportGenerator implements StatementLayoutFileGenerator {

    @Override
    public void generate(List<StatementLayoutSetExportData> exportData) {

    }
}
