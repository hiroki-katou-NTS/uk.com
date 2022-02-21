package nts.uk.file.at.app.export.scheduledailytable;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.calendar.period.DatePeriod;

public interface ScheduleDailyTableExportGenerator {
    void generate(FileGeneratorContext context, ScheduleDailyTableDataSource dataSource, DatePeriod period, boolean displayBothWhenDiffOnly);
}
