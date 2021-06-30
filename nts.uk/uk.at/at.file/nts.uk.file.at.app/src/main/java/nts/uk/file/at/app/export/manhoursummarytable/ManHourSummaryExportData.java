package nts.uk.file.at.app.export.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableFormat;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableOutputContent;
import nts.uk.screen.at.app.kha003.b.ManHourPeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ManHourSummaryExportData {
    private ManHourSummaryTableFormat summaryTableFormat;
    private ManHourSummaryTableOutputContent outputContent;
    private ManHourPeriod period;
}
