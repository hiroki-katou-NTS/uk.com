package nts.uk.screen.at.app.kha003.b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.TotalUnit;
import nts.uk.ctx.at.shared.dom.common.Year;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    public List<GeneralDate> getDateList() {
        if (totalUnit == TotalUnit.YEAR_MONTH.value) return new ArrayList<>();
        return datePeriod.datesBetween();
    }

    public List<YearMonth> getYearMonthList() {
        if (totalUnit == TotalUnit.DATE.value) return new ArrayList<>();
        return yearMonthPeriod.yearMonthsBetween();
    }
}
