package nts.uk.screen.at.app.kha003.d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.screen.at.app.kha003.ManHourSummaryTableFormatDto;
import nts.uk.screen.at.app.kha003.exportcsv.ManHourSummaryTableOutputContentDto;

@Getter
@AllArgsConstructor
public class ManHourAggregationResultDto {
    private ManHourSummaryTableFormatDto summaryTableFormat;
    private ManHourSummaryTableOutputContentDto outputContent;
}
