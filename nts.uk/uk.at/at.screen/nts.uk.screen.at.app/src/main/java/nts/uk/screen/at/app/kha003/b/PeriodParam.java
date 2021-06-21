package nts.uk.screen.at.app.kha003.b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Getter
public class PeriodParam {
    private String startDate;
    private String endDate;
    private YearMonth yearMonthStart;
    private YearMonth yearMonthEnd;

    public DatePeriod getDatePeriod() {
        return new DatePeriod(
                !StringUtils.isEmpty(this.startDate) ? GeneralDate.fromString(this.startDate, "yyyy/MM/dd") : GeneralDate.min(),
                !StringUtils.isEmpty(this.endDate) ? GeneralDate.fromString(this.endDate, "yyyy/MM/dd") : GeneralDate.min());
    }

    public YearMonthPeriod getYmPeriod() {
        return new YearMonthPeriod(yearMonthStart, yearMonthEnd);
    }
}
