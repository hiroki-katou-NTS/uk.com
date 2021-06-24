package nts.uk.screen.at.app.kha003.d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.MasterNameInformation;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;

import java.util.List;

@AllArgsConstructor
@Getter
public class AggregationResultQuery {
    private String code;
    private MasterNameInformation masterNameInfo;
    private List<WorkDetailData> workDetailList;
    private List<GeneralDate> dateList;
    private List<YearMonth> yearMonthList;
}
