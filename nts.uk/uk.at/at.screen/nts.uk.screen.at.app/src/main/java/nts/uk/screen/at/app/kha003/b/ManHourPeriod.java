package nts.uk.screen.at.app.kha003.b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
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
    private String startDate;
    private String endDate;
    private YearMonth yearMonthStart;
    private YearMonth yearMonthEnd;


    public DatePeriod getDatePeriod() {
        return new DatePeriod(GeneralDate.fromString(startDate, "yyyy/MM/dd"), GeneralDate.fromString(endDate, "yyyy/MM/dd"));
    }

    public YearMonthPeriod getYearMonthPeriod() {
        return new YearMonthPeriod(yearMonthStart, yearMonthEnd);
    }

    public List<GeneralDate> getDateList() {
        val datePeriod = new DatePeriod(GeneralDate.fromString(startDate, "yyyy/MM/dd"), GeneralDate.fromString(endDate, "yyyy/MM/dd"));
        return datePeriod.datesBetween();
    }

    public List<YearMonth> getYearMonthList() {
        val yearMonthPeriod = new YearMonthPeriod(yearMonthStart, yearMonthEnd);
        return yearMonthPeriod.yearMonthsBetween();
    }
}
