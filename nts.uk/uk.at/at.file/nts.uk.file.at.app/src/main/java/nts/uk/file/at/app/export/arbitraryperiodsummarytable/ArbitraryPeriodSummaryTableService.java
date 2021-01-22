package nts.uk.file.at.app.export.arbitraryperiodsummarytable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ArbitraryPeriodSummaryTableService extends ExportService<ArbitraryPeriodSummaryTableFileQuery> {

    @Inject
    private ArbitraryPeriodSummaryTableGenerator periodSummaryTableGenerator;

    @Override
    protected void handle(ExportServiceContext<ArbitraryPeriodSummaryTableFileQuery> exportServiceContext) {
        val generatorContext = exportServiceContext.getGeneratorContext();

        periodSummaryTableGenerator.generate(generatorContext, null);
    }
}
