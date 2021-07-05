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

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class ManHourPeriod {
    private Optional<String> startDate;
    private Optional<String> endDate;
    private Optional<String> yearMonthStart;
    private Optional<String> yearMonthEnd;

    public DatePeriod getDatePeriod() {
        if (!startDate.isPresent() || !endDate.isPresent()) return null;
        return new DatePeriod(GeneralDate.fromString(startDate.get(), "yyyy/MM/dd"), GeneralDate.fromString(endDate.get(), "yyyy/MM/dd"));
    }

    public YearMonthPeriod getYearMonthPeriod() {
        if (!yearMonthStart.isPresent() || !yearMonthEnd.isPresent()) return null;
        return new YearMonthPeriod(
                YearMonth.of(Integer.parseInt(yearMonthStart.get().substring(0, 4)), Integer.parseInt(yearMonthStart.get().substring(6, 7))),
                YearMonth.of(Integer.parseInt(yearMonthEnd.get().substring(0, 4)), Integer.parseInt(yearMonthEnd.get().substring(6, 7)))
        );
    }

    public List<GeneralDate> getDateList() {
        val datePeriod = getDatePeriod();
        if (datePeriod == null) return new ArrayList<>();
        return datePeriod.datesBetween();
    }

    public List<YearMonth> getYearMonthList() {
        val yearMonthPeriod = getYearMonthPeriod();
        if (yearMonthPeriod == null) return new ArrayList<>();
        return yearMonthPeriod.yearMonthsBetween();
    }
}
