package nts.uk.file.at.app.export.manhoursummarytable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.kha003.b.ManHourPeriod;
import nts.uk.screen.at.app.kha003.d.ManHourAggregationResultDto;
import nts.uk.screen.at.app.kha003.exportcsv.Dummy;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ManHourSummaryTableExportExcel extends ExportService<ManHourSummaryTableQuery> {
    @Inject
    private ManHourSummaryTableGenerator manHourSummaryTableGenerator;

    @Override
    protected void handle(ExportServiceContext<ManHourSummaryTableQuery> exportServiceContext) {
        val query = exportServiceContext.getQuery();
        val generatorContext = exportServiceContext.getGeneratorContext();

        // Fake data
        ManHourSummaryExportData data = new ManHourSummaryExportData(
                Dummy.SummaryTableFormat.create(),
                Dummy.SummaryTableOutputContent.create(),
                new ManHourPeriod(
                        0,
                        "2021/06/01",
                        "2021/06/03",
                        "2021/06",
                        "2021/06"
                )
        );

        this.manHourSummaryTableGenerator.generate(generatorContext, data);
    }
}
