package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;


import java.util.List;

@Value
@AllArgsConstructor
/**
 * 年月に対応する期間
 */
public class PeriodCorrespondingYm {
    private YearMonth ym;
    private List<DatePeriod> periodList;
}
