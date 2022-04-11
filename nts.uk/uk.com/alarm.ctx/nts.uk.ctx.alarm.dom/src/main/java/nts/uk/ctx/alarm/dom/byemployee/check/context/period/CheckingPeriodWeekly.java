package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;

import java.util.List;

/**
 * 週別データのチェック対象期間
 */
public class CheckingPeriodWeekly {

    public List<AttendanceTimeOfWeeklyKey> calculatePeriod(CheckingPeriodWeekly.Require require, String employeeId) {
        throw new RuntimeException("not implemented");
    }

    public DatePeriod calclateDatePeriod(){
        throw new RuntimeException("not implemented");
    }

    public DatePeriod calclateDatePeriod(AttendanceTimeOfWeeklyKey weeklyKey){
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
