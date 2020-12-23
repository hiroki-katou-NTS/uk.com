package nts.uk.ctx.at.function.dom.alarmworkplace.service.aggregateprocess;

import lombok.*;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * カテゴリ別期間
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class PeriodByAlarmCategory {
    int category;
    DatePeriod period;
    YearMonthPeriod ymPeriod;
    YearMonth yearMonth;
}
