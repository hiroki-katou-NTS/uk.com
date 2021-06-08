package nts.uk.file.at.infra.holidayconfirmationtable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.holidayconfirmationtable.OutputTraceConfirmTableDataSource;
import nts.uk.file.at.app.export.holidayconfirmationtable.OutputTraceConfirmTableReportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;


@Stateless
public class OutputTraceConfirmTableReportGeneratorImpl extends AsposeCellsReportGenerator
        implements OutputTraceConfirmTableReportGenerator {
    @Override
    public void generate(FileGeneratorContext generatorContext, OutputTraceConfirmTableDataSource dataSource) {

    }
}
