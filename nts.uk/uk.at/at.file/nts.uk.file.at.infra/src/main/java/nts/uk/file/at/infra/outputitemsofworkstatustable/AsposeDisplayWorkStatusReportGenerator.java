package nts.uk.file.at.infra.outputitemsofworkstatustable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.outputitemsofworkstatustable.DisplayContentReportData;
import nts.uk.file.at.app.export.outputitemsofworkstatustable.DisplayWorkStatusReportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeDisplayWorkStatusReportGenerator extends AsposeCellsReportGenerator implements DisplayWorkStatusReportGenerator {

    @Override
    public void generate(FileGeneratorContext generatorContext, DisplayContentReportData dataSource) {

    }
}
