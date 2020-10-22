package nts.uk.ctx.at.function.dom.commonform;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.Optional;


/**
 * 月別実績取得の為に年月日から適切な年月に変換する
 */
@Stateless
public class GetSuitableDateByClosureDateDomainService {
    public static YearMonthPeriod getByClosureDate(
            Integer startYear, Integer startMonth, Optional<Integer> startDate,
            Integer endYear, Integer endMonth, Optional<Integer> endDate,
            int closureDay) {
        YearMonth startYearMonth = YearMonth.of(startYear, startMonth);
        YearMonth endYearMonth = YearMonth.of(endYear, endMonth);
        // if start date greater than closure Date then get next start month
        if (startDate.isPresent() && startDate.get() > closureDay) {
            startYearMonth = startYearMonth.addMonths(1);
        }

        // if end date greater than closure Date then get next end month
        if (endDate.isPresent() && endDate.get() > closureDay) {
            endYearMonth = endYearMonth.addMonths(1);
        }

        return new YearMonthPeriod(startYearMonth, endYearMonth);
    }
}
