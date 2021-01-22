package nts.uk.file.at.infra.arbitraryperiodsummarytable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.arbitraryperiodsummarytable.ArbitraryPeriodSummaryTableGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AposeArbitraryPeriodSummaryTableGenerator extends AsposeCellsReportGenerator implements ArbitraryPeriodSummaryTableGenerator {
    @Override
    public void generate(FileGeneratorContext generatorContext, Object dataSource) {

    }
}
