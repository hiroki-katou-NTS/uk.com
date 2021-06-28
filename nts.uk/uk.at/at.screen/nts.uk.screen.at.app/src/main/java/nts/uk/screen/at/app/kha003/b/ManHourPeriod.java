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
public class ManHourPeriod {
    /**
     * 0: DATE
     * 1 : YEAR_MONTH
     */
    private int totalUnit;
    // If totalUnit == DATE => yearMonthPeriod = null OR if totalUnit == YEAR_MONTH => datePeriod = null
    private DatePeriod datePeriod;
    private YearMonthPeriod yearMonthPeriod;
}
