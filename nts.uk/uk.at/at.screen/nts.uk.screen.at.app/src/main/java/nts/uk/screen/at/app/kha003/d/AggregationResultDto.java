package nts.uk.screen.at.app.kha003.d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableFormat;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableOutputContent;

@Getter
@AllArgsConstructor
public class AggregationResultDto {
    private ManHourSummaryTableFormat summaryTableFormat;
    private ManHourSummaryTableOutputContent outputContent;
}
