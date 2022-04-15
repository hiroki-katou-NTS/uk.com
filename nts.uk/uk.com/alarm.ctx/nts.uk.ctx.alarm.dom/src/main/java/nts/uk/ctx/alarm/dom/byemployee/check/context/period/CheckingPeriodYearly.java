package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.List;

/**
 * 年間のチェック対象期間
 */
public class CheckingPeriodYearly {
    private ClosureMonth start;

    public List<ClosureMonth> calculatePeriod(AlarmListCheckerByEmployee.Require require, String employeeId) {
        throw new RuntimeException("not implemented");
    }

    /** 該当する期間 **/
    public DatePeriod getDatePeriod(){
        return new DatePeriod(
                start.defaultPeriod().start(),
                start.defaultPeriod().end().addMonths(11));
    }
    /** 社員の履歴系情報を取得する基準日 **/
    public GeneralDate getBaseDate()
    {
        return getDatePeriod().end();
    }

    public DateInfo getDateInfo() {
        return new DateInfo(new YearMonthPeriod(
                start.yearMonth(), start.yearMonth().addMonths(11)));
    }

    public interface Require {

    }
}
