package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

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

    public DatePeriod calclateDatePeriod(){
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
