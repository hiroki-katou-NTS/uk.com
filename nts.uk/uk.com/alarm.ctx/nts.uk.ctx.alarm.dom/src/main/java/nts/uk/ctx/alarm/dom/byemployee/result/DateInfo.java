package nts.uk.ctx.alarm.dom.byemployee.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.com.time.closure.ClosureMonth;

/**
 * 日付情報
 */
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class DateInfo {

    @Getter
    String formatted;

    public DateInfo(GeneralDate date) {
        formatted = format(date);
    }

    public DateInfo(YearMonth yearMonth) {
        formatted = format(yearMonth);
    }
    
    public DateInfo(Year year) {
        formatted = year.toString();
    }

    public DateInfo(DatePeriod period) {
        formatted = format(period.start()) + " ～ " + format(period.end());
    }

    public DateInfo(YearMonthPeriod period) {
        formatted = format(period.start()) + " ～ " + format(period.end());
    }

    public DateInfo(ClosureMonth closureMonth) {
        // どのように　アラームリスト上でどう出すかは後回し
        formatted = closureMonth.getYearMonth().toString() + closureMonth.closureDate().getClosureDay().toString() + "締め";
    }

    public GeneralDate toGeneralDate() {
        return GeneralDate.fromString(formatted, "yyyy/M/d");
    }

    public static DateInfo none(){
        return new DateInfo("");
    }

    private static String format(GeneralDate date) {
        return date.toString("yyyy/M/d");
    }

    private static String format(YearMonth yearMonth) {
        return yearMonth.year() + "/" + yearMonth.month();
    }

    @Override
    public String toString() {
        return formatted;
    }
}
