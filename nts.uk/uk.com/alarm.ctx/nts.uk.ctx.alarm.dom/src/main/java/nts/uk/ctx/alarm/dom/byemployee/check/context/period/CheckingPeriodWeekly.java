package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;

import java.util.List;

/**
 * 週別データのチェック対象期間
 */
public class CheckingPeriodWeekly {
    private DatePeriod period;

    public DatePeriod getDatePeriod(){
        return this.period;
    }

    public interface Require {

    }
}
