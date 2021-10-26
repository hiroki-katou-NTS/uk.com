package nts.uk.file.at.app.export.scheduledailytable;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScheduleDailyTableExportService extends ExportService<ScheduleDailyTableExportQuery> {
    @Inject
    private CreateScheduleDailyTableFileQuery fileQuery;

    @Inject
    private ScheduleDailyTableExportGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<ScheduleDailyTableExportQuery> exportServiceContext) {
        ScheduleDailyTableExportQuery query = exportServiceContext.getQuery();
        DatePeriod period = new DatePeriod(query.getPeriodStart(), query.getPeriodEnd());
        exportGenerator.generate(
                exportServiceContext.getGeneratorContext(),
                fileQuery.get(
                        query.getWorkplaceGroupIds(),
                        period,
                        query.getOutputItemCode(),
                        query.getPrintTarget()
                ),
                period,
                query.isDisplayBothWhenDiffOnly()
        );
    }
}
