package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 日次データのチェック対象期間
 */
public class CheckingPeriodDaily {

    public DatePeriod calculatePeriod(Require require, String employeeId) {
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
