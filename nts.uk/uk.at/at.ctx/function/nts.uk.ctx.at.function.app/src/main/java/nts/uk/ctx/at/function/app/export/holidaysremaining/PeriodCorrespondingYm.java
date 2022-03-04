package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 年月に対応する期間
 */
@AllArgsConstructor
@Getter
@Setter
public class PeriodCorrespondingYm {
    private YearMonth ym;
    private DatePeriod datePeriod;
}
