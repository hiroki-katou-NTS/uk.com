package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.List;

/**
 * 月次データのチェック対象期間
 */
public class CheckingPeriodMonthly {


    public List<ClosureMonth> calculatePeriod(Require require, String employeeId) {
        throw new RuntimeException("not implemented");
    }

    /** 締め月に該当する期間 **/
    public static DatePeriod getDatePeriod(ClosureMonth closureMonth){
        // TODO: 本当にこれでいいのか？
        return closureMonth.defaultPeriod();
    }
    /** 社員の履歴系情報を取得する基準日 **/
    public static GeneralDate getBaseDate(ClosureMonth closureMonth) {
        return getDatePeriod(closureMonth).end();
    }

    public interface Require {

    }
}
