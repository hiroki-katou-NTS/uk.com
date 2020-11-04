package nts.uk.ctx.at.function.dom.commonform;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;


/**
 * 月別実績取得の為に年月日から適切な年月に変換する
 */
@Stateless
public class GetSuitableDateByClosureDateUtility {
    public static YearMonthPeriod getByClosureDate(DatePeriod datePeriod, int closureDay) {
        YearMonth startYearMonth = YearMonth.of(datePeriod.start().year(), datePeriod.start().month());
        YearMonth endYearMonth = YearMonth.of(datePeriod.end().year(), datePeriod.end().month());
        // if start date greater than closure Date then get next start month
        if (datePeriod.start().day() > closureDay) {
            startYearMonth = startYearMonth.addMonths(1);
        }

        // if end date greater than closure Date then get next end month
        if (datePeriod.end().day() > closureDay) {
            endYearMonth = endYearMonth.addMonths(1);
        }

        return new YearMonthPeriod(startYearMonth, endYearMonth);
    }
}
